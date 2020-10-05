package com.test.websockettest.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketHandler extends TextWebSocketHandler implements InitializingBean {
    //private final Logger logger = LogManager.getLogger(getClass());
    private Map<WebSocketSession, TimerExecutor> sessionMap = new HashMap<WebSocketSession, TimerExecutor>();
	private ScheduledThreadPoolExecutor executor = null;
	private int c1Interval = 3000;
    RestTemplate restTemplate = null;

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
        System.out.println("create SocketHandler instance!");

     	ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
       	restTemplate = new RestTemplate(requestFactory);  	

       	ThreadFactory threadFactory = new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		this.executor = new ScheduledThreadPoolExecutor(1, threadFactory);

		Runnable runnable1 = () -> 
        {
            try 
            {
            	test();
	        }
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
        };
		
        threadFactory.newThread(runnable1);
     	executor.scheduleAtFixedRate(runnable1, 0, c1Interval, TimeUnit.MILLISECONDS);
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

          TimerExecutor ex = new TimerExecutor(session);
          sessionMap.put(session, ex);
          ex.start();
          
          System.out.println("add session!");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
          super.handleMessage(session, message);
          System.out.println("receive message:"+message.toString());
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
    
    void test()
    {
        String url = "https://api.line.me/v2/bot/message/broadcast";
        String body = "{\r\n"
        		+ "    \"messages\":[\r\n"
        		+ "        {\r\n"
        		+ "            \"type\":\"text\",\r\n"
        		+ "            \"text\":\"Hello, world1\"\r\n"
        		+ "        }\r\n"
        		+ "    ]\r\n"
        		+ "}";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer 2QT5qYSNPxQJHqTt8AYjmzPYr2SopUg70mfoGAXInEpVy0gAXiJ2YZk3FZTTImDy9xwTCEU1YFXvecYFfwVXBuTOQQC4mLgSpNeLaiREFbTzaMZ/L+QiWXwvJJfP0rRXlgcjtXBGgXJ6i7KbYlqTagdB04t89/1O/w1cDnyilFU=");
        
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
/*
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String resultBody = responseEntity.getBody();
        int resultCode = responseEntity.getStatusCode().value();
        System.out.println(resultBody);
        System.out.println(resultCode);
        */
        System.out.println("test()");
    }

}
