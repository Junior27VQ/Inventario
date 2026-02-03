package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
	private String codigo;
	private String descripcion;
	private CategoriaUDM categoriasUDM;
	
	
	public UnidadDeMedida() {
		super();
	}
	
	public UnidadDeMedida(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public UnidadDeMedida(String codigo, String nombre, String descripcion, CategoriaUDM categoriasUDM) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriasUDM = categoriasUDM;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUDM getCategoriasUDM() {
		return categoriasUDM;
	}
	public void setCategoriasUDM(CategoriaUDM categoriasUDM) {
		this.categoriasUDM = categoriasUDM;
	}
	@Override
	public String toString() {
		return "UnidadDeMedida [codigo=" + codigo + ", descripcion=" + descripcion
				+ ", categoriasUDM=" + categoriasUDM + "]";
	}
	
}
