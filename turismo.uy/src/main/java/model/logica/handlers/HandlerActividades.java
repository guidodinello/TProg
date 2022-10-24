package model.logica.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import model.logica.clases.ActividadTuristica;

public class HandlerActividades{
    private Map<String,ActividadTuristica> actividadesTuristicas;
    private static HandlerActividades instancia = null;

    private HandlerActividades() {
        actividadesTuristicas = new HashMap<String, ActividadTuristica>();
				
    }

    public static HandlerActividades getInstance() {
        if (instancia == null)
            instancia = new HandlerActividades();
        return instancia;
    }

	public boolean existeActividad(String nombre) {
		return actividadesTuristicas.containsKey(nombre);
	}
	
	public void agregarActividad(ActividadTuristica actividad) {
		actividadesTuristicas.put(actividad.getNombre(), actividad);
	}

    
	public ActividadTuristica obtenerActividadTuristica(String actividad) {
		return actividadesTuristicas.get(actividad);
	}
	public Set<ActividadTuristica> obtenerActividadesTuristicas() {
	    return new HashSet<ActividadTuristica>(actividadesTuristicas.values());
		//return (Set<ActividadTuristica>) actividadesTuristicas.values();
	}
	
	public static void clear() {
		instancia = null;
	}
	
}