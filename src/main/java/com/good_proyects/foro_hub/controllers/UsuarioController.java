package com.good_proyects.foro_hub.controllers;
import com.good_proyects.foro_hub.models.Usuario;
import com.good_proyects.foro_hub.services.iServices.iUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    private iUsuarioServices usuarioServices;

    @GetMapping(value = "/list")
    private List<Usuario> findAll(){
        return usuarioServices.findAll();
    }

    @GetMapping
    public Page<Usuario> paginate(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC, size = 2) Pageable pageable) {
        return usuarioServices.paginate(pageable);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Usuario findById(@PathVariable(value = "id") Integer id){
        return usuarioServices.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Usuario save(@RequestBody Usuario usuario){
        return usuarioServices.save(usuario);
    }

    @PutMapping(value = "/{id}")

    private Usuario update(@PathVariable(value = "id") Integer id,@RequestBody Usuario usuario){
        return usuarioServices.update(id, usuario);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Boolean delete(@PathVariable(value = "id") Integer id){
        return usuarioServices.delete(id);
    }

}
