package com.test.LogViewer.Handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.test.LogViewer.service.LogViewerService;
import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.S3VO;

public class TimerExecutor 
{
	private ScheduledThreadPoolExecutor executor = null;
	private int c1Interval = 3000;
//	private int c2Interval = 3000;
	private WebSocketSession session = null;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	LogViewerService LogViewerService;
 	String lastSelectTime = "0000.00.00";

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
			
	     	try
	     	{
	     		List<S3VO> slist = LogViewerService.getS3Error(lastSelectTime);
	     		List<ApacheVO> alist = LogViewerService.getApacheError(lastSelectTime);
	     		
	     		Date date = new Date();
	         	lastSelectTime = dateFormat.format(date);
	         	
				JSONObject json = new JSONObject();
				json.put("type", "error");
				JSONArray arr = new JSONArray();

	     		for(int i = 0; i < slist.size(); i++)
	     		{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("type", "S3");
					jsonObject.put("code", slist.get(i).getSc_status());
					jsonObject.put("text", slist.get(i).getX_edge_location() + " : " + slist.get(i).getCs_Host() + slist.get(i).getCs_uri_stem());
					jsonObject.put("date", slist.get(i).getWdate());

					arr.add(jsonObject);
	     		}
	     		for(int i = 0; i < alist.size(); i++)
	     		{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("type", "Apache");
					jsonObject.put("code", alist.get(i).getCode());
					jsonObject.put("text", alist.get(i).getUrl());
					jsonObject.put("date", alist.get(i).getWdate());

					arr.add(jsonObject);
	     		}
	     		
	     		if(!arr.isEmpty())
	     		{
					json.put("value", arr);
					session.sendMessage(new TextMessage(json.toJSONString()));
	     		}
	     	}
	     	catch (Exception e) {
	            System.out.println(e.toString());
				e.printStackTrace();
			}
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
	}

	public void stop() {
		this.executor.shutdown();
	}

}
