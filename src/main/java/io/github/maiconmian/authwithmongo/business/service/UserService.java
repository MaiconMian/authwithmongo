package io.github.maiconmian.authwithmongo.business.service;

import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import io.github.maiconmian.authwithmongo.infraestructure.exception.BusinessException;
import io.github.maiconmian.authwithmongo.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com email: " + email, HttpStatus.NOT_FOUND));
    }

    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com ID: " + id, HttpStatus.NOT_FOUND));
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new BusinessException("Não existem usuários", HttpStatus.NOT_FOUND);
        }
        return users;
    }

    public UserEntity saveUser(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException("E-mail já está em uso", HttpStatus.CONFLICT);
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user, String  id) {
        UserEntity userFind = userRepository.findById(id)
                        .orElseThrow(() -> new BusinessException("Usuario não encontrado com id: " + id, HttpStatus.NOT_FOUND));
        user.setId(userFind.getId());
        return userRepository.save(user);
    }

    public void deleteUser(String  id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com ID: " + id, HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }

}
