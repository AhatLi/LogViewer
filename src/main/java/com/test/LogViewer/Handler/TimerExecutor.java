package com.test.LogViewer.Handler;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.test.LogViewer.service.LogViewerService;
import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.S3VO;

public class TimerExecutor 
{
	private ScheduledThreadPoolExecutor executor = null;
	private int c1Interval = 5000;
//	private int c2Interval = 3000;
	private WebSocketSession session = null;
	
	LogViewerService LogViewerService;

	TimerExecutor(WebSocketSession session, LogViewerService LogViewerService) 
	{
		this.executor = new ScheduledThreadPoolExecutor(1);
		this.session = session;
		this.LogViewerService = LogViewerService;
	}

	public void finalize() 
	{
		stop();
	}

	public void start() 
	{
		@SuppressWarnings("unchecked")
		Runnable runnable1 = () -> 
		{
			try 
			{
				List<S3VO> list = LogViewerService.getS3Count();

				JSONObject json = new JSONObject();
				json.put("type", "chartdata");
				json.put("tname", "S3");
				JSONArray arr = new JSONArray();

				for (int i = 0; i < list.size(); i++) 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("keyword", list.get(i).getKeyword());
					jsonObject.put("val", list.get(i).getVal());

					arr.add(jsonObject);
				}

				json.put("value", arr);
				session.sendMessage(new TextMessage(json.toJSONString()));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			try 
			{
				List<ApacheVO> list = LogViewerService.getApacheCount();

				JSONObject json = new JSONObject();
				json.put("type", "chartdata");
				json.put("tname", "Apache");
				JSONArray arr = new JSONArray();

				for (int i = 0; i < list.size(); i++) 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("keyword", list.get(i).getKeyword());
					jsonObject.put("val", list.get(i).getVal());

					arr.add(jsonObject);
				}

				json.put("value", arr);
				session.sendMessage(new TextMessage(json.toJSONString()));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			System.out.println("runnable1 " + LocalTime.now());
		};
/*
		@SuppressWarnings("unchecked")
		Runnable runnable2 = () -> {
			try {
				System.out.println("runnable2 " + LocalTime.now());

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type", "text");
				jsonObject.put("value", "test message send");
				session.sendMessage(new TextMessage(jsonObject.toJSONString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
*/
		executor.scheduleAtFixedRate(runnable1, 0, c1Interval, TimeUnit.MILLISECONDS);
	//	executor.scheduleAtFixedRate(runnable2, 0, c2Interval, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		this.executor.shutdown();
	}

}
