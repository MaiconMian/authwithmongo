package io.github.maiconmian.authwithmongo.infraestructure.repository;

import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserDetails findByEmail(String email);
}
