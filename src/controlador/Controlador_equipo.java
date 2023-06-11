package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import excepciones.EquipoException;
import modelo.Equipo_futbol;

public class Controlador_equipo {

    /**
     * Guarda Equipo/club futbol
     * 
     * @param
     * @return Mensaje
     */
    public String guardarEquipo(Equipo_futbol equipo) {
        String message = "";

        Connection conexion = Conexion.conectar();
        PreparedStatement consulta;

        try {

            consulta = conexion.prepareStatement("call PR_insertar_club(?, ?, ?)");
            consulta.setString(1, equipo.getNombre_equipo());
            consulta.setString(2, equipo.getDirector());
            consulta.setString(3, "A");

            if (consulta.executeUpdate() > 0) {
                message = "Equipo registrado correctamente!";
            }
            conexion.close();

            return message;
        } catch (SQLException e) {
            message = "Error en registro equipo";
            System.out.println("Error en registro equipo: " + e.getMessage());
            return message;
        }

    }

    /**
     * Actualizacion de registro equipo
     * 
     * @param equipo
     * @return String mensaje
     */
    public String actualizarEquipo(Equipo_futbol equipo) throws EquipoException {

        String mensaje = "";

        if (equipo.getId_equipo() == 0 || equipo.getId_equipo() == null) {
            throw new EquipoException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_modificar_club (?, ?, ?);");
            consulta.setInt(1, equipo.getId_equipo());
            consulta.setString(2, equipo.getNombre_equipo());
            consulta.setString(3, equipo.getDirector());

            if (consulta.executeUpdate() > 0) {
                mensaje = "Equipo actualizado correctamente";
            }
            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "Error en actualizar " + e.getMessage();
            System.out.println("Error en modificar equipo con id: " + equipo.getId_equipo());
            return mensaje;
        }
    }

    /**
     * 
     * @return Listado de equipos
     */
    public List<Equipo_futbol> listarEquipos() {

        List<Equipo_futbol> listaEquipos = new ArrayList<Equipo_futbol>();
        Connection conector = Conexion.conectar();
        try {
            Statement consulta = conector.createStatement();
            ResultSet result = consulta.executeQuery("call PR_consultar_club_();");
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Equipo_futbol tmp = new Equipo_futbol(
                        result.getInt("id_club"),
                        result.getString("nombre"),
                        result.getString("director"),
                        result.getString("estado"));

                listaEquipos.add(tmp);
            }

            conector.close();
            return listaEquipos;
        } catch (SQLException e) {
            System.out.println("Error en listar las equipos:" + e.getMessage());
            return null;
        }
    }

    /**
     * Eliminacion de equipo
     * 
     * @param id
     * @return String mensaje
     */
    public String eliminarEquipo(Integer id) throws EquipoException {
        String mensaje = "";

        if (id <= 0) {
            throw new EquipoException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_eliminar_club(?);");
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                mensaje = "Equipo eliminado correctamente";
            }

            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "No se pudo eliminar correctamente " + e.getMessage();
            System.out.println("Error en eliminar equipo con id: " + id);

            return mensaje;
        }
    }

}
