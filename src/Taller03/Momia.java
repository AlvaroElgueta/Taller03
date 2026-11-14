package Taller03;

public class Momia extends Monstruo implements Regenerable, Furioso {

	private int longitudVendajes;

	public Momia(int vida, int velocidad, int fuerza, int resistencia, int longitudVendajes) {
		super(vida, velocidad, fuerza, resistencia);
		this.longitudVendajes = longitudVendajes;
	}

	public int getLongitudVendajes() {
		return longitudVendajes;
	}

	public void setLongitudVendajes(int longitudVendajes) {
		this.longitudVendajes = longitudVendajes;
	}

	@Override
	public String getTipo() {
		return "Momia";
	}

	@Override
	public String regenerarse() {
		return "La momia utiliza sus vendajes para regenerarse";
	}

	@Override
	public String enfurecerse() {
		return "La momia se enfurece";
	}

	@Override
	public double calcularCarrera() {
		return (double) getVelocidad() * 100 / (100 + longitudVendajes);
	}

	@Override
	public double calcularCaza() {
		return (double) longitudVendajes * getFuerza() / 200;
	}

	@Override
	public double calcularLanzamiento() {
		return getFuerza() + (double) longitudVendajes / 10;
	}

	@Override
	public double calcularAmanecer() {
		return (double) (getResistencia() + getVida()) / 2
				* (1 + (double) longitudVendajes / 300);
	}
}
