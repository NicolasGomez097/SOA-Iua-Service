package com.iua.soa.business;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Tarjeta;
import com.iua.soa.model.Transaccion;
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

	//private Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public boolean crearTransaccion(Transaccion transaccion) throws BusinessException,BadRequestException {
		try{
			Optional<Tarjeta> tarjeta = repoTarjeta.findById(transaccion.getTarjeta().getId());
			if(!tarjeta.isPresent())
				throw new BadRequestException("No existe la tarjeta con id " + 
						transaccion.getTarjeta().getId());
			
			Transaccion.Estado estado = getEstado(tarjeta.get().getNumero()
					,transaccion.getMonto());
			if(estado == null)
				throw new BadRequestException("Numero de tarjeta no valido");
			
			transaccion.setEstado(estado);
			transaccion.setFecha(DateUtil.getFechaActual());
			transaccion.setHora(DateUtil.getHoraActual());
			repo.save(transaccion);
		}catch (Exception e) {
			return false;
		}
		return true;			
	}


	@Override
	public Transaccion getTranasccion(int id) throws BusinessException, NotFoundException {
		return null;
	}


	@Override
	public List<Transaccion> getTranascciones() throws BusinessException {
		List<Transaccion> list = repo.findAll();
		return list;
	}	
	
	public Transaccion.Estado getEstado(Integer tarjetaNumero, Float monto) {
		String res_txt = "";
		res_txt = String.format(
				"{\"tarjeta\": {\"numero\": \"%d\" }, \"monto\": %.2f}",
				tarjetaNumero,
				monto);
		try {
			res_txt = HttpUtils.postMethod(
					"https://iua-service.herokuapp.com/autorizar", 
					res_txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res_txt == null)
			return null;
		
		JSONObject json = new JSONObject(res_txt);
		String estado = json.getString("estado");
		if(estado.equals(Transaccion.Estado.APROBADA.toString()))
			return Transaccion.Estado.APROBADA;
	
		return Transaccion.Estado.RECHAZADA;
	}
	
}
