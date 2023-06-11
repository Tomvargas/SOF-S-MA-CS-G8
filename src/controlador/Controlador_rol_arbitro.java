
package controlador;

import conexion.Conexion;
import modelo.Arbitro;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Controlador_rol_arbitro {
    
    /**
     * Guarda arbitro/club futbol
     * @param
     * @return Mensaje
     */
    public String guardarArbitro(Arbitro arbitro) {
        String message = "";

        Connection conexion = Conexion.conectar();
        PreparedStatement consulta;

        try {

            consulta = conexion.prepareStatement("call PR_insertar_arbitro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            consulta.setString(1, arbitro.getCategoria());
            consulta.setString(2, arbitro.getNombre());
            consulta.setString(3, arbitro.getApellido());
            consulta.setString(4, arbitro.getEmail());
            consulta.setString(5, arbitro.getNombre_usuario());
            consulta.setString(6, arbitro.getContrasenia());
            consulta.setString(7, arbitro.getEdad());
            consulta.setString(8, arbitro.getNacionalidad());
            consulta.setString(9, arbitro.getCantidad_partidos());
            consulta.setString(10, "A");
            if (consulta.executeUpdate() > 0) {
                message = "arbitro registrado";
            }
            conexion.close();

            return message;
        } catch (SQLException e) {
            message = "Error en registro arbitro " + e.getMessage();
            System.out.println("Error en registro arbitro: " + e.getMessage());
            return message;
        }

    }

    /**
     * Actualizacion de registro arbitro
     * 
     * @param arbitro
     * @return Mensaje
     */
    public String actualizarArbitro(Arbitro arbitro) {

        String mensaje = "";

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_modificar_arbitro (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            consulta.setInt(1,arbitro.getId_usuario());
            consulta.setString(2, arbitro.getCategoria());
            consulta.setString(3, arbitro.getNombre());
            consulta.setString(4, arbitro.getApellido());
            consulta.setString(5, arbitro.getEmail());
            consulta.setString(6, arbitro.getNombre_usuario());
            consulta.setString(7, arbitro.getContrasenia());
            consulta.setString(8, arbitro.getEdad());
            consulta.setString(9, arbitro.getNacionalidad());
            consulta.setString(10, arbitro.getCantidad_partidos());

            if (consulta.executeUpdate() > 0) {
                mensaje = "arbitro actualizada correctamente";
            }
            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "Error en actualizar " + e.getMessage();
            System.out.println("Error en modificar arbitro con id: " + arbitro.getId_usuario());
            return mensaje;
        }
    }

    /**
     * 
     * @return Listado de arbitros
     */
    public static List<Arbitro> listarArbitros() {

        List<Arbitro> listaArbitros = new ArrayList<Arbitro>();
        Connection conector = Conexion.conectar();
        try {
            Statement consulta = conector.createStatement();
            ResultSet result = consulta.executeQuery("call PR_consultar_arbitro_();");
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Arbitro tmp = new Arbitro(
                        result.getInt("id_arbitro"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getString("nombre_usuario"),
                        result.getString("email"),
                        result.getString("contrasenia"),
                        result.getString("estado"),
                        result.getString("edad"),
                        result.getString("categoria"),
                        result.getString("nacionalidad"),
                        result.getString("cantidad_partidos")
                       );

                        listaArbitros.add(tmp);
            }

            conector.close();
            return listaArbitros;
        } catch (SQLException e) {
            System.out.println("Error en listar las arbitros:" + e.getMessage());
            return null;
        }
    }

    /**
     * Eliminacion de arbitro
     * 
     * @param id
     * @return
     */
    public String eliminarArbitro(Integer id) {
        String mensaje = "";

        if (id <= 0) {
            // throw new arbitroException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_eliminar_arbitro(?);");
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                mensaje = "arbitro eliminado correctamente";
            }

            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "No se pudo eliminar correctamente " + e.getMessage();
            System.out.println("Error en eliminar arbitro con id: " + id);

            return mensaje;
        }
    }
    
}
