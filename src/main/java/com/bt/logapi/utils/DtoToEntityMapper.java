package com.bt.logapi.utils;

import com.bt.logapi.model.dto.ApplicationLogDTO;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.model.entity.Application;
import com.bt.logapi.model.entity.ApplicationLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DtoToEntityMapper {

    private DtoToEntityMapper(){}

    public static Application map(RegisterApplicationDTO registerApplicationDTO) {
        Application application = new Application();
        application.setApplicationId(registerApplicationDTO.getApplicationId());
        return application;
    }

    public static ApplicationLog map(ApplicationLogDTO applicationLogDTO) {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setApplicationId(applicationLogDTO.getApplicationId());
        applicationLog.setDescription(applicationLogDTO.getDescription());

        // Convert the timestamp to MySQL compatible format
        LocalDateTime timestamp = applicationLogDTO.getTimestamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String mysqlFormat = timestamp.format(formatter);


        applicationLog.setTimestamp(LocalDateTime.parse(mysqlFormat, formatter));

        return applicationLog;
    }

}
