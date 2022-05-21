package menorDistancia;


public class DistanciaPontos {
	private char pDestino, pSentido; //pSentido é o ponto adjacente do ponto em questão que leva ao pDestino 
	private double distancia;
	
	public DistanciaPontos() {
	}

	public char getpDestino() {
		return pDestino;
	}

	public void setpDestino(char pDestino) {
		this.pDestino = pDestino;
	}

	public char getpSentido() {
		return pSentido;
	}

	public void setpSentido(char pSentido) {
		this.pSentido = pSentido;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
}
