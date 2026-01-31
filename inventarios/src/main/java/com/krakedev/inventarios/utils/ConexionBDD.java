package com.krakedev.inventarios.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.krakedev.inventarios.excepciones.KrakeDevException;

public class ConexionBDD {
	public static Connection coneccion() throws KrakeDevException {
		Context ctx=null;
		DataSource ds=null;
		Connection con=null;
			try {
				ctx = new InitialContext();
				//JDNI
				ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ConexionInventario");
				con = ds.getConnection();
			} catch (NamingException e) {
				//ctx
				e.printStackTrace();
				throw new KrakeDevException("error en el sistema");
			} catch (SQLException e) {
				// con
				e.printStackTrace();
				throw new KrakeDevException("error de conexion");
			}
		return con;	
	}

}
