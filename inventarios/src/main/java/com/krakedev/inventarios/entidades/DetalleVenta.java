package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVenta {
	private int codigo;
	private Ventas venta;
	private Producto producto;
	private int cantidad;
	private BigDecimal precio;
	private BigDecimal subTotal;
	private BigDecimal subTotalIVA;
	
	public DetalleVenta() {
		super();
	}
	public DetalleVenta(int codigo, Ventas venta, Producto producto, int cantidad, BigDecimal precio,
			BigDecimal subTotal, BigDecimal subTotalIVA) {
		super();
		this.codigo = codigo;
		this.venta = venta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.subTotal = subTotal;
		this.subTotalIVA = subTotalIVA;
	}

	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Ventas getVenta() {
		return venta;
	}
	public void setVenta(Ventas venta) {
		this.venta = venta;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	public BigDecimal getSubTotalIVA() {
		return subTotalIVA;
	}
	public void setSubTotalIVA(BigDecimal subTotalIVA) {
		this.subTotalIVA = subTotalIVA;
	}
	@Override
	public String toString() {
		return "DetalleVenta [codigo=" + codigo + ", venta=" + venta + ", producto=" + producto + ", cantidad="
				+ cantidad + ", precio=" + precio + ", subTotal=" + subTotal + ", subTotalIVA=" + subTotalIVA + "]";
	}
	
}
