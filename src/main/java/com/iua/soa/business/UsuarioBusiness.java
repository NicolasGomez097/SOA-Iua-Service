package com.iua.soa.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Usuario;
import com.iua.soa.repository.RedisRepository;
import com.iua.soa.repository.UsuarioRepository;

@Service
public class UsuarioBusiness implements IUsuarioBusiness{
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private RedisRepository redis;


	@Override
	public boolean crearUsuario(Usuario usuario) throws BusinessException,BadRequestException {
		validateUsuario(usuario);
		try{
			repo.save(usuario);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		return true;		
	}


	@Override
	public Usuario getUsuario(Integer id) throws BusinessException, NotFoundException {
		Optional<Usuario> op;
		String userJSon = null;
		Gson parser = new Gson();
		
		userJSon = redis.get(id.toString());
		
		if(userJSon != null) {
			Usuario user = parser.fromJson(userJSon, Usuario.class);
			return user;
		}
			
		try {			
			op = repo.findById(id);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}	
		if(!op.isPresent())
			throw new NotFoundException("No existe el usuario con id " + id);
		
		userJSon = parser.toJson(op.get(), Usuario.class);
		redis.insert(id.toString(), userJSon);
		
		return op.get();		
	}
	
	@Override
	public Usuario getUsuarioOnlyDB(Integer id) throws BusinessException, NotFoundException {
		Optional<Usuario> op;
			
		try {			
			op = repo.findById(id);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}	
		if(!op.isPresent())
			throw new NotFoundException("No existe el usuario con id " + id);
				
		return op.get();		
	}

	@Override
	public Usuario getUsuarioByDNI(Integer dni) throws BusinessException,NotFoundException {
		Optional<Usuario> op;
		String userJSon = null;
		Gson parser = new Gson();
		
		userJSon = redis.get(dni.toString());
		
		if(userJSon != null) {
			Usuario user = parser.fromJson(userJSon, Usuario.class);
			return user;
		}
			
		try {			
			op = repo.findByDni(dni);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}	
		if(!op.isPresent())
			throw new NotFoundException("No existe el usuario con dni " + dni);
		
		userJSon = parser.toJson(op.get(), Usuario.class);
		redis.insert(dni.toString(), userJSon);
		
		return op.get();	
	}
	
	@Override
	public Usuario getUsuarioByDNIOnlyDB(Integer dni) throws BusinessException,NotFoundException {
		Optional<Usuario> op;
		
		try {			
			op = repo.findByDni(dni);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		}	
		if(!op.isPresent())
			throw new NotFoundException("No existe el usuario con dni " + dni);
				
		return op.get();		
	}

	@Override
	public List<Usuario> getUsuarios() throws BusinessException {
		List<Usuario> list = repo.findAll();
		return list;
	}	
	
	private void validateUsuario (Usuario usuario) throws BadRequestException {
		if(usuario.getApellido() == null)
			throw new BadRequestException("El campo apellido es obligatorio.");
		if(usuario.getApellido().length() == 0)
			throw new BadRequestException("El campo apellido no puede ser vacio.");
		if(usuario.getDni() == null)
			throw new BadRequestException("El campo dni es obligatorio.");
		if(usuario.getDni() < 1)
			throw new BadRequestException("El campo dni no puede ser menor a 1.");
		if(usuario.getNombre() == null)
			throw new BadRequestException("El campo nombre es obligatorio.");
		if(usuario.getNombre().length() == 0)
			throw new BadRequestException("El campo nombre no puede ser vacio");
	}
}
