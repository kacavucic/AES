package com.naprednoprogramiranje.aes.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Signing session model represents details of the generated OTC code
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "signing_sessions")
public class SigningSession {

    /**
     * ID as a secret used for generation of the OTC code
     */
    @Id
    private String id;

    /**
     * Timestamp when the OTC code is generated
     */
    private long timestamp;

    /**
     * Generated OTC code associated with the session
     */
    private String otcCode;

    /**
     * Filepath of the file to be signed in this signing session
     */
    private String filepath;

}
