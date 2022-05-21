package menorDistancia;

/* Algoritmo da atividade proposta pela empresa Sinerji
 * 21/05/2022
 * @Victor Eduardo
 * */

import java.io.IOException;
import java.util.ArrayList;

public class menorDistancia {
	private static final int INFINITO = 999999;
	private static ArrayList<Ponto> listaPontos = new ArrayList<Ponto>();
	
	
	// Dado o id, retorna o ponto(cidade) da lista com esse mesmo id
	 
	public static Ponto achaPontoChar(char id) {
		Ponto ponto = null;
		for(int i = 0; i < listaPontos.size(); i++) {
			if (listaPontos.get(i).getId() == id) {
				ponto = listaPontos.get(i);
				break;
			}
		}
		return ponto;
	}
	
	// Calcula peso entre dois pontos
	
	public static double calculaPeso(char p1, char p2) {
		int x1 = achaPontoChar(p1).getX();
		int x2 = achaPontoChar(p2).getX();
		int y1 = achaPontoChar(p1).getY();
		int y2 = achaPontoChar(p2).getY();
		
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	/* Para cada cidade na lista, preenche sua lista respectiva (ListaDistancias),
	 * com o caminho inicial (sentido) que se deve tormar para todos os destinos possíveis,
	 * isso é, a principio, cada cidade só irá saber como chegar na sua cidade adjacente mais a distancia;
	 * cidades mais distantes serão setadas com distancia infinita 
	*/
	public static void setaDestinosIniciais(Grafo<Ponto> grafo) {
		char p1, p2, pAdjacente;
		double peso;
		
		for(int i = 0; i < listaPontos.size(); i++) {
			listaPontos.get(i).setUpdate(true);
			for(int j = 0; j < grafo.getArestas(listaPontos.get(i)).size(); j++) {
				p1 = grafo.getArestas(listaPontos.get(i)).get(j).getP1().getPonto().getId();
				p2 = grafo.getArestas(listaPontos.get(i)).get(j).getP2().getPonto().getId();
				peso = grafo.getArestas(listaPontos.get(i)).get(j).getPeso();
				
				if ((p1 == listaPontos.get(i).getId())) pAdjacente = p2;
				else pAdjacente = p1;

				DistanciaPontos distanciaPontos = new DistanciaPontos();
				distanciaPontos.setpDestino(pAdjacente);
				distanciaPontos.setpSentido(pAdjacente);
				distanciaPontos.setDistancia(peso);
				listaPontos.get(i).addListaDistancias(distanciaPontos);
			}
		}
		
		// Seta o infinito para cidades não adjacentes, ou '0' para ela mesma.
		for(int i = 0; i < listaPontos.size(); i++) {
			for(int j = 0; j < listaPontos.size(); j++) {
				if (procuraPorElementoDestino(listaPontos.get(i).getListaDistancias(), listaPontos.get(j).getId()) == -1) {
					DistanciaPontos distanciaPontos = new DistanciaPontos();
					distanciaPontos.setpDestino(listaPontos.get(j).getId());
					distanciaPontos.setpSentido('0');
					distanciaPontos.setDistancia(i==j ? 0:INFINITO);
					listaPontos.get(i).addListaDistancias(distanciaPontos);
				}
			}
		}
	}
	
	/* Dado uma lista contento todos os destinos possíveis, acha o index de um destino em específico
	 * ou -1 caso não o encontre.
	*/
	public static int procuraPorElementoDestino(ArrayList<DistanciaPontos> lista, char subElemento) {
		
		for(int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getpDestino() == subElemento) {
				return i;
			}
		}
		return -1;
	}
	
	// Procura por uma cidade na lista, retornando o index de onde ela está. -1 caso não encontre
	
	public static int procuraPorElementoPonto(char elemento) {
		for(int i = 0; i < listaPontos.size(); i++) {
			if (listaPontos.get(i).getId() == elemento) {
				return i;
			}
		}
		return -1;
	}
	
	// Função para debug. Para cada cidade, printa sua respectiva lista de destinos
	
	public static void printaLista() {
		for(int i = 0; i < listaPontos.size(); i++) {
			System.out.println("Id: " + listaPontos.get(i).getId());
			for(int j = 0; j < listaPontos.size(); j++) {
				System.out.println("Destino: " + listaPontos.get(i).getListaDistancias().get(j).getpDestino());
				System.out.println("Sentido: " + listaPontos.get(i).getListaDistancias().get(j).getpSentido());
				System.out.println("Distancia: " + listaPontos.get(i).getListaDistancias().get(j).getDistancia() + "\n");
			
			}
		}
	}
	
	// Verifica se há cidades que necessitam de atualização em sua lista de distancia
	
	public static boolean verificaSeHaUpdate() {
		for(int i = 0; i < listaPontos.size(); i++) {
			if (listaPontos.get(i).getUpdate() == true) return true;
		}
		return false;
	}
	
	/* Função principal. Funciona como um vírus que se espalha infinitamente até que todas as cidades
	 * saibam o sentido do melhor trajeto para chegar nas demais. Sempre que uma cidade é atualizada,
	 * passa-se a nova informação para as cidades vizinhas que, se forem atualizadas, passam adiante.
	 * */
	
	public static void otimizaEmT1(Grafo<Ponto> grafo) {
		
		char p1, p2, pCentral, pAdjacente, destinoFinal, destinoIntermediario;
		double peso, distanciaAteDestinoOriginal, distanciaAteDestinoConcorrente;
		int index, indexDestino, indexCentral;
		
		setaDestinosIniciais(grafo);
		
		while (verificaSeHaUpdate()) {
			for(int i = 0; i < listaPontos.size(); i++) {
				if (listaPontos.get(i).getUpdate() == true) {
						// Para cada cidade adjacente
						for(int j = 0; j < grafo.getArestas(listaPontos.get(i)).size(); j++) {
							p1 = grafo.getArestas(listaPontos.get(i)).get(j).getP1().getPonto().getId();
							p2 = grafo.getArestas(listaPontos.get(i)).get(j).getP2().getPonto().getId();
							peso = grafo.getArestas(listaPontos.get(i)).get(j).getPeso();
							
							if ((p1 == listaPontos.get(i).getId())) {
								pCentral = p1;
								pAdjacente = p2;
							} else {
								pCentral = p2;
								pAdjacente = p1;
							}
							
							indexCentral = procuraPorElementoPonto(pCentral);

							/* Uma vez encontrada a cidade central que "espalhará o vírus", compara todos os destinos armazenados com os destinos da cidade adjacente
							 * em questão. Se algum for mais vantajoso, atualiza esse destino para a cidade adjacente.
							 * */
							for (int m = 0; m < listaPontos.get(indexCentral).getListaDistancias().size(); m++) {
								
								destinoFinal = listaPontos.get(indexCentral).getListaDistancias().get(m).getpDestino();
								destinoIntermediario = pCentral;
								
								if (destinoFinal == destinoIntermediario || pAdjacente == destinoFinal) continue; // Não é necessário atualizar o destino para a cidade central ou adjacente.
								
								index = procuraPorElementoPonto(pAdjacente); // Caça o elemento pra mexer nele
								indexDestino = procuraPorElementoDestino(listaPontos.get(index).getListaDistancias(), destinoFinal);
								
								distanciaAteDestinoOriginal = listaPontos.get(index).getListaDistancias().get(indexDestino).getDistancia();
								distanciaAteDestinoConcorrente = listaPontos.get(indexCentral).getListaDistancias().get(m).getDistancia() + peso; // Nova oferta
								
								if (distanciaAteDestinoConcorrente < distanciaAteDestinoOriginal) {
									listaPontos.get(index).getListaDistancias().get(indexDestino).setpSentido(pCentral);
									listaPontos.get(index).getListaDistancias().get(indexDestino).setDistancia(distanciaAteDestinoConcorrente);
									listaPontos.get(index).setUpdate(true);
								}
							}
						}
				listaPontos.get(i).setUpdate(false);
				}
			}
		}
	}
	
	/* Função recurssiva que, a partir de uma cidade (v1), procura a próxima cidade em sua lista de distancias cujo destino será aquele de v2
	 * O fornecimento da distância é instântanea, enquanto que o forncedimento do caminho é exatamente o trajeto mínimo.
	*/
	public static void mostraMelhorTrajeto(char v1, char v2, boolean inicial) {
		int index = procuraPorElementoPonto(v1);
		int proximoIndex = procuraPorElementoDestino(listaPontos.get(index).getListaDistancias(), v2);
		char proximoV1 = listaPontos.get(index).getListaDistancias().get(proximoIndex).getpSentido();
		double distancia = listaPontos.get(index).getListaDistancias().get(proximoIndex).getDistancia();
		
		if (inicial) {
			System.out.print("Menor distancia de " + Character.toUpperCase(v1) + " ate " + Character.toUpperCase(v2) + " eh: " + distancia + "\nA melhor rota eh: "); 
			System.out.print(Character.toUpperCase(v1) + " ");
		}
		if (proximoV1 == '0') return;
		System.out.print(Character.toUpperCase(proximoV1) + " ");
		mostraMelhorTrajeto(proximoV1, v2, false);
	}
	
	public static void main(String[] args) throws IOException {
		char v1, v2;
		Grafo<Ponto> grafo = new Grafo<Ponto>();
		
		// Preenche o grafo de acordo com a imagem fornecida
		
		listaPontos.add(new Ponto('a', 4, 2));
		listaPontos.add(new Ponto('b', 1, 4));
		listaPontos.add(new Ponto('c', 7, 6));
		listaPontos.add(new Ponto('d', 4, 5));
		listaPontos.add(new Ponto('e', 2, 12));
		listaPontos.add(new Ponto('f', 6, 10));
		listaPontos.add(new Ponto('g', 9, 12));
		listaPontos.add(new Ponto('h', 11, 8));
		listaPontos.add(new Ponto('i', 8, 3));
		listaPontos.add(new Ponto('j', 11, 2));
		
		for(int i=0; i < listaPontos.size(); i++) {
			grafo.addVertice(listaPontos.get(i));
		}
		
		grafo.addAresta(calculaPeso('a', 'b'), 
	    achaPontoChar('a'), achaPontoChar('b'));
		grafo.addAresta(calculaPeso('a', 'c'),
		achaPontoChar('a'), achaPontoChar('c'));
		grafo.addAresta(calculaPeso('c', 'd'),
		achaPontoChar('c'), achaPontoChar('d'));
		grafo.addAresta(calculaPeso('d', 'e'),
		achaPontoChar('d'), achaPontoChar('e'));
		grafo.addAresta(calculaPeso('e', 'b'),
		achaPontoChar('e'), achaPontoChar('b'));
		grafo.addAresta(calculaPeso('a', 'h'),
		achaPontoChar('a'), achaPontoChar('h'));
		grafo.addAresta(calculaPeso('h', 'j'),
		achaPontoChar('h'), achaPontoChar('j'));
		grafo.addAresta(calculaPeso('j', 'c'),
		achaPontoChar('j'), achaPontoChar('c'));
		grafo.addAresta(calculaPeso('i', 'c'),
		achaPontoChar('i'), achaPontoChar('c'));
		grafo.addAresta(calculaPeso('c', 'g'),
		achaPontoChar('c'), achaPontoChar('g'));
		grafo.addAresta(calculaPeso('d', 'f'),
		achaPontoChar('d'), achaPontoChar('f'));
		grafo.addAresta(calculaPeso('e', 'f'),
		achaPontoChar('e'), achaPontoChar('f'));
		grafo.addAresta(calculaPeso('f', 'g'),
		achaPontoChar('f'), achaPontoChar('g'));
		grafo.addAresta(calculaPeso('e', 'g'),
		achaPontoChar('e'), achaPontoChar('g'));
		
		
		System.out.println("Otimizando grafo...");
		otimizaEmT1(grafo);
		System.out.println("OK");
		//printaLista();
		
		while(true) {
			System.out.println("Entre com a primeira cidade:");
			v1 = (char)System.in.read();
			v1 = Character.toLowerCase(v1);
			System.in.read();
			System.out.println("Entre com a segunda cidade:");
			v2 = (char)System.in.read();
			v2 = Character.toLowerCase(v2);
			System.in.read();
			
			if (procuraPorElementoPonto(v1) == -1 || procuraPorElementoPonto(v2) == -1) {
				System.out.println("Entrada invalida. Tente novamente\n");
				continue;
			}
			
			mostraMelhorTrajeto(v1, v2, true);
			System.out.println("\nDe novo? (s/n)");
			v1 = (char)System.in.read();
			System.in.read();
			if (v1 != 's') break;
		}
		
	}

}
