package com.good_proyects.foro_hub.controllers;
import com.good_proyects.foro_hub.exceptions.ResourceNotFoundException;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.dtos.Genero;
import com.good_proyects.foro_hub.repository.iTemaRepository;
import com.good_proyects.foro_hub.services.iServices.iHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/home")
public class HomeController implements iHomeService {

    @Autowired
    private iTemaRepository temaRepository;

    @Override
    @GetMapping("/categoria/{genero}")
    public List<Tema> findByGenero(@PathVariable(value = "genero") Genero genero) {

        List<Tema> temas = temaRepository.findByGenero(genero);

        if (temas == null || temas.isEmpty()){
            throw new ResourceNotFoundException("Genero no encontrado!");
        }else {
            return temas;
        }
    }

    @GetMapping("/fecha/{date}")
    public List<Tema> getTemasByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Tema> temasPorFecha = temaRepository.findTemasByDate(date);

        if (temasPorFecha == null || temasPorFecha.isEmpty()){
            throw new ResourceNotFoundException("No hay Temas en la fecha ingresada!");
        }else {
            return temasPorFecha;
        }
    }
}
