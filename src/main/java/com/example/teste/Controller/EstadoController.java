package com.example.teste.Controller;

import java.util.List;

import com.example.teste.DAO.Repository;
import com.example.teste.Entidade.Estado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class EstadoController {

    @Autowired
    Repository repository;

    @GetMapping("status")
    public List<Estado> lista(){
        return repository.findAll();
    }

    //@GetMapping("porEstado/{id}")
    //public Estado listaEstados(@PathVariable(value="estaUf") Long id){
        //return repository.findById(id)
    //}

    @PostMapping("status")
    public Estado salva(@RequestBody Estado estado){
        return repository.save(estado);
    }

}