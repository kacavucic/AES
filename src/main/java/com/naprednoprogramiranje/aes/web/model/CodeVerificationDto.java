package com.naprednoprogramiranje.aes.web.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class CodeVerificationDto {
    @NotNull(message = "OTC Code is required")
    private String otcCode;
    @NotNull(message = "Username is required")
    private String username;
}
