package com.dpk.wgj.config.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/{role}/{userId}/{orderId}",configurator=MyEndpointConfigure.class)
@Component
public class DriverAndPassWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;

    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    private Session session;

    @Autowired
    private DriverInfoService driverInfoService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private OrderInfoService orderInfoService;


    // driver  passenger
    private String role;

    private Integer userId;

    private Integer orderId;



    @OnOpen
    public void onOpen(Session session,@PathParam(value="role")String role
            , @PathParam(value="userId")String userId, @PathParam(value="orderId")String orderId) throws IOException {
        this.session = session;

        this.role = role;
        this.userId = Integer.parseInt(userId);
        if(role.equals("passenger")){this.orderId = Integer.parseInt(orderId);}
        //key    driver,7     passenger,8   的形式
        String key = role+","+userId;
        System.out.println(session.getId()+","+key+"链接进来了");
        sessionPool.put(key, session);
        for (String k : sessionPool.keySet()) {
            if(!sessionPool.get(k).equals(role+","+userId)) { //send to others only.
//                sendMessage("someone just joined in.",k);
            }
        }
        logger.info("new connection...current online count: {}", getOnlineCount());
    }

    @OnClose
    public void onClose() throws IOException{

        for (String k : sessionPool.keySet()) {
            System.out.println("要关闭的链接："+k);
//            sessionPool.get(k).sendMessage("someone just closed a connection.", k);
            sessionPool.remove(role+","+userId);

        }
        logger.info("one connection closed...current online count: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,@PathParam(value="userId")String userId) throws IOException {
        try {
            this.userId = Integer.parseInt(userId);
            String[] msgArr = message.split(",");//message为'role,message'的形式  例如driver,toWait
            role = msgArr[0];

            System.out.println("后台ws收到的信息:"+message+",用户id:"+this.userId);
            DriverInfo driverInfo = new DriverInfo();
            Passenger passenger = new Passenger();
            OrderInfoTableMessage tableMessage = new OrderInfoTableMessage();
            List<OrderInfo> orderInfos = null;
            if(role.equals("driver")){

                driverInfo = driverInfoService.getDriverInfoByDriverId(this.userId);
                OrderInfo order = new OrderInfo();
                switch (msgArr[1]){
                    case "toWait":
                        /*1. 遍历session 查询有无正在请求司机的乘客 向客户端发送信息*/
                        for (String key : sessionPool.keySet()) {

                            String[] arr = key.split(",");
                            if(arr[0].equals("passenger")){
                                int passId = Integer.parseInt(arr[1]);
                                passenger = passengerService.getPassengerByPassengerId(passId);
                                if(passenger.getPassengerStatus() == 0) {//呼车的状态

//                                    sendMessage(1,"成功接单 请前往乘客点",passenger, "driver,"+userId);

                                    // 查询出乘客id=passId且刚下的订单
                                    tableMessage = new OrderInfoTableMessage();
                                    tableMessage.setLimit(1);tableMessage.setOffset(0);tableMessage.setOrder("desc");tableMessage.setSort("order_id");
                                    OrderInfo orderInfo1 = new OrderInfo();
                                    orderInfo1.setOrderStatus(0);
                                    orderInfo1.setPassengerId(passId);
                                    tableMessage.setOrderInfo(orderInfo1);
                                    orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);

                                    if(orderInfos!=null){
                                        System.out.println("查询到乘客刚下的订单："+orderInfos.get(0).getOrderId());
                                        order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                                    }
                                }
                            }

                        }

                        /*2.给乘客发送信息  给司机发送信息*/
                        for (String k : sessionPool.keySet()) {
                            String[] a = k.split(",");
                            if(a[0].equals("passenger")){

                                if(Integer.parseInt(a[1]) == order.getPassengerId()){

                                    //将订单状态改为接单状态
                                    order.setOrderId(orderId);
                                    order.setDriverId(this.userId);
                                    order.setOrderStatus(1);
                                    orderInfoService.updateOrderInfoByOrderId(order);

                                    Passenger pass = passengerService.getPassengerByPassengerId(order.getPassengerId());
                                    //将乘客状态改为服务中
                                    pass.setPassengerStatus(1);
                                    passengerService.updatePassengerStatus(pass);

                                    JSONObject o = new JSONObject();
                                    order = orderInfoService.getOrderInfoByOrderId(orderId);
                                    o.put("order",order);o.put("passenger",pass);
                                    sendMessage(1,"h成功拿到订单，请前往乘客 点",o, "driver,"+userId);

                                }
                            } else if(a[0].equals("driver")){

                                if(Integer.parseInt(a[1]) == order.getDriverId()) {
                                    //将司机状态改为接客前
                                    driverInfo.setFlag(1);
                                    driverInfoService.updateApiDriverInfoByDriverId(driverInfo);

                                    CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                                    CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);

                                    sendMessage(1,"已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger,"+order.getPassengerId());

                                }
                            }
                        }
                        break;

                    case "arriveToPassenger": //到乘客上车点（接到乘客）
                        System.out.println("到乘客上车点（接到乘客）");
                        // 查询出司机id=userId且status=1(已经接单)
                        tableMessage = new OrderInfoTableMessage();
                        tableMessage.setLimit(1);tableMessage.setOffset(0);tableMessage.setOrder("desc");tableMessage.setSort("order_id");
                        OrderInfo orderInfo = new OrderInfo();
                        orderInfo.setOrderStatus(1);
                        orderInfo.setDriverId(this.userId);
                        tableMessage.setOrderInfo(orderInfo);
                        orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);

                        if(orderInfos!=null){
                            System.out.println("查询到司机已经接到的订单："+orderInfos.get(0).getOrderId());
                            order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                        }
                        orderInfo = new OrderInfo();
                        orderInfo.setOrderId(order.getOrderId());
                        orderInfo.setOrderStatus(2);//设置订单为派送状态
                        orderInfoService.updateOrderInfoByOrderId(orderInfo);

                        //将司机状态改为服务中
                        driverInfo.setFlag(2);
                        driverInfoService.updateApiDriverInfoByDriverId(driverInfo);
                        sendMessage(1,"司机已经接到了我", null, "passenger,"+order.getPassengerId());

                        break;
                    case "arriveToDest": //司机端按下到达目的地按钮
                        System.out.println("到达目的地");
                        // 查询出司机id=userId且status=2(派送中)
                        tableMessage = new OrderInfoTableMessage();
                        tableMessage.setLimit(1);tableMessage.setOffset(0);tableMessage.setOrder("desc");tableMessage.setSort("order_id");
                        OrderInfo orderInfo2 = new OrderInfo();
                        orderInfo2.setOrderStatus(2);
                        orderInfo2.setDriverId(this.userId);
                        tableMessage.setOrderInfo(orderInfo2);
                        orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);

                        if(orderInfos!=null){
                            System.out.println("查询到司机已经接到的订单："+orderInfos.get(0).getOrderId());
                            order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                        }
                        orderInfo2 = new OrderInfo();
                        orderInfo2.setOrderId(order.getOrderId());
                        orderInfo2.setOrderStatus(3);//设置订单为派送状态
                        orderInfoService.updateOrderInfoByOrderId(orderInfo2);

                        //将乘客状态改为服务后
                        Passenger p = new Passenger();
                        p.setPassengerId(order.getPassengerId());
                        p.setPassengerStatus(2);
                        passengerService.updatePassengerStatus(p);
                        sendMessage(2,"已经到达目的地，结束订单", null, "passenger,"+order.getPassengerId());
                    case "changeDriver"://司机端按下一键改派按钮
                        // TODO: 2018/7/14 还没做
                        /*1.降低服务质量星级*/

                        /*2.向乘客发送消息更换司机*/

                        /*3.插入日志记录*/

                        break;
                }

            }else if(role.equals("passenger")){
                OrderInfo order = new OrderInfo();
                switch (msgArr[1]){
                    case "arriveDest":
                        break;
                    case "toWait":
//                        for (String k: sessionPool.keySet()){
//                            System.out.println("现在连接池中还有:"+k+",sessionId:"+sessionPool.get(k).getId());
//                        }
                        System.out.println("订单Id："+","+orderId);
                        List<DriverInfo> driverInfoList = driverInfoService.getDriverInfoByDriverStatus(1);
                        int driverId; //要接单的司机id
                        /*1.查询出所有可以接单的乘客列表  */
                        for (DriverInfo d: driverInfoList){
                            // TODO: 2018/7/14    根据乘客下单位置 派送距离最近的司机的匹配算法还没写 

                            // 可接单的   选择最高的司机星级
                            if(d.getFlag()==0){
                                //这里积分匹配目前是定死的状态
                                if (d.getDriverLevelStar() > 91){
                                    driverId = d.getDriverId();

                                    for (String key : sessionPool.keySet()) {
                                        String[] arr = key.split(",");
                                        if (arr[0].equals("driver")){
                                            if (Integer.parseInt(arr[1]) == driverId) {

                                                /*将订单状态改为接单状态*/
                                                order.setOrderStatus(1);
                                                order.setOrderId(orderId);
                                                order.setDriverId(driverId);
                                                orderInfoService.updateOrderInfoByOrderId(order);

                                                Passenger pass = passengerService.getPassengerByPassengerId((this.userId));
                                                JSONObject o = new JSONObject();
                                                order = orderInfoService.getOrderInfoByOrderId(orderId);
                                                o.put("order",order);o.put("passenger",pass);
                                                sendMessage(1, "j成功拿到订单，请前往乘客点", o, "driver," + driverId);

                                                CarInfo carInfo = carInfoService.getCarInfoByCarId(d.getCarId());
                                                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, d);
                                                sendMessage(1, "已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger," + this.userId);
                                            }
                                        }
//                                System.out.println("key= "+ key + " and value= " + sessionPool.get(key));
                                    }

                                }
                            }
                        }
                        break;
                    case "cancelOrder":
                        tableMessage = new OrderInfoTableMessage();
                        tableMessage.setLimit(1);tableMessage.setOffset(0);tableMessage.setOrder("desc");tableMessage.setSort("order_id");
                        OrderInfo orderInfo3 = new OrderInfo();
                        orderInfo3.setOrderStatus(1);
                        orderInfo3.setDriverId(this.userId);
                        tableMessage.setOrderInfo(orderInfo3);
                        orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);

                        if(orderInfos!=null){
                            System.out.println("查询到乘客要进行取消的订单id："+orderInfos.get(0).getOrderId());
                            order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                        }

                        orderInfo3 = new OrderInfo();
                        orderInfo3.setOrderId(order.getOrderId());
                        orderInfo3.setOrderStatus(4);//设置订单为取消状态
                        orderInfoService.updateOrderInfoByOrderId(orderInfo3);

                        //将乘客状态改为服务后
                        passenger = passengerService.getPassengerByPassengerId(orderInfo3.getPassengerId());
                        passenger.setPassengerStatus(2);
                        passengerService.updatePassengerStatus(passenger);

                        sendMessage(3,"乘客取消了订单", null, "passenger,"+order.getPassengerId());

                        //将司机状态改为接单前
                        driverInfo = driverInfoService.getDriverInfoByDriverId(order.getDriverId());
                        driverInfo.setFlag(0);
                        driverInfoService.updateApiDriverInfoByDriverId(driverInfo);

                        sendMessage(3,"乘客取消了订单", null, "driver,"+order.getDriverId());

                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 点对点推送的通知方法
     * @param status
     * @param msg
     * @param result
     * @param sendObj
     * @throws IOException
     */
    public void sendMessage(int status, String msg, Object result, String sendObj) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",status);
        jsonObject.put("msg", msg);
        jsonObject.put("result",result);
        String sendMsg = JSON.toJSONString(jsonObject);

        System.out.println("发送的消息:"+sendMsg+"发送对象："+sendObj+","+sessionPool.get(sendObj));
        Session s = sessionPool.get(sendObj);
        if(s!=null){
            try {
                s.getBasicRemote().sendText(sendMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static synchronized int getOnlineCount(){
        return DriverAndPassWebSocket.onlineCount;
    }

    public static synchronized void incrOnlineCount(){
        DriverAndPassWebSocket.onlineCount++;
    }

    public static synchronized void decOnlineCount(){
        DriverAndPassWebSocket.onlineCount--;
    }

}

