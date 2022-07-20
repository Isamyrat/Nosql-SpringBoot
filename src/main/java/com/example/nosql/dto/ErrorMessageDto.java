package com.example.nosql.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Schema(name = "ErrorMessage")
@Getter
@Setter
@AllArgsConstructor
public class ErrorMessageDto {
    @Schema(example = "User not found.")
    private String errorMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorMessageDto that = (ErrorMessageDto) o;
        return Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage);
    }
}
