package Taller03;

public class HombreLobo extends Monstruo implements Mordedor, Furioso {

	private int ferocidad;
	private int faseLunar;

	public HombreLobo(int vida, int velocidad, int fuerza, int resistencia,
			int ferocidad, int faseLunar) {
		super(vida, velocidad, fuerza, resistencia);
		this.ferocidad = ferocidad;
		this.faseLunar = faseLunar;
	}

	public int getFerocidad() {
		return ferocidad;
	}

	public void setFerocidad(int ferocidad) {
		this.ferocidad = ferocidad;
	}

	public int getFaseLunar() {
		return faseLunar;
	}

	public void setFaseLunar(int faseLunar) {
		this.faseLunar = faseLunar;
	}

	@Override
	public String getTipo() {
		return "HombreLobo";
	}

	@Override
	public String morder() {
		return "El hombre lobo utiliza sus colmillos";
	}

	@Override
	public String enfurecerse() {
		return "El hombre lobo aumenta su ferocidad";
	}

	@Override
	public double calcularCarrera() {
		return (getVelocidad() + (double) ferocidad / 5) * faseLunar / 4;
	}

	@Override
	public double calcularCaza() {
		return (double) ferocidad * faseLunar / 4 + (double) getVelocidad() / 4;
	}

	@Override
	public double calcularLanzamiento() {
		return getFuerza() + (double) ferocidad * faseLunar / 8;
	}

	@Override
	public double calcularAmanecer() {
		return (double) (getResistencia() + getVida()) / 2 * (5 - faseLunar) / 4;
	}
}
