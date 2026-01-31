package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDocumentoBDD {
	public ArrayList<TipoDocumento> tipodcm() throws KrakeDevException {
		ArrayList<TipoDocumento> tiposDocumentos=new ArrayList<TipoDocumento>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TipoDocumento tipoDocumet=null;
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("select codigo_dcm,descripcion from tipo_documento ");
			
			rs=ps.executeQuery();
			while(rs.next()) {
				String codigo=rs.getString("codigo_dcm");
				String descripcion=rs.getString("descripcion");
				
				tipoDocumet=new TipoDocumento(codigo,descripcion);
				tiposDocumentos.add(tipoDocumet);
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
		
		return tiposDocumentos;
	}

}
