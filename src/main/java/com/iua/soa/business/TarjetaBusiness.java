package com.iua.soa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Tarjeta;
import com.iua.soa.repository.TarjetaRepository;

@Service
public class TarjetaBusiness implements ITarjetaBusiness{
	
	@Autowired
	private TarjetaRepository repo;

	//private Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public boolean crearTarjeta(Tarjeta tarjeta) throws BusinessException {
		try{
			repo.save(tarjeta);
		}catch (Exception e) {
			return false;
		}
		return true;			
	}


	@Override
	public Tarjeta getTarjeta(int id) throws BusinessException, NotFoundException {
		return null;
	}


	@Override
	public List<Tarjeta> getTarjetas() throws BusinessException {
		List<Tarjeta> list = repo.findAll();
		return list;
	}	
	
}
