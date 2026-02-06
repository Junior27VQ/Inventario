package com.krakedev.inventarios.entidades;

public class EstadoPedido {
	private String codogo;
	private String descripcion;
	
	
	public EstadoPedido() {
		super();
	}
	public EstadoPedido(String codogo, String descripcion) {
		super();
		this.codogo = codogo;
		this.descripcion = descripcion;
	}
	public String getCodogo() {
		return codogo;
	}
	public void setCodogo(String codogo) {
		this.codogo = codogo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "EstadoPedido [codogo=" + codogo + ", descripcion=" + descripcion + "]";
	}
	

}
