package model.logica.clases;

import java.util.GregorianCalendar;
import java.util.Set;
import model.datatypes.DTActividad;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Proveedor extends Usuario {
	private Map<String, ActividadTuristica> actividades;
	private String descripcion, sitioWeb;
	
	public Proveedor(String nickname, String email, String nombre, String apellido, String contrasena, GregorianCalendar fechaNac, String descripcion, String sitioWeb) {
		super(nickname, email, nombre, apellido, contrasena, fechaNac);
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
		actividades = new HashMap<String, ActividadTuristica>();
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getSitioWeb() {
		return sitioWeb;
	}
	
	public void setDescripcion(String desc) {
		this.descripcion = desc;
	}
	
	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
	
	public void agregarActividad(ActividadTuristica actividad) {
		this.actividades.put(actividad.getNombre(), actividad);
	}
	
	public Set<DTActividad> getDTActividades(){
		Set<DTActividad> resultado = new HashSet<DTActividad>();
		this.actividades.forEach((key, value)->{
			resultado.add(value.getDTActividad());
		});
		return resultado;
	}
}
