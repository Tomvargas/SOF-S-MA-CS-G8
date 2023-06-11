package modelo;

public class Usuario {

    protected Integer id_usuario;
    protected String nombre;
    protected String apellido;
    protected String nombre_usuario;
    protected String contrasenia;
    protected String email;
    protected String estado;
    protected Integer id_rol;
    protected String nombre_rol;

    public Usuario() {
    }

    public Usuario(Integer id_usuario, String nombre, String apellido) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuario(Integer id_usuario, String nombre, String apellido, String nombre_usuario, String contrasenia,
            String email, String estado, Integer id_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.estado = estado;
        this.id_rol = id_rol;
    }

    public Usuario(Integer id_usuario, String nombre, String apellido, String nombre_usuario, String contrasenia,
            String email, Integer id_rol, String nombre_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.id_rol = id_rol;
        this.nombre_rol = nombre_rol;
    }

    public Usuario(Integer id_usuario, String nombre, String apellido, String nombre_usuario, String contrasenia,
            String email, String estado) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.estado = estado;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }
}
