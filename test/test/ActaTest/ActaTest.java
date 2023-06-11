package test.ActaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;

import controlador.Controlador_actas_partido;
import excepciones.ActasException;
import modelo.Actas_partido;
import modelo.Partido;

public class ActaTest {

    Controlador_actas_partido controlador_actas_partido = new Controlador_actas_partido();

    /*
     *  CASO DE PRUEBA - 001: SE EVALUA EL BLOQUE DE CODIGO
     *  QUE ESTA ENCARGADO DE REGISTRAR EL INGRESO DE LAS ACTAS DEL PARTIDO
     *  POR PARTE DEL ARBITRO
     */
    @Test
    public void agregarActa() {

        Actas_partido acta = new Actas_partido();
        acta.setPartido(new Partido(505));
        acta.setFecha_emision(Date.valueOf(LocalDate.now()));
        acta.setHora_inicio_partido("12:00:00");
        acta.setHora_fin_partido("14:00:00");
        acta.setNombre_equipo_local("");
        acta.setNombre_equipo_rival("");
        acta.setDuracion_partido("2:00:00");
        acta.setGoles_equipo_local(2);
        acta.setGoles_equipo_rival(3);
        acta.setEquipo_ganador("Manchester United");

        String passed;
        try {
            passed = controlador_actas_partido.guardarActa(acta);
            assertEquals("Acta registrada correctamente!", passed);
            assertEquals(true, passed);
            assertEquals("paquitos", acta.getEquipo_ganador());
        } catch (ActasException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
     *  CASO DE PRUEBA - 002: : SE EVALUA EL BLOQUE DE CODIGO
     *  QUE ESTA ENCARGADO DE ACTUALIZAR LAS DE LAS ACTAS DEL PARTIDO
     *  POR PARTE DEL ARBITRO
     */
    @Test
    public void actualizarActa() {

    }

    /*
     *  CASO DE PRUEBA - 003: SE EVALUA EL BLOQUE DE CODIGO
     *  QUE ESTA ENCARGADO DE ELIMINAR LAS DE LAS ACTAS DEL PARTIDO
     *  POR PARTE DEL ARBITRO
     */
    @Test
    public void eliminarActa() {

    }

    /*
     *  CASO DE PRUEBA - 004: SE EVALUA EL BLOQUE DE CODIGO
     *  QUE ESTA ENCARGADO DE CONSULTAR LAS DE LAS ACTAS DEL PARTIDO
     *  POR PARTE DEL ARBITRO
     */
    @Test
    public void listarActas() {

    }
}
