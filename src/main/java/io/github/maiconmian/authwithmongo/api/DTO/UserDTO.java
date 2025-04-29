package io.github.maiconmian.authwithmongo.api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    private LocalDate birthday;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
}
