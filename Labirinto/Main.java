import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        // Solicita que o usuário digite o nome do arquivo
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor, digite o nome do arquivo: ");
        String arquivo = scanner.nextLine();

        int numLinhas = 0, numColunas = 0;
        char[][] labirinto = null;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            linha = br.readLine();
            try {
            numLinhas = Integer.parseInt(linha);

            linha = br.readLine();
            numColunas = Integer.parseInt(linha);
            } catch (Exception e) {
                System.out.println("Erro ao ler número de linhas e colunas");
            }

            labirinto = new char[numLinhas][numColunas];

            int indexLinha = 0;
            while ((linha = br.readLine()) != null) {
                labirinto[indexLinha] = linha.toCharArray();
                indexLinha++;
            }
            Labirinto meuLabirinto = null;
            try {
                meuLabirinto = new Labirinto(labirinto, numLinhas, numColunas);
                meuLabirinto.encontrarSaida();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir o arquivo. Verifique o caminho especificado");
        }
        
}
}
