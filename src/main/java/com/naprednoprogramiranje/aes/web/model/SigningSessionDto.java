package com.naprednoprogramiranje.aes.web.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class SigningSessionDto {
    private String id;
    private long timestamp;
    private String otcCode;
    private String filepath;

}
