package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.exceptions.ResourceNotFoundException;
import com.good_proyects.foro_hub.models.Respuesta;
import com.good_proyects.foro_hub.repository.iRespuestaRepository;
import com.good_proyects.foro_hub.services.iServices.iRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RespuestaService implements iRespuestaService {

    @Autowired
    private iRespuestaRepository respuestaRepository;

    @Override
    public List<Respuesta> findAll() {
        return null;
    }

    @Override
    public Page<Respuesta> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Respuesta findById(Integer id) {
        return respuestaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ERROR ID: Respuesta no encontrada!"));
    }


    @Override
    public Respuesta save(Respuesta respuesta) {
        return null;
    }

    @Override
    public Respuesta update(Integer id, Respuesta respuesta) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

}
