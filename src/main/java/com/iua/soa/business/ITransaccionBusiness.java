package com.iua.soa.business;

import java.util.List;

import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Transaccion;	

public interface ITransaccionBusiness {
	public boolean crearTransaccion(Transaccion transaccion) throws BusinessException,BadRequestException;
	public Transaccion getTranasccion(int id) throws BusinessException,NotFoundException;
	public List<Transaccion> getTranascciones() throws BusinessException;
}
