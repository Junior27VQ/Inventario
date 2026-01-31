package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.TipoDocumentoBDD;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("dcm")
public class ServiciosTiposDcm {
		
		@Path("tipos")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response buscar(){
			TipoDocumentoBDD dcm=new TipoDocumentoBDD();
			ArrayList<TipoDocumento> tiposdcm=null;
			try {
				tiposdcm=dcm.tipodcm();
				return Response.ok(tiposdcm).build();
			} catch (KrakeDevException e) {
				// error al recuperar cliente
				e.printStackTrace();
				return Response.serverError().build();
			}
		}	
}


