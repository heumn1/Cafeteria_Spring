package ru.heumn.Cafeteria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.heumn.Cafeteria.storage.dto.UserDto;
import ru.heumn.Cafeteria.factories.UserDtoFactory;
import ru.heumn.Cafeteria.storage.enums.Role;
import ru.heumn.Cafeteria.storage.entities.UserEntity;
import ru.heumn.Cafeteria.storage.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDtoFactory userDtoFactory;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Optional<UserEntity> user = Optional.of(userRepository.findByLogin(name));

        if(user.isPresent())
        {
            user.get().setLastLogin(Instant.now());
            userRepository.save(user.get());
        }

        return userRepository.findByLogin(name);
    }

    public Boolean addUser(UserDto userDto){

        String encodedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        UserEntity user = userDtoFactory.makeUserEntity(userDto);

        if(userRepository.findByLogin(user.getLogin()) != null){
            return false;
        }
        else {
            user.setActive(true);
            user.setDateCreate(Instant.now());

            userRepository.save(user);
        }
        return true;
    }

    public List<UserDto> getAllUsers(){

        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        userEntityList
                .forEach(user -> userDtoList.add(userDtoFactory.makeUserDto(user)));

        return userDtoList;
    }

    public void setRoleForUser(Long id, Role role) {

        Optional<UserEntity> user = userRepository.findById(id);

        if(user.isPresent() )
        {
            if(!user.get().getRoles().contains(role))
            {
                user.get().getRoles().add(role);
                userRepository.save(user.get());
            }
        }
    }

    public void DeleteRoleForUser(Long id, Role role) {

        Optional<UserEntity> user = userRepository.findById(id);

        if(user.isPresent() )
        {
            if(user.get().getRoles().contains(role))
            {
                user.get().getRoles().remove(role);
                userRepository.save(user.get());
            }
        }
    }
}
