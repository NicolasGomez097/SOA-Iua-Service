package com.iua.soa.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Transaccion;
import com.iua.soa.repository.TransaccionRepository;

@Service
public class TransaccionBusiness implements ITransaccionBusiness{
	
	@Autowired
	private TransaccionRepository repo;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public boolean crearTransaccion(Transaccion transaccion) throws BusinessException {
		return false;		
	}


	@Override
	public Transaccion getTranasccion(int id) throws BusinessException, NotFoundException {
		return null;
	}


	@Override
	public List<Transaccion> getTranascciones() throws BusinessException {
		return null;
	}	
	
}
