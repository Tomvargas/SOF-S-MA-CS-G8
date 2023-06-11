package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import controlador.Controlador_actas_partido;
import modelo.Actas_partido;
/**
 * Test Agregar actas - Caso de Prueba
 */
public class AgregarActasTest {
    
    private Controlador_actas_partido controller = new Controlador_actas_partido();

    /*
     * CP002
     */
    @Test
    public void guardarActa(){
        
        /* Actas_partido acta = new Actas_partido(
            522,
            "14:00",
            "16:00",
            "Los juanes",
            "paquitos",
            50,
            3,
            2,
            "paquitos",
            500
        );

        boolean passed = controller.guardar(acta);
        assertTrue("Se ha agregado acta: " + acta.getId_acta_partido(), passed);
        assertEquals(true, passed);
        assertEquals("paquitos", acta.getEquipo_ganador()); */
    }
}
