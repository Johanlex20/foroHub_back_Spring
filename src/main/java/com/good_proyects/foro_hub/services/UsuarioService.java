package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.repository.iUsuarioRepository;
import com.good_proyects.foro_hub.services.iServices.iUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
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
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("ERROR ID: id no encontrado en la base de datos!"));
    }


    @Override
    public Usuario save(Usuario usuario) {
        Usuario nuevoUsuairo = new Usuario();
        nuevoUsuairo.setNombre(usuario.getNombre());
        nuevoUsuairo.setEmail(usuario.getEmail());
        nuevoUsuairo.setPassword(usuario.getPassword());
        nuevoUsuairo.setRole(usuario.getRole());
        nuevoUsuairo.setActivo(Boolean.TRUE);
        nuevoUsuairo.setCreatedAt(LocalDateTime.now());
        return usuarioRepository.save(nuevoUsuairo);
    }

    @Override
    public Usuario update(Integer id, Usuario usuario) {
        Usuario usuarioActualizado = findById(id);

        if (usuarioActualizado != null){
            usuarioActualizado.setNombre(usuario.getNombre());
            usuarioActualizado.setEmail(usuario.getEmail());
            usuarioActualizado.setPassword(usuario.getPassword());
            usuarioActualizado.setRole(usuario.getRole());
            usuarioActualizado.setUpdatedAt(LocalDateTime.now());
        }else {
            throw new RuntimeException("ERROR ACTUALIZAR: Usuario no se pudo actualizar!");
        }
        return usuarioRepository.save(usuarioActualizado);
    }

    @Override
    public Boolean delete(Integer id) {
        usuarioRepository.deleteById(id);
        System.out.println("Usuiario eliminado!");
        return true;
    }

}
