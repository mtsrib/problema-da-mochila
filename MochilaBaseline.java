package problema_da_mochila;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MochilaBaseline {
	static List<Item> itemList  = new ArrayList<Item>();		// lista de candidatas
	static List<Item> solucaoList  = new ArrayList<Item>();		// lista solu��o
	
	// algoritmo de for�a bruta que retorna o valor m�ximo de lucro
	
	public static int bruteForce(int n, int C, int i) {
		
		int vin, vex; // valores correspondentes a incluir ou n�o incluir um item
		
		if(i == n || C <= 0){  // condi��o base 
			return 0;
		}
		if (itemList.get(i).getWeight() <= C){
			vin = bruteForce(n, C - itemList.get(i).getWeight(), i + 1) + itemList.get(i).getValue();
			vex = bruteForce(n, C, i + 1);	
			return Math.max(vin, vex);
		}
		else {
			vex = bruteForce(n, C, i + 1);
			return vex;
		}
	}
	
	public static void add(char name, int weight, int value) {
		itemList.add(new Item(name, weight, value));
	}
	
	private static void mochilaSolucao(int C, int k) {
		int var1, var2;
		if (k < itemList.size()) {
			var1 = bruteForce(itemList.size(), C, k);
			var2 = bruteForce(itemList.size(), C, k+1);
			if (var1 != var2) {
				solucaoList.add(itemList.get(k));
				mochilaSolucao(C - itemList.get(k).getWeight() , k+1);
				}
			else {
				mochilaSolucao(C, k+1);
			}
		}
	}
	 
	public static void main(String[] args) {
		
		int n;
		int C;
		Scanner s = new Scanner(System.in);
		System.out.println("Informe uma quantidade N de alimentos dispon�veis para plantio: "
				+ "(Considere um tamanho pequeno para N para melhor funcionamento/desempenho do programa)");
		n = s.nextInt(); // n�mero de itens candidatos � compor a mochila
		System.out.println("Informe a quant. de hectares dispon�veis para plantio: "
				+ "(Capacidade)");
		C = s.nextInt(); // capacidade da mochila
		s.close();
		
		String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";  // para gerar uma representa��o dos nomes dos alimentos
		
		for (int i=0; i < n; i++) {
			Random r = new Random();
			char caracter = letras.charAt(r.nextInt(letras.length()));
			add(caracter, r.nextInt(C), r.nextInt(10000)); // ("nome" do alimento, peso associado, valor/lucro associado)
		}
		
		/* 
		// exemplo b�sico para efeito de teste (descrito no relat�rio na se��o 3)
		add('A', 5, 8);
		add('B', 7, 11);
		add('C', 4, 6);
		add('D', 3, 4);
		int C = 15; 
		*/
		
		int MaxVal = bruteForce(itemList.size(), C, 0); // chamada ao algoritmo de for�a bruta
		mochilaSolucao(C, 0); // chamada ao m�todo que constr�i a lista solu��o
		
		System.out.println("\nLucro Total obtido com o plantio dos alimentos escolhidos = " + MaxVal);
		System.out.println("\nLista de Alimentos escolhidos para plantio: " );
		
		for(Item i: solucaoList) {
			System.out.println("Alimento " + i.getName() + 
					" | Lucro associado: " + i.getValue() +
					" | Quant. de hectares associado: " + i.getWeight());	
		} 
	}
}
