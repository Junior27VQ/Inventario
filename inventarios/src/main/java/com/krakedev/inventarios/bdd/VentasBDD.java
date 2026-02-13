package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetalleVenta;
import com.krakedev.inventarios.entidades.Ventas;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;
//todavia falta probar y cumplir con los requerimientos que se piden en el reto
public class VentasBDD {
		public void insertar(Ventas venta) throws KrakeDevException {
			Connection con=null;
			PreparedStatement ps=null;
			PreparedStatement psDet=null;
			PreparedStatement psV=null;
			PreparedStatement psH=null;
			ResultSet claves=null;
			int codigo=0;
			
			Date fechaActual = new Date();
			java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
			Timestamp fechaHoraActual=new Timestamp(fechaActual.getTime());
			try {
				con=ConexionBDD.coneccion();
				ps=con.prepareStatement("insert into cabecera_ventas(fecha,costo,iva,total) "
				    +" values(?,0,0,0)",Statement.RETURN_GENERATED_KEYS);
				ps.setDate(1, fechaSQL);
				ps.executeUpdate();
				
				claves=ps.getGeneratedKeys();
				if (claves.next()) {
					codigo=claves.getInt(1);
				}
				psDet=con.prepareStatement("insert into detalle_venta(cabecera_vnt,producto,cantidad,precio,subtotal,subtotal_iva) "
						+ "values(?,?,?,?,?,?);");
				psH=con.prepareStatement("insert into historial_stock(fecha,referencia,producto,cantidad) "
						+ "values(?,?,?,?);");
				BigDecimal totalSinIva = BigDecimal.ZERO;
				BigDecimal totalIva= BigDecimal.ZERO;
				
				ArrayList<DetalleVenta> detalleVenta=venta.getDetalles();
				DetalleVenta det;
				for (int i=0;i<detalleVenta.size();i++) {
					det=detalleVenta.get(i);
					BigDecimal subtotalItem=det.getProducto().getPrecioVenta().multiply(new BigDecimal(det.getCantidad()));
					totalSinIva=totalSinIva.add(subtotalItem);
					if(det.getProducto().getTieneIVA()) {
						BigDecimal ivaItem=subtotalItem.multiply(new BigDecimal("0.12"));
						totalIva=totalIva.add(ivaItem);
					}
					BigDecimal subtotalConIva;
					if(det.getProducto().getTieneIVA()) {
						subtotalConIva=subtotalItem.multiply(new BigDecimal("1.12"));
					} else {
						subtotalConIva=subtotalItem;
					}
					det.setSubTotal(subtotalItem);
					det.setSubTotalIVA(subtotalConIva);
					//Insertar datos de detalle de ventas
					psDet.setInt(1, codigo);
					psDet.setInt(2, det.getProducto().getCodigo());
					psDet.setInt(3, det.getCantidad());
					psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
					psDet.setBigDecimal(5, det.getSubTotal());
					psDet.setBigDecimal(6, det.getSubTotalIVA());
					psDet.addBatch();
					//Insertar datos de Historial stock
					String referencia="VENTA"+codigo;
					psH.setTimestamp(1, fechaHoraActual);
					psH.setString(2, referencia);
					psH.setInt(3, det.getProducto().getCodigo());
					psH.setInt(4, det.getCantidad()*-1);
					psH.addBatch();
				}
				psDet.executeBatch();
				psH.executeBatch();
				
				BigDecimal totalVenta=totalSinIva.add(totalIva);
				psV=con.prepareStatement("update cabecera_ventas "
						+ "set costo=?, iva=?, total=? "
						+ "where codigo_cv=?;");
				psV.setBigDecimal(1, totalSinIva);
				psV.setBigDecimal(2, totalIva);
				psV.setBigDecimal(3, totalVenta);
				psV.setInt(4, codigo);
				psV.executeUpdate();
				
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
		/*esta parte esta incompleta
		public void actualizar(Ventas venta) throws KrakeDevException {
			Connection con = null;
			PreparedStatement psV = null;
			PreparedStatement psD = null;
			PreparedStatement psH = null;
			Date fechaActual = new Date();
			//java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
			Timestamp fechaHoraActual=new Timestamp(fechaActual.getTime());
			try {
				con=ConexionBDD.coneccion();
				psV=con.prepareStatement("update cabecera_ventas "
						+ "set costo=?, iva=?, total=? "
						+ "where codigo_cv=?;");
				psV.setBigDecimal(1, venta.getTotalSinIVA());
				psV.setBigDecimal(2, venta.getIva());
				psV.setBigDecimal(3, venta.getTotal());
				psV.setInt(4, venta.getCodigo());
				psV.executeUpdate();
				
				ArrayList<DetalleVenta> detallesVentas=venta.getDetalles();
				DetalleVenta det;
				for (int i=0;i<detallesVentas.size();i++) {
					det=detallesVentas.get(i);
					psD=con.prepareStatement("update detalle_venta "
							+ "set producto=?, cantidad=?, precio=? subtotal=? "
							+ "where codigo_dp=?;");
					
					BigDecimal pv=det.getProducto().getPrecioVenta();
					BigDecimal cantidad=new BigDecimal(det.getCantidad());
					BigDecimal subtotal=pv.multiply(cantidad);
					
					psD.setInt(1, det.getCantidad());
					psD.setBigDecimal(2, subtotal);
					psD.setInt(3, det.getProducto().getCodigo());
					psD.setInt(4, det.getCodigo());
					psD.addBatch();
					
					psH=con.prepareStatement("insert into historial_stock(fecha,referencia,producto,cantidad) "
							+ "values(?,?,?,?);");
					String referencia="VENTA"+venta.getCodigo();
					psH.setTimestamp(1, fechaHoraActual);
					psH.setString(2, referencia);
					psH.setInt(3, det.getProducto().getCodigo());
					psH.setInt(4, det.getCantidad()*-1);
					psH.addBatch();
				}
				psD.executeBatch();
				psH.executeBatch();
				
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
		*/

}
