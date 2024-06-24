package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.dtos.Genero;
import com.good_proyects.foro_hub.services.iServices.iHomeService;
import java.time.LocalDate;
import java.util.List;

public class HomeService implements iHomeService {


    @Override
    public List<Tema> findByGenero(Genero genero) {
        return null;
    }

    @Override
    public List<Tema> getTemasByDate(LocalDate localDate) {
        return null;
    }
}
