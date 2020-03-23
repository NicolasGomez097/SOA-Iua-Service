package com.iua.soa.business;

import java.util.List;

import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Tarjeta;	

public interface ITarjetaBusiness {
	public boolean crearTarjeta(Tarjeta tarjeta) throws BusinessException, BadRequestException;
	public Tarjeta getTarjeta(int id) throws BusinessException,NotFoundException;
	public Tarjeta getTarjetaByNumero(int numero) throws BusinessException,NotFoundException;
	public List<Tarjeta> getTarjetas() throws BusinessException;
}
