package Taller03;

public class Vampiro extends Monstruo implements Mordedor, Volador {

	private int velocidadDeVuelo;
	private int resistenciaAlSol;

	public Vampiro(int vida, int velocidad, int fuerza, int resistencia,
			int velocidadDeVuelo, int resistenciaAlSol) {
		super(vida, velocidad, fuerza, resistencia);
		this.velocidadDeVuelo = velocidadDeVuelo;
		this.resistenciaAlSol = resistenciaAlSol;
	}

	public int getVelocidadDeVuelo() {
		return velocidadDeVuelo;
	}

	public void setVelocidadDeVuelo(int velocidadDeVuelo) {
		this.velocidadDeVuelo = velocidadDeVuelo;
	}

	public int getResistenciaAlSol() {
		return resistenciaAlSol;
	}

	public void setResistenciaAlSol(int resistenciaAlSol) {
		this.resistenciaAlSol = resistenciaAlSol;
	}

	@Override
	public String getTipo() {
		return "Vampiro";
	}

	@Override
	public String morder() {
		return "El vampiro utiliza sus colmillos";
	}

	@Override
	public String volar() {
		return "El vampiro comienza a volar";
	}

	@Override
	public double calcularCarrera() {
		return (getVelocidad() + 2.0 * velocidadDeVuelo) / 2;
	}

	@Override
	public double calcularCaza() {
		return (double) (getFuerza() + getVelocidad()) / 3;
	}

	@Override
	public double calcularLanzamiento() {
		return getFuerza() + (double) velocidadDeVuelo / 4;
	}

	@Override
	public double calcularAmanecer() {
		return (double) getResistencia() * resistenciaAlSol / 100;
	}
}
