package com.naprednoprogramiranje.aes.service.totp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class OTC {

    private String id;
    private String otcCode;
    private long timestamp;
}
