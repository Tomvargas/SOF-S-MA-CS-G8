
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Administrador;

public class Controlador_rol_administrador {

    public Administrador administrador;

    public boolean guardar(Administrador adm) {

        boolean respuesta = false;

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;

        try {

        } catch (Exception e) {
            System.out.println("Error de ingreso en la base: " + e.getMessage());
        }

        return respuesta;
    }

}
