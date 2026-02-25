package com.movievault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String birthPlace;
    private String bio;
    private String photoUrl;
}
