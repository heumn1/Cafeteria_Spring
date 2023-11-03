package ru.heumn.Cafeteria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.heumn.Cafeteria.dto.UserDto;
import ru.heumn.Cafeteria.factories.UserDtoFactory;
import ru.heumn.Cafeteria.storage.Role;
import ru.heumn.Cafeteria.storage.entities.UserEntity;
import ru.heumn.Cafeteria.storage.repository.UserRepository;

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
        return userRepository.findByLogin(name);
    }

    public Boolean addUser(UserEntity user){
        if(user.equals(userRepository.findByLogin(user.getLogin()))){
            return false;
        }
        else {
            userRepository.save(user);
        }
        return true;
    }

    public List<UserDto> getAllUsers(){

        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        userEntityList.stream()
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
