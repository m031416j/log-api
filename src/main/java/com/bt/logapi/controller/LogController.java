package com.bt.logapi.controller;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController(value = "/api")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/retrieve-logs/{id}")
    public ResponseEntity<Object> retrieveLogs(@PathParam(value = "id") String id) {
        ApiResponse response = logService.retrieveLogs(id);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        }
        return null;
    }
}
