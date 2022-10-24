package test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import datatypes.tipoUsuario;

import excepciones.YaExisteException;

import logica.handlers.HandlerActividades;
import logica.handlers.HandlerCategorias;
import logica.handlers.HandlerDepartamentos;
import logica.handlers.HandlerPaquetes;
import logica.handlers.HandlerSalidas;
import logica.handlers.HandlerUsuarios;
import logica.interfaces.Fabrica;
import logica.interfaces.ICtrlActividad;
import logica.interfaces.ICtrlUsuario;
import datatypes.DTActividad;
import datatypes.DTPaquete;
import datatypes.DTSalida;
import datatypes.estadoActividad;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ctrlActividadTest{
	private static ICtrlActividad controladorActividad;
	private static ICtrlUsuario controladorUsuario;
	private static HandlerActividades handlerA;
	
	public ctrlActividadTest() {}
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		//clear a los handler
		HandlerActividades.clear();
		HandlerCategorias.clear();
		HandlerSalidas.clear();
		HandlerDepartamentos.clear();
		HandlerUsuarios.clear();
		HandlerPaquetes.clear();
		handlerA = HandlerActividades.getInstance();
		controladorActividad = fabrica.getICtrlActividad();
		controladorUsuario = fabrica.getICtrlUsuario();

		String imgPerfil = "imagen";

		try {
			controladorUsuario.altaUsuario("cris", "cris@", "Cristian", "Gonzalez", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.proveedor, "uruguayo", "provee cosas", "cris.com");
			controladorUsuario.altaUsuario("agus", "agus@", "Agustin", "Franco", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.turista, "uruguayo", null, null);
			controladorUsuario.altaUsuario("eze", "eze@", "Ezequiel", "Medeiros", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.turista, "uruguayo", null, null);
			controladorUsuario.altaUsuario("manuT1", "emailT1", "nombreT1", "apellidT1", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.turista, "uru", null, null);
			controladorUsuario.altaUsuario("manuT2", "emailT2", "nombreT2", "apellidT2", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.turista, "uru", null, null );
			controladorUsuario.altaUsuario("manuP1", "emailP1", "nombreP1", "apellidP1", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.proveedor, "uruguayo", "descManuP1", "linkP1" );
			controladorUsuario.altaUsuario("manuP2", "emailP2", "nombreP2", "apellidP2", "password", new GregorianCalendar(), imgPerfil, tipoUsuario.proveedor, "uruguayo", "descManuP2", "linkP2" );
		} catch (YaExisteException e2) {
			e2.printStackTrace();
		}

	}
	
	//DEPARTAMENTOS	
	@Test
	@Order(1)
	void testListarDepartamentosVacio() {
		assertEquals(controladorActividad.listarDepartamentos().isEmpty(), true);
	}
	
	@Test
	@Order(2)
	public void testAltaDepartamentosOk() {
		try {
			String nombreDepto1 = "Montevideo";
			String nombreDepto2 = "Canelones";
			String nombreDepto3 = "Artigas";
			controladorActividad.altaDepartamento(nombreDepto1, "Capital de Uruguay", "mvdeo.com.uy");
			controladorActividad.altaDepartamento(nombreDepto2, "Me gustan los canelones", "canelones.com.uy");
			controladorActividad.altaDepartamento(nombreDepto3, "El procer", "artigas.com.uy");
			assertEquals(controladorActividad.listarDepartamentos().isEmpty(), false);
			assertEquals(controladorActividad.listarDepartamentos().contains(nombreDepto1), true);
			assertEquals(controladorActividad.listarDepartamentos().contains(nombreDepto2), true);
			assertEquals(controladorActividad.listarDepartamentos().contains(nombreDepto3), true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	void testAltaDepartamentosRepetidos() {
		String nombreDepto1 = "Montevideo";
		String nombreDepto2 = "Canelones";
		String nombreDepto3 = "Artigas";
		assertEquals(controladorActividad.listarDepartamentos().isEmpty(), false);
		assertThrows(YaExisteException.class, ()->{controladorActividad.altaDepartamento(nombreDepto1, "Capital de Uruguay", "mvdeo.com.uy");});
		assertThrows(YaExisteException.class, ()->{controladorActividad.altaDepartamento(nombreDepto2, "Capital de Uruguay", "mvdeo.com.uy");});
		assertThrows(YaExisteException.class, ()->{controladorActividad.altaDepartamento(nombreDepto3, "Capital de Uruguay", "mvdeo.com.uy");});
		
	}
	
	@Test
	@Order(4)
	void testListarDepartamentos() {
		Set<String> deptos = controladorActividad.listarDepartamentos();
		assertEquals(deptos.contains("Montevideo"), true);
		assertEquals(deptos.contains("Canelones"), true);
		assertEquals(deptos.contains("Artigas"), true);
		assertEquals(deptos.contains("NoDepartamento"), false);
	}

	
	//ACTIVIDADES
	@Test
	@Order(5)
	void testListarActividadesDepartamentoVacia() {
		String nombreDepto1 = "Montevideo";
		String nombreDepto2 = "Canelones";
		String nombreDepto3 = "Artigas";
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto1).isEmpty(), true);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto2).isEmpty(), true);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto3).isEmpty(), true);
	}
	
	@Test
	@Order(6)
	void testAltaActividadesOK() {
		try {
			String deptoActividad1 = "Montevideo";
			String deptoActividad2 = "Canelones";
			String deptoActividad3 = "Artigas";
			String deptoActividad4 = "Montevideo";
			
			String nombActividad1 = "Actividad 1";
			String nombActividad2 = "Actividad 2";
			String nombActividad3 = "Actividad 3";
			String nombActividad4 = "Actividad 4";
			
			String desActividad1 = "act1 d";
			String desActividad2 = "act2 d";
			String desActividad3 = "act3 d";
			String desActividad4 = "descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga"
					+ "descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga"
					+ "descripcion larga descripcion larga descripcion larga descripcion larga";
			
			int duraHsActividad1 = 2;
			int duraHsActividad2 = 3;
			int duraHsActividad3 = 4;;
			int duraHsActividad4 = 5;
			
			float costoActividad1 = 11;
			float costoActividad2 = 12;
			float costoActividad3 = 13;
			float costoActividad4 = 420;
			
			String ciudadActividad1 = "Centro";
			String ciudadActividad2 = "Paso palomeque";
			String ciudadActividad3 = "Cerro Signorelli";
			String ciudadActividad4 = "Centro";
			
			String nickProvAct1 = "cris";
			String nickProvAct2 = "cris";
			String nickProvAct3 = "cris";
			String nickProvAct4 = "cris";
			
			GregorianCalendar fechaAct1 = new GregorianCalendar(2000, 2, 2);
			GregorianCalendar fechaAct2 = new GregorianCalendar(2000, 3, 2);
			GregorianCalendar fechaAct3 = new GregorianCalendar(2001, 3, 2);
			GregorianCalendar fechaAct4 = new GregorianCalendar(2001, 3, 3);;
			
			String img = "imgDir";
			Set<String> conjuntoCat = new HashSet<String>();
			for (int i = 0; i < 5; i++) {
				controladorActividad.altaCategoria("Categoria " + i);
				conjuntoCat.add("Categoria " + i );
			}

			
			controladorActividad.altaActividadTuristica(deptoActividad1, nombActividad1, desActividad1, duraHsActividad1, costoActividad1, ciudadActividad1, nickProvAct1, fechaAct1, img, conjuntoCat, estadoActividad.agregada);
			controladorActividad.altaActividadTuristica(deptoActividad2, nombActividad2, desActividad2, duraHsActividad2, costoActividad2, ciudadActividad2, nickProvAct2, fechaAct2, img, conjuntoCat, estadoActividad.agregada);
			controladorActividad.altaActividadTuristica(deptoActividad3, nombActividad3, desActividad3, duraHsActividad3, costoActividad3, ciudadActividad3, nickProvAct3, fechaAct3, img, conjuntoCat, estadoActividad.agregada);
			controladorActividad.altaActividadTuristica(deptoActividad4, nombActividad4, desActividad4, duraHsActividad4, costoActividad4, ciudadActividad4, nickProvAct4, fechaAct4, img, conjuntoCat, estadoActividad.agregada);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getNombre(),nombActividad1);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getNombre(),nombActividad2);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getNombre(),nombActividad3);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getNombre(),nombActividad4);
			
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getDescripcion(),desActividad1);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getDescripcion(),desActividad2);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getDescripcion(),desActividad3);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getDescripcion(),desActividad4);
			
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getDuracionHs(),duraHsActividad1);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getDuracionHs(),duraHsActividad2);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getDuracionHs(),duraHsActividad3);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getDuracionHs(),duraHsActividad4);
			
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getCostoPorTurista(),costoActividad1);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getCostoPorTurista(),costoActividad2);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getCostoPorTurista(),costoActividad3);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getCostoPorTurista(),costoActividad4);
			
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getNombreCiudad(),ciudadActividad1);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getNombreCiudad(),ciudadActividad2);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getNombreCiudad(),ciudadActividad3);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getNombreCiudad(),ciudadActividad4);
			
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getFechaAlta(),fechaAct1);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getFechaAlta(),fechaAct2);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getFechaAlta(),fechaAct3);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getFechaAlta(),fechaAct4);
			
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad1).getEstado(), estadoActividad.agregada);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad2).getEstado(), estadoActividad.agregada);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad3).getEstado(), estadoActividad.agregada);
			assertEquals(handlerA.obtenerActividadTuristica(nombActividad4).getEstado(), estadoActividad.agregada);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(7)
	void testAltaActividadesRepetidas() {
		String deptoActividad1 = "Montevideo";
		String deptoActividad2 = "Canelones";
		String deptoActividad3 = "Artigas";
		String deptoActividad4 = "Montevideo";
		
		String nombActividad1 = "Actividad 1";
		String nombActividad2 = "Actividad 2";
		String nombActividad3 = "Actividad 3";
		String nombActividad4 = "Actividad 4";
		
		String desActividad1 = "act1 d";
		String desActividad2 = "act2 d";
		String desActividad3 = "act3 d";
		String desActividad4 = "descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga"
				+ "descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga descripcion larga"
				+ "descripcion larga descripcion larga descripcion larga descripcion larga";
		
		int duraHsActividad1 = 2;
		int duraHsActividad2 = 2;
		int duraHsActividad3 = 2;;
		int duraHsActividad4 = 3;
		
		float costoActividad1 = 10;
		float costoActividad2 = 10;
		float costoActividad3 = 10;
		float costoActividad4 = 420;
		
		String ciudadActividad1 = "Centro";
		String ciudadActividad2 = "Paso palomeque";
		String ciudadActividad3 = "Cerro Signorelli";
		String ciudadActividad4 = "Centro";
		
		String nickProvAct1 = "cris";
		String nickProvAct2 = "cris";
		String nickProvAct3 = "cris";
		String nickProvAct4 = "cris";
		
		GregorianCalendar fechaAct1 = new GregorianCalendar(2000, 2, 2);
		GregorianCalendar fechaAct2 = new GregorianCalendar(2000, 3, 2);
		GregorianCalendar fechaAct3 = new GregorianCalendar(2001, 3, 2);
		GregorianCalendar fechaAct4 = new GregorianCalendar(2001, 3, 3);
		
		String img = "imgDir";
		Set<String> setString = new HashSet<String>();

		assertThrows(YaExisteException.class, ()->{controladorActividad.altaActividadTuristica(deptoActividad1, nombActividad1, desActividad1, duraHsActividad1, costoActividad1, ciudadActividad1, nickProvAct1, fechaAct1, img, setString, estadoActividad.agregada);});
		assertThrows(YaExisteException.class, ()->{controladorActividad.altaActividadTuristica(deptoActividad2, nombActividad2, desActividad2, duraHsActividad2, costoActividad2, ciudadActividad2, nickProvAct2, fechaAct2, img, setString, estadoActividad.agregada);});
		assertThrows(YaExisteException.class, ()->{controladorActividad.altaActividadTuristica(deptoActividad3, nombActividad3, desActividad3, duraHsActividad3, costoActividad3, ciudadActividad3, nickProvAct3, fechaAct3, img, setString, estadoActividad.agregada);});
		assertThrows(YaExisteException.class, ()->{controladorActividad.altaActividadTuristica(deptoActividad4, nombActividad4, desActividad4, duraHsActividad4, costoActividad4, ciudadActividad4, nickProvAct4, fechaAct4, img, setString, estadoActividad.agregada);});
		
	}
	
	@Test
	@Order(8)
	void testListarActividadesDepartamento() {
		String nombreDepto1 = "Montevideo";
		String nombreDepto2 = "Canelones";
		String nombreDepto3 = "Artigas";
		String nombActividad1 = "Actividad 1";
		String nombActividad2 = "Actividad 2";
		String nombActividad3 = "Actividad 3";
		String nombActividad4 = "Actividad 4";
		
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto1).isEmpty(), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto2).isEmpty(), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto3).isEmpty(), false);
		
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto1).contains(nombActividad1), true);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto1).contains(nombActividad2), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto1).contains(nombActividad3), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto1).contains(nombActividad4), true);
		
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto2).contains(nombActividad1), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto2).contains(nombActividad2), true);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto2).contains(nombActividad3), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto2).contains(nombActividad4), false);
		
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto3).contains(nombActividad1), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto3).contains(nombActividad2), false);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto3).contains(nombActividad3), true);
		assertEquals(controladorActividad.listarActividadesDepartamento(nombreDepto3).contains(nombActividad4), false);
		
	}
	
	@Test
	@Order(9)
	void testGetInfoActividad() {
		String actividad = "Actividad 1";
		DTActividad dtAct = controladorActividad.getInfoActividad(actividad);
		
		assertEquals(dtAct.getNombre(), "Actividad 1");
		assertEquals(dtAct.getDescripcion(), "act1 d");
		assertEquals(dtAct.getDuracionHs(), 2);
		assertEquals(dtAct.getCosto(), 11);
		
		actividad = "Actividad 2";
		dtAct = controladorActividad.getInfoActividad(actividad);
		assertEquals(dtAct.getNombre(), "Actividad 2");
		assertEquals(dtAct.getDescripcion(), "act2 d");
		assertEquals(dtAct.getDuracionHs(), 3);
		assertEquals(dtAct.getCosto(), 12);
		assertEquals(dtAct.getestado(), estadoActividad.agregada);
		assertEquals(dtAct.getCategorias().contains("Categoria 1"), true);
		assertEquals(dtAct.getFechaAlta(), new GregorianCalendar(2000, 3, 2));
		assertEquals(dtAct.getFechaAltaString(), "2/4/2000");
		assertEquals(dtAct.getImgDir(), "imgDir");
		assertEquals(dtAct.getNombreCiudad(), "Paso palomeque");
		assertEquals(dtAct.toString(), "Nombre: Actividad 2");
		
		
		
	}
	
	//SALIDAS
	
	@Test
	@Order(10)
	void testListaSinSalidas() {
		String nombActividad1 = "Actividad 1";
		String nombActividad2 = "Actividad 2";
		String nombActividad3 = "Actividad 3";
		String nombActividad4 = "Actividad 4";
		Set<String> listaAct1 = controladorActividad.listarNombresSalidasDeActividad(nombActividad1);
		Set<String> listaAct2 = controladorActividad.listarNombresSalidasDeActividad(nombActividad2);
		Set<String> listaAct3 = controladorActividad.listarNombresSalidasDeActividad(nombActividad3);
		Set<String> listaAct4 = controladorActividad.listarNombresSalidasDeActividad(nombActividad4);
		
		assertEquals(listaAct1.isEmpty(),true);
		assertEquals(listaAct2.isEmpty(),true);
		assertEquals(listaAct3.isEmpty(),true);
		assertEquals(listaAct4.isEmpty(),true);
	}
	
	///verifico que se de de alta bien una salida
	@Test
	@Order(11)
	void testAltaSalidasOk() {
		GregorianCalendar fecha = new GregorianCalendar(2023,8,30);
		try {
			controladorActividad.altaSalidaTuristica("A Centro", fecha, "Centro", 10, new GregorianCalendar(2023, 5, 20), "Actividad 1", "imagen");
			controladorActividad.altaSalidaTuristica("A Palomeque", fecha, "Palomeque", 10, new GregorianCalendar(), "Actividad 2", "imagen");
			controladorActividad.altaSalidaTuristica("A Canelones", new GregorianCalendar(2021, 1, 20), "Canelones", 10, new GregorianCalendar(), "Actividad 2", "imagen");
			controladorActividad.altaSalidaTuristica("Al Cerro", fecha, "Cerro Signorelli", 10, new GregorianCalendar(2023, 5, 20), "Actividad 3", "imagen");
			
		} catch (YaExisteException e1) {
			e1.printStackTrace();
		}
		DTSalida nueva1 =  controladorActividad.getInfoCompletaSalida("A Centro");
		assertEquals("A Centro", nueva1.getNombre());
		assertEquals("Centro", nueva1.getlugarSalida());
		assertEquals(10, nueva1.getcantidadMaximaDeTuristas());
		assertEquals(nueva1.getTuristasInscriptos().isEmpty(),true);
		assertEquals(nueva1.getNombreActividad(), "Actividad 1");
		assertEquals(nueva1.getImgDir(), "imagen");
		assertEquals(nueva1.getNombreDepartamentoActividad(), "Montevideo");
		assertEquals(nueva1.toString(), "A Centro" + " - " + "Centro");
		
		
		DTSalida nueva2 =  controladorActividad.getInfoCompletaSalida("A Palomeque");
		assertEquals("A Palomeque", nueva2.getNombre());
		assertEquals("Palomeque", nueva2.getlugarSalida());
		assertEquals(10, nueva2.getcantidadMaximaDeTuristas());
		assertEquals(nueva1.getTuristasInscriptos().isEmpty(),true);
		
		DTSalida nueva3 =  controladorActividad.getInfoCompletaSalida("A Canelones");
		assertEquals("A Canelones", nueva3.getNombre());
		assertEquals("Canelones", nueva3.getlugarSalida());
		assertEquals(10, nueva3.getcantidadMaximaDeTuristas());
		assertEquals(nueva3.getTuristasInscriptos().isEmpty(),true);
		
		DTSalida nueva4 =  controladorActividad.getInfoCompletaSalida("Al Cerro");
		assertEquals("Al Cerro", nueva4.getNombre());
		assertEquals("Cerro Signorelli", nueva4.getlugarSalida());
		assertEquals(10, nueva4.getcantidadMaximaDeTuristas());
		assertEquals(nueva4.getTuristasInscriptos().isEmpty(),true);

	}
	
	@Test
	@Order(12)
	void testAltaSalidaTuristicaFail() {
		GregorianCalendar fecha = new GregorianCalendar(2022,8,30);
		assertThrows(YaExisteException.class,()->{controladorActividad.altaSalidaTuristica("A Centro", new GregorianCalendar(2023, 5, 20), "Centro", 10, new GregorianCalendar(), "Actividad 1", "imagen");});
		assertThrows(YaExisteException.class,()->{controladorActividad.altaSalidaTuristica("A Palomeque", fecha, "Palomeque", 10, new GregorianCalendar(), "Actividad 2", "imagen");});
		assertThrows(YaExisteException.class,()->{controladorActividad.altaSalidaTuristica("A Canelones", new GregorianCalendar(2021, 1, 20), "Canelones", 10, new GregorianCalendar(), "Actividad 2", "imagen");});
		assertThrows(YaExisteException.class,()->{controladorActividad.altaSalidaTuristica("Al Cerro", fecha, "Cerro Signorelli", 10, new GregorianCalendar(2023, 5, 20), "Actividad 3", "imagen");});
	}
	
	@Test
	@Order(13)
	void testListarInfoSalidasVigentes() {
		Set<DTSalida> salidasAct1 = controladorActividad.listarInfoSalidasVigentes("Actividad 1", new GregorianCalendar());
		Set<String> salidasAct1St = new HashSet<String>();
		salidasAct1.forEach((e) -> {salidasAct1St.add(e.getNombre());});
		
		assertEquals(salidasAct1St.contains("A Centro"), true);
		assertEquals(salidasAct1St.contains("Al Cerro"), false);
		
		Set<DTSalida> salidasAct2 = controladorActividad.listarInfoSalidasVigentes("Actividad 2", new GregorianCalendar());
		Set<String> salidasAct2St = new HashSet<String>();
		salidasAct2.forEach((e) -> {salidasAct2St.add(e.getNombre());});
		
		assertEquals(salidasAct2St.contains("A Canelones"), false);
		assertEquals(salidasAct2St.contains("A Palomeque"), true);
		assertEquals(salidasAct2St.contains("Al Cerro"), false);
		
	}
	
	@Test
	@Order(14)
	void testlistarNombresSalidasDeActividad(){
	
	 Set<String> salidas =  controladorActividad.listarNombresSalidasDeActividad("Actividad 1");
	
	 assertEquals(salidas.contains("A Centro"),true, "La salida asociada a la Actividad 1 es A centro");
		
	 Set<String> salidas2 =  controladorActividad.listarNombresSalidasDeActividad("Actividad 2");
	
	 assertEquals(salidas2.contains("A Palomeque"),true, "La salida asociada a la Actividad 2 es A Palomeque");
	 assertEquals(salidas2.contains("A Canelones"),true, "La salida asociada a la Actividad 2 es A Canelones");
		
	 Set<String> salidas3 =  controladorActividad.listarNombresSalidasDeActividad("Actividad 3");
	
	 assertEquals(salidas3.contains("Al Cerro"),true, "La salida asociada a la Actividad 3 es Al cerro");
	}
	
	@Test
	@Order(15)
	void testgetInfoCompletaSalida() {
		DTSalida nueva =  controladorActividad.getInfoCompletaSalida("A Canelones");
		assertEquals("A Canelones", nueva.getNombre());
		assertEquals("Canelones", nueva.getlugarSalida());
		assertEquals(10, nueva.getcantidadMaximaDeTuristas());
		assertEquals(nueva.getTuristasInscriptos().isEmpty(),true,"No hay turistas inscirptos");
		
	}
	
	@Test
	@Order(16)
	void testListarPaquetesVacio() {
		assertEquals(controladorActividad.listarPaquetes().isEmpty(), true);
	}
	
	@Test
	@Order(17)
	void testCrearPaqueteOk() {
		String nombreP1 = "Paquete 1";
		String nombreP2 = "Paquete 2";
		String nombreP3 = "Paquete 3";
		String nombreP4 = "Paquete 4";
		
		String descP1 = "Descripcion 1";
		String descP2 = "Descripcion 2";
		String descP3 = "Descripcion 3";
		String descP4 = "Descripcion 4";
		
		int validezP1 = 1;
		int validezP2 = 2;
		int validezP3 = 2;
		int validezP4 = 4;
		
		float descuentoP1 = (float) 0.1;
		float descuentoP2 = (float) 0.2;
		float descuentoP3 = (float) 0.3;
		float descuentoP4 = (float) 0.4;
		
		GregorianCalendar fechaAltaP1 = new GregorianCalendar(2000, 2, 2);
		GregorianCalendar fechaAltaP2 = new GregorianCalendar(2000, 3, 2);
		GregorianCalendar fechaAltaP3 = new GregorianCalendar(2000, 4, 2);
		GregorianCalendar fechaAltaP4 = new GregorianCalendar(2015, 2, 2);
		
		String img1 = "media/imagenes/img1.png";
		String img2 = "media/imagenes/img2.png";
		String img3 = "media/imagenes/img3.png";
		String img4 = "media/imagenes/img4.png";
		
		try {
			controladorActividad.crearPaquete(nombreP1, descP1, validezP1, descuentoP1, fechaAltaP1, img1);
			controladorActividad.crearPaquete(nombreP2, descP2, validezP2, descuentoP2, fechaAltaP2, img2);
			controladorActividad.crearPaquete(nombreP3, descP3, validezP3, descuentoP3, fechaAltaP3, img3);
			controladorActividad.crearPaquete(nombreP4, descP4, validezP4, descuentoP4, fechaAltaP4, img4);
			
			assertEquals(controladorActividad.listarPaquetes().isEmpty(), false);
			assertEquals(controladorActividad.listarPaquetes().contains(nombreP1), true);
			assertEquals(controladorActividad.listarPaquetes().contains(nombreP2), true);
			assertEquals(controladorActividad.listarPaquetes().contains(nombreP3), true);
			assertEquals(controladorActividad.listarPaquetes().contains(nombreP4), true);
			assertEquals(controladorActividad.listarPaquetes().contains("no paquete"), false);
			
			assertEquals(controladorActividad.getInfoPaquete(nombreP1).getNombre(), nombreP1);
			assertEquals(controladorActividad.getInfoPaquete(nombreP2).getNombre(), nombreP2);
			assertEquals(controladorActividad.getInfoPaquete(nombreP3).getNombre(), nombreP3);
			assertEquals(controladorActividad.getInfoPaquete(nombreP4).getNombre(), nombreP4);
			
			assertEquals(controladorActividad.getInfoPaquete(nombreP1).getDescripcion(), descP1);
			assertEquals(controladorActividad.getInfoPaquete(nombreP2).getDescripcion(), descP2);
			assertEquals(controladorActividad.getInfoPaquete(nombreP3).getDescripcion(), descP3);
			assertEquals(controladorActividad.getInfoPaquete(nombreP4).getDescripcion(), descP4);
			
			assertEquals(controladorActividad.getInfoPaquete(nombreP1).getPeriodoValidez(), validezP1);
			assertEquals(controladorActividad.getInfoPaquete(nombreP2).getPeriodoValidez(), validezP2);
			assertEquals(controladorActividad.getInfoPaquete(nombreP3).getPeriodoValidez(), validezP3);
			assertEquals(controladorActividad.getInfoPaquete(nombreP4).getPeriodoValidez(), validezP4);
			
			assertEquals(controladorActividad.getInfoPaquete(nombreP1).getFechaAlta(), fechaAltaP1);
			assertEquals(controladorActividad.getInfoPaquete(nombreP2).getFechaAlta(), fechaAltaP2);
			assertEquals(controladorActividad.getInfoPaquete(nombreP3).getFechaAlta(), fechaAltaP3);
			assertEquals(controladorActividad.getInfoPaquete(nombreP4).getFechaAlta(), fechaAltaP4);
			
			assertEquals(controladorActividad.getInfoPaquete(nombreP1).getActividades().isEmpty(), true);
			assertEquals(controladorActividad.getInfoPaquete(nombreP2).getActividades().isEmpty(), true);
			assertEquals(controladorActividad.getInfoPaquete(nombreP3).getActividades().isEmpty(), true);
			assertEquals(controladorActividad.getInfoPaquete(nombreP4).getActividades().isEmpty(), true);
				
			assertEquals(controladorActividad.getInfoPaquete(nombreP1).getImg(), img1);
			assertEquals(controladorActividad.getInfoPaquete(nombreP2).getImg(), img2);
			assertEquals(controladorActividad.getInfoPaquete(nombreP3).getImg(), img3);
			assertEquals(controladorActividad.getInfoPaquete(nombreP4).getImg(), img4);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	@Test
	@Order(18)
	void testCrearPaqueteException() {
		String nombreP1 = "Paquete 1";
		String nombreP2 = "Paquete 2";
		String nombreP3 = "Paquete 3";
		String nombreP4 = "Paquete 4";
		
		String descP1 = "Descripcion 1";
		String descP2 = "Descripcion 2";
		String descP3 = "Descripcion 3";
		String descP4 = "Descripcion 4";
		
		int validezP1 = 1;
		int validezP2 = 2;
		int validezP3 = 2;
		int validezP4 = 4;
		
		float descuentoP1 = (float) 0.1;
		float descuentoP2 = (float) 0.2;
		float descuentoP3 = (float) 0.3;
		float descuentoP4 = (float) 0.4;
		
		GregorianCalendar fechaAltaP1 = new GregorianCalendar(2000, 2, 2);
		GregorianCalendar fechaAltaP2 = new GregorianCalendar(2000, 3, 2);
		GregorianCalendar fechaAltaP3 = new GregorianCalendar(2000, 4, 2);
		GregorianCalendar fechaAltaP4 = new GregorianCalendar(2015, 2, 2);
		
		String img1 = "media/imagenes/img1.png";
		String img2 = "media/imagenes/img2.png";
		String img3 = "media/imagenes/img3.png";
		String img4 = "media/imagenes/img4.png";
		
		assertThrows(YaExisteException.class,()->{controladorActividad.crearPaquete(nombreP1, descP1, validezP1, descuentoP1, fechaAltaP1, img1);});
		assertThrows(YaExisteException.class,()->{controladorActividad.crearPaquete(nombreP2, descP2, validezP2, descuentoP2, fechaAltaP2, img2);});
		assertThrows(YaExisteException.class,()->{controladorActividad.crearPaquete(nombreP3, descP3, validezP3, descuentoP3, fechaAltaP3, img3);});
		assertThrows(YaExisteException.class,()->{controladorActividad.crearPaquete(nombreP4, descP4, validezP4, descuentoP4, fechaAltaP4, img4);});
	
	}
	
	@Test
	@Order(19)
	void testIngresarActividadAPaquete() {
		String nombActividad1 = "Actividad 1";
		String nombActividad2 = "Actividad 2";
		String nombActividad3 = "Actividad 3";
		String nombActividad4 = "Actividad 4";
		
		String nombreP1 = "Paquete 1";
		String nombreP2 = "Paquete 2";
		String nombreP3 = "Paquete 3";
		String nombreP4 = "Paquete 4";
		//p1
		controladorActividad.ingresarActividadAPaquete(nombreP1, nombActividad1);
		controladorActividad.ingresarActividadAPaquete(nombreP1, nombActividad2);
		controladorActividad.ingresarActividadAPaquete(nombreP1, nombActividad3);
		controladorActividad.ingresarActividadAPaquete(nombreP1, nombActividad4);
		//p2
		controladorActividad.ingresarActividadAPaquete(nombreP2, nombActividad2);
		controladorActividad.ingresarActividadAPaquete(nombreP2, nombActividad3);
		//p3
		controladorActividad.ingresarActividadAPaquete(nombreP3, nombActividad4);
		
		
		assertEquals(controladorActividad.getInfoPaquete(nombreP1).getActividades().isEmpty(), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP2).getActividades().isEmpty(), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP3).getActividades().isEmpty(), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP4).getActividades().isEmpty(), true);
		
		assertEquals(controladorActividad.getInfoPaquete(nombreP1).getActividades().contains(nombActividad1), true);
		assertEquals(controladorActividad.getInfoPaquete(nombreP1).getActividades().contains(nombActividad2), true);
		assertEquals(controladorActividad.getInfoPaquete(nombreP1).getActividades().contains(nombActividad3), true);
		assertEquals(controladorActividad.getInfoPaquete(nombreP1).getActividades().contains(nombActividad4), true);
		
		assertEquals(controladorActividad.getInfoPaquete(nombreP2).getActividades().contains(nombActividad1), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP2).getActividades().contains(nombActividad2), true);
		assertEquals(controladorActividad.getInfoPaquete(nombreP2).getActividades().contains(nombActividad3), true);
		assertEquals(controladorActividad.getInfoPaquete(nombreP2).getActividades().contains(nombActividad4), false);
		
		assertEquals(controladorActividad.getInfoPaquete(nombreP3).getActividades().contains(nombActividad1), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP3).getActividades().contains(nombActividad2), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP3).getActividades().contains(nombActividad3), false);
		assertEquals(controladorActividad.getInfoPaquete(nombreP3).getActividades().contains(nombActividad4), true);
		
	}
	
	@Test
	@Order(20)
	void listarActividadesDepartamentoMenosPaquete() {
		String nombreP1 = "Paquete 1";
		String nombreP2 = "Paquete 2";
		String nombreP3 = "Paquete 3";
		String nombreP4 = "Paquete 4";
		
		String nombActividad1 = "Actividad 1";
		String nombActividad2 = "Actividad 2";
		String nombActividad3 = "Actividad 3";
		String nombActividad4 = "Actividad 4";
		
		String nombreDepto1 = "Montevideo";
		String nombreDepto2 = "Canelones";
		String nombreDepto3 = "Artigas";
		
		controladorActividad.cambiarEstadoActividad(estadoActividad.confirmada, nombActividad1);
		controladorActividad.cambiarEstadoActividad(estadoActividad.confirmada, nombActividad2);
		controladorActividad.cambiarEstadoActividad(estadoActividad.confirmada, nombActividad3);
		controladorActividad.cambiarEstadoActividad(estadoActividad.confirmada, nombActividad4);
		
		Set<String> actDept1MenosP1 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto1, nombreP1);
		Set<String> actDept1MenosP2 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto1, nombreP2);
		Set<String> actDept1MenosP3 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto1, nombreP3);
		Set<String> actDept1MenosP4 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto1, nombreP4);
		
		Set<String> actDept2MenosP1 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto2, nombreP1);
		Set<String> actDept2MenosP2 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto2, nombreP2);
		Set<String> actDept2MenosP3 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto2, nombreP3);
		Set<String> actDept2MenosP4 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto2, nombreP4);
		
		Set<String> actDept3MenosP1 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto3, nombreP1);
		Set<String> actDept3MenosP2 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto3, nombreP2);
		Set<String> actDept3MenosP3 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto3, nombreP3);
		Set<String> actDept3MenosP4 = controladorActividad.listarActividadesDepartamentoMenosPaquete(nombreDepto3, nombreP4);
		
		assertEquals(actDept1MenosP1.isEmpty(), true);
		assertEquals(actDept1MenosP2.contains(nombActividad1), true);
		assertEquals(actDept1MenosP2.contains(nombActividad2), false);
		assertEquals(actDept1MenosP2.contains(nombActividad3), false);
		assertEquals(actDept1MenosP2.contains(nombActividad4), true);
		assertEquals(actDept1MenosP3.contains(nombActividad1), true);
		assertEquals(actDept1MenosP3.contains(nombActividad2), false);
		assertEquals(actDept1MenosP3.contains(nombActividad3), false);
		assertEquals(actDept1MenosP3.contains(nombActividad4), false);
		assertEquals(actDept1MenosP4.contains(nombActividad1), true);
		assertEquals(actDept1MenosP4.contains(nombActividad2), false);
		assertEquals(actDept1MenosP4.contains(nombActividad3), false);
		assertEquals(actDept1MenosP4.contains(nombActividad4), true);
		
		assertEquals(actDept2MenosP1.isEmpty(), true);
		assertEquals(actDept2MenosP2.contains(nombActividad1), false);
		assertEquals(actDept2MenosP2.contains(nombActividad2), false);
		assertEquals(actDept2MenosP2.contains(nombActividad3), false);
		assertEquals(actDept2MenosP2.contains(nombActividad4), false);
		assertEquals(actDept2MenosP3.contains(nombActividad1), false);
		assertEquals(actDept2MenosP3.contains(nombActividad2), true);
		assertEquals(actDept2MenosP3.contains(nombActividad3), false);
		assertEquals(actDept2MenosP3.contains(nombActividad4), false);
		assertEquals(actDept2MenosP4.contains(nombActividad1), false);
		assertEquals(actDept2MenosP4.contains(nombActividad2), true);
		assertEquals(actDept2MenosP4.contains(nombActividad3), false);
		assertEquals(actDept2MenosP4.contains(nombActividad4), false);
		
		assertEquals(actDept3MenosP1.isEmpty(), true);
		assertEquals(actDept3MenosP2.contains(nombActividad1), false);
		assertEquals(actDept3MenosP2.contains(nombActividad2), false);
		assertEquals(actDept3MenosP2.contains(nombActividad3), false);
		assertEquals(actDept3MenosP2.contains(nombActividad4), false);
		assertEquals(actDept3MenosP3.contains(nombActividad1), false);
		assertEquals(actDept3MenosP3.contains(nombActividad2), false);
		assertEquals(actDept3MenosP3.contains(nombActividad3), true);
		assertEquals(actDept3MenosP3.contains(nombActividad4), false);
		assertEquals(actDept3MenosP4.contains(nombActividad1), false);
		assertEquals(actDept3MenosP4.contains(nombActividad2), false);
		assertEquals(actDept3MenosP4.contains(nombActividad3), true);
		assertEquals(actDept3MenosP4.contains(nombActividad4), false);
		
	}
	
	@Test
	@Order(21)
	void testGetInfoPaquete() {
		String nombreP1 = "Paquete 1";
		String nombreP2 = "Paquete 2";
		String nombreP3 = "Paquete 3";
		String nombreP4 = "Paquete 4";
		
		String descP1 = "Descripcion 1";
		String descP2 = "Descripcion 2";
		String descP3 = "Descripcion 3";
		String descP4 = "Descripcion 4";
		
		float descuentoP1 = (float) 0.1;
		float descuentoP2 = (float) 0.2;
		float descuentoP3 = (float) 0.3;
		float descuentoP4 = (float) 0.4;
		
		int validezP1 = 1;
		int validezP2 = 2;
		int validezP3 = 2;
		int validezP4 = 4;
		
		GregorianCalendar fechaAltaP1 = new GregorianCalendar(2000, 2, 2);
		GregorianCalendar fechaAltaP2 = new GregorianCalendar(2000, 3, 2);
		GregorianCalendar fechaAltaP3 = new GregorianCalendar(2000, 4, 2);
		GregorianCalendar fechaAltaP4 = new GregorianCalendar(2015, 2, 2);
		
		String nombActividad1 = "Actividad 1";
		String nombActividad2 = "Actividad 2";
		String nombActividad3 = "Actividad 3";
		String nombActividad4 = "Actividad 4";
		
		DTPaquete dtP1 = controladorActividad.getInfoPaquete(nombreP1);
		DTPaquete dtP2 = controladorActividad.getInfoPaquete(nombreP2);
		DTPaquete dtP3 = controladorActividad.getInfoPaquete(nombreP3);
		DTPaquete dtP4 = controladorActividad.getInfoPaquete(nombreP4);
		
		assertEquals(dtP1.getNombre(), nombreP1);
		assertEquals(dtP2.getNombre(), nombreP2);
		assertEquals(dtP3.getNombre(), nombreP3);
		assertEquals(dtP4.getNombre(), nombreP4);
		
		assertEquals(dtP1.getDescripcion(), descP1);
		assertEquals(dtP2.getDescripcion(), descP2);
		assertEquals(dtP3.getDescripcion(), descP3);
		assertEquals(dtP4.getDescripcion(), descP4);
		
		assertEquals(dtP1.getDescuento(), descuentoP1);
		assertEquals(dtP2.getDescuento(), descuentoP2);
		assertEquals(dtP3.getDescuento(), descuentoP3);
		assertEquals(dtP4.getDescuento(), descuentoP4);
		
		assertEquals(dtP1.getPeriodoValidez(), validezP1);
		assertEquals(dtP2.getPeriodoValidez(), validezP2);
		assertEquals(dtP3.getPeriodoValidez(), validezP3);
		assertEquals(dtP4.getPeriodoValidez(), validezP4);
		
		assertEquals(dtP1.getFechaAlta(), fechaAltaP1);
		assertEquals(dtP2.getFechaAlta(), fechaAltaP2);
		assertEquals(dtP3.getFechaAlta(), fechaAltaP3);
		assertEquals(dtP4.getFechaAlta(), fechaAltaP4);
		
		assertEquals(dtP1.getActividades().contains(nombActividad1), true);
		assertEquals(dtP1.getActividades().contains(nombActividad2), true);
		assertEquals(dtP1.getActividades().contains(nombActividad3), true);
		assertEquals(dtP1.getActividades().contains(nombActividad4), true);
		
		assertEquals(dtP2.getActividades().contains(nombActividad1), false);
		assertEquals(dtP2.getActividades().contains(nombActividad2), true);
		assertEquals(dtP2.getActividades().contains(nombActividad3), true);
		assertEquals(dtP2.getActividades().contains(nombActividad4), false);
		
		assertEquals(dtP3.getActividades().contains(nombActividad1), false);
		assertEquals(dtP3.getActividades().contains(nombActividad2), false);
		assertEquals(dtP3.getActividades().contains(nombActividad3), false);
		assertEquals(dtP3.getActividades().contains(nombActividad4), true);
		
		assertEquals(dtP4.getActividades().contains(nombActividad1), false);
		assertEquals(dtP4.getActividades().contains(nombActividad2), false);
		assertEquals(dtP4.getActividades().contains(nombActividad3), false);
		assertEquals(dtP4.getActividades().contains(nombActividad4), false);
		
	}
	
	@Test
	@Order(22)
	void testAltaCategoriaOk() {
		try {
			controladorActividad.altaCategoria("Categoria 22");
			controladorActividad.altaCategoria("Categoria 23");
			controladorActividad.altaCategoria("Categoria 24");
			assertEquals(HandlerCategorias.getInstance().existeCategoria("Categoria 22"), true);
			assertEquals(HandlerCategorias.getInstance().existeCategoria("Categoria 23"), true);
			assertEquals(HandlerCategorias.getInstance().existeCategoria("Categoria 24"), true);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(23)
	void testAltaCategoriaFail() {
		assertThrows(YaExisteException.class,()  -> {controladorActividad.altaCategoria("Categoria 22");});
		assertThrows(YaExisteException.class, () -> {controladorActividad.altaCategoria("Categoria 23");});
		assertThrows(YaExisteException.class, () -> {controladorActividad.altaCategoria("Categoria 24");});
	}
	
	@Test
	@Order(24)
	void testListarCategorias() {
		Set<String> categorias = controladorActividad.listarCategorias();
		
		assertEquals(categorias.contains("Categoria 0"), true);
		assertEquals(categorias.contains("Categoria 1"), true);
		assertEquals(categorias.contains("Categoria 2"), true);
		assertEquals(categorias.contains("Categoria 3"), true);
		assertEquals(categorias.contains("Categoria 4"), true);
		assertEquals(categorias.contains("Categoria 5"), false);
		assertEquals(categorias.contains("Categoria 22"), true);
		assertEquals(categorias.contains("Categoria 23"), true);
		assertEquals(categorias.contains("Categoria 24"), true);
		assertEquals(categorias.contains("Categoria 25"), false);
	}
	
	@Test
	@Order(25)
	void testGetDTActividadesConfirmadas() {
		Set<DTActividad> actividades = controladorActividad.getDTActividadesConfirmadas();
		
		assertEquals(actividades.isEmpty(), false);
		assertEquals(actividades.size() == 4, true);
		
	}
	
	@Test
	@Order(25)
	void testListarPaquetesPorCategoria() {
		String nombreP1 = "Paquete 1"; //Actividad 1, 2, 3, 4 con categorias: 0, 1, 2, 3 y 4
		String nombreP2 = "Paquete 2"; //Act 2 y 3
		String nombreP3 = "Paquete 3"; //Act 4
		
		Set<String> paquetes1 = controladorActividad.listarPaquetesCategoria("Categoria 0");
		Set<String> paquetes2 = controladorActividad.listarPaquetesCategoria("Categoria 1");
		Set<String> paquetes3 = controladorActividad.listarPaquetesCategoria("Categoria 2");
		Set<String> paquetes4 = controladorActividad.listarPaquetesCategoria("Categoria 3");
		Set<String> paquetes5 = controladorActividad.listarPaquetesCategoria("Categoria 4");
		
		assertEquals(paquetes1.contains(nombreP1), true);
		assertEquals(paquetes1.contains(nombreP2), true);
		assertEquals(paquetes1.contains(nombreP3), true);
		
		assertEquals(paquetes2.contains(nombreP1), true);
		assertEquals(paquetes2.contains(nombreP2), true);
		assertEquals(paquetes2.contains(nombreP3), true);
		
		assertEquals(paquetes3.contains(nombreP1), true);
		assertEquals(paquetes3.contains(nombreP2), true);
		assertEquals(paquetes3.contains(nombreP3), true);
		
		assertEquals(paquetes4.contains(nombreP1), true);
		assertEquals(paquetes4.contains(nombreP2), true);
		assertEquals(paquetes4.contains(nombreP3), true);
	}
	
	@Test
	@Order(26)
	void testGetInfoActividadConPaqutes() {
		DTActividad act1 = controladorActividad.getInfoActividad("Actividad 1");
		DTActividad act2 = controladorActividad.getInfoActividad("Actividad 2");
		
		assertEquals(act1.getPaquetes().isEmpty(), false);
		assertEquals(act2.getPaquetes().isEmpty(), false);
		
	}
	
	@Test
	@Order(27)
	void testDTSalidaSinParametros() {
		DTSalida sal = new DTSalida();
		
		assertEquals(sal.getNombre().isEmpty(), true);
		assertEquals(sal.getcantidadMaximaDeTuristas(), 0);
		assertEquals(sal.getlugarSalida().isEmpty(), true);
		assertEquals(sal.getTuristasInscriptos(), null);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}


