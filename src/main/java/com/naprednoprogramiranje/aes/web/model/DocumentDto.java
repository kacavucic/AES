package com.naprednoprogramiranje.aes.web.model;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class DocumentDto {
    @NotNull(message = "Document is required")
    private File document;
}
