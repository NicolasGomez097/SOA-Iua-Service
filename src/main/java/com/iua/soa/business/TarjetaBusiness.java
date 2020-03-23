package com.iua.soa.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Tarjeta;
import com.iua.soa.model.Usuario;
import com.iua.soa.repository.TarjetaRepository;
import com.iua.soa.repository.UsuarioRepository;
import com.iua.soa.utils.DateUtil;

@Service
public class TarjetaBusiness implements ITarjetaBusiness{
	
	@Autowired
	private TarjetaRepository repo;
	
	@Autowired
	private UsuarioRepository repoUsuario;

	

	@Override
	public boolean crearTarjeta(Tarjeta tarjeta) throws BusinessException, BadRequestException {
		validateTarjeta(tarjeta);
		
		try {
			repo.save(tarjeta);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		return true;			
	}


	@Override
	public Tarjeta getTarjeta(int id) throws BusinessException, NotFoundException {
		Optional<Tarjeta> op;
		try {			
			op = repo.findById(id);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}	
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarjeta con id " + id);
		
		return op.get();		
	}
	
	@Override
	public Tarjeta getTarjetaByNumero(int id) throws BusinessException, NotFoundException {
		Optional<Tarjeta> op;
		try {			
			op = repo.findByNumero(id);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}	
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarjeta con numero " + id);
		
		return op.get();	
	}


	@Override
	public List<Tarjeta> getTarjetas() throws BusinessException {
		List<Tarjeta> list = repo.findAll();
		return list;
	}	
	
	private void validateTarjeta(Tarjeta tarjeta) throws BadRequestException {
		if (tarjeta.getCodigoSeguridad() == null)
			throw new BadRequestException("El codigo de seguridad es obligatorio.");
		
		if (tarjeta.getCodigoSeguridad() > 999 ||
				tarjeta.getCodigoSeguridad() < 100)
			throw new BadRequestException("El codigo de seguridad nos es valido, debe tener 3 digitos sin empezar por 0.");
		
		if (tarjeta.getNumero() == null)
			throw new BadRequestException("El número de tarjeta es obligatorio.");
		
		Optional<Tarjeta> opTarjeta = repo.findByNumero(tarjeta.getNumero());	
		
		if(opTarjeta.isPresent())
			throw new BadRequestException("Ya existe la tarjeta con ese numero.");
		
		if (tarjeta.getNumero() < 1) 
			throw new BadRequestException("El número de tarjeta no puede ser menor a 1.");
		
		if (tarjeta.getFechaVencimiento() == null)
			throw new BadRequestException("La fecha de vencimiento de la tarjeta es obligatorio.");
		
		if(!DateUtil.esFechaValidaAñoMes(tarjeta.getFechaVencimiento()))
			throw new BadRequestException("La fecha de vencimiento debe tener el formato 'yyyy-MM'.");
		
		int compare = tarjeta.getFechaVencimiento().compareTo(DateUtil.getFechaActual());
		if (compare  <= 0)
			throw new BadRequestException("La tarjeta esta vencida.");
		
		if (tarjeta.getUsuario() == null)
			throw new BadRequestException("El campo usuario es obligatorio.");
		
		if (tarjeta.getUsuario().getId() == null)
			throw new BadRequestException("El campo usuario.id  es obligatorio.");
		
		Optional<Usuario> opUsuario = repoUsuario.findById(tarjeta.getUsuario().getId());	
		
		if(!opUsuario.isPresent())
			throw new BadRequestException("No existe el usuario con ese id.");
	}
	
}
