package com.test.LogViewer.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.test.LogViewer.service.LogViewerService;
import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.S3VO;

public class SocketHandler extends TextWebSocketHandler implements InitializingBean {
    //private final Logger logger = LogManager.getLogger(getClass());
    private Map<WebSocketSession, TimerExecutor> sessionMap = new HashMap<WebSocketSession, TimerExecutor>();
	private ScheduledThreadPoolExecutor executor = null;
	private int c1Interval = 3000;
    RestTemplate restTemplate = null;

	@Autowired
	LogViewerService LogViewerService;

    private ClientHttpRequestFactory getClientHttpRequestFactory() 
    {
    	SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
		
        return clientHttpRequestFactory;
    }

    public void destroy()
    {
        System.out.println("run destroy!!!");
    }
    
    @Override
    protected void finalize() throws Throwable 
    { 
		this.executor.shutdown();
        System.out.println("run finalize!!!");

    }
    
    public SocketHandler ()
    {
        super();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
          super.afterConnectionClosed(session, status);

          TimerExecutor executor = sessionMap.get(session);
          executor.stop();
          sessionMap.remove(session);
          
          System.out.println("remove session!");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
          super.afterConnectionEstablished(session);

          TimerExecutor ex = new TimerExecutor(session, LogViewerService);
          sessionMap.put(session, ex);
          ex.start();
          
          System.out.println("add session!");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
          super.handleMessage(session, message);
          
          String msg = message.getPayload().toString();
          String s[] = msg.split(",");
          
          if(s[0].equals("S3"))
          {
        	    List<S3VO> list = LogViewerService.getS3(s[1]);
				
				JSONObject json = new JSONObject();
				json.put("type", "logdata");
				json.put("tname", "S3");
				json.put("code", s[1]);
				JSONArray arr = new JSONArray();
				
				for (int i = 0; i < list.size(); i++) 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("msg", list.get(i).getMsg());
					jsonObject.put("wdate", list.get(i).getWdate());
				
					arr.add(jsonObject);
				}
				
				json.put("value", arr);
				session.sendMessage(new TextMessage(json.toJSONString()));
          }
          else if(s[0].equals("apache"))
          {
	      	    List<ApacheVO> list = LogViewerService.getApache(s[1]);
				
				JSONObject json = new JSONObject();
				json.put("type", "logdata");
				json.put("tname", "Apache");
				json.put("code", s[1]);
				JSONArray arr = new JSONArray();
				
				for (int i = 0; i < list.size(); i++) 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("msg", list.get(i).getMsg());
					jsonObject.put("wdate", list.get(i).getWdate());
				
					arr.add(jsonObject);
				}
				
				json.put("value", arr);
				session.sendMessage(new TextMessage(json.toJSONString()));
          }
          
          System.out.println("receive message:"+message.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("web socket error!"+ exception);
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("call method!");
         
          return super.supportsPartialMessages();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
