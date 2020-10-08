package com.test.websockettest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.websockettest.vo.S3VO;
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

	@Override
	public List<S3VO> getS3(S3VO s3vo) {
        return TestDAO.getS3(s3vo);
	}
}