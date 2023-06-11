package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controlador.Controlador_actas_partido;
import excepciones.ActasException;

/**
 * Test Eliminacion de acta id: 20
 * Caso de Prueba
 */
public class EliminarActaTest {

    Controlador_actas_partido controller = new Controlador_actas_partido();
    /**
     * CP - 00
     */
    @Test
    public void eliminar (){

        String mensaje = "";
        try {
            mensaje = controller.eliminarActa(20);
        } catch (ActasException e) {
            e.printStackTrace();
        }
        
        assertEquals("Acta eliminada correctamente", mensaje);        

    }
    
}
