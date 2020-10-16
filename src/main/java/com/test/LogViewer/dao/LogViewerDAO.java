package com.test.LogViewer.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.ErrLogVO;
import com.test.LogViewer.vo.S3VO;

@Repository
public class LogViewerDAO {

    @Autowired
    private SqlSessionTemplate SqlSession;
    
    public List<S3VO> getS3(S3VO s3VO){
    	return SqlSession.selectList("websocket.mappers.S3Mapper.getS3", s3VO);
    }
    
    public List<S3VO> getS3Count(){
    	return SqlSession.selectList("websocket.mappers.S3Mapper.getS3Count");
    }
    
    public List<S3VO> getS3Error(String date){
    	return SqlSession.selectList("websocket.mappers.S3Mapper.getS3Error", date);
    }
    
    public List<S3VO> getS3ErrorCount(String date){
    	return SqlSession.selectList("websocket.mappers.S3Mapper.getS3ErrorCount", date);
    }
    
    public List<ApacheVO> getApache(ApacheVO s3VO){
    	return SqlSession.selectList("websocket.mappers.ApacheMapper.getApache", s3VO);
    }
    
    public List<ApacheVO> getApacheCount(){
    	return SqlSession.selectList("websocket.mappers.ApacheMapper.getApacheCount");
    }
    
    public List<ApacheVO> getApacheError(String date){
    	return SqlSession.selectList("websocket.mappers.ApacheMapper.getApacheError", date);
    }
    
    public List<ApacheVO> getApacheErrorCount(String date){
    	return SqlSession.selectList("websocket.mappers.ApacheMapper.getApacheErrorCount", date);
    }
    
    public List<ErrLogVO> getErrorLog(String date){
    	return SqlSession.selectList("websocket.mappers.ErrLogMapper.getErrorLog", date);
    }
}
