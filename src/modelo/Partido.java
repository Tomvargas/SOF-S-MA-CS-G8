package modelo;
/**
 * Clase Partido
 */
public class Partido {
    
    protected Integer id_partido;
    protected Equipo_futbol club_local;
    protected Equipo_futbol club_rival;
    protected String estado;
    protected String partido_descripcion;

    public Partido() {
    }

    public Partido(Integer id_partido) {
        this.id_partido = id_partido;
    }

    public Partido(Integer id_partido, String partido_descripcion) {
        this.id_partido = id_partido;
        this.partido_descripcion = partido_descripcion;
    }

    public Partido(Integer id_partido, Equipo_futbol club_local, Equipo_futbol club_rival) {
        this.id_partido = id_partido;
        this.club_local = club_local;
        this.club_rival = club_rival;
    }

    public Partido(Integer id_partido, Equipo_futbol club_local, Equipo_futbol club_rival, String estado) {
        this.id_partido = id_partido;
        this.club_local = club_local;
        this.club_rival = club_rival;
        this.estado = estado;
    }

    public Equipo_futbol getClub_local() {
        return club_local;
    }

    public void setClub_local(Equipo_futbol club_local) {
        this.club_local = club_local;
    }

    public Equipo_futbol getClub_rival() {
        return club_rival;
    }

    public void setClub_rival(Equipo_futbol club_rival) {
        this.club_rival = club_rival;
    }

    public Integer getId_partido() {
        return id_partido;
    }
    public void setId_partido(Integer id_partido) {
        this.id_partido = id_partido;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPartido_descripcion() {
        return partido_descripcion;
    }

    public void setPartido_descripcion(String partido_descripcion) {
        this.partido_descripcion = partido_descripcion;
    }

    
}
