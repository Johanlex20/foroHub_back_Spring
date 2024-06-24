package com.good_proyects.foro_hub.controllers;
import com.good_proyects.foro_hub.models.Respuesta;
import com.good_proyects.foro_hub.services.iServices.iRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/respuesta")
public class RespuestaController {

    @Autowired
    private iRespuestaService respuestaService;

    @GetMapping("/{id}")
    Respuesta findById(@PathVariable(value = "id") Integer id){
        return respuestaService.findById(id);
    }

}
