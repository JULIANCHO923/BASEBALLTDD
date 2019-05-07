package co.com.ceiba.tdd.baseball.test.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.com.ceiba.tdd.baseball.dominio.Jugador;
import co.com.ceiba.tdd.baseball.presentador.Presentador;
import co.com.ceiba.tdd.baseball.presentador.PresentadorConsola;
import co.com.ceiba.tdd.baseball.test.databuilder.EquipoBuilder;
import co.com.ceiba.tdd.baseball.test.databuilder.JugadorBuilder;
import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.swing.SwingAleatorio;
import co.com.ceiba.tdd.baseball.swing.TipoSwing;
import co.com.ceiba.tdd.baseball.dominio.Constantes;
import co.com.ceiba.tdd.baseball.dominio.Equipo;

public class EquipoTest {

	public Swing swingAleatorio;
	public Presentador presentadorConsola;
	
	@Before
	public void inicializar(){
		swingAleatorio = spy(SwingAleatorio.class);	
		presentadorConsola = new PresentadorConsola();
	}
	
	@Test
	public void CuandoAdicionoUnJugadorEntoncesAumentaLista(){

		// Arrange
		Equipo equipo = new EquipoBuilder().conNombre("TeamA").build();
		Jugador jugador = new JugadorBuilder().build();
		
		// Act
		equipo.getJugadores().add(jugador);
		
		// Assert
		Assert.assertEquals("La lista de jugadores NO adiciona un Jugador",1, equipo.getJugadores().size());
		
		
	}	

	@Test
	public void CuandoAdicionoNuevoJugadorEntoncesAumentaLista(){

		// Arrange
		Equipo equipo = new EquipoBuilder().conNombre("TeamA").build();
		int tamañoEsperado = 1;
		// Act
		equipo.adicionarNuevoJugador();
		
		// Assert
		Assert.assertEquals("La lista de jugadores NO adiciona un Jugador",tamañoEsperado, equipo.getJugadores().size());
		Assert.assertEquals("El id del jugador no es el esperado ","TeamA-0", equipo.jugadorPosicionInicial().getId());
		
		
	}
	
	
	@Test
	public void CuandoAdicionoNuevoJugadorEntoncesEsAdicionadoEnLaPrimeraPosicionDeLaLista(){
	
		// Arrange
		Equipo equipo = new EquipoBuilder().build();
		Jugador jugador1 = new JugadorBuilder().conId("Jugador1").build();
		jugador1.avanzaPosicion();
		
		Jugador jugador2 = new JugadorBuilder().conId("Jugador2").build();		
		//
		equipo.adicionarJugador(jugador1);
		equipo.adicionarJugador(jugador2);
		
		Assert.assertEquals("Cuando se adiciona Jugador2 este NO se ubica en la primera posicion de la lista de Jugadores", "Jugador2", equipo.jugadorPosicionInicial().getId());
	}
	
	@Test
	public void CuandoJugadorUnoEstaEnPosicionUnoYJugadorDosEstaEnPosicionCeroYObtieneBateoEntoncesJugadoresAvanzanDePosicion(){
	
		// Arrange
		Equipo equipo = new EquipoBuilder().build();
		Jugador jugador1 = new JugadorBuilder().build();
		jugador1.avanzaPosicion();
		int posicionEsperadaJugador1 = 2;
		Jugador jugador2 = new JugadorBuilder().conSwing(swingAleatorio).build();
		int posicionEsperadaJugador2 = 1;
		
		jugador2.addObserver(jugador1);
		
		equipo.adicionarJugador(jugador1);
		equipo.adicionarJugador(jugador2);		
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.BATEO);			
		
		// Act
		TipoSwing swing = equipo.jugadorPosicionInicial().swing();
		if(TipoSwing.BATEO == swing){
			equipo.jugadorPosicionInicial().indicaAvanceUnaBase();
			equipo.jugadorPosicionInicial().avanzaPosicion();
			equipo.jugadorPosicionInicial().notificarObservadores();
		}
		
		//Assert
		verify(swingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Bateo NO recibe Enum Bateo", TipoSwing.BATEO, swing);
		Assert.assertEquals("Jugador2 con Bateo en Posicion 0 NO avanza a posición 1", posicionEsperadaJugador2, equipo.getJugadores().get(0).getPosicion());
		Assert.assertEquals("Jugador1 en Posicion 1 NO avanza a posición 2", posicionEsperadaJugador1, equipo.getJugadores().get(1).getPosicion());
	}
	
	@Test
	public void CuandoJugadorObtieneBateoYLlegaAHomeEntoncesAumentaCarrera(){
		
		// Arrange	
		Equipo equipo = new EquipoBuilder().conPresentador(presentadorConsola).conSwing(swingAleatorio).build();
		Jugador jugador1 = new JugadorBuilder().conPresentador(presentadorConsola).conSwing(swingAleatorio).build();	
		
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();		
		equipo.adicionarJugador(jugador1);
		
		Jugador jugador2 = new JugadorBuilder().conPresentador(presentadorConsola).conSwing(swingAleatorio).build();	
		equipo.adicionarJugador(jugador2);
		
		jugador2.addObserver(jugador1);
		
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.BATEO);			
				
		// Act
		TipoSwing swing = equipo.jugadorPosicionInicial().swing();
		
		if(TipoSwing.BATEO == swing){
			equipo.jugadorPosicionInicial().indicaAvanceUnaBase();
			equipo.jugadorPosicionInicial().avanzaPosicion();
			equipo.jugadorPosicionInicial().notificarObservadores();
			equipo.removerJugadoresConOutOEnHome();
		}
		
		//Assert
		verify(swingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Bateo NO recibe Enum BATEO", TipoSwing.BATEO, swing);
		Assert.assertEquals("El equipo NO aumenta a 1 sus carreras", 1, equipo.getCarreras());
	}
	
	@Test
	public void CuandoJugadorTieneDosStrikeYObtieneOtroStrikeEntoncesObtieneOutYAumentaOutsDelEquipo(){
		
		// Arrange		
		Equipo equipo = new EquipoBuilder().conPresentador(presentadorConsola).build();
		Jugador jugador1 = new JugadorBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();
		jugador1.aumentaStrike();
		jugador1.aumentaStrike();
		equipo.getJugadores().add(jugador1);
		int strikeEsperado = 3;
		int outEsperado = 1;
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.STRIKE);			
				
		
		// Act
		TipoSwing swing = equipo.jugadorPosicionInicial().swing();
		
		if(TipoSwing.STRIKE == swing){
			equipo.jugadorPosicionInicial().aumentaStrike();
		}
		
		if(equipo.jugadorPosicionInicial().tieneMaximoStrike()){
			equipo.jugadorPosicionInicial().jugadorEstaOut();
			equipo.aumentarOut();
		}		
		
		//Assert
		verify(swingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Strike NO recibe Enum STRIKE", TipoSwing.STRIKE, swing);
		Assert.assertEquals("Jugador con 2 Strike NO aumenta a 3", strikeEsperado, equipo.jugadorPosicionInicial().getStrike());
		Assert.assertTrue("El jugador esta out: True",  equipo.jugadorPosicionInicial().isOut());
		Assert.assertEquals("El equipo NO aumenta a 1 sus Outs", outEsperado, equipo.getOut());
	}				
	
	@Test
	public void CuandoBasesEstanLlenasYJugadorEnPosicionCeroBateaEntoncesSeAnota(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().conPresentador(presentadorConsola).build();
		Jugador jugador1 = new JugadorBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		Jugador jugador2 = new JugadorBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();
		jugador2.avanzaPosicion();
		jugador2.avanzaPosicion();
		Jugador jugador3 = new JugadorBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();		
		jugador3.avanzaPosicion();		
		Jugador jugador4 = new JugadorBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();		
		equipo.adicionarJugador(jugador1);
		equipo.adicionarJugador(jugador2);
		equipo.adicionarJugador(jugador3);
		equipo.adicionarJugador(jugador4);
		
		jugador4.addObserver(jugador3);
		jugador4.addObserver(jugador2);
		jugador4.addObserver(jugador1);
		
		TipoSwing swingEsperado = TipoSwing.BATEO;
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.BATEO);			
		
		// Act
		TipoSwing swing = equipo.jugadorPosicionInicial().swing();
		if(TipoSwing.BATEO == swing){
			equipo.jugadorPosicionInicial().indicaAvanceUnaBase();
			equipo.jugadorPosicionInicial().avanzaPosicion();
			equipo.jugadorPosicionInicial().notificarObservadores();
			equipo.removerJugadoresConOutOEnHome();
		}
		
		
		//Assert
		verify(swingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Bateo NO recibe Enum Bateo", swingEsperado, swing);
		Assert.assertEquals("Jugador4 que Batea en Posicion 0 NO aumenta a posición 1", 1, equipo.getJugadores().get(0).getPosicion());
		Assert.assertEquals("Jugador3 en Posicion 1 NO aumenta a posición 2", 2, equipo.getJugadores().get(1).getPosicion());
		Assert.assertEquals("Jugador2 en Posicion 2 NO aumenta a posición 3", 3, equipo.getJugadores().get(2).getPosicion());
		Assert.assertEquals("El equipo NO aumenta a 1 sus carreras", 1, equipo.getCarreras());
		Assert.assertEquals("Lista con 4 jugadores NO disminuye a 3 jugadores", 3, equipo.getJugadores().size());		
	}
		
	@Test
	public void CuandoHayJugadorEnPosicionInicialYSinOutEntoncesSiHayJugadorDisponibleParaRealizarSwing(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().build();				
		Jugador jugador1 = new JugadorBuilder().build();		
		equipo.adicionarJugador(jugador1);
		// Act		
		boolean hayJugadorDisponible = equipo.hayJugadorDisponibleEnPosicionSwing();	
		
		//Assert					
		Assert.assertTrue("NO hay Jugador Disponible para batear", hayJugadorDisponible);
	}
	
	@Test
	public void CuandoNoHayJugadorEnPosicionInicialEntoncesNoHayJugadorDisponibleParaRealizarSwing(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().build();				
		Jugador jugador1 = new JugadorBuilder().build();		
		equipo.adicionarJugador(jugador1);
		equipo.jugadorPosicionInicial().avanzaPosicion();
		// Act		
		boolean hayJugadorDisponible = equipo.hayJugadorDisponibleEnPosicionSwing();	
		
		//Assert					
		Assert.assertFalse("Si hay Jugador Disponible para batear", hayJugadorDisponible);
	}
	
	@Test
	public void CuandoListaDeJugadoresEstaVaciaEntoncesNoHayJugadorDisponibleParaRealizarSwing(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().build();				
		Jugador jugador1 = new JugadorBuilder().build();
		jugador1.avanzaPosicion();
		equipo.adicionarJugador(jugador1);
		// Act		
		boolean hayJugadorDisponible = equipo.hayJugadorDisponibleEnPosicionSwing();	
		
		//Assert					
		Assert.assertFalse("Si hay Jugador Disponible para batear", hayJugadorDisponible);
	}	
	
	@Test
	public void CuandoHayJugadorEnPosicionInicialYConOutEntoncesNoHayJugadorDisponibleParaRealizarSwing(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().build();						
		// Act		
		boolean hayJugadorDisponible = equipo.hayJugadorDisponibleEnPosicionSwing();	
		
		//Assert					
		Assert.assertFalse("Si hay Jugador Disponible para batear", hayJugadorDisponible);
	}
	
	@Test
	public void CuandoSeJuegaYJugadoresRealizanSwingStrikeNueveVecesEntoncesEquipoLlegaATresOuts(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE);
		
		// Act		
		equipo.jugar();	
		
		//Assert		
		verify(swingAleatorio,times(9)).generarSwing();
		Assert.assertEquals("Equipo No alcanza a tener el maximo número de Out permitidos",Constantes.Equipo.MAX_OUT_PERMITIDO, equipo.getOut());
	}
	
	@Test
	public void CuandoSeJuegaYJugadoresBateoYDosCarrerasAdemasRealizanSwingStrikeNueveVecesEntoncesEquipoLlegaATresOuts(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.BATEO,TipoSwing.BATEO,TipoSwing.BATEO,TipoSwing.BATEO,TipoSwing.BATEO,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE);
		
		// Act		
		equipo.jugar();	
		
		//Assert		
		verify(swingAleatorio,times(14)).generarSwing();
		Assert.assertEquals("Equipo Batea y no obtiene 2 carreras esperadas",2, equipo.getCarreras());
		Assert.assertEquals("Equipo No alcanza a tener el maximo número de Out permitidos",Constantes.Equipo.MAX_OUT_PERMITIDO, equipo.getOut());
	}
	
	
	@Test
	public void CuandoEquipoTiene3OutsEntoncesLimpiaValores(){
	
		// Arrange		
		Equipo equipo = new EquipoBuilder().conSwing(swingAleatorio).conPresentador(presentadorConsola).build();
		when(swingAleatorio.generarSwing()).thenReturn(TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE,TipoSwing.STRIKE);
		int outEsperados = Constantes.Equipo.OUT_INICIAL;
		// Act		
		equipo.jugar();	
		equipo.limpiarValores();
		//Assert		
		verify(swingAleatorio,times(9)).generarSwing();
		Assert.assertTrue("Lista de Jugadores NO se inicializa vacia", equipo.getJugadores().isEmpty());
		Assert.assertEquals("Equipo con 3 out No Limpia sus valores",outEsperados, equipo.getOut());
	}
}
