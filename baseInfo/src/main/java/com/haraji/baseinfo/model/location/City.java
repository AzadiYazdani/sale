package com.haraji.baseinfo.model.location;

import lombok.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
public class City {

    private int id;

    @NotNull
    @Min(1)
    private int stateId;

    @NotBlank
    private String title;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", stateId=" + stateId +
                ", title='" + title + '\'' +
                '}';
    }
}
