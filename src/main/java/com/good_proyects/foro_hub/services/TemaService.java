package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.exceptions.BadRequestExcepton;
import com.good_proyects.foro_hub.exceptions.ResourceNotFoundException;
import com.good_proyects.foro_hub.models.Respuesta;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.*;
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
import java.util.stream.Collectors;

@Service
public class TemaService implements iTemaService {

    @Autowired
    private iTemaRepository temaRepository;

    @Autowired
    private iUsuarioRepository usuarioRepository;

    @Override
    public List<TemaDto> findAll() {
        List<Tema>temas = temaRepository.findAll();
        return temas.stream()
                .map(this::manejoRespuestaCliente)// Transforma cada Tema a TemaDto reducido
                .collect(Collectors.toList());
    }

    @Override
    public Page<TemaDto> paginate(Pageable pageable) {
        Page<Tema> temas = temaRepository.findAll(pageable);
        return temas.map(this::manejoRespuestaCliente);
    }

    public TemaDto findById(Integer id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado con ID: " + id));
        return manejoRespuestaCliente(tema);
    }

    public TemaDto save(TemaDto temaDto) {
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

            tema = temaRepository.save(tema);
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR CREACION TEMA: Falla no es posible realizar el proceso!", e);
        }
        return manejoRespuestaCliente(tema);
    }

    public TemaDto update(Integer id, TemaActualizarDTO temaActualizarDTO) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado con ID: " + id));

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
                tema = temaRepository.save(tema);
            }else {
                throw new BadRequestExcepton("ERROR ACTUALIZAR: No se pudo actualizar tema!");
            }
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }

        return manejorRespuestaClienteCorta(tema);
    }

    @Override
    public Boolean delete(Integer id) {
        temaRepository.deleteById(id);
        return true;
    }

    private TemaDto manejoRespuestaCliente(Tema tema) {
        TemaDto temaDto = new TemaDto();
        temaDto.setId(tema.getId());
        temaDto.setTitulo(tema.getTitulo());
        temaDto.setMensaje(tema.getMensaje());
        temaDto.setGenero(tema.getGenero());
        temaDto.setUsuarioId(tema.getUsuarioId().getId()); // Aquí solo se asigna el ID del usuario
        temaDto.setUsuarioNombre(tema.getUsuarioId().getNombre()); // Aquí solo se asigna el nombre del usuario
        temaDto.setCreatedAt(tema.getCreatedAt());
        temaDto.setUpdatedAt(tema.getUpdatedAt());
        temaDto.setActivo(tema.getActivo());
        temaDto.setRespuestas(tema.getRespuestas());
        // No se establecen las respuestas aquí, ya que son datos reducidos

        return temaDto;
    }

    private TemaDto manejorRespuestaClienteCorta(Tema tema) {
        TemaDto temaDto = new TemaDto();
        temaDto.setId(tema.getId());
        temaDto.setTitulo(tema.getTitulo());
        temaDto.setMensaje(tema.getMensaje());
        temaDto.setGenero(tema.getGenero());
        temaDto.setUsuarioId(tema.getUsuarioId().getId());
        temaDto.setUsuarioNombre(tema.getUsuarioId().getNombre());
        temaDto.setCreatedAt(tema.getCreatedAt());
        temaDto.setUpdatedAt(tema.getUpdatedAt());
        temaDto.setActivo(tema.getActivo());
        //temaDto.setRespuestas(tema.getRespuestas()); // Aquí puedes ajustar según tus necesidades

        return temaDto;
    }

}
