package io.github.maiconmian.authwithmongo.business.service;

import io.github.maiconmian.authwithmongo.infraestructure.entity.RoleEntity;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import io.github.maiconmian.authwithmongo.infraestructure.exception.BusinessException;
import io.github.maiconmian.authwithmongo.infraestructure.repository.RoleRepository;
import io.github.maiconmian.authwithmongo.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleEntity getRoleById(String id){
        return roleRepository.findById(id).orElseThrow(() -> new BusinessException("Role dont find with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<RoleEntity> getAllRoles(){
        List<RoleEntity> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new BusinessException("There aren´t roles", HttpStatus.NOT_FOUND);
        }
        return roles;
    }
}
