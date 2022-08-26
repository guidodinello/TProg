package logica.controladores;
import java.util.Set;

import datatypes.DTActividad;
import datatypes.DTPaquete;
import datatypes.DTSalida;

import java.util.GregorianCalendar;
import java.util.HashSet;

import excepciones.NoExisteUsuario;
import excepciones.YaExisteException;
import logica.clases.Departamento;
import logica.clases.ActividadTuristica;
import logica.handlers.HandlerDepartamentos;
import logica.handlers.HandlerActividades;
import logica.interfaces.*;
//import java.util.GregorianCalendar;
//import java.util.Set;
import logica.clases.SalidaTuristica;
//import logica.clases.Turista;
import logica.clases.Proveedor;
//import datatypes.DTActividad;
//import datatypes.DTPaquete;
//import datatypes.DTSalida;
import logica.handlers.HandlerSalidas;
import logica.handlers.HandlerUsuarios;

public class CtrlActividad implements ICtrlActividad{
	
	
	public void altaDepartamento(String nombreDepartamento,String descripcion ,String URL) throws YaExisteException{
		HandlerDepartamentos hD = HandlerDepartamentos.getInstance();
		if(hD.existeDepartamento(nombreDepartamento)){
			throw new YaExisteException("El departamento " + nombreDepartamento + "ya se encuentra registrado.");
		}
		Departamento newD = new Departamento(nombreDepartamento, descripcion,URL);
		hD.add(newD);
		
	}
	
	
	public Set<String> listarDepartamentos(){
		Set<String> departamentos = new HashSet<String>();
		HandlerDepartamentos hD = HandlerDepartamentos.getInstance();
		Set<Departamento> dep = hD.obtenerDepartamentos();
		dep.forEach((e) -> { departamentos.add(e.getNombre()); });
		return departamentos;
	}
	
	public Set<String> listarActividadesDepartamento(String depto){
		HandlerDepartamentos hD = HandlerDepartamentos.getInstance();
		Departamento d = hD.getDepto(depto);
		Set<String> actividades = d.listarActividades();
		return actividades;
	}
	//Actividades
	public DTActividad getInfoActividad(String actividad) {
		//Falta la parte de Paquetes
		HandlerActividades hA = HandlerActividades.getInstance();
		ActividadTuristica at = hA.obtenerActividadTuristica(actividad);
		DTActividad res = at.getDTActividad();
		return res;
	}
	
	public void altaActividadTuristica(String nomDep, String nomActividad, String desc,int duraHs,float costo,String nombCiudad,String nickProv, GregorianCalendar fechaAlta) throws YaExisteException {
		HandlerActividades hA = HandlerActividades.getInstance();
		if(hA.existeActividad(nomActividad)){
			throw new YaExisteException("Ya existe una actividad turistica " + nomActividad + " registrada.");
		}

		ActividadTuristica resu = new ActividadTuristica(nomActividad, desc, duraHs, costo, nombCiudad, fechaAlta);
		
		HandlerDepartamentos hD = HandlerDepartamentos.getInstance();
		hD.getDepto(nomDep).agregarActividad(resu);
		HandlerUsuarios hU = HandlerUsuarios.getInstance();
		Proveedor p = (Proveedor) hU.getProveedorByNickname(nickProv);
		p.agregarActividad(resu);
		hA.agregarActividad(resu);
	}
	
	@SuppressWarnings("null")
	public Set<String> listarNombresSalidasDeActividad(String actividad){
		Set<String> result = null;
		HandlerSalidas hS = HandlerSalidas.getInstance();
		SalidaTuristica[] salidas=  hS.getSalidas();
		for (int i = 0; i < salidas.length; i++) {
			String nombreSal =salidas[i].getActividad().getNombre();
			if(nombreSal == actividad) {
				result.add(nombreSal);
			}
		}
		return result;
		
	}
	
	public Set<String> listarActividadesDepartamentoMenosPaquete(String depto, String nombrePaquete){
		return null;
	}
	
	//Salidas
	public Set<DTSalida> listarInfoSalidasVigentes(String actividad,GregorianCalendar fechaSistema){
		HandlerActividades hA = HandlerActividades.getInstance();
		ActividadTuristica at = hA.obtenerActividadTuristica(actividad);
		return at.getInfoBasicaSalidasVigentes(fechaSistema);
	}
	
	public void altaSalidaTuristica(String nombreSal,GregorianCalendar fechaSal, String lugarSal,int cantMaxTuristas, GregorianCalendar fechaAlta,String  actividad) throws YaExisteException {
		HandlerSalidas hS = HandlerSalidas.getInstance();
		if (hS.existeSalida(nombreSal)) {
			throw new YaExisteException("La salida " + nombreSal + "ya se encuentra registrada");
			
		}
		HandlerActividades hA = HandlerActividades.getInstance();
		ActividadTuristica actividadAux = hA.obtenerActividadTuristica(actividad);
		
		SalidaTuristica newSal = new SalidaTuristica(nombreSal,fechaSal,lugarSal,cantMaxTuristas,fechaAlta,actividadAux);
		actividadAux.agregarSalida(newSal);
		hS.addSalidas(newSal);
	
	}
	
	public DTSalida getInfoCompletaSalida(String salida) {
	
		HandlerSalidas hS = HandlerSalidas.getInstance();
		
		DTSalida nueva =   new DTSalida();
		SalidaTuristica[] salidas=  hS.getSalidas();
		for (int i = 0; i < salidas.length; i++) {
			String nombreSal =salidas[i].getNombre();
			if(nombreSal == salida) {
				//TODO crear DT
				nueva.setNombre(nombreSal);
				nueva.setfechaAlta(salidas[i].getfechaAlta());
				nueva.setfechaSalida(salidas[i].getfechaSalida());
				nueva.setlugarSalida(salidas[i].getlugarSalida());
				nueva.setmaxTuristas(salidas[i].getcantidadMaximaDeTuristas());
				nueva.setTuristas(salidas[i].getTuristasInscriptos());
			}
		}
		
		
		return nueva;
	}
	
	//Paquetes
	public GregorianCalendar crearPaquete(String nombre,String descripcion,int validez,float descuento,GregorianCalendar fechaDeAlta) {
		return null;
	}
	
	public Set<String> listarPaquetes(){
		return null;
	}
	
	public void ingresarActividadAPaquete(String nombrePaquete,String nombreActividad) {
		
	}
	
	public DTPaquete getInfoPaquete(String paqueteSeleccionado) {
		return null;
	}
}