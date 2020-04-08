package com.iua.soa.business;

import java.util.List;

import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Usuario;	

public interface IUsuarioBusiness {
	public boolean crearUsuario(Usuario usuario) throws BusinessException,BadRequestException;
	public Usuario getUsuario(Integer id) throws BusinessException,NotFoundException;
	public Usuario getUsuarioOnlyDB(Integer id) throws BusinessException,NotFoundException;
	public Usuario getUsuarioByDNI(Integer dni) throws BusinessException,NotFoundException;
	public Usuario getUsuarioByDNIOnlyDB(Integer dni) throws BusinessException,NotFoundException;
	public List<Usuario> getUsuarios() throws BusinessException;
}
