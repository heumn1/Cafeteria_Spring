package ru.heumn.Cafeteria.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Поле логина не должно быть пустое")
    @NotEmpty(message = "Поле логина не должно быть пустое")
    @Size(min = 5, max = 50, message = "Логин не должен быть меньше 5 и больше 50")
    String login;

    @NotNull(message = "Поле имени не должно быть пустое")
    @NotEmpty(message = "Поле имени не должно быть пустое")
    @Size(min = 2, max = 20, message = "Имя не должено быть меньше 2 и больше 20")
    String name;

    @NotNull(message = "Поле фамилии не должно быть пустое")
    @NotEmpty(message = "Поле фамилии не должно быть пустое")
    @Size(min = 2, max = 25, message = "Фамилия не должена быть меньше 2 и больше 25")
    String lastName;

    String patronymic;

    @Size(min = 6, max = 25, message = "пароль не должена быть меньше 6 и больше 25")
    @NotNull(message = "Поле пароль не должно быть пустое")
    @NotEmpty(message = "Поле пароль не должно быть пустое")
    String password;

    Instant dateCreate;

    Boolean active;

    Instant lastLogin;

    @Enumerated(EnumType.STRING)
    Set<Role> roles;
}
