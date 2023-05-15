package com.bt.logapi.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class RequestValidatorTest {

    @DisplayName("When given an invalid id then throw Illegal Argument Exception")
    @ParameterizedTest()
    @ValueSource(strings = {"", "  "})
    void testValidateIdIllegalArgumentException(String value) {

        assertThrows(IllegalArgumentException.class, () -> {
            RequestValidator.isValidId(value);
        });

    }

}