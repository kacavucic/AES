package com.naprednoprogramiranje.aes.service.ipaddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GeoIP {
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;
}
