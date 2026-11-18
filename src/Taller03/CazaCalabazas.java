package Taller03;

public class CazaCalabazas implements Minijuego {

	@Override
	public String getNombre() {
		return "Caza de Calabazas";
	}

	@Override
	public String getDescripcion() {
		return "Muerde la mayor cantidad de calabazas en 60 segundos";
	}

	@Override
	public String getHabilidadPremiada() {
		return "Morder";
	}

	@Override
	public double calcularPotencial(Monstruo monstruo) {
		return monstruo.calcularCaza();
	}
}
