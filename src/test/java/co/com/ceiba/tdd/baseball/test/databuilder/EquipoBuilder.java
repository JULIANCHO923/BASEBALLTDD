package co.com.ceiba.tdd.baseball.test.databuilder;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.swing.SwingAleatorio;
import co.com.ceiba.tdd.baseball.dominio.Constantes;
import co.com.ceiba.tdd.baseball.dominio.Equipo;
import co.com.ceiba.tdd.baseball.dominio.Jugador;
import co.com.ceiba.tdd.baseball.presentador.Presentador;

public class EquipoBuilder {

	private int id;
	private String nombre;
	private int out;
	private int carreras;	
	private List<Jugador> jugadores;
	private Swing swingAleatorio;
	private Presentador presentador;
	
	public EquipoBuilder(){
		this.id = Constantes.Juego.EQUIPO_UNO;
		this.nombre = "TeamA";
		this.out = 0;
		this.carreras = 0;
		this.jugadores = new ArrayList<Jugador>();
		this.swingAleatorio = new SwingAleatorio();
	}
	
	public Equipo build(){
		return new Equipo(id, nombre, swingAleatorio, presentador);
	}
	
	public EquipoBuilder conId(int id){
		this.id = id;
		return this;
	}		
	
	public EquipoBuilder conCarreras(int carreras){
		this.carreras = carreras;
		return this;
	}		
	
	public EquipoBuilder conNombre(String nombre){
		this.nombre = nombre;
		return this;
	}		
	
	public EquipoBuilder conPresentador(Presentador presentador){
		this.presentador = presentador;
		return this;
	}
	
	public EquipoBuilder conSwing(Swing generarAleatorio){
		this.swingAleatorio = generarAleatorio;
		return this;
	}
	
}
