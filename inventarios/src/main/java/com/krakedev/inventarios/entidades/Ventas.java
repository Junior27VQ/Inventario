package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Ventas {
	private int codigo;
	private Date fecha;
	private BigDecimal totalSinIVA;
	private BigDecimal iva;
	private BigDecimal total;
	private ArrayList<DetalleVenta> detalles;
	
	public Ventas() {
		this.detalles=new ArrayList<>();
		this.totalSinIVA = BigDecimal.ZERO;
		this.iva = BigDecimal.ZERO;
		this.total = BigDecimal.ZERO;
		
	}
	public Ventas(int codigo, Date fecha, BigDecimal totalSinIVA, BigDecimal iva, BigDecimal total) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.totalSinIVA = totalSinIVA;
		this.iva = iva;
		this.total = total;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getTotalSinIVA() {
		return totalSinIVA;
	}
	public void setTotalSinIVA(BigDecimal totalSinIVA) {
		this.totalSinIVA = totalSinIVA;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public ArrayList<DetalleVenta> getDetalles() {
		return detalles;
	}
	public void setDetalles(ArrayList<DetalleVenta> detalles) {
		this.detalles = detalles;
	}
	@Override
	public String toString() {
		return "Ventas [codigo=" + codigo + ", fecha=" + fecha + ", totalSinIVA=" + totalSinIVA + ", iva=" + iva
				+ ", total=" + total + "]";
	}
	

}
