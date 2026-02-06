package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
	public ArrayList<Producto> buscar(String subcadena) throws KrakeDevException {
		ArrayList<Producto> productos=new ArrayList<Producto>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Producto producto=null;
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("select pro.codigo_pro,pro.nombre as nombre_pro,udm.codigo_udm,udm.descripcion,cast (pro.precio_venta as decimal(6,2)),pro.tiene_iva,cast (pro.costo as decimal(6,4)),cat.codigo_cat,cat.nombre as nombre_cat,pro.stock "
					+ "from productos pro, unidades_medidas udm, categorias cat "
					+ "where pro.udm = udm.codigo_udm "
					+ "and pro.categoria = cat.codigo_cat "
					+ "and upper (pro.nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			while(rs.next()) {
				int codigoProducto=rs.getInt("codigo_pro");
				String nombreProducto=rs.getString("nombre_pro");
				String codigoUDM=rs.getString("codigo_udm");
				String descripcionUDM=rs.getString("descripcion");
				//String categoriaUDM=rs.getString("categoria_udm");
				BigDecimal precioVenta=rs.getBigDecimal("precio_venta");
				boolean tieneIVA=rs.getBoolean("tiene_iva");
				BigDecimal costo=rs.getBigDecimal("costo");
				int codigoCat=rs.getInt("codigo_cat");
				String nombreCat=rs.getString("nombre_cat");
				//int categoriaPadre=rs.getInt("categoria_padre");
				int stock=rs.getInt("stock");
				//obcervacion "para guardar los datos recuperados es mejor usar los set ya que los guarda sin importar el orden que se define en el constructor con parametros
				UnidadDeMedida udm=new UnidadDeMedida(codigoUDM,descripcionUDM);
				//CategoriaUDM cudm=new CategoriaUDM();
				Categoria cat=new Categoria(codigoCat,nombreCat);
				producto=new Producto(codigoProducto,nombreProducto,udm,precioVenta,tieneIVA,costo,cat,stock);
				productos.add(producto);
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
		
		return productos;
	}
	public void insertar(Producto producto) throws KrakeDevException {
		Connection con=null;
		PreparedStatement ps;
		try {
			con=ConexionBDD.coneccion();
			ps=con.prepareStatement("insert into productos(nombre,udm,precio_venta,tiene_iva,costo,categoria,stock)"
			    +" values(?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadDeMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.getTieneIVA());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			
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

}
