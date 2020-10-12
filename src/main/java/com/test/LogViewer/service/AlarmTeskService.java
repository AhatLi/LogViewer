package com.test.LogViewer.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    
	@Scheduled(fixedDelay=10000000)
	public void TestScheduler()
	{
     	try
     	{
     		List<S3VO> slist = LogViewerService.getS3Error(lastSelectTime);
     		List<ApacheVO> alist = LogViewerService.getApacheError(lastSelectTime);
     		List<String> msg = new ArrayList<String>();

     		for(int i = 0; i < slist.size(); i++)
     		{
     			msg.add(slist.get(i).getX_edge_location() + " : " + slist.get(i).getCs_Host() + slist.get(i).getCs_uri_stem());
     		}
     		for(int i = 0; i < alist.size(); i++)
     		{
     			msg.add(alist.get(i).getUrl());
     		}
     		
     		if(msg.isEmpty())
     			return;
     		
     		Date date = new Date();
         	lastSelectTime = dateFormat.format(date);
     		
         	for(int i = 0; i < msg.size(); i++)
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
		        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		        String resultBody = responseEntity.getBody();
		        int resultCode = responseEntity.getStatusCode().value();
         	}
     	}
     	catch (Exception e) {
            System.out.println(e.toString());
			e.printStackTrace();
		}
		
		
	    System.out.println("스케줄링 테스트");
	}
}
