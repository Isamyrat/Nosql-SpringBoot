package com.example.nosql.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Username can not be empty.")
    @Size(min = 5, max = 50)
    @Schema(example = "auditor_test")
    private String username;

    @NotBlank(message = "Password can not be empty.")
    @Size(min = 8, max = 40)
    @Schema(example = "ac8fd58as6dgf584")
    private String password;

    @NotBlank(message = "Role can not be empty.")
    @Schema(example = "USER")
    private String roleName;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) &&
               Objects.equals(password, userDto.password) && Objects.equals(roleName, userDto.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, roleName);
    }
}
