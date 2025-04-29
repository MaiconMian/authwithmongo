package io.github.maiconmian.authwithmongo.infraestructure.repository;

import io.github.maiconmian.authwithmongo.infraestructure.entity.AddressEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<AddressEntity, Integer> {
}
