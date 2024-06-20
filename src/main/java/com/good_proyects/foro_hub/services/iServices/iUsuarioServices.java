package com.good_proyects.foro_hub.services.iServices;
import com.good_proyects.foro_hub.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface iUsuarioServices {

    List<Usuario> findAll();
    Page<Usuario> paginate(Pageable pageable);
    Usuario findById(Integer id);
    Usuario save(Usuario usuario);
    Usuario update(Integer id, Usuario usuario);
    Boolean delete(Integer id);

}
