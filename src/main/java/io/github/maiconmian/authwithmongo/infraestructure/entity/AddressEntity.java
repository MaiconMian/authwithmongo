package io.github.maiconmian.authwithmongo.infraestructure.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {
    private String street;
    private String neighborhood;
    private String city;
    private String state;
}
