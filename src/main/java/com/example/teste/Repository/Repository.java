package com.example.teste.Repository;

import com.example.teste.Entidade.Estado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Estado, Long> {
}