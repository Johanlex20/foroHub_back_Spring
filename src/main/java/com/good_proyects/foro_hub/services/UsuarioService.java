package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.exceptions.BadRequestExcepton;
import com.good_proyects.foro_hub.exceptions.ResourceNotFoundException;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.usuario.UsuarioDTO;
import com.good_proyects.foro_hub.models.dtos.usuario.UsuarioRegistroDTO;
import com.good_proyects.foro_hub.repository.iUsuarioRepository;
import com.good_proyects.foro_hub.services.iServices.iUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements iUsuarioServices {
    @Autowired
    private iUsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::manejoRespuestaCortaUsuarioCliente)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UsuarioDTO> paginate(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(this::manejoRespuestaCortaUsuarioCliente);
    }

    @Override
    public UsuarioDTO findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: id no encontrado en la base de datos!"));
        return manejoRespuestaCortaUsuarioCliente(usuario);
    }

    String img = "02f7ab2c-c0c9-4f54-9134-d6dece39f4fa.jpg";
    public UsuarioDTO save(UsuarioRegistroDTO usuarioRegistroDTO) {

        boolean usuarioExiste =usuarioRepository.existsByEmail(usuarioRegistroDTO.getEmail());

        if (usuarioExiste){
            throw new BadRequestExcepton("El email ya existe!");
        }

        Usuario usuario = null;

        try{
            usuario = new Usuario();
            usuario.setNombre(usuarioRegistroDTO.getNombre());
            usuario.setEmail(usuarioRegistroDTO.getEmail());
            usuario.setPassword(usuarioRegistroDTO.getPassword());
            usuario.setRole(usuarioRegistroDTO.getRole());
            usuario.setActivo(Boolean.TRUE);
            usuario.setCreatedAt(LocalDateTime.now());
            usuario.setFilePerfil(img);
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR CREACION: Falla no es posible realizar el proceso!", e);
        }
        usuario = usuarioRepository.save(usuario);
        return manejoRespuestaCortaUsuarioCliente(usuario);
    }

    @Override
    public UsuarioDTO update(Integer id, UsuarioRegistroDTO usuarioRegistroDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));

        boolean usuarioExiste =usuarioRepository.existsByEmailAndIdNot(usuarioRegistroDTO.getEmail(), id);

        if (usuarioExiste){
            throw new BadRequestExcepton("El email ya existe!");
        }

        try {
            if (usuario != null){
                usuario.setNombre(usuarioRegistroDTO.getNombre());
                usuario.setEmail(usuarioRegistroDTO.getEmail());
                usuario.setPassword(usuarioRegistroDTO.getPassword());
                usuario.setRole(usuarioRegistroDTO.getRole());
                usuario.setUpdatedAt(LocalDateTime.now());
                usuario.setFilePerfil(img);
            }else {
                throw new BadRequestExcepton("ERROR ACTUALIZAR: Usuario no se pudo actualizar!");
            }
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        usuario = usuarioRepository.save(usuario);
        return manejoRespuestaCortaUsuarioCliente(usuario);
    }

    @Override
    public Boolean delete(Integer id) {
        usuarioRepository.deleteById(id);
        return true;
    }

    private UsuarioDTO manejoRespuestaUsuarioCliente(Usuario usuario) {
        UsuarioDTO  usuarioDTO = new UsuarioDTO();
         usuarioDTO.setId(usuario.getId());
         usuarioDTO.setNombre(usuario.getNombre());
         usuarioDTO.setEmail(usuario.getEmail());
         usuarioDTO.setPassword(usuario.getPassword());
         usuarioDTO.setRole(usuario.getRole());
         usuarioDTO.setFilePerfil(img);
         usuarioDTO.setCreatedAt(usuario.getCreatedAt());
         usuarioDTO.setUpdatedAt(usuario.getUpdatedAt());
         usuarioDTO.setActivo(usuario.getActivo());
         usuarioDTO.setRespuestas(usuario.getRespuestas());
         usuarioDTO.setTemas(usuario.getTemas()); // Se establecen las respuestas aquí, ya que son datos reducidos

        return  usuarioDTO;
    }

    private UsuarioDTO manejoRespuestaCortaUsuarioCliente(Usuario usuario) {
        UsuarioDTO  usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setRole(usuario.getRole());
        usuarioDTO.setUpdatedAt(LocalDateTime.now());
        usuarioDTO.setFilePerfil(img);
        usuarioDTO.setCreatedAt(usuario.getCreatedAt());
        usuarioDTO.setUpdatedAt(usuario.getUpdatedAt());
        usuarioDTO.setActivo(usuario.getActivo()); // No se establecen las respuestas ni temas aquí, ya que son datos reducidos
        return  usuarioDTO;
    }

}
