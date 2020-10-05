package com.test.websockettest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.websockettest.vo.TestVO;
import com.test.websockettest.dao.TestDAO;

@Repository
public class TestServiceImpl implements TestService {
      
      @Autowired
      TestDAO TestDAO;
      
      @Override
      public List<TestVO> getTestValue(TestVO testVO){
            return TestDAO.getTestValue(testVO);
      }
}