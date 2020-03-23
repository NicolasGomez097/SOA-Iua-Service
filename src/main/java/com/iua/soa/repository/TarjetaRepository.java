package com.iua.soa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iua.soa.model.Tarjeta;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer>{
	Optional<Tarjeta> findById(int id);
	Optional<Tarjeta> findByNumero(int numero);
}
