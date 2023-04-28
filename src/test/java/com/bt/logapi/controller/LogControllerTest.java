package com.bt.logapi.controller;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.service.LogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class LogControllerTest {

    @MockBean
    private LogService service;

    @Mock
    private ApiResponse apiResponse;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When retrieve logs is called with a valid id then return a valid body and http 200")
    void testRetrieveLogsSuccess() throws Exception {
        // given
        String id = "123";
        // when
        when(service.retrieveLogs(id)).thenReturn(apiResponse);
        when(apiResponse.isSuccess()).thenReturn(true);
        when(apiResponse.getData()).thenReturn("OK");
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get
                ("/api/retrieve-logs/{id}", id);
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        // then
        assertNotNull(response);
        assertEquals("OK", response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("When retrieve logs is called with an invalid id then return http 400")
    void testRetrieveLogsBadRequest() throws Exception {
        // given
        String id = "123";
        // when
        when(service.retrieveLogs(id)).thenReturn(apiResponse);
        when(apiResponse.isSuccess()).thenReturn(false);
        when(apiResponse.getData()).thenReturn("BAD REQUEST");
        when(apiResponse.getResponseCode()).thenReturn(400);
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get
                ("/api/retrieve-logs/{id}", id);
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        // then
        assertNotNull(response);
        assertEquals("BAD REQUEST", response.getContentAsString());
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("When register app is called with a dto then return 201")
    void testRegisterApplicationSuccess() throws Exception {
        // given
        final String filePath = "classpath:data/registerApplicationDto_201.json";
        // when
        when(service.registerApplication(any())).thenReturn(apiResponse);
        when(apiResponse.isSuccess()).thenReturn(true);
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post
                ("/api/register-app")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(ResourceUtils.getFile(filePath).toPath()));
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        // then
        assertNotNull(response);
        assertEquals(201, response.getStatus());
    }


    @Test
    @DisplayName("When register app is called with an invalid dto then return 400")
    void testRegisterApplicationBadRequest() throws Exception {
        // given
        final String filePath = "classpath:data/registerApplicationDto_201.json";
        // when
        when(service.registerApplication(any())).thenReturn(apiResponse);
        when(apiResponse.isSuccess()).thenReturn(false);
        when(apiResponse.getData()).thenReturn("BAD REQUEST");
        when(apiResponse.getResponseCode()).thenReturn(400);
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post
                        ("/api/register-app")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(ResourceUtils.getFile(filePath).toPath()));
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        // then
        assertNotNull(response);
        assertEquals("BAD REQUEST", response.getContentAsString());
        assertEquals(400, response.getStatus());
    }
}