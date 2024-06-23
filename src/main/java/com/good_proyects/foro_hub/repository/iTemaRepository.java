package com.good_proyects.foro_hub.repository;
import com.good_proyects.foro_hub.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iTemaRepository extends JpaRepository<Tema,Integer> {
    boolean existsByTitulo(String titulo);
    boolean existsByTituloAndIdNot(String titulo ,Integer id);
    boolean existsByMensaje(String mensaje);
    boolean existsByMensajeAndIdNot(String mensaje, Integer id);
}
