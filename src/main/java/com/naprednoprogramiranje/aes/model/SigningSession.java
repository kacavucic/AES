package com.naprednoprogramiranje.aes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "signing_sessions")
public class SigningSession {

    @Id
    private String id;

    private long timestamp;

    private String otcCode;

    private String filepath;

}
