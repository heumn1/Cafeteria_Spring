package ru.heumn.Cafeteria.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.Role;

import java.time.Instant;
import java.util.Set;


@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;

    @NotNull
    String login;

    @NotNull
    String name;

    @NotNull
    String lastName;

    @NotNull
    String patronymic;

    @NotNull
    String password;

    @NotNull
    Instant dateCreate;

    @NotNull
    Boolean active;

    Instant lastLogin;

    @Enumerated(EnumType.STRING)
    Set<Role> roles;
}
