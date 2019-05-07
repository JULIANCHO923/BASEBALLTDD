package co.com.ceiba.tdd.baseball.dominio;

import java.util.Observable;
import java.util.Observer;

import co.com.ceiba.tdd.baseball.exception.ExcepcionMovimientoInvalido;
import co.com.ceiba.tdd.baseball.presentador.Presentador;
import co.com.ceiba.tdd.baseball.swing.Swing;
import co.com.ceiba.tdd.baseball.swing.TipoSwing;

public class Jugador extends Observable implements Observer {

	private String id;
	private boolean out;
	private int strike;
	private int bola;
	private int posicion;
	private int avance; // 0 No avanza 1 Avanza una Base 2 HomeRun
	private Swing swing;
	private Presentador presentador;

	public Jugador(String id, Swing swing, Presentador presentador) {
		this.id = id;
		this.out = Constantes.Jugador.NO_ESTA_OUT;
		this.strike = Constantes.Jugador.STRIKE_INICIAL;
		this.bola = Constantes.Jugador.BOLA_INICIAL;
		this.posicion = Constantes.Jugador.POSICION_INICIAL;
		this.avance = Constantes.Jugador.NO_AVANZA;
		this.swing = swing;
		this.presentador = presentador;
	}

	public TipoSwing swing() {
		return swing.generarSwing();
	}

	public void avanzaPosicion() {

		if (this.posicion < Constantes.Juego.POSICION_HOME) {

			this.posicion += Constantes.Juego.AVANCE_POSICION_JUGADOR;

			this.presentador.comunicar("\n--" + this.getId() + " avanza al plato #" + this.getPosicion());

		} else {
			this.presentador.comunicar("Jugador moviendose ilegal: " + this.getId());
			throw new ExcepcionMovimientoInvalido(
					"El jugador en posición HOME intenta avance a una posición más no determinada en el juego");
		}
	}

	public void aumentaStrike() {
		this.strike += Constantes.Juego.AVANCE_STRIKE_JUGADOR;
	}

	public void aumentaBola() {
		this.bola += Constantes.Juego.AVANCE_BOLA_JUGADOR;
	}

	public void jugadorEstaOut() {
		this.out = Constantes.Jugador.ESTA_OUT;
	}

	public boolean tieneMaximoStrike() {
		return Constantes.Jugador.MAX_STRIKE_PERMITIDO == this.strike;
	}	

	public boolean tieneMaximaBola() {
		return Constantes.Jugador.MAX_BOLA_PERMITIDA == this.bola;
	}	
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Jugador) {
			Jugador jugador = (Jugador) arg1;
			if (jugador.getAvance() == Constantes.Jugador.AVANZA_UNA_BASE) {
				this.avanzaPosicion();
			} else if (jugador.getAvance() == Constantes.Jugador.INICIO_HOME_RUN) {
				this.avanzaHomeRun();
			}

			/*
			 * Notifica a sus observadores que avanzo para que en caso tal de
			 * que este en home, sea eliminado
			 */			
			this.notificarObservadores();
		}
	}

	public void jugar() {
		while (this.getAvance() == Constantes.Jugador.NO_AVANZA && !this.isOut()) {
			TipoSwing tipoSwing = this.swing();
			this.presentador.comunicar("\nJugador: " + this.getId() + " realiza un swing --> ");
			switch (tipoSwing) {
			case BATEO:
				this.presentador.comunicar("BATEO");
				this.indicaAvanceUnaBase();
				this.avanzaPosicion();
				this.notificarObservadores();
				break;
			case HOMERUN:
				this.presentador.comunicar("HOMERUN!!!");
				this.indicaAvanceHomeRun();
				this.avanzaHomeRun();
				this.notificarObservadores();
				break;
			case BOLA:
				this.presentador.comunicar("BOLA");
				this.aumentaBola();
				if (this.tieneMaximaBola()) {

					this.presentador.comunicar("\n*" + this.getId() + " acumula "
							+ Constantes.Jugador.MAX_BOLA_PERMITIDA + " Todos los jugadores avanzan una posición");
					this.indicaAvanceUnaBase();
					this.avanzaPosicion();
					this.notificarObservadores();
				}
				break;
			case STRIKE:
				this.presentador.comunicar("STRIKE");
				this.aumentaStrike();
				if (this.tieneMaximoStrike()) {

					this.presentador.comunicar("\n*" + this.getId() + " acumula "
							+ Constantes.Jugador.MAX_STRIKE_PERMITIDO + " Strike: Esta OUT");

					this.jugadorEstaOut();
					this.notificarObservadores();
				}
				break;
			case OTRO:
				this.presentador.comunicar("OTRO");
				throw new ExcepcionMovimientoInvalido(
						"El jugador " + this.getId() + " realizó un SWING equivalente a OTRO");
			default:
				throw new ExcepcionMovimientoInvalido("El jugador " + this.getId() + " realizó un SWING no permitido");
			}
		}
		this.indicaNoAvance();
	}
	
	public void indicaAvanceUnaBase() {
		this.avance = Constantes.Jugador.AVANZA_UNA_BASE;
	}

	public void indicaNoAvance() {
		this.avance = Constantes.Jugador.NO_AVANZA;
	}

	public void indicaAvanceHomeRun() {
		this.avance = Constantes.Jugador.INICIO_HOME_RUN;
	}
	
	public void avanzaHomeRun() {
		this.posicion = Constantes.Juego.POSICION_HOME;
	}
	
	public void notificarObservadores() {
		synchronized (this) {
			this.setChanged();
			this.notifyObservers(this);
		}
	}
	
	public String getId() {
		return this.id;
	}

	public boolean isOut() {
		return this.out;
	}

	public int getStrike() {
		return this.strike;
	}

	public int getBola() {
		return this.bola;
	}

	public int getPosicion() {
		return this.posicion;
	}	

	public int getAvance() {
		return this.avance;
	}
}
