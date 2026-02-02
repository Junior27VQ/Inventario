package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;


public class ProveedoresBDD {
	
	public ArrayList<Proveedor> buscar(String subcadena) throws KrakeDevException {
		ArrayList<Proveedor> proveedores=new ArrayList<Proveedor>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Proveedor proveedor=null;
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("select pro.identificador,pro.tipo_documento,td.descripcion,pro.nombre,pro.telefono,pro.correo,pro.direccion "
					+ "from proveedores pro, tipo_documento td "
					+ "where pro.tipo_documento = td.codigo_dcm "
					+ "and upper (nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			while(rs.next()) {
				String identificador=rs.getString("identificador");
				String tdcodigo=rs.getString("tipo_documento");
				String tddescripcion=rs.getString("descripcion");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");
				TipoDocumento td=new TipoDocumento(tdcodigo,tddescripcion);
				proveedor=new Proveedor(identificador,td,nombre,telefono,correo,direccion);
				proveedores.add(proveedor);
			}
		} catch (KrakeDevException e) {
			// mensaje de error en connexion
			e.printStackTrace();
			throw e; //propagar la excepcion
		} catch (SQLException e) {
			// base de datos
			e.printStackTrace();
			throw new KrakeDevException("error al consultar datos. Detalles: "+e.getMessage());
		}
		
		return proveedores;
	}
	public void insertar(Proveedor proveedor) throws KrakeDevException {
		Connection con=null;
		PreparedStatement ps;
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion)"
			    +" values(?, ?, ?, ?, ?, ?)");
			ps.setString(1, proveedor.getIdentificador());
			ps.setString(2, proveedor.getTipoDocumento().getCodigo());
			ps.setString(3, proveedor.getNombre());
			ps.setString(4, proveedor.getTelefono());
			ps.setString(5, proveedor.getCorreo());
			ps.setString(6, proveedor.getDireccion());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// ps
			e.printStackTrace();
			throw new KrakeDevException("error al insertar el proveedor. Detalles: "+e.getMessage());
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
	public void actualizar(Proveedor proveedor) throws KrakeDevException {
		Connection con=null;
		PreparedStatement ps;
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("update proveedor set nombre=?, telefono=? where identificador=? ");
			    
			ps.setString(1, proveedor.getNombre());
			ps.setString(2, proveedor.getTelefono());
			ps.setString(3, proveedor.getDireccion());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// ps
			e.printStackTrace();
			throw new KrakeDevException("error identificador del proveedor no existe. Detalles: "+e.getMessage());
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
