
package modelo;

public class Arbitro extends Usuario {

    // Atributos de clase
    protected String edad;
    protected String categoria;
    protected String nacionalidad;
    protected String cantidad_partidos;

    public Arbitro() {
        this.edad = "";
        this.categoria = "";
        this.nacionalidad = "";
        this.cantidad_partidos = "";

    }

    public Arbitro(Integer id_usuario, String nombre, String apellido,
            String nombre_usuario, String email, String contrasenia,
            String estado, String edad, String categoria, String nacionalidad,
            String cantidad_partidos) {
        super(id_usuario, nombre, apellido, nombre_usuario, contrasenia, email, estado);
        this.edad = edad;
        this.categoria = categoria;
        this.nacionalidad = nacionalidad;
        this.cantidad_partidos = cantidad_partidos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCantidad_partidos() {
        return cantidad_partidos;
    }

    public void setCantidad_partidos(String cantidad_partidos) {
        this.cantidad_partidos = cantidad_partidos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

}
