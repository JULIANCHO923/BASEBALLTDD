package co.com.ceiba.tdd.baseball.main;

import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.swing.SwingAleatorio;
import co.com.ceiba.tdd.baseball.dominio.Constantes;
import co.com.ceiba.tdd.baseball.dominio.Juego;
import co.com.ceiba.tdd.baseball.dominio.Equipo;
import co.com.ceiba.tdd.baseball.presentador.Presentador;
import co.com.ceiba.tdd.baseball.presentador.PresentadorConsola;

public class Jugar {
	
	private static Presentador presentadorConsola = new PresentadorConsola();
	private static Swing swingAleatorio = new SwingAleatorio();
	
	public static void main(String[] args) {
		
		Juego juego = new Juego(presentadorConsola);
		
		Equipo equipoA = new Equipo(Constantes.Juego.EQUIPO_UNO,"TeamA",swingAleatorio, presentadorConsola);
		Equipo equipoB = new Equipo(Constantes.Juego.EQUIPO_DOS,"TeamB", swingAleatorio, presentadorConsola);		

		juego.iniciarJuego(equipoA, equipoB);				
	}
}