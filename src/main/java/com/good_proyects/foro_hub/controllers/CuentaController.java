package com.good_proyects.foro_hub.controllers;
import com.good_proyects.foro_hub.exceptions.BadRequestExcepton;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.autenticacion.PerfilUsuarioDTO;
import com.good_proyects.foro_hub.models.dtos.usuario.UsuarioRegistroDTO;
import com.good_proyects.foro_hub.repository.iUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class CuentaController {

    private final iUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/registro")
    public PerfilUsuarioDTO registro(@RequestBody @Validated UsuarioRegistroDTO usuarioRegistroDTO){
        boolean emailExists = usuarioRepository.existsByEmail(usuarioRegistroDTO.getEmail());
        if (emailExists){
            throw new BadRequestExcepton("ERROR EMAIL DUPLICADO: El email ya existe!");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRegistroDTO.getNombre());
        usuario.setEmail(usuarioRegistroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        usuario.setRole(usuarioRegistroDTO.getRole());
        usuario.setFilePerfil(usuarioRegistroDTO.getFilePerfil());
        usuario.setActivo(Boolean.TRUE);
        usuario.setCreatedAt(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return new PerfilUsuarioDTO(usuario.getNombre(),usuario.getEmail(), usuario.getPassword(),usuario.getRole(), usuario.getId());

    }

}
