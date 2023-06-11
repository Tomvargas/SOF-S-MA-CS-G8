package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import conexion.Conexion;
import excepciones.AsistenciaException;
import modelo.Asistencia;

public class Controlador_asistencia {

    /**
     * 
     * @param asistencia
     * @return Mensaje
     */
    public String guardarAsistencia(Asistencia asistencia) {
        String message = "";

        Connection conexion = Conexion.conectar();
        PreparedStatement consulta;

        try {

            consulta = conexion.prepareStatement("call PR_insertar_asistencia (?, ?, ?, ?, ?, ?)");
            consulta.setString(1, asistencia.getPartido());
            consulta.setString(2, asistencia.getLugar());
            consulta.setDate(3, asistencia.getFecha());
            consulta.setBoolean(4, asistencia.getAsistio());
            consulta.setString(5, "A");
            consulta.setInt(6, asistencia.getId_arbitro());

            if (consulta.executeUpdate() > 0) {
                message = "Asistencia registrada";
            }
            conexion.close();

            return message;
        } catch (SQLException e) {
            message = "Error en registro asistencia";
            System.out.println("Error en registro asistencia: " + e.getMessage());
            return message;
        }

    }

    /**
     * 
     * @param asistencias
     * @return
     */
    public String guardarAsistencias(List<Asistencia> asistencias) {
        String message = "";
        try {
            for (Asistencia asistencia : asistencias) {
                guardarAsistencia(asistencia);
            }
            return message;
        } catch (Exception e) {
            message = "Error en registro de asistencias";
            System.out.println("Error en registro de asistencias: " + e.getMessage());
            return message;
        }

    }

    /**
     * Actualizacion de registro Asistencia
     * 
     * @param asistencia
     * @return Mensaje
     */
    public String actualizarAsistencia(Asistencia asistencia) throws AsistenciaException{

        String mensaje = "";

        if(asistencia.getId_asistencia() == null || asistencia.getId_asistencia() == 0){
            throw new AsistenciaException("Id asistencia no puede ser nulo o cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_modificar_asistencia(?, ?, ?, ?, ?);");
            consulta.setInt(1, asistencia.getId_asistencia());
            consulta.setString(2, asistencia.getPartido());
            consulta.setString(3, asistencia.getLugar());
            consulta.setDate(4, asistencia.getFecha());
            consulta.setBoolean(5, asistencia.getAsistio());
            consulta.setInt(6, asistencia.getId_arbitro());

            if (consulta.executeUpdate() > 0) {
                mensaje = "Asistencia actualizada correctamente";
            }
            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "Error en actualizar " + e.getMessage();
            System.out.println("Error en modificar asistencia con id: " + asistencia.getId_asistencia());
            return mensaje;
        }
    }

    public List<Asistencia> listarAsistencias() {
        List<Asistencia> listaAsistencia = new ArrayList<Asistencia>();
        Connection conector = Conexion.conectar();
        try {
            Statement consulta = conector.createStatement();
            ResultSet result = consulta.executeQuery("call PR_consultar_asistencia_();");
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Asistencia tmp = new Asistencia();

                listaAsistencia.add(tmp);
            }

            conector.close();

            return listaAsistencia;
        } catch (SQLException e) {
            System.out.println("Error en listar las asistencias :" + e.getMessage());
            return null;
        }
    }

    /**
     * Eliminacion de asistencia
     * 
     * @param id
     * @return
     */
    public String eliminarAsistencia(Integer id) throws AsistenciaException {
        String mensaje = "";

        if (id <= 0) {
            throw new AsistenciaException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call  PR_eliminar_asistencia(?);");
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                mensaje = "Asistencia eliminada correctamente";
            }

            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "No se pudo eliminar correctamente " + e.getMessage();
            System.out.println("Error en eliminar asistencia con id: " + id);

            return mensaje;
        }
    }

    /**
     * 
     * @return Listado de fechas a los que los arbitros fueron seleccionados
     * @throws UsuarioException
     */
    public static List<Asistencia> listarAsistencias(Integer id_arbitro) {

        /*
         * if (id_arbitro == null) {
         * throw new UsuarioException("Id del usuario es nulo");
         * }
         */

        List<Asistencia> listadoFechasAsistir = new ArrayList<Asistencia>();
        Connection conector = Conexion.conectar();
        try {
            PreparedStatement consulta = conector.prepareStatement("call pr_consultar_asistencia_arbitro(?);");
            consulta.setInt(1, id_arbitro);
            ResultSet result = consulta.executeQuery();
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Asistencia tmp = new Asistencia(
                        result.getInt("id_asistencia"),
                        result.getString("partido"),
                        result.getString("lugar"),
                        result.getDate("fecha"),
                        result.getBoolean("asistencia"));

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
