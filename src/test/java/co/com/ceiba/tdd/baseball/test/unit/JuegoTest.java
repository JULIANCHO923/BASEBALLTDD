package co.com.ceiba.tdd.baseball.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.tdd.baseball.dominio.Constantes;
import co.com.ceiba.tdd.baseball.dominio.Equipo;
import co.com.ceiba.tdd.baseball.dominio.Juego;
import co.com.ceiba.tdd.baseball.presentador.PresentadorConsola;
import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.swing.SwingAleatorio;
import co.com.ceiba.tdd.baseball.swing.TipoSwing;
import co.com.ceiba.tdd.baseball.test.databuilder.EquipoBuilder;

public class JuegoTest {

	public Swing swingAleatorio;
	
	@Before
	public void inicializar(){
		swingAleatorio = spy(SwingAleatorio.class);	
	}
	
	@Test
	public void CuandoInicializoJuegoEntoncesArregloMarcadoresSeInicializa() {

		// Arrange
		Juego juego;

		// act
		juego = new Juego(new PresentadorConsola());

		// assert
		assertNotNull("Juego iniciado con lista nula", juego.getMarcador());
	}

	@Test
	public void MostrarEncabezadoSeMuestraEncabezadoMarcador() {

		// Arrange
		Juego juego = new Juego(new PresentadorConsola());
		String salidaEsperada = "######## MARCADOR FINAL ########\n| Entradas |\n";

		// act
		String salidaActual = juego.encabezadoMarcadorJuego();

		// assert
		assertEquals("El encabezado del marcador final no es el correcto", salidaEsperada.trim(), salidaActual.trim());
	}

	@Test
	public void MostrarEncabezadoSeMuestraEncabezadoParaUnaEntrada() {

		// Arrange
		Juego juego = new Juego(new PresentadorConsola());
		int entradaCualquiera = 1; 
		/* Se adiciona para que el tamaño de la lista aumente a 2 */
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		/* Se cierra una entrada cuando hay 2 marcadores */
		String salidaEsperada = "######## MARCADOR FINAL ########\n| Entradas | 1  |\n";

		// act
		String salidaActual = juego.encabezadoMarcadorJuego();

		// assert
		assertEquals("El encabezado del marcador final no es el correcto", salidaEsperada.trim(), salidaActual.trim());
	}

	@Test
	public void MostrarEncabezadoSeMuestraEncabezadoParaCuatroEntradas() {

		// Arrange
		Juego juego = new Juego(new PresentadorConsola());
		int entradaCualquiera = 1; 
		/* Se adiciona para que el tamaño de la lista aumente a 4 */
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		juego.getMarcador().add(entradaCualquiera);
		/* Se cierra una entrada cuando hay 8 marcadores */
		String salidaEsperada = "######## MARCADOR FINAL ########\n| Entradas | 1  | 2  | 3  | 4  |\n";

		// act
		String salidaActual = juego.encabezadoMarcadorJuego();

		// assert
		assertEquals("El encabezado del marcador final no es el correcto", salidaEsperada.trim(), salidaActual.trim());
	}

	@Test
	public void CuandoSeTieneLaListaDeMarcadoresYLasCarrerasTotalesEntoncesCalculoResultadoDeCarrerasEnEntrada4() {

		// Arrange
		Juego juego = new Juego(new PresentadorConsola()); 
		/* Se adiciona para que el tamaño de la lista aumente a 4 
		 * Números Pares Equipo Uno 
		 * Números Pares Equipo Dos
		 */
		juego.getMarcador().add(5);
		juego.getMarcador().add(0);
		
		juego.getMarcador().add(0);
		juego.getMarcador().add(0);
		
		juego.getMarcador().add(1);
		juego.getMarcador().add(0);
		/* Se cierra una entrada cuando hay 8 marcadores */
		
		int carrerasEntrada4 = 3;
		int posicionArregloEsperada = 6; // posicion en el arreglo va de dos en dos por equipo (equipo1: 0-2-4-6) (equipo2:1-3-5-7)
		int carrerasTotales = 9;
		int entrada = 4;
		
		Equipo equipo = new EquipoBuilder().conId(Constantes.Juego.EQUIPO_UNO).conNombre("EquipoA").conPresentador(new PresentadorConsola()).build();
		for (int i = 1; i <= carrerasTotales; i++) {
			equipo.aumentarCarreras();
		}
		
		// act
		juego.calcularCarreras(equipo, entrada);
		
		// assert
		int posicionEquipoPorEntrada = ((entrada - 1) * Constantes.Juego.NUM_EQUIPOS) + equipo.getId();
		int carrerasEntrada4EquipoUno = juego.getMarcador().get(posicionEquipoPorEntrada);
		assertEquals("En el arreglo de Marcadores la Posicion del equipo Uno en la entrada Cuatro NO es calculado como 6", posicionArregloEsperada, posicionEquipoPorEntrada);
		assertEquals("El calculo de las carreras en la entrada 4 NO coincide con lo esperado", carrerasEntrada4, carrerasEntrada4EquipoUno);
	}
	
	@Test
	public void CuandoSeTieneLaListaDeMarcadoresYLasCarrerasTotalesEntoncesCalculoResultadoDeCarrerasEnEntrada4YSeMuestraEnPantalla() {

		// Arrange
		Juego juego = new Juego(new PresentadorConsola()); 
		/* Se adiciona para que el tamaño de la lista aumente a 4 
		 * Números Pares Equipo Uno 
		 * Números Pares Equipo Dos
		 */
		juego.getMarcador().add(5);
		juego.getMarcador().add(1);
		juego.getMarcador().add(0);
		juego.getMarcador().add(2);
		juego.getMarcador().add(1);
		juego.getMarcador().add(0);
		juego.getMarcador().add(3);
		juego.getMarcador().add(3);
		/* Se cierra una entrada cuando hay 8 marcadores */
		
		Equipo equipoA = new EquipoBuilder().conId(Constantes.Juego.EQUIPO_UNO).conNombre("EquipoA").conPresentador(new PresentadorConsola()).build();
		Equipo equipoB = new EquipoBuilder().conId(Constantes.Juego.EQUIPO_DOS).conNombre("EquipoB").conPresentador(new PresentadorConsola()).build();
		
		String salidaEsperada = "######## MARCADOR FINAL ########\n| Entradas | 1  | 2  | 3  | 4  | \n"
				+ "|   EquipoA  | 5 | 0 | 1 | 3 |  => 9\n"				
				+ "|   EquipoB  | 1 | 2 | 0 | 3 |  => 6\n";
		
		// act
		String salidaActual = juego.encabezadoMarcadorJuego() + "|   "+ equipoA.getNombre() + "  | " + 
				juego.marcadorEquipo(equipoA.getId()) + "|   "+ equipoB.getNombre() + "  | " + juego.marcadorEquipo(equipoB.getId());

		// assert
		assertEquals("El resultado de la entrada para el equipo NO corresponde con lo esperado", salidaEsperada.trim(), salidaActual.trim());
	}	
	
	
	@Test
	public void CuandoJuegoIndicaAEquipoJugarYEsteAnota2HomeRunsSeguidosYOBtiene3OutsEntoncesValoresSonLimpiadosYCarrerasAumentanAUno(){
	
		// Arrange
		Juego juego = new Juego(new PresentadorConsola()); 
		Equipo equipo = new EquipoBuilder().conSwing(swingAleatorio).conPresentador(new PresentadorConsola()).build();
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.HOMERUN,TipoSwing.HOMERUN,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE);
		int carrerasEsperadas = 2;
		int outEsperados = 0;
		
		// Act		
		juego.jugar(equipo);	
		
		//Assert		
		verify(swingAleatorio,times(11)).generarSwing();
		Assert.assertEquals("En el juego el equipo NO obtiene 2 carreras", carrerasEsperadas, equipo.getCarreras());
		Assert.assertTrue("Equipo NO reinicia sus valores de lista de jugadores",equipo.getJugadores().isEmpty());
		Assert.assertEquals("Equipo reinicia sus valores de outs",outEsperados,equipo.getOut());
	}
	
	@Test
	public void CuandoSeSimulaJuegoMarcadorEntoncesCarrerasResultaEnEquipoA3YEquipoB2(){
	
		// Arrange
		Juego juego = new Juego(new PresentadorConsola()); 
		Equipo equipoA = new EquipoBuilder().conId(Constantes.Juego.EQUIPO_UNO).conNombre("EquipoA").conSwing(swingAleatorio).conPresentador(new PresentadorConsola()).build();
		Equipo equipoB = new EquipoBuilder().conId(Constantes.Juego.EQUIPO_DOS).conNombre("EquipoB").conSwing(swingAleatorio).conPresentador(new PresentadorConsola()).build();
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.HOMERUN,TipoSwing.HOMERUN,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.HOMERUN,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.HOMERUN,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.HOMERUN,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,
													   TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE);
		String salidaEsperada = "######## MARCADOR FINAL ########\n| Entradas | 1  | 2  | 3  | 4  | \n"
				+ "|   EquipoA  | 2 | 0 | 0 | 1 |  => 3\n"				
				+ "|   EquipoB  | 0 | 1 | 1 | 0 |  => 2\n";
		
		
		// Act		
		juego.iniciarJuego(equipoA, equipoB);	
		
		String salidaActual = juego.encabezadoMarcadorJuego() + "|   "+ equipoA.getNombre() + "  | " + 
				juego.marcadorEquipo(equipoA.getId()) + "|   "+ equipoB.getNombre() + "  | " + juego.marcadorEquipo(equipoB.getId());
		
		//Assert
		verify(swingAleatorio,times(77)).generarSwing();
		assertEquals("El resultado de la entrada para el equipo NO corresponde con lo esperado", salidaEsperada.trim(), salidaActual.trim());
	}
	
}
