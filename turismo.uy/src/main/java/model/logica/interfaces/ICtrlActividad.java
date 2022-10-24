package model.logica.interfaces;
import excepciones.YaExisteException;
import model.logica.clases.ActividadTuristica;
import model.logica.clases.SalidaTuristica;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import model.datatypes.DTSalida;
import model.datatypes.estadoActividad;
import model.datatypes.DTPaquete;
import java.util.GregorianCalendar;
import model.datatypes.DTActividad;



public interface ICtrlActividad{

	public abstract void altaDepartamento(String nombreDepartamento, String descripcion,String URL) throws YaExisteException;
	public abstract void altaCategoria(String nombre) throws YaExisteException;
	public abstract DTActividad getInfoActividad(String actividad);
	public abstract void altaActividadTuristica(String nomDep, String nomActividad, String desc,int duraHs,float costo,String nombCiudad,String nickProv, GregorianCalendar fechaAlta, String imgDir,  Set<String> categorias, estadoActividad estado) throws YaExisteException;
	public abstract Set<String> listarNombresSalidasDeActividad(String actividad);
	public abstract Set<DTSalida> listarInfoSalidasVigentes(String actividad,GregorianCalendar fechaSistema);
	public abstract void  altaSalidaTuristica(String nombreSal, GregorianCalendar fechaSal, String lugarSal,int cantMaxTuristas,GregorianCalendar fechaAlta, String actividad, String img)throws YaExisteException;
	public abstract DTSalida getInfoCompletaSalida(String salida);
	public abstract Set<String> listarDepartamentos();
	public abstract Set<String> listarActividadesDepartamento(String depto);
	public abstract Set<String> listarActividadesDepartamentoMenosPaquete(String depto, String nombrePaquete);
	public abstract void crearPaquete(String nombre,String descripcion,int validez,float descuento,GregorianCalendar fechaDeAlta, String img) throws YaExisteException;
	public abstract Set<String> listarPaquetes();
	public abstract void ingresarActividadAPaquete(String nombrePaquete,String nombreActividad);
	public abstract DTPaquete getInfoPaquete(String paqueteSeleccionado);
	public abstract Set<String> listarCategorias();
	public abstract void cambiarEstadoActividad(estadoActividad estado, String nombreAct);
	public abstract <T> Set<T> filterActividades(Function<ActividadTuristica, T> returnFunction, Predicate<ActividadTuristica> condition);
	public abstract Set<DTActividad> getDTActividadesConfirmadas();
    public abstract Set<String> listarPaquetesCategoria(String categoria);
}