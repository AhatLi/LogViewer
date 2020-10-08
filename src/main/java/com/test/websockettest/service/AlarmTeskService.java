package com.test.websockettest.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service 
public class AlarmTeskService 
{    
 	ClientHttpRequestFactory requestFactory = null;
 	RestTemplate restTemplate = null;
 	
 	AlarmTeskService()
 	{
 		requestFactory = getClientHttpRequestFactory();
     	restTemplate = new RestTemplate(requestFactory);
 	}
 	
    private ClientHttpRequestFactory getClientHttpRequestFactory() 
    {
    	SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
        return clientHttpRequestFactory;
    }
    
	@Scheduled(fixedDelay=100000)
	public void TestScheduler()
	{
     	try
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
     	catch (Exception e) {
            System.out.println(e.toString());
			e.printStackTrace();
		}
		
		
	    System.out.println("스케줄링 테스트");
	}
}
