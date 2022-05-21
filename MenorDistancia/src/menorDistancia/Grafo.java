package menorDistancia;

import java.util.ArrayList;

// Usado definição de grafo bidirecional

public class Grafo<Ponto> {
	private ArrayList<Vertice<Ponto>> vertices;
	private ArrayList<Aresta<Ponto>> arestas;
	
	public Grafo() {
		this.vertices = new ArrayList<Vertice<Ponto>>();
		this.arestas = new ArrayList<Aresta<Ponto>>();
	}
	
	public void addVertice(Ponto ponto) {
		this.vertices.add(new Vertice<Ponto>(ponto));
	}

	public void addAresta(double peso, Ponto p1, Ponto p2) {
		Vertice<Ponto> inicio = this.getVertice(p1);
		Vertice<Ponto> fim = this.getVertice(p2);
		Aresta<Ponto> aresta = new Aresta<Ponto>(peso, inicio, fim);
		inicio.addAresta(aresta);
		fim.addAresta(aresta);
		this.arestas.add(aresta);
	}
	
	public Vertice<Ponto> getVertice(Ponto ponto) {
		Vertice<Ponto> vertice = null;
		for(int i=0; i < this.vertices.size(); i++) {
			if (this.vertices.get(i).getPonto().equals(ponto)) {
				vertice = this.vertices.get(i);
				break;
			}
		}
		return vertice;
	}
	
	public ArrayList<Vertice<Ponto>> getArrayVertices() {
		return vertices;
	}
	
	public ArrayList<Aresta<Ponto>> getArestas(Ponto ponto) {
		Vertice<Ponto> vertice = getVertice(ponto);
		return (vertice.getArestas());
	}
}
