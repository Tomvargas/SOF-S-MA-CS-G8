package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import controlador.Controlador_actas_partido;
import modelo.Actas_partido;

public class ListarActaTest {
    
    private Controlador_actas_partido controller = new Controlador_actas_partido();

    /**
     *  CP - 00
     */
    @Test
    public void listar(){

        List<Actas_partido> tmp = controller.listarActas();
        assertEquals("Los juanes", tmp.get(0).getNombre_equipo_rival());
        assertEquals(9, tmp.size());

    }
}
