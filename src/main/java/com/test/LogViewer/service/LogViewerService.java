package com.test.LogViewer.service;

import java.util.List;

import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.ErrLogVO;
import com.test.LogViewer.vo.S3VO;

public interface LogViewerService 
{
    public List<S3VO> getS3(S3VO s3VO);
    public List<S3VO> getS3Count();
    public List<S3VO> getS3Error(String date);
    public List<S3VO> getS3ErrorCount(String date);
    
    public List<ApacheVO> getApache(ApacheVO apacheVO);
    public List<ApacheVO> getApacheCount();
    public List<ApacheVO> getApacheError(String date);
    public List<ApacheVO> getApacheErrorCount(String date);
    
    public List<ErrLogVO> getErrorLog(String date);
}
