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

import com.iua.soa.business.IUsuarioBusiness;
import com.iua.soa.exeptions.BadRequestException;
import com.iua.soa.exeptions.BusinessException;
import com.iua.soa.exeptions.NotFoundException;
import com.iua.soa.model.Usuario;
import com.iua.soa.utils.Constantes;

@RestController
@RequestMapping(Constantes.URL_USUARIO)
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioBusiness usuarioBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearTransaccion(@RequestBody Usuario usuario){
		
		try {
			usuarioBusiness.crearUsuario(usuario);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("id_transaccion", usuario.getId().toString());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (BadRequestException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Usuario>> getTransacciones(){
		try {			
			return new ResponseEntity<List<Usuario>>(usuarioBusiness.getUsuarios(),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Usuario>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> getTransaccion(@PathVariable("id") int idUsuario) {
		try {
			Usuario t = usuarioBusiness.getUsuario(idUsuario);
			return new ResponseEntity<Usuario>(t,HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
}
