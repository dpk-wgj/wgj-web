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
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/{role}/{userId}/{orderId}",configurator=MyEndpointConfigure.class)
@Component
public class MyWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

//    private static Map<String,Session> sessionPool = new HashMap<String,Session>();
//    private static ConcurrentHashMap<String,String> sessionIds = new ConcurrentHashMap<String,String>();

    private Session session;

    @Autowired
    private DriverInfoService driverInfoApiService;

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
        //key    driver,7     passenger,3   的形式
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
    public void onMessage(String message) throws IOException {
        try {
            String[] msgArr = message.split(",");//message为'role,message'的形式  例如driver,toWait
            role = msgArr[0];

            System.out.println("后台ws收到的信息:"+message);
            DriverInfo driverInfo = new DriverInfo();
            Passenger passenger = new Passenger();
            OrderInfoTableMessage tableMessage = new OrderInfoTableMessage();
            List<OrderInfo> orderInfos = null;
            if(role.equals("driver")){

                driverInfo = driverInfoApiService.getDriverInfoByDriverId(userId);
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

                                    sendMessage(1,"成功接单 请前往乘客点",passenger, "driver,"+userId);

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

                                    /*将订单状态改为接单状态*/
                                    order.setOrderId(orderId);
                                    order.setDriverId(userId);
                                    order.setOrderStatus(1);
                                    orderInfoService.updateOrderInfoByOrderId(order);

                                    Passenger pass = passengerService.getPassengerByPassengerId(order.getPassengerId());
                                    sendMessage(1,"成功接单 请前往乘客点",pass, "driver,"+userId);

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
                        orderInfo.setDriverId(userId);
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

                        sendMessage(1,"司机已经接到了我", null, "passenger,"+order.getPassengerId());

                        break;
                    case "arriveToDest": //司机端按下到达目的地按钮
                        System.out.println("到达目的地");
                        // 查询出司机id=userId且status=2(派送中)
                        tableMessage = new OrderInfoTableMessage();
                        tableMessage.setLimit(1);tableMessage.setOffset(0);tableMessage.setOrder("desc");tableMessage.setSort("order_id");
                        OrderInfo orderInfo2 = new OrderInfo();
                        orderInfo2.setOrderStatus(2);
                        orderInfo2.setDriverId(userId);
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

                        sendMessage(2,"已经到达目的地，结束订单", null, "passenger,"+order.getPassengerId());

                        break;
                }

            }else if(role.equals("passenger")){
                switch (msgArr[1]){
                    case "arriveDest":
                        break;
                    case "toWait":

                        OrderInfo order = new OrderInfo();

                        for (String k: sessionPool.keySet()){
                            System.out.println("现在连接池中海油:"+k+",sessionId:"+sessionPool.get(k).getId());
                        }
                        System.out.println("订单Id："+","+orderId);

                        List<DriverInfo> driverInfoList = driverInfoApiService.getDriverInfoByDriverStatus(1);
                        int driverId; //要接单的司机id
                        for (DriverInfo d: driverInfoList){
                            /*可接单的   选择最高的司机星级*/
                            if(d.getFlag()==0){
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

                                                Passenger pass = passengerService.getPassengerByPassengerId((userId));
                                                sendMessage(1, "成功接单 请前往乘客点", pass, "driver," + driverId);

                                                CarInfo carInfo = carInfoService.getCarInfoByCarId(d.getCarId());
                                                CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, d);
                                                sendMessage(1, "已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger," + userId);

                                            }
                                        }
//                                System.out.println("key= "+ key + " and value= " + sessionPool.get(key));
                                    }

                                }
                            }
                        }

                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


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
        return MyWebSocket.onlineCount;
    }

    public static synchronized void incrOnlineCount(){
        MyWebSocket.onlineCount++;
    }

    public static synchronized void decOnlineCount(){
        MyWebSocket.onlineCount--;
    }


    public void updateOrder(int orderId, int status){
        try {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderStatus(status);
            orderInfo.setOrderId(orderId);
            System.out.println("接单状态："+orderId);
            orderInfoService.updateOrderInfoByOrderId(orderInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

