package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import conexion.Conexion;
import excepciones.AgendaException;
import modelo.Agenda;
import modelo.Equipo_futbol;
import modelo.Partido;

public class Controlador_agenda {

    /**
     * 
     * @param agenda
     * @return Mensaje
     */
    public String guardarAgenda(Agenda agenda) {
        String message = "";

        Connection conexion = Conexion.conectar();
        PreparedStatement consulta;

        try {

            consulta = conexion.prepareStatement("call PR_insertar_agenda (?, ?, ?, ?, ?)");
            consulta.setDate(1, agenda.getFecha_partido());
            consulta.setString(2, agenda.getLugar_partido());
            consulta.setTime(3, agenda.getHora_partido());
            consulta.setString(4, "A");
            consulta.setInt(5, agenda.getPartido_id().getId_partido());

            if (consulta.executeUpdate() > 0) {
                message = "Agenda registrada correctamente";
            }
            conexion.close();

            return message;
        } catch (SQLException e) {
            message = "Error en registro agenda";
            System.out.println("Error en registro agenda: " + e.getMessage());
            return message;
        }

    }

    /**
     * Actualizacion de registro Agenda
     * 
     * @param agenda
     * @return Mensaje
     */
    public String actualizarAgenda(Agenda agenda) throws AgendaException{

        String mensaje = "";

        if(agenda.getId_agenda() == null || agenda.getId_agenda() == 0){
            throw new AgendaException("Id agenda no puede ser nulo o 0");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_modificar_agenda(?, ?, ?, ?, ?);");
            consulta.setInt(1, agenda.getId_agenda());
            consulta.setDate(2, agenda.getFecha_partido());
            consulta.setString(3, agenda.getLugar_partido());
            consulta.setTime(4, agenda.getHora_partido());
            consulta.setInt(5, agenda.getPartido_id().getId_partido());

            if (consulta.executeUpdate() > 0) {
                mensaje = "Agenda actualizada correctamente";
            }
            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "Error en actualizar " + e.getMessage();
            System.out.println("Error en modificar agenda con id: " + agenda.getId_agenda());
            return mensaje;
        }
    }

    public List<Agenda> listarAgendas() {
        List<Agenda> listaAgenda = new ArrayList<Agenda>();
        Connection conector = Conexion.conectar();
        try {
            Statement consulta = conector.createStatement();
            ResultSet result = consulta.executeQuery("call PR_consultar_agenda_();");
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Agenda tmp = new Agenda(
                        result.getInt("id_agenda"),
                        new Partido(result.getInt("partido_id_partido"), new Equipo_futbol(
                            result.getInt("club_id_local"), result.getString("nombre_local")
                        ), new Equipo_futbol(
                            result.getInt("club_id_rival"), result.getString("nombre_rival")
                        )) ,
                        result.getDate("fecha_partido"),
                        result.getString("lugar_partido"),
                        result.getTime("hora_partido"),
                        result.getString("estado"));

                listaAgenda.add(tmp);
            }

            conector.close();

            return listaAgenda;
        } catch (SQLException e) {
            System.out.println("Error en listar las agendas :" + e.getMessage());
            return null;
        }
    }

    /**
     * Eliminacion de agenda
     * 
     * @param id
     * @return String mensaje
     */
    public String eliminarAgenda(Integer id) throws AgendaException {
        String mensaje = "";

        if (id <= 0) {
            throw new AgendaException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call  PR_eliminar_agenda(?);");
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                mensaje = "Agenda eliminada correctamente";
            }

            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "No se pudo eliminar correctamente " + e.getMessage();
            System.out.println("Error en eliminar agenda con id: " + id);

            return mensaje;
        }
    }

}
