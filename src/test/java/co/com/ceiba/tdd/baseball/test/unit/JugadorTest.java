package co.com.ceiba.tdd.baseball.test.unit;


import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.tdd.baseball.dominio.Constantes;
import co.com.ceiba.tdd.baseball.dominio.Jugador;
import co.com.ceiba.tdd.baseball.exception.ExcepcionMovimientoInvalido;
import co.com.ceiba.tdd.baseball.presentador.PresentadorConsola;
import co.com.ceiba.tdd.baseball.swing.SwingAleatorio;
import co.com.ceiba.tdd.baseball.swing.TipoSwing;

public class JugadorTest {
	
	private SwingAleatorio SwingAleatorio;
	
	@Before
	public void inicializar()  throws Exception{
		SwingAleatorio = spy(SwingAleatorio.class);		
	}
	
	@Test
	public void CuandoInicializoJugadorEntoncesPosicionYStrikeYOutInicianEnCero(){
		
		// Arrange
		Jugador jugador = null;
		
		// Act
		jugador = new Jugador("Jugador A", new SwingAleatorio(), new PresentadorConsola());
						
		// Assert
		Assert.assertEquals("La posición inicial del jugador no es cero", 0, jugador.getPosicion());
		Assert.assertEquals("El jugador no debe iniciar con strike", 0, jugador.getStrike());
		Assert.assertFalse( "El jugador inicia con outs = 'true', deberia false", jugador.isOut());			
	}
		
	@Test
	public void  CuandoJugadorAvanzaDeLaPosicionCeroEntoncesPosicionAumentaAUno(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", new SwingAleatorio(), new PresentadorConsola());
				
		// Act
		jugador.avanzaPosicion();

		//Assert
		Assert.assertEquals("Jugador en posición cero NO avanza a posición Uno", 1, jugador.getPosicion());		
	}
	
	@Test
	public void  CuandoJugadorAvanzaDeLaPosicionUnoEntoncesPosicionAumentaADos(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", new SwingAleatorio(), new PresentadorConsola());
		jugador.avanzaPosicion();		
		
		// Act
		jugador.avanzaPosicion();

		Assert.assertEquals("Jugador en posición Uno NO avanza a posición Dos", 2, jugador.getPosicion());		
	}

	
	@Test
	public void  CuandoJugadorAvanzaDeLaPosicionTresEntoncesPosicionAumentaACuatro(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", new SwingAleatorio(), new PresentadorConsola());
		jugador.avanzaPosicion();
		jugador.avanzaPosicion();	
		jugador.avanzaPosicion();	
		
		// Act
		jugador.avanzaPosicion();
		
		//Assert
		Assert.assertEquals("Jugador en posición Tres NO avanza a posición Cuatro", 4, jugador.getPosicion());		
	}

	@Test(expected = ExcepcionMovimientoInvalido.class)
	public void  CuandoJugadorAvanzaDeLaPosicionHomeEntoncesExcepcionMovimientoInvalido(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", new SwingAleatorio(), new PresentadorConsola());
		jugador.avanzaPosicion();
		jugador.avanzaPosicion();	
		jugador.avanzaPosicion();	
		jugador.avanzaPosicion();
		
		// Act
		jugador.avanzaPosicion();
	}
	
	/**
	 * 1. Swing( Batear, Bola, Strike)
	 * 0 - 45 Batéo 
	 * TipoSwing.BOLA - 55 Bola (Bolas ilimitadas)
	 * 56 - TipoSwing.STRIKE Strike
	 * 
	 */
	@Test
	public void CuandoJugadorHaceSwingYObtieneBateoEntoncesDevuelveEnteroBatea(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());		
		TipoSwing swingEsperado = TipoSwing.BATEO;
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BATEO);		
		
		
		// Act
		TipoSwing swing = jugador.swing();
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		
		Assert.assertEquals("Jugador solicita Swing = Bateo y NO recibe Enum Bateo", swingEsperado, swing);		
	}
	
	@Test
	public void CuandoJugadorObtieneBolaEntoncesDevuelveEnteroBola(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());		
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BOLA);		
		TipoSwing esperada = TipoSwing.BOLA;
		
		// Act
		TipoSwing swing = jugador.swing();
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertSame("Cuando el jugador realiza Swing, No recibe Enum Bola", esperada , swing);		
	}
	
	@Test
	public void CuandoJugadorObtieneStrikeEntoncesDevuelveTextoStrike(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());						
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.STRIKE);		
		TipoSwing esperada = TipoSwing.STRIKE;  
		
		// Act
		TipoSwing swing = jugador.swing();
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Strike NO recibe Enum Strike", esperada , swing);		
	}			
	
	
	@Test
	public void CuandoJugadorObtieneBateoEntoncesAumentaPosicion(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BATEO);			
		TipoSwing esperada = TipoSwing.BATEO;
		int posicionEsperada = 1;
		
		// Act
		TipoSwing swing = jugador.swing();
		if(TipoSwing.BATEO == swing){
			jugador.avanzaPosicion();
		}
		
		//Assert
		Assert.assertSame("Jugador solicita Swing Bateo NO recibe Enum Bateo", esperada, swing);
		Assert.assertEquals("Jugador con Bateo en Posicion Cero NO avanza a posición Uno", posicionEsperada, jugador.getPosicion());
	}	
	
	@Test
	public void CuandoJugadorObtieneStrikeEntoncesAumentaValorStrike(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());		
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.STRIKE);	
		TipoSwing esperada = TipoSwing.STRIKE;
		
		// Act
		TipoSwing swing = jugador.swing();
		if(TipoSwing.STRIKE == swing){
			jugador.aumentaStrike();
		}
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Strike NO recibe Enum Strike", esperada, swing);
		Assert.assertEquals("Jugador con Strike en Cero NO aumenta a Uno", 1, jugador.getStrike());
	}		
	
	@Test
	public void CuandoJugadorObtieneTercerStrikeEntoncesjugadorEstaOut(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());	
		jugador.aumentaStrike();
		jugador.aumentaStrike();
		
		TipoSwing esperada = TipoSwing.STRIKE;
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.STRIKE);	
		
		
		// Act
		TipoSwing swing = jugador.swing();
		if(TipoSwing.STRIKE == swing){
			jugador.aumentaStrike();
		}
		
		if(jugador.getStrike() == Constantes.Jugador.MAX_STRIKE_PERMITIDO){
			jugador.jugadorEstaOut();
		}
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Strike NO recibe Enum Strike", esperada, swing);
		Assert.assertEquals("Jugador con Strike en Dos NO aumenta a Tres", Constantes.Jugador.MAX_STRIKE_PERMITIDO, jugador.getStrike());
		Assert.assertTrue("Jugador con 3 Strike NO obtiene un Out", jugador.isOut());
	}	
	
	@Test
	public void CuandoJugadorObtieneBolaEntoncesAumentaBolaEn1(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());				
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BOLA);	
		int bolaEsperada = 1;
		TipoSwing esperada = TipoSwing.BOLA;
		
		// Act
		TipoSwing swing = jugador.swing();
		if(TipoSwing.BOLA == swing){
			jugador.aumentaBola();
		}
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing BOLA NO recibe valor Enum BOLA", esperada, swing);
		Assert.assertEquals("Jugador con Bola en Cero NO aumenta a Uno", bolaEsperada, jugador.getBola());
	}
	
	@Test
	public void CuandoJugadorObtiene4BolaEntoncesAvanzaPosicion(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());
		jugador.aumentaBola();
		jugador.aumentaBola();
		jugador.aumentaBola();
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BOLA);	
		int posicionEsperada = 1;
		int bolaEsperada = Constantes.Jugador.MAX_BOLA_PERMITIDA;
		TipoSwing swingEsperado = TipoSwing.BOLA;
		
		// Act
		TipoSwing swing = jugador.swing();
		if(TipoSwing.BOLA == swing){
			jugador.aumentaBola();
			if (jugador.getBola() == Constantes.Jugador.MAX_BOLA_PERMITIDA) {

				jugador.indicaAvanceUnaBase();
				jugador.avanzaPosicion();
			}
		}				
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertSame("Jugador solicita Swing Bola NO recibe Enum Bola", swingEsperado, swing);
		Assert.assertEquals("Jugador con Bola en Tres NO aumenta a Cuatro", bolaEsperada, jugador.getBola());
		Assert.assertEquals("Jugador en poisicion 0 y 4 Bolas NO avanza a posicion 1", posicionEsperada, jugador.getPosicion());
	}
	
	@Test
	public void CuandoJugadorCon2BolasObtiene1BolaEntoncesPermaneceEnMismaPosicionYBolasAumentaEn3(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A1", SwingAleatorio, new PresentadorConsola());
		jugador.aumentaBola();
		jugador.aumentaBola();
		
		
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BOLA,TipoSwing.BATEO);	
		
		int posicionEsperada = 1;
		int bolasEsperadas = 3;
			
		// Act
		jugador.jugar();
		
		//Assert
		verify(SwingAleatorio, times(2)).generarSwing();
		Assert.assertEquals("Jugador con 2 Bolas NO aumenta en 3", bolasEsperadas, jugador.getBola());
		Assert.assertEquals("Jugador en poisicion 0, 3 Bolas y Batea NO avanza en posicion 1", posicionEsperada, jugador.getPosicion());
	}

	
	
	@Test
	public void CuandoJugadorObtiene4BolaEntoncesTodosLosJugadoresAvanzaPosicion(){
		
		// Arrange
		Jugador jugador1 = new Jugador("Jugador A1", SwingAleatorio, new PresentadorConsola());
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		Jugador jugador2 = new Jugador("Jugador A2", SwingAleatorio, new PresentadorConsola());
		jugador2.avanzaPosicion();
		jugador2.avanzaPosicion();
		Jugador jugador3 = new Jugador("Jugador A3", SwingAleatorio, new PresentadorConsola());
		jugador3.avanzaPosicion();
		
		Jugador jugador4 = new Jugador("Jugador A4", SwingAleatorio, new PresentadorConsola());
		jugador4.aumentaBola();
		jugador4.aumentaBola();
		jugador4.aumentaBola();
		
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.BOLA);	
		
		int posicionEsperadaJugador1 = 4;
		int posicionEsperadaJugador2 = 3;
		int posicionEsperadaJugador3 = 2;
		int posicionEsperadaJugador4 = 1;
	
		jugador4.addObserver(jugador3);
		jugador4.addObserver(jugador2);
		jugador4.addObserver(jugador1);
		
		// Act
		jugador4.jugar();
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertEquals("Jugador4 en poisicion 0 y 4 Bolas NO avanza a posicion 1", posicionEsperadaJugador4, jugador4.getPosicion());
		Assert.assertEquals("Jugador3 en poisicion 1 NO avanza a posicion 2", posicionEsperadaJugador3, jugador3.getPosicion());
		Assert.assertEquals("Jugador2 en poisicion 2 NO avanza a posicion 3", posicionEsperadaJugador2, jugador2.getPosicion());
		Assert.assertEquals("Jugador1 en poisicion 3 NO avanza a posicion 4", posicionEsperadaJugador1, jugador1.getPosicion());
	}
	

	@Test
	public void CuandoJugadorObtieneHomeRunEntoncesTodosLosJugadoresAvanzaPosicionHome(){
		
		// Arrange
		Jugador jugador1 = new Jugador("Jugador A1", SwingAleatorio, new PresentadorConsola());
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		jugador1.avanzaPosicion();
		Jugador jugador2 = new Jugador("Jugador A2", SwingAleatorio, new PresentadorConsola());
		jugador2.avanzaPosicion();
		jugador2.avanzaPosicion();
		Jugador jugador3 = new Jugador("Jugador A3", SwingAleatorio, new PresentadorConsola());
		jugador3.avanzaPosicion();
		
		Jugador jugador4 = new Jugador("Jugador A4", SwingAleatorio, new PresentadorConsola());
		jugador4.aumentaBola();
		jugador4.aumentaBola();
		jugador4.aumentaBola();
		
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.HOMERUN);	
		
		int posicionEsperadaJugadores = Constantes.Juego.POSICION_HOME;
		
		jugador4.addObserver(jugador3);
		jugador4.addObserver(jugador2);
		jugador4.addObserver(jugador1);
		
		// Act
		jugador4.jugar();
		
		//Assert
		verify(SwingAleatorio).generarSwing();
		Assert.assertEquals("Jugador4 en poisicion 0 y 4 Bolas NO avanza a posicion 1", posicionEsperadaJugadores, jugador4.getPosicion());
		Assert.assertEquals("Jugador3 en poisicion 1 NO avanza a posicion 2", posicionEsperadaJugadores, jugador3.getPosicion());
		Assert.assertEquals("Jugador2 en poisicion 2 NO avanza a posicion 3", posicionEsperadaJugadores, jugador2.getPosicion());
		Assert.assertEquals("Jugador1 en poisicion 3 NO avanza a posicion 4", posicionEsperadaJugadores, jugador1.getPosicion());
	}

	@Test(expected = ExcepcionMovimientoInvalido.class)
	public void  CuandoJugadorObtieneSwingOtroEntoncesExcepcionMovimientoInvalido(){
		
		// Arrange
		Jugador jugador = new Jugador("Jugador A", SwingAleatorio, new PresentadorConsola());
		
		when(SwingAleatorio.generarSwing()).thenReturn(TipoSwing.OTRO);
		// Act
		jugador.jugar();
	}
	
	
}