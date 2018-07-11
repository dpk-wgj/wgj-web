package com.dpk.wgj.config.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Message;
import com.dpk.wgj.service.CarInfoService;
import com.dpk.wgj.service.DriverInfoService;
import com.fasterxml.jackson.core.JsonParser;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/{role}/{userId}",configurator=MyEndpointConfigure.class)
@Component
public class MyWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private static Map<String,Session> sessionPool = new HashMap<String,Session>();
    private static Map<String,String> sessionIds = new HashMap<String,String>();

    private Session session;

    @Autowired
    private DriverInfoService driverInfoApiService;

    @Autowired
    private CarInfoService carInfoService;
    // driver  passenger
    private String role;

    private String userId;


    @OnOpen
    public void onOpen(Session session,@PathParam(value="role")String role
            , @PathParam(value="userId")String userId) throws IOException {
        this.session = session;
        webSocketSet.add(this);

        this.role = role;
        this.userId = userId;
        //key    driver,7     passenger,3   的形式
        String key = role+","+userId;
        sessionPool.put(key, session);
        sessionIds.put(session.getId(), role+","+userId);

        for(MyWebSocket item : webSocketSet){
            if(!item.equals(this)) { //send to others only.
                item.sendMessage("someone just joined in.",sessionIds.get(session.getId()));
            }
        }
        logger.info("new connection...current online count: {}", getOnlineCount());
    }

    @OnClose
    public void onClose() throws IOException{
        webSocketSet.remove(this);

        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());

        for(MyWebSocket item : webSocketSet){
            item.sendMessage("someone just closed a connection.",sessionIds.get(session.getId()));
        }

        logger.info("one connection closed...current online count: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        try {
            System.out.println("后台ws收到的信息:"+message);
            JSONObject msgJson = new JSONObject();
            // broadcast received message
            if(role.equals("driver")){

                for(MyWebSocket item : webSocketSet){
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
                }
            }else if(role.equals("passenger")){

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
                                    msgJson.put("status",1);
                                    msgJson.put("msg","您要开始接单了hhhhhhhhhhhhh");
                                    msgJson.put("passengerId",userId);
                                    String msg = JSON.toJSONString(msgJson);
                                    sendMessage(msg, "driver,"+driverId);
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
        Session s = sessionPool.get(sendObj);
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

