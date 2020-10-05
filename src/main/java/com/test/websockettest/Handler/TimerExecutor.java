package com.test.websockettest.Handler;

import java.time.LocalTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class TimerExecutor 
{
	private ScheduledThreadPoolExecutor executor = null;
	private int c1Interval = 5000;
	private int c2Interval = 3000;
	private WebSocketSession session = null;
	
	TimerExecutor(WebSocketSession session)
	{
		 this.executor = new ScheduledThreadPoolExecutor(1);
		 this.session = session;
	}

    public void finalize() {
    	stop();
    }
	
	public void start()
	{
        @SuppressWarnings("unchecked")
		Runnable runnable1 = () -> 
        {
            try 
            {
	            System.out.println("runnable1 " + LocalTime.now());
				JSONObject json = new JSONObject();
				json.put("type", "chartdata");
	      	  	JSONArray arr = new JSONArray();
	      	  	{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("LogType", "INFO");
					jsonObject.put("Count", (int)(Math.random() * 1000));
					arr.add(jsonObject);
	      	  	}
	      	  	{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("LogType", "ERROR");
					jsonObject.put("Count", (int)(Math.random() * 1000));
					arr.add(jsonObject);
	      	  	}
	      	  	{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("LogType", "WARN");
					jsonObject.put("Count", (int)(Math.random() * 1000));
					arr.add(jsonObject);
	      	  	}
	      	  	{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("LogType", "DEBUG");
					jsonObject.put("Count", (int)(Math.random() * 1000));
					arr.add(jsonObject);
	      	  	}
				json.put("value", arr);
	            session.sendMessage(new TextMessage(json.toJSONString()));
	        }
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
        };
    
        @SuppressWarnings("unchecked")
		Runnable runnable2 = () -> 
        {
            try
            {
	            System.out.println("runnable2 " + LocalTime.now());
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type", "text");
				jsonObject.put("value", "test message send");
	            session.sendMessage(new TextMessage(jsonObject.toJSONString()));
            }
            catch (Exception e) 
            {
                e.printStackTrace();
    		}
        };
        
     	executor.scheduleAtFixedRate(runnable1, 0, c1Interval, TimeUnit.MILLISECONDS);
     	executor.scheduleAtFixedRate(runnable2, 0, c2Interval, TimeUnit.MILLISECONDS);	
	}
	
	public void stop()
	{
		this.executor.shutdown();
	}

}
