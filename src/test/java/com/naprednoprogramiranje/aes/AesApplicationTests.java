package com.naprednoprogramiranje.aes;

import com.naprednoprogramiranje.aes.service.storage.StorageProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(StorageProperties.class)
class AesApplicationTests {

    @Test
    void contextLoads() {
    }

}
