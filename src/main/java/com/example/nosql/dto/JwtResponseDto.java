package com.example.nosql.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Schema(name = "JwtResponse")
@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDto {

    @Schema(example = "Bearer")
    private final String type = "Bearer";
    @Schema(example = "xxxxxxx.yyyyyyy.zzzzzzz")
    private String accessToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JwtResponseDto that = (JwtResponseDto) o;
        return Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, accessToken);
    }
}
