package io.github.maiconmian.authwithmongo.infraestructure.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class AddressEntity {
    private String street;
    private String neighborhood;
    private String city;
    private String state;
}
