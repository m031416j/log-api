package com.bt.logapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {

    private String responseCode;
    private String data;
    private boolean isSuccess;
}
