package com.test.websockettest.service;

import java.util.List;

import com.test.websockettest.vo.TestVO;

public interface TestService {
    public List<TestVO> getTestValue(TestVO testVO);
}
