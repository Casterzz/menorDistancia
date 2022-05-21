package menorDistancia;

import java.util.ArrayList;

// Vértice é o ponto que será a cidade para o propóstio final

public class Vertice<Ponto> {
	private Ponto ponto;
	private ArrayList<Aresta<Ponto>> arestas;
	
	public Vertice(Ponto ponto) {
		this.ponto = ponto;
		this.arestas = new ArrayList<Aresta<Ponto>>();
	}

	public Ponto getPonto() {
		return ponto;
	}
	
	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}
	
	public void addAresta(Aresta<Ponto> aresta) {
		this.arestas.add(aresta);
	}

	public ArrayList<Aresta<Ponto>> getArestas() {
		return arestas;
	}
}
