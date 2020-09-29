package com.upsa.backenddakar;

import javax.annotation.sql.DataSourceDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */

@DataSourceDefinition(name = "java:app/jdbc/database",
                      className = "oracle.jdbc.pool.OracleDataSource",                      
                      url="jdbc:oracle:thin:@localhost:1521:xe",
                      user = "username",
                      password = "password",
                      minPoolSize = 1,
                      maxPoolSize = 3
                     )

//@DataSourceDefinition(name = "java:app/jdbc/database",
//                      className = "oracle.jdbc.pool.OracleDataSource",                      
//                      url="jdbc:oracle:thin:@172.25.88.25:1521:alumnos",
//                      user = "chernandezre",
//                      password = "chernandezre",
//                      minPoolSize = 1,
//                      maxPoolSize = 3
//                     )

@ApplicationPath("/")
public class JAXRSConfiguration extends Application {
    
}
