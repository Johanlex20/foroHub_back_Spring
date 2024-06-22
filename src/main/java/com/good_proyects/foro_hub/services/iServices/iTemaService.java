package com.good_proyects.foro_hub.services.iServices;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.dtos.TemaActualizarDTO;
import com.good_proyects.foro_hub.models.dtos.TemaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface iTemaService {

    List<Tema> findAll();
    Page<Tema> paginate(Pageable pageable);
    Tema findById(Integer id);
    Tema save(TemaDto temaDto);
    Tema update(Integer id, TemaActualizarDTO temaActualizarDTO);
    Boolean delete(Integer id);

}
