package Taller03;

public class Zombi extends Monstruo implements Mordedor, Regenerable {

	private int fuerzaDeMordida;

	public Zombi(int vida, int velocidad, int fuerza, int resistencia, int fuerzaDeMordida) {
		super(vida, velocidad, fuerza, resistencia);
		this.fuerzaDeMordida = fuerzaDeMordida;
	}

	public int getFuerzaDeMordida() {
		return fuerzaDeMordida;
	}

	public void setFuerzaDeMordida(int fuerzaDeMordida) {
		this.fuerzaDeMordida = fuerzaDeMordida;
	}

	@Override
	public String getTipo() {
		return "Zombi";
	}

	@Override
	public String morder() {
		return "El zombi muerde con fuerza";
	}

	@Override
	public String regenerarse() {
		return "El zombi regenera sus heridas";
	}
}
