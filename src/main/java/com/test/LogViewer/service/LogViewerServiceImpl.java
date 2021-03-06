package com.test.LogViewer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.LogViewer.vo.ApacheVO;
import com.test.LogViewer.vo.ErrLogVO;
import com.test.LogViewer.vo.S3VO;
import com.test.LogViewer.dao.LogViewerDAO;

@Repository
public class LogViewerServiceImpl implements LogViewerService {      
	@Autowired
	LogViewerDAO LogViewerDAO;

	@Override
    public List<S3VO> getS3(String code)
	{
        return LogViewerDAO.getS3(code);
	}
	
	@Override
    public List<S3VO> getS3Count()
	{
        return LogViewerDAO.getS3Count();
	}

	@Override
    public List<S3VO> getS3Error(String date)
	{
        return LogViewerDAO.getS3Error(date);
	}
	
	@Override
    public List<S3VO> getS3ErrorCount(String date)
	{
        return LogViewerDAO.getS3ErrorCount(date);
	}

	@Override
    public List<ApacheVO> getApache(String code)
	{
        return LogViewerDAO.getApache(code);
	}

	@Override
    public List<ApacheVO> getApacheCount()
	{
        return LogViewerDAO.getApacheCount();
	}

	@Override
    public List<ApacheVO> getApacheError(String date)
	{
        return LogViewerDAO.getApacheError(date);
	}

	@Override
    public List<ApacheVO> getApacheErrorCount(String date)
	{
        return LogViewerDAO.getApacheErrorCount(date);
	}

	@Override
    public List<ErrLogVO> getErrorLog(String date)
    {
        return LogViewerDAO.getErrorLog(date);
    }
}