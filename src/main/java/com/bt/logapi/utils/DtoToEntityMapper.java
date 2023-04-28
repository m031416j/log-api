package com.bt.logapi.utils;

import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.model.entity.Application;

public class DtoToEntityMapper {

    private DtoToEntityMapper(){}

    public static Application map(RegisterApplicationDTO registerApplicationDTO) {
        Application application = new Application();
        application.setApplicationId(registerApplicationDTO.getApplicationId());
        return application;
    }
}
