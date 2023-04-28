package com.bt.logapi.controller;

import com.bt.logapi.model.ApiResponse;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(value = "/api/retrieve-logs/{id}", produces = { "application/json;charset=utf-8" })
    public ResponseEntity<Object> retrieveLogs(@PathVariable String id) throws JsonProcessingException {
        ApiResponse response = logService.retrieveLogs(id);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.getData(), HttpStatus.valueOf(response.getResponseCode()));
    }

    @PostMapping("/api/register-app")
    public ResponseEntity<Object> registerApplication(@RequestBody RegisterApplicationDTO registerApplicationDTO) {
        ApiResponse response = logService.registerApplication(registerApplicationDTO);
        if(response.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getData(), HttpStatus.valueOf(response.getResponseCode()));
    }
}
