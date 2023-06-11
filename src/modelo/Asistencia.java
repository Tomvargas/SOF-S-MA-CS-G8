package modelo;

import java.sql.Date;

public class Asistencia {

    protected Integer id_asistencia;
    protected String partido;
    protected String lugar;
    protected Date fecha;
    protected Boolean asistio;
    protected Integer id_arbitro;

    public Asistencia(){}

    public Asistencia(String partido, String lugar, Date fecha, Boolean asistio, Integer id_arbitro) {
        this.partido = partido;
        this.lugar = lugar;
        this.fecha = fecha;
        this.asistio = asistio;
        this.id_arbitro = id_arbitro;
    }

    public Asistencia(Integer id_asistencia, String partido, String lugar, Date fecha, Boolean asistio) {
        this.id_asistencia = id_asistencia;
        this.partido = partido;
        this.lugar = lugar;
        this.fecha = fecha;
        this.asistio = asistio;
    }

    public Asistencia(Integer id_asistencia, String partido, String lugar, Date fecha, Boolean asistio,
            Integer id_arbitro) {
        this.id_asistencia = id_asistencia;
        this.partido = partido;
        this.lugar = lugar;
        this.fecha = fecha;
        this.asistio = asistio;
        this.id_arbitro = id_arbitro;
    }

    public Integer getId_asistencia() {
        return id_asistencia;
    }

    public void setId_asistencia(Integer id_asistencia) {
        this.id_asistencia = id_asistencia;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getAsistio() {
        return asistio;
    }

    public void setAsistio(Boolean asistio) {
        this.asistio = asistio;
    }

    public Integer getId_arbitro() {
        return id_arbitro;
    }

    public void setId_arbitro(Integer id_arbitro) {
        this.id_arbitro = id_arbitro;
    }

}
