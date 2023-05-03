package problema_da_mochila;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MochilaDinamica {
	static List<Item> itemList  = new ArrayList<Item>();		// lista de candidatas
	static List<Item> solucaoList  = new ArrayList<Item>();		// lista solução
	
	// algoritmo de prog. dinâmica que utiliza uma abordagem topdown para retornar valor máximo de Lucro
	
	public static int topDown(int n, int C, int i, int matriz[][]) {
		int vin, vex; // valores correspondentes a incluir ou não incluir um item
		if(i == n || C <= 0){  // condição base 
			return 0;
		}
		if (matriz[i][C-1] > 0) {
			return matriz[i][C-1];
		}
		if (itemList.get(i).getWeight() <= C){
			vin = topDown(n, C - itemList.get(i).getWeight(), i+1, matriz) + itemList.get(i).getValue();
			vex = topDown(n, C, i+1, matriz);	
			matriz[i][C-1] = Math.max(vin, vex); // aloca os valores em uma matriz
		}
		else {
			vex = topDown(n, C, i+1, matriz);	
			matriz[i][C-1] = vex;
		}
		return matriz[i][C-1];
	}
	
	public static void add(char name, int weight, int value) {
		itemList.add(new Item(name, weight, value));
	}
	
	// método que constrói a lista solução
	private static void mochilaSolucao(int C, int k, int matriz[][]) {
		int var1, var2;
		if (k < itemList.size()) {
			var1 = topDown(itemList.size(), C, k, matriz);
			var2 = topDown(itemList.size(), C, k+1, matriz);
			if (var1 != var2) {
				solucaoList.add(itemList.get(k));
				mochilaSolucao(C - itemList.get(k).getWeight(),k+1, matriz);
				}
			else {
				mochilaSolucao(C, k+1, matriz);
			}
		}
	}
	 
	public static void main(String[] args) {
		
		int n;
		int C;
		Scanner s = new Scanner(System.in);
		System.out.println("Informe uma quantidade N de alimentos disponíveis para plantio: "
				+ "(Considere um tamanho pequeno para N para melhor funcionamento/desempenho do programa)");
		n = s.nextInt(); // número de itens candidatos à compor a mochila
		System.out.println("Informe a quant. de hectares disponíveis para plantio: "
				+ "(Capacidade)");
		C = s.nextInt(); // capacidade da mochila
		s.close();
		
		String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";	// para gerar uma representação dos nomes dos alimentos
		
		for (int i=0; i < n; i++) {
			Random r = new Random();
			char caracter = letras.charAt(r.nextInt(letras.length()));
			add(caracter, r.nextInt(C), r.nextInt(10000));	// gera alguns valores pseudoaleatórios para
		}													// ("nome" do alimento, peso associado, valor/lucro associado)
		
		/*
		// exemplo básico para efeito de teste (descrito no relatório na seção 3)
		add('A', 5, 8);
		add('B', 7, 11);
		add('C', 4, 6);
		add('D', 3, 4);
		int C = 15; 
		*/
		
		int matriz[][] = new int [itemList.size()][C];
		//incializando a matriz com o valor 0
		for(int i=0; i < itemList.size()  ;i++ ) {
			for(int j=0; j < C;j++ ) {
				matriz[i][j] = 0;
			}
		}
		
		int MaxVal = topDown(itemList.size(), C, 0, matriz); // chamadada ao algoritmo de abordagem topdown
		mochilaSolucao(C, 0, matriz); // chamada ao método que constrói a lista solução
		
		System.out.println("\nLucro Total obtido com o plantio dos alimentos escolhidos = " + MaxVal);
		System.out.println("\nLista de Alimentos escolhidos para plantio: " );
		
		for(Item i: solucaoList) {
			System.out.println("Alimento " + i.getName() + 
					" | Lucro associado: " + i.getValue() +
					" | Quant. de hectares associado: " + i.getWeight());	
		} 
	}
}