package co.com.ceiba.tdd.baseball.exception;

public class ExcepcionJugadorEnPosicionInicial extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public ExcepcionJugadorEnPosicionInicial(String mensaje){
		super(mensaje);		
	}
	
}
