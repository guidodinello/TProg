package model.logica.handlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.logica.clases.SalidaTuristica;

/*
 * Clase que contiene la coleccion global de Salidas Turisticas 
 */
public class HandlerSalidas{
    private Map<String,SalidaTuristica > salidasTuristicas;
    private static HandlerSalidas instancia = null;

    private HandlerSalidas() {
        salidasTuristicas = new HashMap<String, SalidaTuristica>();
    }

    public static HandlerSalidas getInstance() {
        if (instancia == null)
            instancia = new HandlerSalidas();
        return instancia;
    }

    public void addSalidas(SalidaTuristica sal) {
        String SnameSal= sal.getNombre();
        
       salidasTuristicas.put(SnameSal, sal);
    }

    public SalidaTuristica obtenerSalidaTuristica(String IDsal) {
        return ((SalidaTuristica) salidasTuristicas.get(IDsal));
    }

    public SalidaTuristica[] getSalidas() {
        if (salidasTuristicas.isEmpty())
            return null;
        else {
            Collection<SalidaTuristica> salidas = salidasTuristicas.values();
            Object[] o = salidas.toArray();
            SalidaTuristica[] salida = new SalidaTuristica[o.length];
            for (int i = 0; i < o.length; i++) {
                salida[i] = (SalidaTuristica) o[i];
            }

            return salida;
        }
    }
    public boolean existeSalida(String nombreSalida) {
		return this.salidasTuristicas.containsKey(nombreSalida);
	}

	public static void clear() {
		instancia = null;
		
	}


}