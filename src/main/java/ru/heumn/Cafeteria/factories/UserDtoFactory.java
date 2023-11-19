package ru.heumn.Cafeteria.factories;


import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.storage.dto.UserDto;
import ru.heumn.Cafeteria.storage.entities.UserEntity;

@Component
public class UserDtoFactory {

    public UserDto makeUserDto(UserEntity userEntity){
        return UserDto.builder()
                .id(userEntity.getId())
                .dateCreate(userEntity.getDateCreate())
                .lastLogin(userEntity.getLastLogin())
                .name(userEntity.getName())
                .roles(userEntity.getRoles())
                .active(userEntity.getActive())
                .lastName(userEntity.getLastName())
                .patronymic(userEntity.getPatronymic())
                .build();
    }

    public UserEntity makeUserEntity(UserDto userDto){
        return UserEntity.builder()
                .id(userDto.getId())
                .dateCreate(userDto.getDateCreate())
                .lastLogin(userDto.getLastLogin())
                .name(userDto.getName())
                .active(userDto.getActive())
                .lastName(userDto.getLastName())
                .patronymic(userDto.getPatronymic())
                .password(userDto.getPassword())
                .login(userDto.getLogin())
                .roles(userDto.getRoles())
                .id(null)
                .build();
    }
}
