package com.good_proyects.foro_hub.models.dtos;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TemaDto {

    @NotNull
    private String titulo;

    @NotNull
    private String mensaje;

    @NotNull
    private Genero genero;

    @NotNull
    private Integer usuarioId;

}
