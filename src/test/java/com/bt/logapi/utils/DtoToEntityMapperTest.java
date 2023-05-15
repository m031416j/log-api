package com.bt.logapi.utils;

import com.bt.logapi.model.dto.ApplicationLogDTO;
import com.bt.logapi.model.dto.RegisterApplicationDTO;
import com.bt.logapi.model.entity.Application;
import com.bt.logapi.model.entity.ApplicationLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class DtoToEntityMapperTest {

    @DisplayName("When given a valid RegisterApplicationDTO returns entity")
    @Test
    void testRegisterApplicationDTOtoEntitySuccess() {
        // given
        RegisterApplicationDTO dto = new RegisterApplicationDTO();
        dto.setApplicationId("appid");

        // when
        Application actual = DtoToEntityMapper.map(dto);

        // then
        assertEquals("appid", actual.getApplicationId());
    }

    @DisplayName("When given a valid RegisterApplicationDTO returns entity")
    @Test
    void testApplicationLogDTOtoEntitySuccess() {
        // given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(formatter);
        ApplicationLogDTO dto = new ApplicationLogDTO();
        dto.setApplicationId("appid");
        dto.setDescription("test");
        dto.setTimestamp(now);


        // when
        ApplicationLog actual = DtoToEntityMapper.map(dto);

        // then
        assertEquals("appid", actual.getApplicationId());
        assertEquals("test", actual.getDescription());
        assertEquals(formattedNow, actual.getTimestamp().format(formatter));
    }


}