package com.test.websockettest.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.test.websockettest.service.AlarmTeskService;
import com.test.websockettest.service.TestService;
import com.test.websockettest.vo.TestVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	AlarmTeskService scheduler;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private DataSource ds;
    
    @Autowired
    TestService TestService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/testReceiver")
	public String testReceiver(Locale locale, Model model, @RequestBody String loginForm) {		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		logger.info("request body {}", loginForm);
		
		return "home";
	}
	
	@RequestMapping(value = "/test")
	public String test(Locale locale, Model model) {		

		System.out.println("123123");

     	
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
	     	ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
	     	RestTemplate restTemplate = new RestTemplate(requestFactory);

	        
	        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	   //  	String result = restTemplate.postForObject(url, entity, String.class);
	
	        String resultBody = responseEntity.getBody();
	        int resultCode = responseEntity.getStatusCode().value();
            System.out.println(resultBody);
            System.out.println(resultCode);
		
     	
     	}
     	catch (Exception e) {
            System.out.println(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}

    
    private ClientHttpRequestFactory getClientHttpRequestFactory() 
    {
    	SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
        return clientHttpRequestFactory;
    }
	
	@RequestMapping(value = "/testDB")
	public String testDB(Locale locale, Model model) {	
        TestVO testVO = new TestVO();
        testVO.setC2("abc");
        
        List<TestVO> list = TestService.getTestValue(testVO);
        
        if( list.size() > 0 ){
        	String aa = "";
        	
        	aa += list.get(0).getC1();
        	aa += " ";
        	aa += list.get(0).getC3();
        	
        	
              model.addAttribute("serverTime", aa);
        }else{
              model.addAttribute("serverTime", "Fail_DB_Connection");
        }
		return "home";
	}
	
}
