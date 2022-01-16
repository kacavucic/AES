package com.naprednoprogramiranje.aes.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class CodeVerificationDto {
    @NotBlank(message = "OTC Code is required")
    private String otcCode;
    @NotBlank(message = "Username is required")
    private String username;
}
