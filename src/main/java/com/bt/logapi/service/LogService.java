package com.bt.logapi.service;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LogService {

    ApiResponse retrieveLogs(String id) throws IllegalArgumentException, JsonProcessingException;

    ApiResponse registerApplication(RegisterApplicationDTO registerApplicationDTO);

    void saveLog(String message);
}
