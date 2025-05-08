package io.github.maiconmian.authwithmongo.infraestructure.repository;

import io.github.maiconmian.authwithmongo.infraestructure.entity.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<RoleEntity, String> {
}
