package modelo;

public class Equipo_futbol {

    protected Integer id_equipo;
    protected String nombre_equipo;
    protected String director;
    protected String estado;

    public Equipo_futbol() {
    }

    public Equipo_futbol(Integer id_equipo, String nombre_equipo, String director, String estado) {
        this.id_equipo = id_equipo;
        this.nombre_equipo = nombre_equipo;
        this.director = director;
        this.estado = estado;
    }


    public Equipo_futbol(Integer id_equipo, String nombre_equipo) {
        this.id_equipo = id_equipo;
        this.nombre_equipo = nombre_equipo;
    }

    public Equipo_futbol(Integer id_equipo) {
        this.id_equipo = id_equipo;
    }

    public Integer getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(Integer id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
