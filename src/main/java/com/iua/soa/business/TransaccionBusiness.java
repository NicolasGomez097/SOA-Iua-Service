package com.iua.soa.business;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Tarjeta;
import com.iua.soa.model.Transaccion;
import com.iua.soa.model.dto.ValidarTarjetaDTO;
import com.iua.soa.repository.TarjetaRepository;
import com.iua.soa.repository.TransaccionRepository;
import com.iua.soa.utils.DateUtil;
import com.iua.soa.utils.HttpUtils;

@Service
public class TransaccionBusiness implements ITransaccionBusiness{
	
	@Autowired
	private TransaccionRepository repo;
	
	@Autowired
	private TarjetaRepository repoTarjeta;

	@Override
	public Transaccion crearTransaccion(Transaccion transaccion) throws BusinessException,BadRequestException {
		validateTransaccion(transaccion);
		try{
			
			Optional<Tarjeta> tarjeta = repoTarjeta.findById(transaccion.getTarjeta().getId());
			Transaccion.Estado estado = getEstado(tarjeta.get().getNumero()
					,transaccion.getMonto());
			if(estado == null)
				throw new BadRequestException("Numero de tarjeta no valido");
			transaccion.setEstado(estado);
			transaccion.setFecha(DateUtil.getFechaActual());
			transaccion.setHora(DateUtil.getHoraActual());
			
			repo.save(transaccion);
		}catch (Exception e) {
			if(e instanceof BadRequestException)
				throw e;
			
			throw new BusinessException();
		}
		return transaccion;			
	}


	@Override
	public Transaccion getTranasccion(int id) throws BusinessException, NotFoundException {
		Optional<Transaccion> op;
		try {
			op = repo.findById(id);
		}catch (Exception e) {
			throw new BusinessException();
		}
		if(!op.isPresent())
			throw new NotFoundException("No se encontro la transaccion con id " + id);
		
		return op.get();
	}


	@Override
	public List<Transaccion> getTranascciones() throws BusinessException {
		List<Transaccion> list = repo.findAll();
		return list;
	}	
	
	private Transaccion.Estado getEstado(Integer tarjetaNumero, Float monto) {
		String res_txt = "";
		HttpUtils.HTTPResponse response = null;
		ValidarTarjetaDTO dto = new ValidarTarjetaDTO(tarjetaNumero, monto);
		res_txt = new Gson().toJson(dto, ValidarTarjetaDTO.class);
		
		try {
			response = HttpUtils.postMethod(
					"https://iua-service.herokuapp.com/autorizar", 
					res_txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response == null || response.getResponseCode() != 201) 
			return null;
		
		
		JSONObject json = new JSONObject(response.getBody());
		String estado = json.getString("estado");
		return Transaccion.Estado.valueOf(estado);
	}
	
	private void validateTransaccion (Transaccion transaccion) throws BadRequestException {
		if(transaccion.getDescripcion() != null && transaccion.getDescripcion().length() == 0) 
			throw new BadRequestException("El campo descripccion no puede tener un texto vacio, no se debe editarlo para mandarlo vacio.");
		
		if(transaccion.getMonto() == null) 
			throw new BadRequestException("El campo monto es obligatorio.");
		
		if(transaccion.getMonto() < 1.0f) 
			throw new BadRequestException("El campo monto debe ser mayor o igual a 1.0 .");
		
		if(transaccion.getTarjeta() == null) 
			throw new BadRequestException("La campo tarjeta es obligatorio.");
		
		if(transaccion.getTarjeta().getId() == null) 
			throw new BadRequestException("La campo tarjeta.id es obligatorio.");
		
		
		
		Optional<Tarjeta> tarjeta = repoTarjeta.findById(transaccion.getTarjeta().getId());
		if(!tarjeta.isPresent())
			throw new BadRequestException("No existe la tarjeta con id " + 
					transaccion.getTarjeta().getId());
		
		

	}
	
}
