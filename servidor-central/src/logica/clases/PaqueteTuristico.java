package logica.clases;

//import java.util.Set;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import java.util.HashMap;
import java.util.GregorianCalendar;

import datatypes.DTPaquete;
import logica.handlers.HandlerUsuarios;

public class PaqueteTuristico {
	private String nombre, descripcion;
	private int periodoValidez;
	private float costo;
	private GregorianCalendar fechaAlta;
	private Map<String, ActividadTuristica> actividades;
	
	public PaqueteTuristico(String nombre, String descripcion, int periodoValidez, float costo, GregorianCalendar fechaAlta) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.periodoValidez = periodoValidez;
		this.costo = costo;
		this.fechaAlta = fechaAlta;
		actividades = new HashMap<String, ActividadTuristica>();
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public int getPeriodoValidez() {
		return periodoValidez;
	}

	public float getCosto() {
		return costo;
	}
	
	public GregorianCalendar getFechaAlta() {
		return fechaAlta;
	}
	
	public boolean tieneActividad(String nombre) {
		return actividades.containsKey(nombre);
	}
	
	public void agregarActividad(ActividadTuristica act) {
		 boolean tiene = false;
		HandlerUsuarios hu = HandlerUsuarios.getInstance();
		 Set<Turista> turistas = hu.listarTuristas();
		 for (Turista t : turistas){
			 if (tiene) break;
			 while(!tiene) {
				 Map<String, Compra> compra =  t.getCompras();
				 if(compra != null) {
				 	for (Compra i : compra.values()) {
				 		String nombre =  i.getPaquete().getNombre();
				 		tiene = nombre == act.getNombre();
				 	}
			 }
			 }
		 }
		 if(!tiene) {
		actividades.put(act.getNombre(), act);
		 }
	}
	
	public DTPaquete getDTPaquete() {
		return new DTPaquete(nombre, descripcion, periodoValidez, costo, fechaAlta, actividades.keySet());
	}
}
