package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.exceptions.BadRequestExcepton;
import com.good_proyects.foro_hub.exceptions.ResourceNotFoundException;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.UsuarioRegistroDTO;
import com.good_proyects.foro_hub.repository.iUsuarioRepository;
import com.good_proyects.foro_hub.services.iServices.iUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService implements iUsuarioServices {
    @Autowired
    private iUsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> paginate(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ERROR ID: id no encontrado en la base de datos!"));
    }

    String img = "02f7ab2c-c0c9-4f54-9134-d6dece39f4fa.jpg";
    public Usuario save(UsuarioRegistroDTO usuarioRegistroDTO) {
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
        return usuarioRepository.save(usuario);

    }

    @Override
    public Usuario update(Integer id, UsuarioRegistroDTO usuarioRegistroDTO) {
        Usuario usuario = findById(id);

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
        return usuarioRepository.save(usuario);
    }

    @Override
    public Boolean delete(Integer id) {
        usuarioRepository.deleteById(id);
        return true;
    }

}
