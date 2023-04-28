package com.bt.logapi.service;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.model.entity.ApplicationLogs;
import com.bt.logapi.repository.LogRepository;
import com.bt.logapi.utils.RequestValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository repository;

    private final ObjectFactory<ApiResponse> apiResponseObjectFactory;

    public LogServiceImpl(LogRepository repository,
                          ObjectFactory<ApiResponse> apiResponseObjectFactory) {
        this.repository = repository;
        this.apiResponseObjectFactory = apiResponseObjectFactory;

    }

    @Override
    public ApiResponse retrieveLogs(String id) throws JsonProcessingException {
        RequestValidator.isValidId(id);
        ApiResponse apiResponse = apiResponseObjectFactory.getObject();
        List<ApplicationLogs> databaseResponse = repository.findAllLogsByApplicationId(id);
        if(databaseResponse.isEmpty()) {
            handleResponse(apiResponse, String.format("No logs found for application : %s", id), 400, false);
        } else {
            handleResponse(apiResponse, writeValueAsString(databaseResponse), 200, true);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse registerApplication(RegisterApplicationDTO registerApplicationDTO) {
        return null;
    }

    private String writeValueAsString(List<ApplicationLogs> databaseResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(databaseResponse);
    }

    private void handleResponse(ApiResponse apiResponse, String data, int code, boolean isSuccess) {
        apiResponse.setData(data);
        apiResponse.setResponseCode(code);
        apiResponse.setSuccess(isSuccess);
    }
}
