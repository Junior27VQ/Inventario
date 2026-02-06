package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class Producto {
	private int codigo;
	private String nombre;
	private UnidadDeMedida unidadDeMedida;
	private BigDecimal precioVenta;
	private boolean tieneIVA;
	private BigDecimal coste;
	private Categoria categoria;
	private int stock;
	
	
	public Producto() {
		super();
	}
	public Producto(int codigo, String nombre, UnidadDeMedida unidadDeMedida, BigDecimal precioVenta, boolean tieneIVA,
			BigDecimal coste, Categoria categoria, int stock) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.unidadDeMedida = unidadDeMedida;
		this.precioVenta = precioVenta;
		this.tieneIVA = tieneIVA;
		this.coste = coste;
		this.categoria = categoria;
		this.stock = stock;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public UnidadDeMedida getUnidadDeMedida() {
		return unidadDeMedida;
	}
	public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public boolean getTieneIVA() {
		return tieneIVA;
	}
	public void setTieneIVA(boolean tieneIVA) {
		this.tieneIVA = tieneIVA;
	}
	public BigDecimal getCoste() {
		return coste;
	}
	public void setCoste(BigDecimal coste) {
		this.coste = coste;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", unidadDeMedida=" + unidadDeMedida
				+ ", precioVenta=" + precioVenta + ", tieneIVA=" + tieneIVA + ", coste=" + coste + ", categoria="
				+ categoria + ", stock=" + stock + "]";
	}
	

}
