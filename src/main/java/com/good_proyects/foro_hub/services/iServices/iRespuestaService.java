package com.good_proyects.foro_hub.services.iServices;
import com.good_proyects.foro_hub.models.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface iRespuestaService {

    List<Respuesta> findAll();
    Page<Respuesta> paginate(Pageable pageable);
    Respuesta findById(Integer id);
    Respuesta save(Respuesta respuesta);
    Respuesta update(Integer id, Respuesta respuesta);
    Boolean delete(Integer id);

}
