package com.test.websockettest.service;

import java.util.List;

import com.test.websockettest.vo.S3VO;
import com.test.websockettest.vo.TestVO;

public interface TestService {
    public List<TestVO> getTestValue(TestVO testVO);
    public List<S3VO> getS3(S3VO s3VO);
}
