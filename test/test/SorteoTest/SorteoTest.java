package test.SorteoTest;

import org.junit.Test;

import controlador.Controlador_sorteo;
import excepciones.SorteoException;
import modelo.Sorteo;

public class SorteoTest {
    
    Controlador_sorteo controlador_sorteo = new Controlador_sorteo();

    /*
     *  CASO DE PRUEBA - 016 SE EVALUA EL BLOQUE DE CODIGO
     *  QUE ESTA ENCARGADO DE REALIZAR EL SORTEO DE ÁRBITROS 
     *  DE ENTRE LOS PARTIDOS REGISTRADOS, ESTE PROCESO ESTA
     *  A CARGO DEL PRESIDENTE DE LA FEDERACION
     */
    @Test
    public void agregarSorteo(){
        Sorteo sorteo = new Sorteo();
        sorteo.setAgenda(null);
        
        try {
            controlador_sorteo.guardarSorteo(sorteo);
        } catch (SorteoException e) {
            System.out.println(e.getMessage());
        }
    }
}
