package com.test.LogViewer.service;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.S3VO;

@Service 
public class AlarmTeskService 
{
    @Autowired
    LogViewerService LogViewerService;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	ClientHttpRequestFactory requestFactory = null;
 	RestTemplate restTemplate = null;
 	
 	String lastSelectTime;
 	
 	AlarmTeskService()
 	{
 		requestFactory = getClientHttpRequestFactory();
     	restTemplate = new RestTemplate(requestFactory);
 		Date date = new Date();
     	lastSelectTime = dateFormat.format(date);
 	}
 	
    private ClientHttpRequestFactory getClientHttpRequestFactory() 
    {
    	SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
        return clientHttpRequestFactory;
    }
    
	@Scheduled(fixedDelay=3000)
	public void TestScheduler()
	{
     	try
     	{
     		List<S3VO> slist = LogViewerService.getS3Error(lastSelectTime);
     		List<ApacheVO> alist = LogViewerService.getApacheError(lastSelectTime);
     		List<String> msg = new ArrayList<String>();
     		
     		Date date = new Date();
         	lastSelectTime = dateFormat.format(date);

     		for(int i = 0; i < slist.size(); i++)
     		{
     			msg.add("에러로그 발생!! S3 : "+ slist.get(i).getSc_status() + ", " + slist.get(i).getX_edge_location() + " : " + slist.get(i).getCs_Host() + slist.get(i).getCs_uri_stem());
     		}
     		for(int i = 0; i < alist.size(); i++)
     		{
     			msg.add("에러로그 발생!! Apache : " + alist.get(i).getCode() + ", " + alist.get(i).getUrl());
     		}
     		
     		if(msg.isEmpty())
     			return;

	         	
	        HttpHeaders headers = new HttpHeaders();
	        
	        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	        headers.set("Authorization", "Bearer 2QT5qYSNPxQJHqTt8AYjmzPYr2SopUg70mfoGAXInEpVy0gAXiJ2YZk3FZTTImDy9xwTCEU1YFXvecYFfwVXBuTOQQC4mLgSpNeLaiREFbTzaMZ/L+QiWXwvJJfP0rRXlgcjtXBGgXJ6i7KbYlqTagdB04t89/1O/w1cDnyilFU=");

	        String url = "https://api.line.me/v2/bot/message/broadcast";
	        
         	for(int i = 0; i < msg.size(); i++)
         	{
    			JSONObject json = new JSONObject();
    			JSONArray messages = new JSONArray();

				JSONObject body = new JSONObject();
				body.put("type", "text");
				body.put("text", msg.get(i));
				messages.add(body);
	         	json.put("messages", messages);

	         	System.out.println(json.toJSONString());
		        HttpEntity<String> entity = new HttpEntity<String>(json.toJSONString(), headers);
		        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
         	}
	
	        //String resultBody = responseEntity.getBody();
	        //int resultCode = responseEntity.getStatusCode().value();
     	}
     	catch (Exception e) {
            System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
}
