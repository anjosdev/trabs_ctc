import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import java.util.*;
import java.io.IOException;


public class Labirinto implements Cloneable {
    private char[][] labirinto;
    private int numLinhas;
    private int numColunas;
    private Coordenada atual;
    private boolean encontrouSaida = false;

    public Labirinto (char[][] labirinto, int numLinhas, int numColunas) throws Exception {
        this.labirinto = labirinto;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        }

    public void encontrarSaida() throws Exception {
        if (labirinto.length != numLinhas) {
            throw new Exception("Quantidade de linhas informada está errada");
        }

        for (int linha = 0; linha < labirinto.length; linha++) {
            if (labirinto[linha].length != numColunas) {
                throw new Exception("Colunas ou quantidade de colunas inválidas");
            }
        }
        


        char[] charCaracteresValidos = new char[]{' ', 'E', 'S', '#'};
        List<char[]> caracteresValidos = Arrays.asList(charCaracteresValidos);
        List<Character> listCaractersValidos = new ArrayList<Character>();
        for (char c : charCaracteresValidos) {
            listCaractersValidos.add(c);
        }

        int numDeSaidas = 0;
        for (int i = 0; i < this.labirinto.length; i++) {
            for (int j = 0; j < this.numColunas; j++) {
                if (labirinto[i][j] == 'S') {
                    numDeSaidas++;
                }

                if (!listCaractersValidos.contains(labirinto[i][j])) {
                    throw new Exception("Caracter inválido no labirinto. Terminando o programa.");
                }

            }
        }

        if (numDeSaidas > 1) {
            throw new Exception("Mais de uma saída. Terminando o programa.");
        }



        Pilha<Coordenada> caminho = null;
        try {
            caminho = new Pilha<Coordenada>(this.numLinhas*this.numColunas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pilha<Fila<Coordenada>> possibilidades = null;
        try {
            possibilidades = new Pilha<Fila<Coordenada>>(this.numLinhas*this.numColunas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Coordenada atual = null;
        try {
            atual = this.encontrarEntrada();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        Fila<Coordenada> fila = null;
        try {
            fila = new Fila<Coordenada>(3);
        } catch (Exception e) {
            e.printStackTrace();
        }


        boolean voltandoDeRegressivo = false;
        do {
            try {
                if (!voltandoDeRegressivo) {
                    fila = this.adjacentesValidos(atual);
                }
                atual = fila.recupereUmItem();
                if (atual.getConteudo() == 'S') {
                    this.printlabirinto(labirinto);
                    encontrouSaida = true;
                    System.out.println("Solução encontrada!");
                    break;
                }
                fila.removaUmItem();
                labirinto[atual.getX()][atual.getY()] = '*';
                caminho.guardeUmItem(atual);
                possibilidades.guardeUmItem(fila);
                voltandoDeRegressivo = false;
            } catch (Exception e) {
                try {
                    atual = caminho.recupereUmItem();
                    caminho.removaUmItem();
                    labirinto[atual.getX()][atual.getY()] = ' ';
                    
                    fila = possibilidades.recupereUmItem();
                    possibilidades.removaUmItem();
                    voltandoDeRegressivo = true;
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        } while (!possibilidades.isVazia());

        if (possibilidades.isVazia()) {
            System.out.println("Não existe caminho que leva da entrada até a saída");
        }

        Pilha<Coordenada> inverso = null;
        try {
            inverso = new Pilha(numLinhas*numColunas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        while (!caminho.isVazia()) {
            try {
                inverso.guardeUmItem(caminho.recupereUmItem());
                caminho.removaUmItem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

	if (encontrouSaida) {
	        System.out.println("O caminho da entrada até a saída será mostrado");
        	while (!inverso.isVazia()) {
	            try {
	                System.out.print(inverso.recupereUmItem());
	                System.out.print(" ");
	                inverso.removaUmItem();
	            } catch (Exception e) {
	                e.printStackTrace();
        	    }
	}
        try{
            FileWriter output = new FileWriter("solucao.txt");
            for (char[] linha : labirinto) {
                for (char c : linha) {
                    output.write(c);
              }
              output.write('\n');
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    

    public Fila<Coordenada> adjacentesValidos(Coordenada c) {
        Fila adjacentes = null;
        try {
            adjacentes = new Fila<Coordenada>(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int x = c.getX();
        int y = c.getY();
        int numColunas = this.labirinto[0].length;
        int numLinhas = this.labirinto.length;
        Coordenada esquerda, direita, acima, abaixo;
        esquerda = direita = acima = abaixo = null;
        
        // Coordenada a esquerda
        if (y - 1 >= 0) {
            try {
            esquerda = new Coordenada(this.labirinto[x][y - 1], x, y - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Coordenada a direita
        if (y + 1 < numColunas) {
            try {
            direita = new Coordenada(this.labirinto[x][y + 1], x, y + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Coordenada acima
        if (x - 1 >= 0) {
            try {
            acima = new Coordenada(this.labirinto[x - 1][y], x - 1, y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Coordenada abaixo
        if (x + 1 < numLinhas) {
            try {
            abaixo = new Coordenada(this.labirinto[x+1][y], x+1, y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (esquerda != null) {
            try {
                if (esquerda.getConteudo() == ' ' || esquerda.getConteudo() == 'S') {
                    adjacentes.guardeUmItem(esquerda);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (direita != null) {
            try {
                if (direita.getConteudo() == ' ' || direita.getConteudo() == 'S') {
                    adjacentes.guardeUmItem(direita);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (acima != null) {
            try {
                if (acima.getConteudo() == ' ' || acima.getConteudo() == 'S') {
                    adjacentes.guardeUmItem(acima);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (abaixo != null) {
            try {
                if (abaixo.getConteudo() == ' ' || abaixo.getConteudo() == 'S') {
                    adjacentes.guardeUmItem(abaixo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        return adjacentes;
    }

    public void printlabirinto(char[][] labirinto) {
        for (int i = 0; i < this.labirinto.length; i++) {
            for (int j = 0; j < this.labirinto[i].length; j++) {
                System.out.print(this.labirinto[i][j]);
            }
            System.out.println();
        }
    }

    public Coordenada encontrarEntrada() throws Exception {
        Coordenada entrada = null;
        Coordenada entradaEncontrada = null;
        int numDeEntradas = 0;
        // Verifica se a entrada está na primeira linha
        for (int i = 0; i < this.numColunas; i++) {
            if (this.labirinto[0][i] == 'E') {
                entradaEncontrada = new Coordenada('E', 0, i);
                if (entrada != null) {
                    if (entradaEncontrada.getX() != entrada.getX() && entradaEncontrada.getY() != entrada.getY()) {
                        numDeEntradas++;
                    }
                } else {
                    entrada = entradaEncontrada;
                    numDeEntradas++;
                }
            } else if (this.labirinto[0][i] == ' ') {
                throw new Exception("Borda sem parede");
            }
        }

        // Verifica se a entrada está na primeira coluna
        for (int i = 0; i < this.numLinhas; i++) {
            if (labirinto[i][0] == 'E') {
                entradaEncontrada = new Coordenada('E', i, 0);
                if (entrada != null) {
                    if (entradaEncontrada.getX() != entrada.getX() && entradaEncontrada.getY() != entrada.getY()) {
                        numDeEntradas++;
                    }
                } else {
                    entrada = entradaEncontrada;
                    numDeEntradas++;
                }
            } else if (this.labirinto[i][0] == ' ') {
                throw new Exception("Borda sem parede");
            }
        }

        // Verifica se a entrada está na última linha
        for (int i = 0; i < numColunas; i++) {
            if (this.labirinto[numLinhas - 1][i] == 'E') {
                entradaEncontrada = new Coordenada('E', numLinhas - 1, i);
                if (entrada != null) {
                    if (entradaEncontrada.getX() != entrada.getX() && entradaEncontrada.getY() != entrada.getY()) {
                        numDeEntradas++;
                    }
                } else {
                    entrada = entradaEncontrada;
                    numDeEntradas++;
                }
            } else if (this.labirinto[numLinhas - 1][i] == ' ') {
                throw new Exception("Borda sem parede");
            }
        }

         // Verifica se a entrada está na última coluna
        for (int i = 0; i < numLinhas; i++) {
            if (this.labirinto[i][numColunas - 1] == 'E') {
                entradaEncontrada = new Coordenada('E', numLinhas - 1, i);
                if (entrada != null) {
                    if (entradaEncontrada.getX() != entrada.getX() && entradaEncontrada.getY() != entrada.getY()) {
                        numDeEntradas++;
                    }
                } else {
                    entrada = entradaEncontrada;
                    numDeEntradas++;
                }
            } else if (this.labirinto[i][numColunas - 1] == ' ') {
                throw new Exception("Borda sem parede");
            }
        }

        if (entrada == null) {
            throw new Exception("Labirinto sem entrada");
        } else if (numDeEntradas > 1) {
            throw new Exception("Mais de uma entrada. Encerrando o programa.");
        }

        return entrada;
    }
}

