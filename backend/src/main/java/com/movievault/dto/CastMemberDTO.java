package com.movievault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CastMemberDTO {
    private Long personId;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String characterName;
    private Integer castOrder;
}
