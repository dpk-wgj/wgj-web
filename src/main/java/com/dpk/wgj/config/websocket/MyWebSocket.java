package com.dpk.wgj.config.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpk.wgj.bean.DTO.UserDTO;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.OrderInfo;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.dpk.wgj.service.OrderInfoService;
import com.dpk.wgj.service.PassengerService;
import com.fasterxml.jackson.core.JsonParser;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import org.elasticsearch.common.collect.CopyOnWriteHashMap;
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
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/{role}/{userId}/{operateId}",configurator=MyEndpointConfigure.class)
@Component
public class MyWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static HashMap<String, MyWebSocket> sessionPool = new HashMap<>();

//    private static Map<String,Session> sessionPool = new HashMap<String,Session>();
    private static Map<String,String> sessionIds = new HashMap<String,String>();

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

    private String operateId;

    @OnOpen
    public void onOpen(Session session,@PathParam(value="role")String role
            , @PathParam(value="userId")String userId, @PathParam(value="operateId")String operateId) throws IOException {
        this.session = session;

        this.role = role;
        this.userId = userId;
        this.operateId = operateId;
        //key    driver,7     passenger,3   的形式
        String key = role+","+userId;
        sessionPool.put(key, this);
        sessionIds.put(this.session.getId(), key);
        for (String k : sessionPool.keySet()) {
            if(!sessionPool.get(k).equals(this)) { //send to others only.
                sessionPool.get(k).sendMessage("someone just joined in.",sessionIds.get(session.getId()));
            }
        }
        logger.info("new connection...current online count: {}", getOnlineCount());
    }

    @OnClose
    public void onClose() throws IOException{

        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());

        for (String k : sessionPool.keySet()) {
            sessionPool.get(k).sendMessage("someone just closed a connection.", k);
        }

        logger.info("one connection closed...current online count: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        try {
            System.out.println("后台ws收到的信息:"+message);
            JSONObject driverMsgJson = new JSONObject();
            JSONObject passMsgJson = new JSONObject();

            // broadcast received message
            if(role.equals("driver")){

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
                System.out.println("操作："+","+operateId);
                switch ((operateId)){
                    case "0": //新增订单

//                        break;
//                    case 1:
//                        break;
//                    default:
//                        break;
                }


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
                                    driverMsgJson.put("status",1);
                                    driverMsgJson.put("msg","您要开始接单了hhhhhhhhhhhhh");
                                    driverMsgJson.put("passengerInfo",passenger);
                                    String driverMsg = JSON.toJSONString(driverMsgJson);
                                    sendMessage(driverMsg, "driver,"+driverId);


                                    passMsgJson.put("status",1);
                                    passMsgJson.put("msg","已经有司机接单,请在原地等待司机接送");
                                    passMsgJson.put("driverInfo",d);

                                    String passMsg = JSON.toJSONString(passMsgJson);

                                    sendMessage(passMsg, "passenger,"+userId);

                                }
//                                System.out.println("key= "+ key + " and value= " + sessionPool.get(key));
                            }

                        }
                    }
                }
//            for (String key : sessionPool.keySet()) {
//                System.out.println("key= "+ key + " and value= " + sessionPool.get(key));
//            }
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

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param message 发送的消息
     * @param sendObj  要发送的对象
     * @throws IOException
     */
    public void sendMessage(String message, String sendObj) throws IOException {
        System.out.println("发送的消息:"+message+"发送对象："+sendObj);
        Session s = sessionPool.get(sendObj).session;
        if(s!=null){
            try {
                s.getBasicRemote().sendText(message);
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

