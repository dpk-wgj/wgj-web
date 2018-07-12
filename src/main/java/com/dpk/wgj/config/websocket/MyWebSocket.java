package com.dpk.wgj.config.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpk.wgj.bean.*;
import com.dpk.wgj.bean.DTO.CarInfoDTO;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.tableInfo.OrderInfoTableMessage;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;
import com.fasterxml.jackson.core.JsonParser;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private String userId;

    private Integer orderId;



    @OnOpen
    public void onOpen(Session session,@PathParam(value="role")String role
            , @PathParam(value="userId")String userId, @PathParam(value="orderId")String orderId) throws IOException {
        this.session = session;

        this.role = role;
        this.userId = userId;
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
            System.out.println("后台ws收到的信息:"+message);

            if(role.equals("driver")){

                /*1. 遍历session 查询有无正在请求司机的乘客 向客户端发送信息*/
                for (String key : sessionPool.keySet()) {

                    String[] arr = key.split(",");
                    if(arr[0].equals("passenger")){
                        int passId = Integer.parseInt(arr[1]);
                        Passenger passenger = passengerService.getPassengerByPassengerId(passId);
                        if(passenger.getPassengerStatus() == 0) {//呼车的状态

                            sendMessage(1,"成功接单 请前往乘客点",passenger, "driver,"+userId);

                            // 查询出乘客id=passId且刚下的订单
                            OrderInfoTableMessage tableMessage = new OrderInfoTableMessage();
                            OrderInfo orderInfo = new OrderInfo();
                            Passenger p = new Passenger();
                            CarInfo carInfo = new CarInfo();
                            orderInfo.setOrderStatus(0);
                            p.setPassengerId(passId);
                            tableMessage.setOrderInfo(orderInfo);
                            tableMessage.setPassenger(p);
                            tableMessage.setCarInfo(carInfo);
                            tableMessage.getDriverInfo().setDriverName("%%");
                            tableMessage.getPassenger().setPassengerPhoneNumber("%%");
                            tableMessage.getCarInfo().setCarNumber("%%");

                            List<OrderInfo> orderInfos = orderInfoService.findOrderInfoByMultiCondition(tableMessage);

                            for (OrderInfo o: orderInfos){
                                System.out.println("查询到乘客刚下的订单："+o.getOrderId());
                            }
                            // 将订单状态改为接单状态
//                            OrderInfo orderInfo = new OrderInfo();
//                            orderInfo.setOrderStatus(1);
//                            orderInfo.setOrderId(orderId);
//                            System.out.println("接单状态："+orderId);
//                            orderInfoService.updateOrderInfoByOrderId(orderInfo);
//
//                            CarInfo carInfo = carInfoService.getCarInfoByCarId(d.getCarId());
//                            CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, d);
//                            sendMessage(1,"已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger,"+userId);

                        }
                    }

                }
//                for(MyWebSocket item : webSocketSet){
//                    DriverInfo driverInfo = (DriverInfo) JSON.parseObject(message, DriverInfo.class);
//
//                    int upApiStatus = 0;
//                    try {
//                        upApiStatus = driverInfoApiService.updateApiDriverInfoByDriverId(driverInfo);
//                        if (upApiStatus == 1) {
//                            item.sendMessage("更新司机信息成功", sessionIds.get(session.getId()));
//                            return;
//                        }
//                        item.sendMessage("更新司机信息失败",sessionIds.get(session.getId()));
//                        return;
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        item.sendMessage("更新司机信息失败",sessionIds.get(session.getId()));
//                        return;
//                    }
//                }
            }else if(role.equals("passenger")){

                for (String k: sessionPool.keySet()){
                    System.out.println("现在连接池中海油:"+k+",sessionId:"+sessionPool.get(k).getId());
                }
                System.out.println("操作："+","+orderId);
//                switch ((orderId)){
//                    case 0: //新增订单

//                        break;
//                    case 1:
//                        break;
//                    default:
//                        break;
//                }


                List<DriverInfo> driverInfoList = driverInfoApiService.getDriverInfoByDriverStatus(1);
                int driverId; //要接单的司机id
                for (DriverInfo d: driverInfoList){
                    /*可接单的   选择最高的司机星级*/
                    if(d.getFlag()==0){
                        if (d.getDriverLevelStar() > 91){
                            driverId = d.getDriverId();

                            for (String key : sessionPool.keySet()) {
                                String[] arr = key.split(",");
                                if(Integer.parseInt(arr[1]) == driverId){
                                    Passenger passenger = passengerService.getPassengerByPassengerId(Integer.parseInt(userId));

                                    sendMessage(1,"成功接单 请前往乘客点",passenger, "driver,"+driverId);

                                    /*将订单状态改为接单状态*/
                                    OrderInfo orderInfo = new OrderInfo();
                                    orderInfo.setOrderStatus(1);
                                    orderInfo.setOrderId(orderId);
                                    System.out.println("接单状态："+orderId);
                                    orderInfoService.updateOrderInfoByOrderId(orderInfo);

                                    CarInfo carInfo = carInfoService.getCarInfoByCarId(d.getCarId());
                                    CarInfoDTO carInfoDTO = new CarInfoDTO(carInfo, d);
                                    sendMessage(1,"已经有司机接单,请在原地等待司机接送", carInfoDTO, "passenger,"+userId);


                                }
//                                System.out.println("key= "+ key + " and value= " + sessionPool.get(key));
                            }

                        }
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    public void sendMessage(String message, String sendObj) throws IOException {
//
//
//        System.out.println("发送的消息:"+message+"发送对象："+sendObj+","+sessionPool.get(sendObj));
//        Session s = sessionPool.get(sendObj);
//        if(s!=null){
//            try {
//                s.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

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
}

