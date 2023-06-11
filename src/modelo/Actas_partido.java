package modelo;

import java.sql.Date;

public class Actas_partido {

    //Atributos de clase
    private int id_acta_partido;
    private Date fecha_emision;
    private String hora_inicio_partido;
    private String hora_fin_partido;
    private String nombre_equipo_rival;
    private String nombre_equipo_local;
    //private Time duracion_partido;
    private String duracion_partido;
    private int goles_equipo_local;
    private int goles_equipo_rival;
    protected Partido partido;
    private String equipo_ganador;

    //Constructor vacio
    public Actas_partido() {
    }

    public Actas_partido(int id_acta_partido, Date fecha_emision, String hora_inicio_partido, String hora_fin_partido,
            String nombre_equipo_rival, String nombre_equipo_local, String duracion_partido, int goles_equipo_local,
            int goles_equipo_rival, Partido partido, String equipo_ganador) {
        this.id_acta_partido = id_acta_partido;
        this.fecha_emision = fecha_emision;
        this.hora_inicio_partido = hora_inicio_partido;
        this.hora_fin_partido = hora_fin_partido;
        this.nombre_equipo_rival = nombre_equipo_rival;
        this.nombre_equipo_local = nombre_equipo_local;
        this.duracion_partido = duracion_partido;
        this.goles_equipo_local = goles_equipo_local;
        this.goles_equipo_rival = goles_equipo_rival;
        this.partido = partido;
        this.equipo_ganador = equipo_ganador;
    }

    public int getId_acta_partido() {
        return id_acta_partido;
    }

    public void setId_acta_partido(int id_acta_partido) {
        this.id_acta_partido = id_acta_partido;
    }

    public String getHora_inicio_partido() {
        return hora_inicio_partido;
    }

    public void setHora_inicio_partido(String hora_inicio_partido) {
        this.hora_inicio_partido = hora_inicio_partido;
    }

    public String getHora_fin_partido() {
        return hora_fin_partido;
    }

    public void setHora_fin_partido(String hora_fin_partido) {
        this.hora_fin_partido = hora_fin_partido;
    }

    public String getNombre_equipo_rival() {
        return nombre_equipo_rival;
    }

    public void setNombre_equipo_rival(String nombre_equipo_rival) {
        this.nombre_equipo_rival = nombre_equipo_rival;
    }

    public String getNombre_equipo_local() {
        return nombre_equipo_local;
    }

    public void setNombre_equipo_local(String nombre_equipo_local) {
        this.nombre_equipo_local = nombre_equipo_local;
    }

    public String getDuracion_partido() {
        return duracion_partido;
    }

    public void setDuracion_partido(String duracion_partido) {
        this.duracion_partido = duracion_partido;
    }

    public int getGoles_equipo_local() {
        return goles_equipo_local;
    }

    public void setGoles_equipo_local(int goles_equipo_local) {
        this.goles_equipo_local = goles_equipo_local;
    }

    public int getGoles_equipo_rival() {
        return goles_equipo_rival;
    }

    public void setGoles_equipo_rival(int goles_equipo_rival) {
        this.goles_equipo_rival = goles_equipo_rival;
    }

    public String getEquipo_ganador() {
        return equipo_ganador;
    }

    public void setEquipo_ganador(String equipo_ganador) {
        this.equipo_ganador = equipo_ganador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "ID: " + id_acta_partido + ", Hora inicio: " + hora_inicio_partido + "hora fin "+ hora_fin_partido + " id partido: "+ partido.toString() ;
    }

    
}
