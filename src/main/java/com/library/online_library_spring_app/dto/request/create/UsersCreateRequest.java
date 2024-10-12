package com.library.online_library_spring_app.dto.request.create;

import com.library.online_library_spring_app.dao.entity.Role;
import com.library.online_library_spring_app.enums.RoleName;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersCreateRequest {
    @NotNull(message = "Username can not be null")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
    @NotNull(message = "Firstname can not be null")
    private String firstName;
    @NotNull(message = "Lastname can not be null")
    private String lastName;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;
    private String address;
    private Role role = Role.
    private Boolean userIsActive = true;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
}
