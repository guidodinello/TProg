package model.logica.interfaces;

import java.util.GregorianCalendar;
import java.util.Set;

import model.datatypes.DTActividad;
import model.datatypes.DTSalida;
import model.datatypes.DTUsuario;
import model.datatypes.tipoUsuario;
import model.datatypes.tipoInscripcion;

import excepciones.YaExisteException;
import excepciones.CompraFailException;
import excepciones.InscriptionFailException;

public interface ICtrlUsuario {

	public abstract void altaUsuario(String nickname,String email, String nombre, String apellido, String contrasena, GregorianCalendar fechaNac,
	        String imgDir, tipoUsuario tipo, String nacionalidad, String desc, String sitioWeb) throws YaExisteException;
	public abstract void ingresarInscripcion(String nickname, String salida, int cant, GregorianCalendar fecha, tipoInscripcion tipo, String paquete) throws InscriptionFailException;
	public abstract void ingresarCompra(String nickname, String paquete, int cant, GregorianCalendar fecha) throws CompraFailException;
	public abstract Set<String> listarTuristas();
	public abstract Set<String> listarProveedores();
	public abstract void actualizarUsuario(String nickname, String nombre, String apellido, GregorianCalendar fechaNac, String nacionalidad, String desc, String sitioWeb);
	public abstract Set<DTSalida> listarInfoSalidasTurista(String t); 
	public abstract Set<DTActividad> listarInfoCompletaActividadesProveedor(String p);
	public abstract Set<String> listarUsuarios();
	public abstract DTUsuario getInfoBasicaUsuario(String usr);
}