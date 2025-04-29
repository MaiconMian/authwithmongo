package io.github.maiconmian.authwithmongo.infraestructure.repository;

import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(String id);
}
