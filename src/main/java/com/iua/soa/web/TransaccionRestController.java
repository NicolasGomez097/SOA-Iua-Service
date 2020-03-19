package com.iua.soa.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iua.soa.business.ITransaccionBusiness;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Transaccion;
import com.iua.soa.utils.Constantes;

@RestController
@RequestMapping(Constantes.URL_PROYECTO)
public class TransaccionRestController {
	
	@Autowired
	private ITransaccionBusiness transaccionBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearTransaccion(@RequestBody Transaccion transaccion){
		
		try {
			transaccionBusiness.crearTransaccion(transaccion);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("id_transaccion", transaccion.getId().toString());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
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
