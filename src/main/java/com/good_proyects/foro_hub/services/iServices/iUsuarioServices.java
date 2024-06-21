package com.good_proyects.foro_hub.services.iServices;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.UsuarioRegistroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface iUsuarioServices {

    List<Usuario> findAll();
    Page<Usuario> paginate(Pageable pageable);
    Usuario findById(Integer id);
    Usuario save(UsuarioRegistroDTO usuarioRegistroDTO);
    Usuario update(Integer id, UsuarioRegistroDTO usuarioRegistroDTO);
    Boolean delete(Integer id);

}
