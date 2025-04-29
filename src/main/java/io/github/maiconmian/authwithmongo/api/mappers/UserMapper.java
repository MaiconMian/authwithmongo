package io.github.maiconmian.authwithmongo.api.mappers;

import com.mongodb.DBObject;
import io.github.maiconmian.authwithmongo.api.DTO.UserDTO;
import io.github.maiconmian.authwithmongo.infraestructure.entity.AddressEntity;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMapper {

    public static UserEntity toEntity(UserDTO userDTO) {

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(userDTO.getCity());
        addressEntity.setState(userDTO.getState());
        addressEntity.setNeighborhood(userDTO.getNeighborhood());

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setBirthday(userDTO.getBirthday());
        userEntity.setCreation(LocalDate.now());
        userEntity.setAddress(addressEntity);

        return userEntity;
    }
}
