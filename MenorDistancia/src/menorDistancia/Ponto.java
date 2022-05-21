package menorDistancia;

import java.util.ArrayList;

/* Cada ponto é uma cidade. X e Y são os pontos das coordenadas cartesianas
 * Id o nome da cidade. Update caso sua listaDistancias necessite de atualização
 * ListaDistancias contem a distancia e sentido para um determinado destino.
*/

public class Ponto {
	private int x, y;
	private char id;
	private boolean update = false;
	private ArrayList<DistanciaPontos> listaDistancias;
	
	public Ponto(char id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.listaDistancias = new ArrayList<DistanciaPontos>();
	}

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean getUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public ArrayList<DistanciaPontos> getListaDistancias() {
		return listaDistancias;
	}

	public void addListaDistancias(DistanciaPontos distanciaPonto) {
		this.listaDistancias.add(distanciaPonto);
	}
}
