package Taller03;

public class LanzamientoLapidas implements Minijuego {

	@Override
	public String getNombre() {
		return "Lanzamiento de Lapidas";
	}

	@Override
	public String getDescripcion() {
		return "Lanza una lapida lo mas lejos posible";
	}

	@Override
	public String getHabilidadPremiada() {
		return "Enfurecerse";
	}

	@Override
	public double calcularPotencial(Monstruo monstruo) {
		return monstruo.calcularLanzamiento();
	}
}
