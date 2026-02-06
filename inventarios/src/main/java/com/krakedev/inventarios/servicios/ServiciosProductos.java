package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProductosBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("productos")
public class ServiciosProductos {
	@Path("buscar/{subcadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("subcadena")String subcadena){
		ProductosBDD pro=new ProductosBDD();
		ArrayList<Producto> productos=null;
		try {
			productos=pro.buscar(subcadena);
			return Response.ok(productos).build();
		} catch (KrakeDevException e) {
			// error al recuperar cliente
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Producto producto) {
		System.out.println(">>>>>>>>>>>"+producto);
		ProductosBDD proBDD=new ProductosBDD();
		try {
			proBDD.insertar(producto);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			// mostrar mensaje de error
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
