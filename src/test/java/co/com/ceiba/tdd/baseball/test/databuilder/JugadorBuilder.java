package co.com.ceiba.tdd.baseball.test.databuilder;

import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.swing.SwingAleatorio;
import co.com.ceiba.tdd.baseball.dominio.Constantes;
import co.com.ceiba.tdd.baseball.dominio.Jugador;
import co.com.ceiba.tdd.baseball.presentador.Presentador;
import co.com.ceiba.tdd.baseball.presentador.PresentadorConsola;

public class JugadorBuilder {

	private String id;
	private boolean out;
	private int strike;	
	private int posicion;
	private int bola;
	private Swing swingAleatorio;
	private Presentador presentador;
	
	public JugadorBuilder(){
		this.id = "TeamA-1";
		this.out = false;
		this.strike = Constantes.Jugador.STRIKE_INICIAL;
		this.bola = Constantes.Jugador.BOLA_INICIAL;
		this.posicion = 0;
		this.swingAleatorio = new SwingAleatorio();	
		this.presentador = new PresentadorConsola();
	}
	
	public Jugador build(){
		return new Jugador(id ,swingAleatorio, presentador);
	}
	
	public JugadorBuilder conId(String id){
		this.id= id;
		return this;
	}
	
	public JugadorBuilder conPresentador(Presentador presentador){
		this.presentador = presentador;
		return this;
	}
	
	public JugadorBuilder conPosicion(int posicion){
		this.posicion = posicion;		
		return this;
	}
	
	public JugadorBuilder conSwing(Swing swingAleatorio){
		this.swingAleatorio = swingAleatorio;		
		return this;
	}
	
}
