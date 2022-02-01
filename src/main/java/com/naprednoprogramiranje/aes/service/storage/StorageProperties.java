package com.naprednoprogramiranje.aes.service.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Defines location for uploaded files
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "upload-dir";
}
