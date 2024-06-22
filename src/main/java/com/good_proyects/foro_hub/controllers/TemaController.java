package com.good_proyects.foro_hub.controllers;
import com.good_proyects.foro_hub.models.Tema;
import com.good_proyects.foro_hub.models.dtos.TemaActualizarDTO;
import com.good_proyects.foro_hub.models.dtos.TemaDto;
import com.good_proyects.foro_hub.services.iServices.iTemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/topicos")
public class TemaController {

    @Autowired
    private iTemaService temaService;

    @GetMapping(value = "/list")
    private List<Tema> findAll(){
        return temaService.findAll();
    }

    @GetMapping
    private Page<Tema> paginate(@PageableDefault(sort = "titulo",direction = Sort.Direction.ASC ,size = 5) Pageable pageable){
        return temaService.paginate(pageable);
    }

    @GetMapping(value = "/{id}")
    private Tema findById(@PathVariable(value = "id") Integer id){
        return temaService.findById(id);
    }

    @PostMapping
    private Tema save(@RequestBody TemaDto temaDto){
        return temaService.save(temaDto);
    }

    @PutMapping(value = "/{id}")
    private Tema update(@PathVariable(value = "id") Integer id, @RequestBody TemaActualizarDTO temaActualizarDTO){
        return temaService.update(id,temaActualizarDTO);
    }

    @DeleteMapping(value = "/{id}")
    private Boolean delete(@PathVariable(value = "id") Integer id){
        return temaService.delete(id);
    }

}