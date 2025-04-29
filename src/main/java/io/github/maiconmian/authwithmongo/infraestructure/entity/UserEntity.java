package io.github.maiconmian.authwithmongo.infraestructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String  id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
    private LocalDate creation;
    private AddressEntity address;
}
