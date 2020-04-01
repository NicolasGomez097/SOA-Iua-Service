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

import com.iua.soa.business.ITransaccionBusiness;
import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Transaccion;
import com.iua.soa.utils.Constantes;

@RestController
@RequestMapping(Constantes.URL_TRANSACCION)
public class TransaccionRestController {
	
	@Autowired
	private ITransaccionBusiness transaccionBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearTransaccion(@RequestBody Transaccion transaccion){
		
		try {
			transaccionBusiness.crearTransaccion(transaccion);	
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("estado_transaccion", transaccion.getEstado().toString());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (BadRequestException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("error", e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("")
	public ResponseEntity<List<Transaccion>> getTransacciones(){
		try {			
			return new ResponseEntity<List<Transaccion>>(transaccionBusiness.getTranascciones(),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Transaccion>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Transaccion> getTransaccion(@PathVariable("id") int idTransaccion) {
		try {
			Transaccion t = transaccionBusiness.getTranasccion(idTransaccion);
			return new ResponseEntity<Transaccion>(t,HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Transaccion>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Transaccion>(HttpStatus.NOT_FOUND);
		}
	}
}
