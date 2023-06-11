package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import excepciones.SorteoException;
import excepciones.UsuarioException;
import modelo.Agenda;
import modelo.Partido;
import modelo.Sorteo;

public class Controlador_sorteo {

    List<Sorteo> listaSorteo = new ArrayList<Sorteo>();

    /**
     * 
     * @param Sorteo
     * @return Mensaje
     */
    public String guardarSorteo(Sorteo sorteo) throws SorteoException {
        String message = "";

        Connection conexion = Conexion.conectar();
        PreparedStatement consulta;

        if (sorteo.getArbitro_id() == null || sorteo.getArbitro_id_sustituto() == null) {
            throw new SorteoException("Error id de arbitros no pueden ser nulos");
        }

        try {

            consulta = conexion.prepareStatement("call PR_insertar_sorteo (?, ?, ?, ?, ?)");
            consulta.setDate(1, sorteo.getFecha_sorteo());
            consulta.setInt(2, sorteo.getPartido_id());
            consulta.setInt(3, sorteo.getArbitro_id());
            consulta.setInt(4, sorteo.getArbitro_id_sustituto());
            consulta.setString(5, "A");

            if (consulta.executeUpdate() > 0) {
                message = "Sorteo registrada";
            }
            conexion.close();

            return message;
        } catch (SQLException e) {
            message = "Error en registro Sorteo";
            System.out.println("Error en registro Sorteo: " + e.getMessage());
            return message;
        }

    }

    /**
     * Actualizacion de registro Sorteo
     * 
     * @param Sorteo
     * @return Mensaje
     */
    public String actualizarSorteo(Sorteo sorteo) {

        String mensaje = "";

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_modificar_sorteo (?, ?, ?, ?, ?);");
            consulta.setInt(1, sorteo.getId_sorteo());
            consulta.setDate(2, sorteo.getFecha_sorteo());
            consulta.setInt(3, sorteo.getPartido_id());
            consulta.setInt(4, sorteo.getArbitro_id());
            consulta.setInt(5, sorteo.getArbitro_id_sustituto());

            if (consulta.executeUpdate() > 0) {
                mensaje = "Sorteo actualizada correctamente";
            }
            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "Error en actualizar " + e.getMessage();
            System.out.println("Error en modificar Sorteo con id: " + sorteo.getId_sorteo());
            return mensaje;
        }
    }

    /**
     * 
     * @return Listado de Sorteos
     */
    public List<Sorteo> listarSorteos() {

        Connection conector = Conexion.conectar();
        try {
            Statement consulta = conector.createStatement();
            ResultSet result = consulta.executeQuery("call PR_consultar_sorteo_();");
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Sorteo tmp = new Sorteo(
                        result.getInt("id_sorteo"),
                        result.getDate("fecha_sorteo"),
                        result.getInt("arbitro_id"),
                        result.getInt("arbitro_id_sustituto"),
                        result.getInt("partido_id"),
                        result.getString("estado"));

                listaSorteo.add(tmp);
            }

            conector.close();

            return listaSorteo;
        } catch (SQLException e) {
            System.out.println("Error en listar las actas Sorteo :" + e.getMessage());
            return null;
        }
    }

    /**
     * Eliminacion de Sorteo
     * 
     * @param id
     * @return
     */
    public String eliminarSorteo(Integer id) throws SorteoException{
        String mensaje = "";

        if (id <= 0) {
            throw new SorteoException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_eliminar_sorteo (?);");
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                mensaje = "Sorteo eliminado correctamente";
            }

            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "No se pudo eliminar correctamente " + e.getMessage();
            System.out.println("Error en eliminar Sorteo con id: " + id);

            return mensaje;
        }
    }

    /**
     * 
     * @return Listado de fechas a los que los arbitros fueron seleccionados
     * @throws UsuarioException
     */
    public static List<Sorteo> listarAsistenciasPartidos(Integer id_arbitro) throws UsuarioException {

        if(id_arbitro == null){
            throw new UsuarioException("Id del usuario es nulo");
        }
        
        List<Sorteo> listadoFechasAsistir = new ArrayList<Sorteo>();
        Connection conector = Conexion.conectar();
        try {
            PreparedStatement consulta = conector.prepareStatement("call pr_consultar_arbitros_partidos(?);");
            consulta.setInt(1, id_arbitro);
            ResultSet result = consulta.executeQuery();
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Sorteo tmp = new Sorteo(
                        result.getInt("id_sorteo"),
                        new Agenda(new Partido(result.getInt("id_partido"), result.getString("partido")), result.getDate("fecha_partido"),
                                result.getString("lugar_partido"), result.getTime("hora_partido")),
                        result.getInt("id_arbitro"),
                        result.getInt("id_arbitro_sustituto")

                );

                listadoFechasAsistir.add(tmp);
            }

            conector.close();
            return listadoFechasAsistir;
        } catch (SQLException e) {
            System.out.println("Error en listar las arbitros:" + e.getMessage());
            return null;
        }
    }

}
