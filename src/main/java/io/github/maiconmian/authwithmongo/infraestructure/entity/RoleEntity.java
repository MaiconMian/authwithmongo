package io.github.maiconmian.authwithmongo.infraestructure.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="roles")
@Schema(hidden = true)
public class RoleEntity {
    @Id
    private String id;
    private String name;
    private String description;
}
