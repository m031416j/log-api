package com.bt.logapi.service;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.model.entity.ApplicationLogs;
import com.bt.logapi.repository.LogRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class LogServiceImplTest {

    @InjectMocks
    private LogServiceImpl service;

    @Mock
    private ObjectFactory<ApiResponse> apiResponseObjectFactory;

    @Mock
    private LogRepository repository;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When given a valid app id then return a list of logs")
    void testRetrieveLogsSuccess() throws JsonProcessingException {
        // given
        String id = "app1";
        List<ApplicationLogs> logs = generateLogs();

        // when
        when(apiResponseObjectFactory.getObject()).thenReturn(new ApiResponse());
        when(repository.findAllLogsByApplicationId(id)).thenReturn(logs);

        ApiResponse response = service.retrieveLogs(id);

        // then
        assertNotNull(response);
        assertEquals(200, response.getResponseCode());
        assertTrue(response.isSuccess());
    }

    @Test
    @DisplayName("When given an invalid app id then return error message")
    void testRetrieveLogsBadRequest() throws JsonProcessingException {
        // given
        String id = "app1";
        List<ApplicationLogs> logs = new ArrayList<>();

        // when
        when(apiResponseObjectFactory.getObject()).thenReturn(new ApiResponse());
        when(repository.findAllLogsByApplicationId(id)).thenReturn(logs);

        ApiResponse response = service.retrieveLogs(id);

        // then
        assertNotNull(response);
        assertEquals(String.format("No logs found for application : %s", id), response.getData());
        assertEquals(400, response.getResponseCode());
        assertFalse(response.isSuccess());
    }

    private List<ApplicationLogs> generateLogs() {
        List<ApplicationLogs> logs = new ArrayList<>();
        logs.add(new ApplicationLogs("test", new Date(1), "test", "test"));
        return logs;
    }

}