package co.com.ceiba.tdd.baseball.dominio;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.tdd.baseball.presentador.Presentador;

public class Juego {

	private List<Integer> marcadorPorEntradas;
	private Presentador presentador;

	public Juego(Presentador presentador) {
		this.presentador = presentador;
		this.marcadorPorEntradas = new ArrayList<>();
	}

	public String encabezadoMarcadorJuego() {
		StringBuilder marcadorActualJuego = new StringBuilder();
		marcadorActualJuego.append("\n######## MARCADOR FINAL ########");
		marcadorActualJuego.append("\n| Entradas | ");

		for (int i = 0; i < (this.marcadorPorEntradas.size() / 2); i++) {
			marcadorActualJuego.append((i + 1) + "  | ");
		}
		marcadorActualJuego.append("\n");

		return marcadorActualJuego.toString();
	}

	public String marcadorEquipo(int i) {
		StringBuilder marcador = new StringBuilder();
		int carrerasTotales = 0;
		for (; i < this.marcadorPorEntradas.size(); i += 2) {
			marcador.append(this.marcadorPorEntradas.get(i) + " | ");
			carrerasTotales += this.marcadorPorEntradas.get(i);
		}
		marcador.append(" => " + carrerasTotales + "\n");
		return marcador.toString();
	}

	public void calcularCarreras(Equipo equipo, int entradaActual) {
		
		int carrerasObtenidasHastaLaEntradaAnterior = 0;
		int numeroEquipo = equipo.getId();
		
		for (int i = numeroEquipo; i < this.marcadorPorEntradas.size(); i += Constantes.Juego.NUM_EQUIPOS) {
			carrerasObtenidasHastaLaEntradaAnterior += this.marcadorPorEntradas.get(i);
		}
		int carrerasUltimaEntrada = equipo.getCarreras() - carrerasObtenidasHastaLaEntradaAnterior;
		int posicionEquipoPorEntrada = ((entradaActual - 1) * Constantes.Juego.NUM_EQUIPOS) + numeroEquipo;
		this.marcadorPorEntradas.add(posicionEquipoPorEntrada, carrerasUltimaEntrada);
	}

	public void iniciarJuego(Equipo equipoA, Equipo equipoB) {
		int entradaActual = 1;
		
		while (entradaActual <= Constantes.Juego.ENTRADAS) {
			this.presentador.comunicar("INICIO de la a entrada: " + entradaActual);
			
			jugar(equipoA);		
			calcularCarreras(equipoA, entradaActual);

			jugar(equipoB);		
			calcularCarreras(equipoB, entradaActual);
			
			this.presentador.comunicar("FIN de la a entrada: " + entradaActual);

			entradaActual += Constantes.Juego.AVANCE_ENTRADAS;
		}

		presentador.comunicar(this.encabezadoMarcadorJuego() + "|   "+ equipoA.getNombre() + "  | " + this.marcadorEquipo(equipoA.getId()) + "|   "+ equipoB.getNombre() + "  | " + this.marcadorEquipo(equipoB.getId()));
	}
	
	public void jugar(Equipo equipo){
		this.presentador.comunicar("->El equipo: " + equipo.getNombre() + " inicia su ataque:");
		equipo.jugar();
		equipo.limpiarValores();
	}

	public List<Integer> getMarcador(){
		return this.marcadorPorEntradas;
	}
	
}
