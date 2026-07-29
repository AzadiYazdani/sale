package com.haraji.baseinfo.model.location;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
public class Province {

    private Long id;

    @NotBlank
    private String name;


    @Override
    public String toString() {
        return "province{" +
                "id=" + id +
                ", title='" + name + '\'' +
                '}';
    }
}
