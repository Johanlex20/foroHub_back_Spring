package com.good_proyects.foro_hub.services.iServices;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.dtos.TemaActualizarDTO;
import com.good_proyects.foro_hub.models.dtos.TemaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface iTemaService {

    List<TemaDto> findAll();
    Page<TemaDto> paginate(Pageable pageable);
    TemaDto findById(Integer id);
    TemaDto save(TemaDto temaDto);
    TemaDto update(Integer id, TemaActualizarDTO temaActualizarDTO);
    Boolean delete(Integer id);

}
