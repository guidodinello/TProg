package model.logica.clases;

//import java.util.GregorianCalendar;
//importaciones
//import logica.clases.ActividadTuristica;
//import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Departamento{
	private String nombre;
	private String URL;
	private String descripcion;
	private Map<String, ActividadTuristica> actividades;
	
	public Departamento(String nombre,String descripcion, String URL) {
		this.nombre = nombre;
		this.URL = URL;
		this.descripcion = descripcion;
		this.actividades = new HashMap<String, ActividadTuristica>();
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getURL() {
		return URL;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public Map<String, ActividadTuristica> getActividades(){
		return actividades;
	}
	
	public Set<String> listarActividades() {
		return actividades.keySet();
	}
	
	public void agregarActividad(ActividadTuristica actividad) {
		this.actividades.put(actividad.getNombre(), actividad);
	}
}