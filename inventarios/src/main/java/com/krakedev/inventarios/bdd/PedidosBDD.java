package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {
	public void insertar(Pedido pedido) throws KrakeDevException {
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		ResultSet claves=null;
		int codigo=0;
		
		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("insert into cabecera_pedido(proveedor,fecha,estado)"
			    +" values(?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");
			
			ps.executeUpdate();
			
			claves=ps.getGeneratedKeys();
			if (claves.next()) {
				codigo=claves.getInt(1);
			}
			ArrayList<DetallePedido> detallesPedido=pedido.getDetalles();
			DetallePedido det;
			for (int i=0;i<detallesPedido.size();i++) {
				det=detallesPedido.get(i);
				psDet=con.prepareStatement("insert into detalle_pedido(cabecera_pdd,producto,cantidad_solicitada,subtotal,cantidad_recibida) "
						+ "values(?,?,?,?,?)");
				psDet.setInt(1, codigo);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				
				BigDecimal pv=det.getProducto().getPrecioVenta();
				BigDecimal cantidad=new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal=pv.multiply(cantidad);
				
				psDet.setBigDecimal(4, subtotal);
				psDet.setInt(5, det.getCantidadRecibida());
				
				psDet.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al insertar el proveedor. Detalles: "+e.getMessage());
		} catch (KrakeDevException e) {
			throw e;
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void actualizar(Pedido pedido) throws KrakeDevException {
		Connection con=null;
		PreparedStatement psP;
		PreparedStatement psD;
		
		try {
			con=ConexionBDD.coneccion();
			psP=con.prepareStatement("update cabecera_pedido "
					+ "set estado='R' "
					+ "where numero=?;");
			psP.setInt(1, pedido.getNumero());
			psP.executeUpdate();
			
			psD=con.prepareStatement("update detalle_pedido "
					+ "set cantidad_recibida=?, subtotal=? "
					+ "where codigo_dp=?;");
			ArrayList<DetallePedido> detallesPedido=pedido.getDetalles();
			DetallePedido det;
			
			for (int i=0;i<detallesPedido.size();i++) {
				det=detallesPedido.get(i);
				
				BigDecimal pv=det.getProducto().getPrecioVenta();
				BigDecimal cantidad=new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal=pv.multiply(cantidad);
				
				psD.setInt(1, det.getCantidadRecibida());
				psD.setBigDecimal(2, subtotal);
				psD.setInt(3, det.getCodigo());
				psD.addBatch();
			}
			psD.executeBatch();
			
			
		} catch (SQLException e) {
			// ps
			e.printStackTrace();
			throw new KrakeDevException("error cedula del cliente no existe. Detalles: "+e.getMessage());
		} catch (KrakeDevException e) {
			//parametro con
			throw e;
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
	
