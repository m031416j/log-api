package com.bt.logapi.listener;

import com.bt.logapi.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class AsyncLogListenerTest {

    @InjectMocks
    private AsyncLogListener listener;

    @Mock
    private LogService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When a message is received then call the service class")
    void testReceiveMessageSuccess() {
        // given
        String message = "test";

        // when
        doNothing().when(service).saveLog(message);
        listener.receiveMessage(message);

        // then
        verify(service, times(1)).saveLog(message);
    }
}