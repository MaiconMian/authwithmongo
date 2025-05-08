package io.github.maiconmian.authwithmongo.api.mappers;

import com.mongodb.DBObject;
import io.github.maiconmian.authwithmongo.api.DTO.UserDTO;
import io.github.maiconmian.authwithmongo.business.service.RoleService;
import io.github.maiconmian.authwithmongo.infraestructure.entity.AddressEntity;
import io.github.maiconmian.authwithmongo.infraestructure.entity.RoleEntity;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UserMapper {

    private static RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        UserMapper.roleService = roleService;
    }

    public static UserEntity toEntity(UserDTO userDTO) {

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(userDTO.getCity());
        addressEntity.setState(userDTO.getState());
        addressEntity.setNeighborhood(userDTO.getNeighborhood());

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());

        userEntity.setPassword(encryptedPassword);
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setBirthday(userDTO.getBirthday());
        userEntity.setCreation(LocalDate.now());
        userEntity.setAddress(addressEntity);

        RoleEntity roleEntity = roleService.getRoleById(userDTO.getRoleId());

        userEntity.setRole(roleEntity);

        return userEntity;
    }
}
