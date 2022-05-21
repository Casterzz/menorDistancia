package menorDistancia;


// Cada aresta conecta dois pontos (cidades), e possui um peso (distância)

public class Aresta<Ponto> {
	private Double peso;
	private Vertice<Ponto> p1, p2;
	
	public Aresta(double peso, Vertice<Ponto> p1, Vertice<Ponto> p2) {
		this.peso = peso;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Vertice<Ponto> getP1() {
		return p1;
	}
	public void setP1(Vertice<Ponto> p1) {
		this.p1 = p1;
	}
	public Vertice<Ponto> getP2() {
		return p2;
	}
	public void setP2(Vertice<Ponto> p2) {
		this.p2 = p2;
	}
}
