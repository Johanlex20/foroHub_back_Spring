package com.good_proyects.foro_hub.models.dtos.autenticacion;
import lombok.Data;

@Data
public class SolicitudAutenticacion {
    private String email;
    private String password;
}
