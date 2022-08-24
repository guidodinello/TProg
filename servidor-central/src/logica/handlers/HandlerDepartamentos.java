package logica.handlers;

//importaciones
//import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import logica.clases.Departamento;
import logica.controladores.hD;


public class HandlerDepartamentos{
	private Map<String, Departamento> departamentos;
	private static HandlerDepartamentos instancia = null;
	
	private HandlerDepartamentos() {
		this.departamentos = new HashMap<String, Departamento>();
	};
	
	public static HandlerDepartamentos getInstance() {
		if(HandlerDepartamentos.instancia == null) {
			HandlerDepartamentos.instancia = new HandlerDepartamentos();
		}
		return HandlerDepartamentos.instancia;
	}
	
	//Pre: no existe un departamento con nombre d.getNombre
	public void add(Departamento d) {
		String nombreDepartamento = d.getNombre();
		this.departamentos.put(nombreDepartamento, d);
	}
	
	public boolean existeDepartamento(String nombreDepartamento) {
		return this.departamentos.containsKey(nombreDepartamento);
	}

	public Set<Departamento> obtenerDepartamentos() {
		Set<Departamento> departamentos = new HashSet<Departamento>(this.departamentos.values());
		return departamentos;
	}
	
	public Departamento getDepto(String depto) {
		return departamentos.get(depto);
	}
	
	public Set<String> listarDepartamentos(){
		Set<String> resu = new HashSet<>();
		for(Map.Entry<String, Departamento> entry : this.departamentos.entrySet()) {
			resu.add(entry.getValue().getNombre());
		}
		return resu;
	}
}