package co.com.ceiba.tdd.baseball.presentador;

public class PresentadorConsola implements Presentador {

	public void comunicar(String mensaje){
		System.out.println(mensaje);
	}
	
}
