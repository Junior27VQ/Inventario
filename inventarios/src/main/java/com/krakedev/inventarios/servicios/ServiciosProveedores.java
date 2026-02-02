package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;


@Path("proveedores")
public class ServiciosProveedores {
	
	@Path("buscar/{subcadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("subcadena")String subcadena){
		ProveedoresBDD pro=new ProveedoresBDD();
		ArrayList<Proveedor> proveedores=null;
		try {
			proveedores=pro.buscar(subcadena);
			return Response.ok(proveedores).build();
		} catch (KrakeDevException e) {
			// error al recuperar cliente
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Proveedor proveedor) {
		System.out.println(">>>>>>>>>>>"+proveedor);
		ProveedoresBDD proBDD=new ProveedoresBDD();
		try {
			proBDD.insertar(proveedor);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			// mostrar mensaje de error
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)//se usa el Consumes cuando recibe un parametro
	public Response actualizar(Proveedor proveedor) {
		System.out.println("ACTUALIZAR<<<<<<<<<<<<<"+proveedor);
		ProveedoresBDD cli=new ProveedoresBDD();
		try {
			cli.actualizar(proveedor);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
