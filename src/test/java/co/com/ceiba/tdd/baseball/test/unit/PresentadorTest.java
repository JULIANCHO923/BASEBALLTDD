package co.com.ceiba.tdd.baseball.test.unit;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import co.com.ceiba.tdd.baseball.presentador.Presentador;
import co.com.ceiba.tdd.baseball.presentador.PresentadorConsola;

public class PresentadorTest {

	@Test
	public void CuandoEnvioMensajeEsteSeMuestraEnPantallaPorConsola(){
		
		// Arrange
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();		
		System.setOut(new PrintStream(outContent));		
		Presentador presentadorConsola = new PresentadorConsola();
		String esperado = "Jugador Batea";
		
		// act
		presentadorConsola.comunicar(esperado);
		
		// assert
		String actual = outContent.toString().trim();				
		assertEquals("La salida por consola no muestra el mensaje de bateo", esperado,actual);
	}
	
	
	
}
