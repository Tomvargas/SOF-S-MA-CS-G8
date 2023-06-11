package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import conexion.Conexion;
import excepciones.UsuarioException;
import modelo.Usuario;
import modelo.UsuarioCache;

public class Controlador_usuario {

    protected Usuario usuario = new Usuario();

    /**
     * Metodo para inicio de sesion
     * 
     * @param objeto usuario
     * @return boolean
     */
    public boolean login(Usuario objeto) {

        boolean respuesta = false;// Esta variable indicara si la conexion esta
                                  // abierta o no.
        String _usuario = objeto.getNombre_usuario();
        String _contrasenia = objeto.getContrasenia();

        // Dentro de esta variable se guardara el retorno del metodo
        // conectar de la clase conexion.
        Connection conexion_arbitro = Conexion.conectar();

        // Aqui se guarda el query del login
        String sql = "call pr_login(?, ?);";
        PreparedStatement declaracion;

        // Captura errores de conexion
        try {

            declaracion = conexion_arbitro.prepareStatement(sql);
            declaracion.setString(1, _usuario);
            declaracion.setString(2, _contrasenia);

            ResultSet resultado = declaracion.executeQuery();// Aqui se ejecuta el query que se paso
                                                             // como parametro sql.

            // Bucle while que sirve para que al momento de ejcutar el query
            // la variable nombre_usuario y contraseña coinciden, entonces
            // la variable respuesta cambia a true.
            while (resultado.next()) {
                UsuarioCache.getUsuarioCache().setId_usuario(resultado.getInt("id"));
                UsuarioCache.getUsuarioCache().setNombre_usuario(resultado.getString("nombre_usuario"));
                UsuarioCache.getUsuarioCache().setEmail(resultado.getString("email"));
                UsuarioCache.getUsuarioCache().setId_rol(resultado.getInt("id_rol"));
                respuesta = true;
            }

        } catch (SQLException e) {

            System.out.println("Error al iniciar sesion" + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error de inicio de sesion");
        }

        return respuesta;// Al final se retorna el valor de la respuesta
    }

    /**
     * Guarda Usuario/usuario
     * 
     * @param
     * @return Mensaje
     */
    public String guardarUsuario(Usuario usuario) {
        String message = "";

        Connection conexion = Conexion.conectar();
        PreparedStatement consulta;

        try {

            consulta = conexion.prepareStatement("call PR_insertar_usuario(?, ?, ?, ?, ?, ?, ?)");
            consulta.setString(1, usuario.getNombre());
            consulta.setString(2, usuario.getApellido());
            consulta.setString(3, usuario.getNombre_usuario());
            consulta.setString(4, usuario.getEmail());
            consulta.setString(5, usuario.getContrasenia());
            consulta.setString(6, "A");
            consulta.setInt(7, usuario.getId_rol());

            if (consulta.executeUpdate() > 0) {
                message = "Usuario registrado correctamente!";
            }
            conexion.close();

            return message;
        } catch (SQLException e) {
            message = "Error en registro Usuario";
            System.out.println("Error en registro Usuario: " + e.getMessage());
            return message;
        }

    }

    /**
     * Actualizacion de registro Usuario
     * 
     * @param usuario
     * @return String mensaje
     */
    public String actualizarUsuario(Usuario usuario) throws UsuarioException {

        String mensaje = "";

        if (usuario.getId_usuario() == 0 || usuario.getId_usuario() == null) {
            throw new UsuarioException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_modificar_usuario (?, ?, ?);");
            consulta.setInt(1, usuario.getId_usuario());
            consulta.setString(2, usuario.getNombre());
            consulta.setString(3, usuario.getApellido());
            consulta.setString(4, usuario.getNombre_usuario());
            consulta.setString(5, usuario.getEmail());
            consulta.setInt(6, usuario.getId_rol());

            if (consulta.executeUpdate() > 0) {
                mensaje = "Usuario actualizado correctamente";
            }
            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "Error en actualizar " + e.getMessage();
            System.out.println("Error en modificar Usuario con id: " + usuario.getId_usuario());
            return mensaje;
        }
    }



    /**
     * 
     * @return Listado de Usuarios
     */
    public List<Usuario> listarUsuarios() {

        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        Connection conector = Conexion.conectar();
        try {
            Statement consulta = conector.createStatement();
            ResultSet result = consulta.executeQuery("call PR_consultar_usuario_();");
            // ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Usuario tmp = new Usuario(
                        result.getInt("id"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getString("nombre_usuario"),
                        result.getString("contrasenia"),
                        result.getString("email"),
                        result.getInt("id_rol"),
                        result.getString("nombre_rol")
                        );

                listaUsuarios.add(tmp);
            }

            conector.close();
            return listaUsuarios;
        } catch (SQLException e) {
            System.out.println("Error en listar las Usuarios:" + e.getMessage());
            return null;
        }
    }

    /**
     * Eliminacion de Usuario
     * 
     * @param id
     * @return String mensaje
     */
    public String eliminarUsuario(Integer id) throws UsuarioException {
        String mensaje = "";

        if (id <= 0) {
            throw new UsuarioException("Id no puede ser menor o igual a cero");
        }

        Connection conector = Conexion.conectar();
        PreparedStatement consulta;
        try {
            consulta = conector.prepareStatement("call PR_eliminar_usuario(?);");
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                mensaje = "Usuario eliminado correctamente";
            }

            conector.close();
            return mensaje;
        } catch (SQLException e) {
            mensaje = "No se pudo eliminar correctamente " + e.getMessage();
            System.out.println("Error en eliminar Usuario con id: " + id);

            return mensaje;
        }
    }

}
