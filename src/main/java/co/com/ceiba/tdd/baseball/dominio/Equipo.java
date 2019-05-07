package co.com.ceiba.tdd.baseball.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.exception.ExcepcionJugadorEnPosicionInicial;
import co.com.ceiba.tdd.baseball.presentador.Presentador;

public class Equipo implements Observer {

	private int id;
	private String nombre;
	private int out;
	private int carreras;
	private List<Jugador> jugadores;
	private Swing swingAleatorio;
	private int jugadoresParticipando; // es más informativa para generar
										// jugadores con nuevo id
	private Presentador presentador;

	public Equipo(int id, String nombre, Swing generarAleatorio, Presentador presentador) {
		this.id = id;
		this.nombre = nombre;
		this.out = Constantes.Equipo.OUT_INICIAL;
		this.carreras = Constantes.Equipo.CARRERAS_INICIALES;
		this.jugadores = new ArrayList<>();
		this.swingAleatorio = generarAleatorio;
		this.jugadoresParticipando = Constantes.Equipo.JUGADORES_INICIALES;
		this.presentador = presentador;
	}

	public void adicionarNuevoJugador() {
		String nombreJugador = this.getNombre() + "-" + this.jugadoresParticipando;
		Jugador jugador = new Jugador(nombreJugador, this.swingAleatorio, this.presentador);
		adicionarJugador(jugador);
		this.jugadoresParticipando += Constantes.Equipo.AVANCE_JUGADORES_PARTICIPANDO;
	}

	public Jugador jugadorPosicionInicial() {
		return this.jugadores.get(Constantes.Equipo.POSICION_INICIAL_JUGADOR_EN_LISTA);
	}

	public void adicionarJugador(Jugador jugador) {
		jugador.addObserver(this);
		for (Jugador jugador1 : this.jugadores) {
			jugador.addObserver(jugador1);
		}
		this.jugadores.add(Constantes.Equipo.POSICION_INICIAL_JUGADOR_EN_LISTA, jugador);
	}

	public void removerJugadoresConOutOEnHome() {
		for (int i = this.jugadores.size() - 1; i >= 0; i--) {
			if (this.jugadores.get(i).isOut()
					|| Constantes.Juego.POSICION_HOME == this.jugadores.get(i).getPosicion()) {
				this.presentador.comunicar("El jugador: " + this.jugadores.get(i).getId() + " SALE del juego");
				removerJugadorDelJuego(i);
			}
		}
	}

	private void removerJugadorDelJuego(int posicion) {
		this.jugadores.get(posicion).deleteObservers();
		this.jugadores.remove(posicion);
	}

	public void aumentarCarreras() {
		this.carreras += Constantes.Juego.AVANCE_CARRERAS_EQUIPO;
		this.presentador
				.comunicar("#ANOTACIÓN DEL EQUIPO " + this.nombre + " ahora suman " + this.carreras + " carreras");
	}

	public void aumentarOut() {
		this.out += Constantes.Juego.AVANCE_OUT_EQUIPO;
		this.presentador.comunicar("#OUT DEL EQUIPO " + this.nombre + " van " + this.out + " outs hasta el momento");
	}

	public void limpiarValores() {
		this.out = Constantes.Equipo.OUT_INICIAL;
		this.jugadores = new ArrayList<>();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Jugador) {
			Jugador jugador = (Jugador) arg1;

			if (jugador.getPosicion() == Constantes.Juego.POSICION_HOME) {
				presentador.comunicar("->HOME: " + jugador.getId());
				this.aumentarCarreras();
			
			} else if (jugador.isOut()) {
				presentador.comunicar("->OUT: " + jugador.getId());
				this.aumentarOut();
				
				if (this.getOut() >= Constantes.Equipo.MAX_OUT_PERMITIDO) {
					presentador.comunicar("->TRES OUTS: El equipo acaba su ataque ");
				}
			}
			removerJugadoresConOutOEnHome();
		}
	}

	public void jugar() {
		while (this.getOut() < Constantes.Equipo.MAX_OUT_PERMITIDO) {
			iniciarJugador();
		}
	}

	public void iniciarJugador() {
		if (!hayJugadorDisponibleEnPosicionSwing()) {
			adicionarNuevoJugador();
			jugadorPosicionInicial().jugar();
		} else {
			throw new ExcepcionJugadorEnPosicionInicial("Hay un jugador que esta ocupando la posición inicial");
		}
	}

	public boolean hayJugadorDisponibleEnPosicionSwing() {
		return !this.jugadores.isEmpty()
				&& this.jugadorPosicionInicial().getPosicion() == Constantes.Jugador.POSICION_INICIAL
				&& !this.jugadorPosicionInicial().isOut();
	}

	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getOut() {
		return this.out;
	}

	public int getCarreras() {
		return this.carreras;
	}

	public List<Jugador> getJugadores() {
		return this.jugadores;
	}
}
