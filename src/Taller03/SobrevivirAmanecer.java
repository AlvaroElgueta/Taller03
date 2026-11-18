package Taller03;

public class SobrevivirAmanecer implements Minijuego {

	@Override
	public String getNombre() {
		return "Sobrevivir al Amanecer";
	}

	@Override
	public String getDescripcion() {
		return "Resiste los primeros rayos del sol";
	}

	@Override
	public String getHabilidadPremiada() {
		return "Regenerarse";
	}

	@Override
	public double calcularPotencial(Monstruo monstruo) {
		return monstruo.calcularAmanecer();
	}
}
