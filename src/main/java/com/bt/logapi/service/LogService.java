package com.bt.logapi.service;

import com.bt.logapi.model.ApiResponse;

public interface LogService {

    ApiResponse retrieveLogs(String id);
}
