package com.example.teste.DAO;

import com.example.teste.Entidade.Estado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Estado, Long> {

	//Estado findByEstado(Long id);

    

}