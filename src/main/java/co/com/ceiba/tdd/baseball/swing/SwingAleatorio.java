package co.com.ceiba.tdd.baseball.swing;

import java.util.Random;

import co.com.ceiba.tdd.baseball.dominio.Constantes;

public class SwingAleatorio implements Swing {

	@Override
	public TipoSwing generarSwing() {
		return swing(generarSwingAleatorio());
	}

	private int generarSwingAleatorio() {
		return new Random().nextInt(Constantes.Jugador.SWING_MAX);
	}

	private TipoSwing swing(int swing) {
		TipoSwing tipoSwing = TipoSwing.OTRO;
		if (esBateo(swing)) {
			tipoSwing = TipoSwing.BATEO;
		} else if (esHomeRun(swing)) {
			tipoSwing = TipoSwing.HOMERUN;
		} else if (esBola(swing)) {
			tipoSwing = TipoSwing.BOLA;
		} else if (esStrike(swing)) {
			tipoSwing = TipoSwing.STRIKE;
		}
		return tipoSwing;
	}

	private boolean esBateo(int swing) {
		return (swing >= Constantes.Jugador.RANGO_INICAL_BATEO && swing <= Constantes.Jugador.RANGO_FINAL_BATEO);
	}

	private boolean esHomeRun(int swing) {
		return (swing >= Constantes.Jugador.RANGO_INICAL_HOME_RUN && swing <= Constantes.Jugador.RANGO_FINAL_HOME_RUN);
	}

	private boolean esBola(int swing) {
		return (swing >= Constantes.Jugador.RANGO_INICAL_BOLA && swing <= Constantes.Jugador.RANGO_FINAL_BOLA);
	}

	private boolean esStrike(int swing) {
		return (swing >= Constantes.Jugador.RANGO_INICAL_STRIKE && swing <= Constantes.Jugador.RANGO_FINAL_STRIKE);
	}

}
