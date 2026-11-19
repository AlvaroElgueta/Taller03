package Taller03;

public class ResultadoMinijuego {

	private Minijuego minijuego;
	private Monstruo monstruo;
	private String habilidad;
	private int puntaje;

	public ResultadoMinijuego(Minijuego minijuego, Monstruo monstruo, String habilidad, int puntaje) {
		this.minijuego = minijuego;
		this.monstruo = monstruo;
		this.habilidad = habilidad;
		this.puntaje = puntaje;
	}

	public Minijuego getMinijuego() {
		return minijuego;
	}

	public Monstruo getMonstruo() {
		return monstruo;
	}

	public String getHabilidad() {
		return habilidad;
	}

	public int getPuntaje() {
		return puntaje;
	}
}
