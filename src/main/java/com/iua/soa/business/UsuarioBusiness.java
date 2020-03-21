package com.iua.soa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Usuario;
import com.iua.soa.repository.UsuarioRepository;

@Service
public class UsuarioBusiness implements IUsuarioBusiness{
	
	@Autowired
	private UsuarioRepository repo;

	//private Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public boolean crearUsuario(Usuario usuario) throws BusinessException,BadRequestException {
		if(usuario.getApellido() == null)
			throw new BadRequestException("El apellido no puede ser vacio.");
		if(usuario.getDni() == null)
			throw new BadRequestException("El dni no puede ser vacio.");
		if(usuario.getDni() < 0)
			throw new BadRequestException("El dni no puede ser negativo.");
		if(usuario.getApellido() == null)
			throw new BadRequestException("El apellido no puede ser vacio");
		if(usuario.getApellido() == null)
			throw new BadRequestException("El apellido no puede ser vacio");
		try{
			repo.save(usuario);
		}catch (Exception e) {
			return false;
		}
		return true;		
	}


	@Override
	public Usuario getUsuario(int id) throws BusinessException, NotFoundException {
		return null;
	}


	@Override
	public List<Usuario> getUsuarios() throws BusinessException {
		List<Usuario> list = repo.findAll();
		return list;
	}	
	
}
