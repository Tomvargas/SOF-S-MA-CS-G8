package modelo;

import java.sql.Date;

public class Sorteo {
    
    protected Integer id_sorteo;
    protected Date fecha_sorteo;
    
    protected Integer arbitro_id;
    protected Integer arbitro_id_sustituto;
    protected Integer partido_id;
    protected String estado;

    protected Agenda agenda;
    
    public Sorteo() {
    }
    
    public Sorteo(Integer id_sorteo, Date fecha_sorteo, Integer partido_id, Integer arbitro_id, Integer arbitro_id_sustituto, String estado) {
        this.id_sorteo = id_sorteo;
        this.fecha_sorteo = fecha_sorteo;
        this.partido_id = partido_id;
        this.arbitro_id = arbitro_id;
        this.arbitro_id_sustituto = arbitro_id_sustituto;
        this.estado = estado;
    }

    public Sorteo(Integer id_sorteo, Agenda agenda, Integer arbitro_id, Integer arbitro_id_sustituto) {
        this.id_sorteo = id_sorteo;
        this.agenda = agenda;
        this.arbitro_id = arbitro_id;
        this.arbitro_id_sustituto = arbitro_id_sustituto;
    }

    public Integer getId_sorteo() {
        return id_sorteo;
    }
    public void setId_sorteo(Integer id_sorteo) {
        this.id_sorteo = id_sorteo;
    }
    public Date getFecha_sorteo() {
        return fecha_sorteo;
    }
    public void setFecha_sorteo(Date fecha_sorteo) {
        this.fecha_sorteo = fecha_sorteo;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Integer getArbitro_id() {
        return arbitro_id;
    }
    public void setArbitro_id(Integer arbitro_id) {
        this.arbitro_id = arbitro_id;
    }
    public Integer getArbitro_id_sustituto() {
        return arbitro_id_sustituto;
    }
    public void setArbitro_id_sustituto(Integer arbitro_id_sustituto) {
        this.arbitro_id_sustituto = arbitro_id_sustituto;
    }
    public Integer getPartido_id() {
        return partido_id;
    }
    public void setPartido_id(Integer partido_id) {
        this.partido_id = partido_id;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    
}
