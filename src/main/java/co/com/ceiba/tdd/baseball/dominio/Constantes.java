package co.com.ceiba.tdd.baseball.dominio;

public final class Constantes {

	/* Constantes del jugador */
	public final class Jugador {
		
		private Jugador(){
			throw new IllegalStateException("Constantes de Jugador");
		}
		
		public static final int SWING_MAX = 100;
		
		public static final int RANGO_INICAL_BATEO = 0;
		public static final int RANGO_FINAL_BATEO = 39;
		public static final int RANGO_INICAL_HOME_RUN = 40;
		public static final int RANGO_FINAL_HOME_RUN = 45;		
		public static final int RANGO_INICAL_BOLA = 46;
		public static final int RANGO_FINAL_BOLA = 55;
		public static final int RANGO_INICAL_STRIKE = 56;
		public static final int RANGO_FINAL_STRIKE = 100;
		
		public static final int SWING_OTRO = 0;
		public static final int SWING_BATEO = 1;
		public static final int SWING_BOLA = 2;
		public static final int SWING_STRIKE = 3;
		
		
		public static final boolean NO_ESTA_OUT = false;
		public static final boolean ESTA_OUT = true;
		
		public static final int NO_AVANZA = 0;
		public static final int AVANZA_UNA_BASE = 1;
		public static final int INICIO_HOME_RUN = 2;
		
		public static final int STRIKE_INICIAL = 0;
		public static final int MAX_STRIKE_PERMITIDO = 3;
		
		public static final int BOLA_INICIAL = 0;
		public static final int MAX_BOLA_PERMITIDA = 4;
		
		public static final int POSICION_INICIAL = 0;
	}

	/* Constantes del Equipo */
	public final class Equipo {
		
		private Equipo(){
			throw new IllegalStateException("Constantes de Equipo");
		}
		
		public static final int POSICION_INICIAL_JUGADOR_EN_LISTA = 0;
		
		public static final int CARRERAS_INICIALES = 0;
		public static final int OUT_INICIAL = 0;
		public static final int MAX_OUT_PERMITIDO = 3;
		public static final int JUGADORES_INICIALES = 0;
		public static final int  AVANCE_JUGADORES_PARTICIPANDO=1;
	}

	/* Constantes del Juego */
	public final class Juego {
		 
		private Juego(){
			throw new IllegalStateException("Constantes de Juego");
		}
		
		public static final int POSICION_HOME = 4;
		public static final int ENTRADAS = 4;
		public static final int NUM_EQUIPOS = 2;
		
		public static final int EQUIPO_UNO = 0;
		public static final int EQUIPO_DOS = 1;
		
		public static final double AVANCE_POR_CAMBIO_ROL_EN_ENTRADAS = 0.5;		
		public static final int AVANCE_ENTRADAS = 1;
		public static final int AVANCE_OUT_EQUIPO = 1;
		public static final int AVANCE_CARRERAS_EQUIPO= 1;
		
		public static final int AVANCE_POSICION_JUGADOR = 1;		
		public static final int AVANCE_STRIKE_JUGADOR = 1;
		public static final int AVANCE_BOLA_JUGADOR = 1;
		public static final int AVANCE_OUT_JUGADOR = 1;
	}
}
