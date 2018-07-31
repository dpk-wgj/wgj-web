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

import com.sun.org.apache.xpath.internal.operations.Or;
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

    private static final double minDistance = 0.035;

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
    public void onClose(@PathParam(value="userId")String userId) throws IOException{

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
            String[] msgArr = message.split(",");//message为'role,message'的形式  例如driver,toWait
            role = msgArr[0];

            System.out.println("后台ws收到的信息:"+message+",用户id:"+userId);
            DriverInfo driverInfo = new DriverInfo();
            Passenger passenger = new Passenger();
            OrderInfoTableMessage tableMessage = new OrderInfoTableMessage();
            List<OrderInfo> orderInfos = null;
            if(role.equals("driver")){
                this.userId = Integer.parseInt(userId);
                driverInfo = driverInfoService.getDriverInfoByDriverId(this.userId);
                OrderInfo order = null;
                switch (msgArr[1]){
                    case "toWait":
                        String[] driverLoc = driverInfo.getDriverLocation().split(",");

                        //存在距离比较的map
                        Map<Passenger, double[]> compareMap = new HashMap<>();

                        // 查询出所有在请求的订单
                        orderInfos = orderInfoService.findOrderInfoByOrderStatus(0);

                        if (orderInfos != null) {

                            for (OrderInfo o: orderInfos){
                                Passenger p = passengerService.getPassengerByPassengerId(o.getPassengerId());
                                String[] oLoc = o.getStartLocation().split(",");
                                String[] passLoc = {oLoc[1], oLoc[2]};

                                // TODO: 2018/7/28 司机与乘客距离匹配 还需要优化
                                double[] disD = calMinDistace(driverLoc, passLoc);
                                if (disD[0] < minDistance && disD[1] < minDistance) {
                                    compareMap.put(p, disD);
                                    order = o;
                                    break;
                                }
                            }
                        }
                        if (compareMap.size() != 0) {
                            order.setDriverId(driverInfo.getDriverId());
                            //将订单状态改为接单状态
                            order.setOrderStatus(1);
                            orderInfoService.updateOrderInfoByOrderId(order);
                            //将司机状态改为接客前
                            driverInfo.setFlag(1);
                            driverInfoService.updateDriverInfoByDriverId(driverInfo);
                            Passenger pass = passengerService.getPassengerByPassengerId(order.getPassengerId());
                            //将乘客状态改为服务中
                            pass.setPassengerStatus(1);
                            passengerService.updatePassengerStatus(pass);

                            /*向乘客和司机发送信息*/
                            for (String k : sessionPool.keySet()) {
                                String[] a = k.split(",");
                                if (a[0].equals("driver")) {
                                    if (Integer.parseInt(a[1]) == order.getDriverId()) {
                                        JSONObject o = new JSONObject();
                                        order = orderInfoService.getOrderInfoByOrderId(order.getOrderId());
                                        o.put("order", order);o.put("passenger", pass);
                                        sendMessage(1, "h成功拿到订单，请前往乘客 点", o, "driver,"+order.getDriverId());
                                    }

                                } else if(a[0].equals("passenger")) {

                                    if(Integer.parseInt(a[1]) == order.getPassengerId()) {
                                        CarInfo carInfo = carInfoService.getCarInfoByCarId(driverInfo.getCarId());
                                        CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, driverInfo);
                                        sendMessage(1,"已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger,"+order.getPassengerId());
                                    }

                                }

                            }
                        }

                        break;

                    case "arriveToPassenger": //到乘客上车点（接到乘客）
                        System.out.println("到乘客上车点（接到乘客）");
                        // 查询出司机id=userId且status=1(已经接单)
                        OrderInfo orderInfo = new OrderInfo();
                        orderInfo.setOrderStatus(1);
                        orderInfo.setDriverId(this.userId);
                        orderInfos = orderInfoService.findOrderListByOrderInfo(orderInfo);

                        if (orderInfos != null && orderInfos.size() != 0) {
                            System.out.println("查询到司机已经接到的订单："+orderInfos.get(0).getOrderId());
                            order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                            DriverInfo d = new DriverInfo();
                            d.setDriverId(order.getDriverId());
                            //将司机状态改为服务中
                            d.setFlag(2);
                            driverInfoService.updateDriverInfoByDriverId(d);
                            sendMessage(1,"司机已经接到了我", null, "passenger,"+order.getPassengerId());
                        }

                        break;
                    case "arriveToDest": //司机端按下到达目的地按钮
                        System.out.println("到达目的地");
                        // 查询出司机id=userId且status=2(派送中)
                        OrderInfo orderInfo2 = new OrderInfo();
                        orderInfo2.setOrderStatus(3);
                        orderInfo2.setDriverId(this.userId);
                        orderInfos = orderInfoService.findOrderListByOrderInfo(orderInfo2);

                        if (orderInfos != null && orderInfos.size() != 0 ) {
                            System.out.println("查询到司机已经接到的订单："+orderInfos.get(0).getOrderId());
                            order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                        }

                        sendMessage(2,"已经到达目的地，结束订单", null, "passenger,"+order.getPassengerId());

                        //将司机和乘客从池中删除
                        removeFromPool(sessionPool, 2, order.getDriverId(), order.getPassengerId());

                        break;
                    case "changeDriver"://司机端按下一键改派按钮
                        OrderInfo orderInfo1 = orderInfoService.getOrderInfoByOrderId(orderId);

                        sendMessage(3,"司机取消本订单 即将为您自动分配其他司机", null, "passenger,"+orderInfo1.getPassengerId());

                        sendMessage(3,"取消本订单 即将返回首页", null, "driver,"+orderInfo1.getDriverId());

                        //将司机从池中删除
                        removeFromPool(sessionPool, 0, orderInfo1.getDriverId());
                        /*3.插入日志记录*/

                        break;
                }

            } else if (role.equals("passenger")) {
                this.userId = Integer.parseInt(userId);
                OrderInfo order = orderInfoService.getOrderInfoByOrderId(orderId);
                passenger = passengerService.getPassengerByPassengerId(this.userId);
                switch (msgArr[1]) {
                    case "arriveDest":
                        break;
                    case "toWait":
                        /*要匹配距离司机最近的乘客*/
                        String[] oLoc = order.getStartLocation().split(",");
                        String[] passLoc = {oLoc[1], oLoc[2]};

                        //存在距离比较的map
                        Map<DriverInfo, double[]> compareMap = new HashMap<>();

                        List<DriverInfo> driverInfoList = driverInfoService.getDriverInfoByDriverStatus(1);
                        /*1.查询出所有可以接单的司机列表  */
                        if (driverInfoList != null) {
                            for (DriverInfo d: driverInfoList) {

                                // 可接单的   选择最高的司机星级
                                if (d.getFlag() == 0) {
                                    //这里积分匹配目前是定死的状态
                                    if (d.getDriverLevelStar() > 91) {
                                        for (String key : sessionPool.keySet()) {
                                            String[] arr = key.split(",");
                                            String[] driverLoc = d.getDriverLocation().split(",");
                                            if (arr[0].equals("driver")){
                                                if (Integer.parseInt(arr[1]) == d.getDriverId()) {
                                                    // TODO: 2018/7/28 司机与乘客距离匹配 还需要优化
                                                    double[] disD = calMinDistace(driverLoc, passLoc);
                                                    if (disD[0] < minDistance && disD[1] < minDistance) {
                                                        compareMap.put(d, disD);
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }

                        if (compareMap.size() != 0){

                            DriverInfo targetDriver = null;
                            for (DriverInfo d: compareMap.keySet()) {
                                targetDriver = d;
                            }
                            /*将订单状态改为接单状态*/
                            order.setOrderStatus(1);
                            order.setDriverId(targetDriver.getDriverId());
                            orderInfoService.updateOrderInfoByOrderId(order);

                            Passenger pass = passengerService.getPassengerByPassengerId(passenger.getPassengerId());
                            //将乘客改为服务中
                            pass.setPassengerStatus(1);
                            passengerService.updatePassengerStatus(pass);

                            //将司机状态改为接客前
                            targetDriver.setFlag(1);
                            driverInfoService.updateDriverInfoByDriverId(targetDriver);

                            JSONObject o = new JSONObject();
                            o.put("order",order);o.put("passenger",pass);
                            sendMessage(1, "j成功拿到订单，请前往乘客点", o, "driver," + targetDriver.getDriverId());

                            CarInfo carInfo = carInfoService.getCarInfoByCarId(targetDriver.getCarId());
                            CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, targetDriver);
                            sendMessage(1, "已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger," + userId);

                        }

                        break;
                    case "cancelOrder":
                        OrderInfo orderInfo3 = new OrderInfo();
                        orderInfo3.setOrderStatus(1);
//                        orderInfo3.setDriverId(this.userId);
                        orderInfo3.setPassengerId(this.userId);//this.useId是乘客的Id
                        orderInfos = orderInfoService.findOrderListByOrderInfo(orderInfo3);

                        if (orderInfos != null && orderInfos.size() != 0) {
                            System.out.println("查询到乘客要进行取消的订单id："+orderInfos.get(0).getOrderId());
                            order = orderInfoService.getOrderInfoByOrderId(orderInfos.get(0).getOrderId());
                        }
                        if (order != null) {
                            sendMessage(4,"乘客取消了订单", null, "passenger,"+order.getPassengerId());
                            sendMessage(4,"乘客取消了订单", null, "driver,"+order.getDriverId());
                            //将乘客从池中删除
                            removeFromPool(sessionPool, 1, order.getPassengerId());
                        }

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

    /**
     * 将司机或乘客从池中删除
     * flag=0 删除司机   flag=1 删除乘客    flag=2 both
     * @return
     */
    public void removeFromPool(ConcurrentHashMap<String, Session> sessionPool, int flag, int... ids) {
        for (String key : sessionPool.keySet()) {

            switch (flag) {
                case 0:
                    if (key.equals("driver," + ids[0])) { sessionPool.remove(key); }
                    break;
                case 1:
                    if (key.equals("passenger," + ids[0])) {sessionPool.remove(key);}
                    break;
                case 2:
                    if (key.equals("driver," + ids[0]) || key.equals("passenger"+ ids[1])) { sessionPool.remove(key); }
                    break;
            }

        }
    }

    /**
     * 计算最小距离
     * @return
     */
    public double[] calMinDistace(String[] driverLoc, String[] passLoc){
        double[] r = {999999, 999999};
        double b = Math.abs(Double.parseDouble(driverLoc[0]) - Double.parseDouble(passLoc[0]));
        double c = Math.abs(Double.parseDouble(driverLoc[1]) - Double.parseDouble(passLoc[1]));
        r[0] = b;
        r[1] = c;
        return r;
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

