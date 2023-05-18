package com.bt.logapi.service;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.model.entity.ApplicationLog;
import com.bt.logapi.repository.ApplicationLogRepository;
import com.bt.logapi.repository.ApplicationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class LogServiceImplTest {

    @InjectMocks
    private LogServiceImpl service;

    @Mock
    private ObjectFactory<ApiResponse> apiResponseObjectFactory;

    @Mock
    private ApplicationLogRepository applicationLogRepository;

    @Mock
    private ApplicationRepository applicationRepository;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When given a valid app id then return a list of logs")
    void testRetrieveLogsSuccess() throws JsonProcessingException {
        // given
        String id = "app1";
        List<ApplicationLog> logs = generateLogs();

        // when
        when(apiResponseObjectFactory.getObject()).thenReturn(new ApiResponse());
        when(applicationLogRepository.findAllLogsByApplicationId(id)).thenReturn(logs);

        ApiResponse response = service.retrieveLogs(id);

        // then
        assertNotNull(response);
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @DisplayName("When given an invalid app id then return error message")
    void testRetrieveLogsBadRequest() throws JsonProcessingException {
        // given
        String id = "app1";
        List<ApplicationLog> logs = new ArrayList<>();

        // when
        when(apiResponseObjectFactory.getObject()).thenReturn(new ApiResponse());
        when(applicationLogRepository.findAllLogsByApplicationId(id)).thenReturn(logs);

        ApiResponse response = service.retrieveLogs(id);

        // then
        assertNotNull(response);
        assertEquals(String.format("No logs found for application : %s", id), response.getData());
        assertEquals(404, response.getResponseCode());

    }

    @Test
    @DisplayName("When given valid app then register a new app")
    void testRegisterNewApplicationSuccess() {
        // given
        RegisterApplicationDTO dto = new RegisterApplicationDTO();
        dto.setApplicationId("app1");
        // when
        when(apiResponseObjectFactory.getObject()).thenReturn(new ApiResponse());
        when(applicationRepository.save(any())).thenReturn(null);
        ApiResponse response = service.registerApplication(dto);

        // then
        assertNotNull(response);
        assertEquals(201, response.getResponseCode());

    }

    @Test
    @DisplayName("When given valid message then log a new message in the database")
    void testLogMessageSuccess() throws IOException {
        // given

        final String filePath = "classpath:data/sqsLog.json";
        final String message = Files.readString(Paths.get(ResourceUtils.getFile(filePath).toURI()));

        // when
        when(applicationLogRepository.save(any())).thenReturn(null);
        service.saveLog(message);

        // then
        verify(applicationLogRepository, times(1)).save(any());
        assertTrue(true);
    }

    @Test
    @DisplayName("When given an invalid message then throw json parsing exception")
    void testLogMessageParsingException() throws IOException {
        // given

        // when

        service.saveLog(null);

        // then
        verify(applicationLogRepository, times(0)).save(any());
        assertTrue(true);

    }


    private List<ApplicationLog> generateLogs() {
        List<ApplicationLog> logs = new ArrayList<>();
        logs.add(new ApplicationLog(1, LocalDateTime.now(), "test", "test"));
        return logs;
    }

}