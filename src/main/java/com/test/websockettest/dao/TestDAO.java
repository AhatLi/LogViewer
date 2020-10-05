package com.test.websockettest.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.websockettest.vo.TestVO;

@Repository
public class TestDAO {

    @Autowired
    private SqlSessionTemplate SqlSession;
    
    public List<TestVO> getTestValue(TestVO testVO){
    	
    	return SqlSession.selectList("websocket.mappers.testMapper.getTestValue", testVO);
    }
}
