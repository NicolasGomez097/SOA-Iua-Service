package com.iua.soa.model.dto;


public class ValidarTarjetaDTO {
	
	public ValidarTarjetaDTO(Integer tarjetaNumero, Float monto) {
		this.tarjeta = new TarjetaDTO(tarjetaNumero.toString());
		this.monto = monto;
	}
	
	private Float monto;
	private TarjetaDTO tarjeta;

	public class TarjetaDTO {
		private String numero;
		
		public TarjetaDTO(String numero) {
			this.numero = numero.toString();
		}
		
		public String getNumero() {
			return numero;
		}
		
		public void setNumero(String numero) {
			this.numero = numero;
		}
	}
	
	public TarjetaDTO getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(TarjetaDTO tarjeta) {
		this.tarjeta = tarjeta;
	}
	

	public Float getMonto() {
		return this.monto;
	}
	public void setMonto(Float monto) {
		this.monto = monto;
	} 
}
