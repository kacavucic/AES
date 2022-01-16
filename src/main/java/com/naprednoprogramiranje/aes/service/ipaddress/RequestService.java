package com.naprednoprogramiranje.aes.service.ipaddress;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestService {
    String getClientIp(HttpServletRequest request);
}
