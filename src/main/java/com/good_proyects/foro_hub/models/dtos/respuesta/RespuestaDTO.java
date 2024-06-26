package com.good_proyects.foro_hub.models.dtos.respuesta;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RespuestaDTO {

    private Integer id;
    private String mensajeRespuesta;
    private Integer temaId;
    private Integer usuarioId;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
