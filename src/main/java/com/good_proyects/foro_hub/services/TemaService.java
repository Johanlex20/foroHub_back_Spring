package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.models.dtos.TemaActualizarDTO;
import com.good_proyects.foro_hub.models.dtos.TemaDto;
import com.good_proyects.foro_hub.repository.iTemaRepository;
import com.good_proyects.foro_hub.repository.iUsuarioRepository;
import com.good_proyects.foro_hub.services.iServices.iTemaService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return temaRepository.findById(id).orElseThrow(()-> new RuntimeException("ERROR ID: id no encontrado en la base de datos!"));
    }

    @Override
    public Tema save(TemaDto temaDto) {

        // Validacion de campos nulos
        if (temaDto.getTitulo() == null || temaDto.getMensaje() == null || temaDto.getGenero() == null || temaDto.getUsuarioId() == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }

        Usuario usuario = usuarioRepository.findById(temaDto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Tema tema = new Tema();
        tema.setTitulo(temaDto.getTitulo());
        tema.setMensaje(temaDto.getMensaje());
        tema.setGenero(temaDto.getGenero());
        tema.setCreatedAt(LocalDateTime.now());
        tema.setUsuarioId(usuario);
        tema.setActivo(Boolean.TRUE);
        return temaRepository.save(tema);
    }


    public Tema update(Integer id, TemaActualizarDTO temaActualizarDTO) {
        Tema actualizarTema = findById(id);

        actualizarTema.setTitulo(temaActualizarDTO.getTitulo());
        actualizarTema.setMensaje(temaActualizarDTO.getMensaje());
        actualizarTema.setGenero(temaActualizarDTO.getGenero());
        actualizarTema.setUpdatedAt(LocalDateTime.now());

        return temaRepository.save(actualizarTema);
    }

    @Override
    public Boolean delete(Integer id) {
        temaRepository.deleteById(id);
        return true;
    }
}
