package io.github.maiconmian.authwithmongo.api.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<d> {
    private String status;
    private String message;
    private d data;
}
