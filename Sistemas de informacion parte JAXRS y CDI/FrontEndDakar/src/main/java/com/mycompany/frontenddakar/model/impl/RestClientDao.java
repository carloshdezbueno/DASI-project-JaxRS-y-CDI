/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontenddakar.model.impl;

import com.mycompany.frontenddakar.model.Dao;
import com.mycompany.frontenddakar.model.Etapa;
import com.mycompany.frontenddakar.model.Resultado;
import com.mycompany.frontenddakar.model.Vehiculo;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author albertogarciacampo
 */
public class RestClientDao implements Dao{
    private static final String URI_ROOT    = "http://localhost:8080/BackEndDakar/dakar";
    
    
    private Client client;
    private WebTarget wtRoot;
    private WebTarget wtResultados;
    private WebTarget wtEtapas;
    private WebTarget wtEtapa;
    private WebTarget wtVehiculos;
    private WebTarget wtVehiculo;


    
    @PostConstruct
    public void init(){
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        this.client = clientBuilder.build();
        
        wtRoot = client.target(URI_ROOT);
        wtResultados = wtRoot.path("resultados" );
        wtEtapas  = wtRoot.path("etapas");
        wtEtapa = wtRoot.path("etapas/{idEtapa}");
        wtVehiculos = wtRoot.path("vehiculos");
        wtVehiculo = wtRoot.path("vehiculos/{idVehiculo}");
    }
    

    @Override
    public List<Resultado> requestResultados() {
        return wtResultados.request( MediaType.APPLICATION_JSON )
                        .get( new GenericType< List<Resultado> > () {} );
    }
    
    
    @Override
    public List<Etapa> requestEtapas() {
        return wtEtapas.request( MediaType.APPLICATION_JSON )
                        .get( new GenericType< List<Etapa> > () {} );
    }


    @Override
    public Etapa requestEtapa(String idEtapa) {
        Response response =  wtEtapa.resolveTemplate("idEtapa", idEtapa)
                                     .request( MediaType.APPLICATION_JSON )                       
                                     .get( );
        switch ( response.getStatus() )
        {
            case 200 : return response.readEntity( Etapa.class );
            
            default  : return null; 
        }
    }
    
    
     @Override
    public List<Vehiculo> requestVehiculos() 
    {
        return wtVehiculos.request( MediaType.APPLICATION_JSON )
                        .get( new GenericType< List<Vehiculo> > () {} );
    }

    
     @Override
    public Vehiculo requestVehiculo(String idVehiculo) 
    {
        Response response =  wtVehiculo.resolveTemplate("idVehiculo", idVehiculo)
                                     .request( MediaType.APPLICATION_JSON )                       
                                     .get( );
        switch ( response.getStatus() )
        {
            case 200 : return response.readEntity( Vehiculo.class );
            
            default  : return null; 
        }
    }

    @Override
    public Optional<String> postVehiculo(String idVehiculo, String nombreEquipo, String tipo, String potencia, String piloto, String copiloto, String clasificacion, String tiempoTotal)
    {
        Form form = new Form();
        form.param("idVehiculo", idVehiculo);
        form.param("nombreEquipo", nombreEquipo);
        form.param("tipo", tipo);
        form.param("potencia", potencia);
        form.param("piloto", piloto);
        form.param("copiloto", copiloto);
        form.param("clasificacion", clasificacion);
        form.param("tiempoTotal", tiempoTotal); 
        
        
        Entity entity = Entity.form(form);
        
        Response response = wtVehiculos.request()
                                     .post( entity );
        
        
        
        switch ( response.getStatus() )
        {
            case 201: String location = response.getHeaderString("Location");
                      return Optional.of(location);
                      
            default:  return Optional.empty();
        }
    }

    

    @Override
    public void deleteVehiculo(String idVehiculo) 
    {
        Response response = wtVehiculo.resolveTemplate("idVehiculo", idVehiculo)
                                    .request()
                                    .delete();
        
        switch ( response.getStatus() )
        {
            case 204 : 
                return;
                        
            default:   
                return;
        }
               
    }

   

    
    
}
