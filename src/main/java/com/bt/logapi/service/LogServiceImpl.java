package com.bt.logapi.service;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.model.dto.ApplicationLogDTO;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.model.entity.ApplicationLog;
import com.bt.logapi.repository.ApplicationRepository;
import com.bt.logapi.repository.ApplicationLogRepository;
import com.bt.logapi.utils.DtoToEntityMapper;
import com.bt.logapi.utils.RequestValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.List;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

    private final ApplicationLogRepository applicationLogRepository;

    private final ApplicationRepository applicationRepository;

    private final ObjectFactory<ApiResponse> apiResponseObjectFactory;

    private final ObjectMapper mapper = generateMapper();

    public LogServiceImpl(ApplicationLogRepository repository,
                          ApplicationRepository applicationRepository,
                          ObjectFactory<ApiResponse> apiResponseObjectFactory) {
        this.applicationLogRepository = repository;
        this.applicationRepository = applicationRepository;
        this.apiResponseObjectFactory = apiResponseObjectFactory;

    }

    @Override
    public ApiResponse retrieveLogs(String id) throws JsonProcessingException {
        RequestValidator.isValidId(id);
        ApiResponse apiResponse = apiResponseObjectFactory.getObject();
        List<ApplicationLog> databaseResponse = applicationLogRepository.findAllLogsByApplicationId(id);
        if(databaseResponse.isEmpty()) {
            handleResponse(apiResponse, String.format("No logs found for application : %s", id), 400, false);
        } else {
            handleResponse(apiResponse, writeValueAsString(databaseResponse), 200, true);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse registerApplication(RegisterApplicationDTO registerApplicationDTO) {
        RequestValidator.isValidId(registerApplicationDTO.getApplicationId());
        ApiResponse apiResponse = apiResponseObjectFactory.getObject();
        applicationRepository.save(DtoToEntityMapper.map(registerApplicationDTO));
        handleResponse(apiResponse, "OK", 201, true);
        return apiResponse;
    }

    @Override
    public void saveLog(String message) {
        try {
            ApplicationLogDTO applicationLogDTO = mapper.readValue(message, ApplicationLogDTO.class);
            ApplicationLog applicationLog = DtoToEntityMapper.map(applicationLogDTO);
            applicationLogRepository.save(applicationLog);
            log.error("Successfully Stored Log In The Database");
        } catch (Exception ex) {
            log.error("Error mapping message : {}", ex.getMessage());
        }
    }

    private String writeValueAsString(List<ApplicationLog> databaseResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(DateFormat.getDateInstance());
        return objectMapper.writeValueAsString(databaseResponse);
    }

    private void handleResponse(ApiResponse apiResponse, String data, int code, boolean isSuccess) {
        apiResponse.setData(data);
        apiResponse.setResponseCode(code);
        apiResponse.setSuccess(isSuccess);
    }

    protected ObjectMapper generateMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(DateFormat.getDateInstance());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return objectMapper;
    }
}
