package com.iua.soa.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iua.soa.business.ITarjetaBusiness;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Tarjeta;
import com.iua.soa.utils.Constantes;

@RestController
@RequestMapping(Constantes.URL_TARJETA)
public class TarjetaRestController {
	
	@Autowired
	private ITarjetaBusiness tarjetaBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearTarjeta(@RequestBody Tarjeta tarjeta){
		
		try {
			tarjetaBusiness.crearTarjeta(tarjeta);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("id_transaccion", tarjeta.getId().toString());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Tarjeta>> getTransacciones(){
		try {			
			return new ResponseEntity<List<Tarjeta>>(tarjetaBusiness.getTarjetas(),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Tarjeta>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Tarjeta> getTransaccion(@PathVariable("id") int idTarjeta) {
		try {
			Tarjeta t = tarjetaBusiness.getTarjeta(idTarjeta);
			return new ResponseEntity<Tarjeta>(t,HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Tarjeta>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Tarjeta>(HttpStatus.NOT_FOUND);
		}
	}
}
