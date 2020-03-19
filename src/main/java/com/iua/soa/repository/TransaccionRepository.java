package com.iua.soa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iua.soa.model.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer>{
	Optional<Transaccion> findById(int id);
}
