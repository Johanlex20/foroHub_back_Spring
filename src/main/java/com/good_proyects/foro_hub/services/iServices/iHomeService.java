package com.good_proyects.foro_hub.services.iServices;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.dtos.Genero;
import java.time.LocalDate;
import java.util.List;

public interface iHomeService {

    List<Tema> findByGenero(Genero genero);
    List<Tema> getTemasByDate(LocalDate localDate);


}
