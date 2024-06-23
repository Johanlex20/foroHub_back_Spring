package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.exceptions.BadRequestExcepton;
import com.good_proyects.foro_hub.exceptions.ResourceNotFoundException;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.TemaActualizarDTO;
import com.good_proyects.foro_hub.models.dtos.TemaDto;
import com.good_proyects.foro_hub.repository.iTemaRepository;
import com.good_proyects.foro_hub.repository.iUsuarioRepository;
import com.good_proyects.foro_hub.services.iServices.iTemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemaService implements iTemaService {

    @Autowired
    private iTemaRepository temaRepository;

    @Autowired
    private iUsuarioRepository usuarioRepository;

    @Override
    public List<Tema> findAll() {
        return temaRepository.findAll();
    }

    @Override
    public Page<Tema> paginate(Pageable pageable) {
        return temaRepository.findAll(pageable);
    }

    @Override
    public Tema findById(Integer id) {
        return temaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("ERROR ID: id no encontrado en la base de datos!"));
    }

    @Override
    public Tema save(TemaDto temaDto) {
        Tema tema = null;

        boolean tituloExiste = temaRepository.existsByTitulo(temaDto.getTitulo());
        boolean mensajeExiste = temaRepository.existsByMensaje(temaDto.getMensaje());

        if (tituloExiste ) {
            throw new BadRequestExcepton("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new BadRequestExcepton("El mensaje ya exisite!");
        }

        Usuario usuario = usuarioRepository.findById(temaDto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));

        try {
            tema = new Tema();
            tema.setTitulo(temaDto.getTitulo());
            tema.setMensaje(temaDto.getMensaje());
            tema.setGenero(temaDto.getGenero());
            tema.setCreatedAt(LocalDateTime.now());
            tema.setUsuarioId(usuario);
            tema.setActivo(Boolean.TRUE);
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR CREACION TEMA: Falla no es posible realizar el proceso!", e);
        }

       return temaRepository.save(tema);
    }

    public Tema update(Integer id, TemaActualizarDTO temaActualizarDTO) {
        Tema tema = findById(id);

        boolean tituloExiste = temaRepository.existsByTituloAndIdNot(temaActualizarDTO.getTitulo(), id);
        boolean mensajeExiste = temaRepository.existsByMensajeAndIdNot(temaActualizarDTO.getMensaje(), id);

        if (tituloExiste ) {
            throw new BadRequestExcepton("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new BadRequestExcepton("El mensaje ya exisite!");
        }

        try{
            if (tema != null){
                tema.setTitulo(temaActualizarDTO.getTitulo());
                tema.setMensaje(temaActualizarDTO.getMensaje());
                tema.setGenero(temaActualizarDTO.getGenero());
                tema.setUpdatedAt(LocalDateTime.now());
            }else {
                throw new BadRequestExcepton("ERROR ACTUALIZAR: No se pudo actualizar tema!");
            }
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        return temaRepository.save(tema);
    }

    @Override
    public Boolean delete(Integer id) {
        temaRepository.deleteById(id);
        return true;
    }
}
