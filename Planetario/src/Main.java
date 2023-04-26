import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    /**
     * @param args
     */
    public static String planeta() {
        return """
                    .::.
                  .:   .:
        ,MMM8&&&.:    .: 
       MMMMM88&&&&  .: 
      MMMMM88&&&&&&: 
      MMMMM88&&&&&&
    .:MMMMM88&&&&&&
  .:   MMMMM88&&&&
.:    .: MMM8&&& 
:   .: 
 ::   
            """;
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PlanetarioDAO dao = new PlanetarioDAO();
        Logradouro logradouro = null;
        while (true) {
            logradouro = null;
            System.out.print(planeta());
            System.out.println("==========");
            System.out.println("Planetários");
            System.out.println("==========");

            System.out.println("1. Criar novo planetário");
            System.out.println("2. Selecionar planetário por ID");
            System.out.println("3. Atualizar um planetário");
            System.out.println("4. Deletar um planetário");
            System.out.println("5. Sair");

            System.out.print("Digite sua escolha: ");
            int escolha = 0;
            try {
                escolha = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Escolha inválida");
            }
            scanner.nextLine();
            int idPlanetario = 0;

            boolean idInvalido = false;
            switch (escolha) {
                case 1:
                    System.out.print("Digite o nome do planetário: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o CEP do planetário: ");
                    String cep = scanner.nextLine();
                    System.out.print("Digite o complemento do planetário: ");
                    String complemento = scanner.nextLine();
                    System.out.print("Digite a url da foto do planetário: ");
                    String urlFoto = scanner.nextLine();
                    System.out.print("Digite a descrição do planetário: ");
                    String descricao = scanner.nextLine();

                    try
                    {
                        logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", cep);
                        if (logradouro == null) {
                            throw new Exception("**********VERIFIQUE O CEP CADASTRADO**********");
                        }
                        System.out.println(logradouro);
                        
                    }
                    catch (Exception erro)
                    {
                        erro.printStackTrace();
                    }
                    
                    dao.criar(new Planetario(0, nome, cep, complemento, urlFoto, descricao));
                    System.out.println("Planetário criado com sucesso");
                    
                    break;

                case 2:
                    idInvalido = false;
                    do {
                    System.out.println("Digite o id do planetário: ");
                    try {
                    idPlanetario = scanner.nextInt();
                    idInvalido = false;
                    } catch (Exception e) {
                        idInvalido = true;
                        System.out.println("ID inválido");
                        scanner.nextLine();
                    }
                    } while (idInvalido);

                    Planetario planetario = dao.recuperar(idPlanetario);
                    if (planetario != null) {
                        System.out.println(planetario.getIdPlanetario() + "\n=====" + planetario.getNome() + "\n=====" + planetario.getCep() + "\n=====" + planetario.getComplemento() + "\n=====" + planetario.getUrlFoto() + "\n=====" + planetario.getDescricao());
                        logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", planetario.getCep());
                        System.out.println(logradouro);
                    } else {
                        System.out.println("Planetário não encontrado");
                    }
                    break;

                case 3:
                    System.out.print("Digite o id do planetário: ");
                    try {
                    idPlanetario = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("ID inválido");
                        scanner.nextLine();
                        break;
                    }

                        planetario = dao.recuperar(idPlanetario);
                        if (planetario != null) {
                        System.out.println(planetario.getIdPlanetario() + "\n=====" + planetario.getNome() + "\n=====" + planetario.getCep() + "\n=====" + planetario.getComplemento() + "\n=====" + planetario.getUrlFoto() + "\n=====" + planetario.getDescricao());
                        logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", planetario.getCep());
                        System.out.println(logradouro);
                        System.out.print("Digite novo nome para o planetário (ou pressione Enter para deixar o mesmo): ");
                        scanner.nextLine();
                        nome = scanner.nextLine();
                        if (!nome.equals("")) {
                            planetario.setNome(nome);
                        }
                        System.out.print("Digite novo CEP para o planetário (ou pressione Enter para deixar o mesmo): ");
                        // scanner.nextLine();
                        cep = scanner.nextLine();
                        if (!cep.equals("")) {
                            planetario.setCep(cep);
                        }
                        System.out.print("Digite novo complemento (ou não digite nada para deixar o mesmo): ");
                        complemento = scanner.nextLine();
                        if (!complemento.equals("")) {
                            planetario.setComplemento(complemento);
                        }
                        System.out.print("Digite nova URL de foto (ou não digite nada para deixar o mesmo): ");
                        urlFoto= scanner.nextLine();
                        if (!complemento.equals("")) {
                            planetario.setUrlFoto(urlFoto);
                        }
                        System.out.print("Digite nova descrição (ou não digite nada para deixar o mesmo): ");
                        descricao = scanner.nextLine();
                        if (!descricao.equals("")) {
                            System.out.println("descricao:");
                            planetario.setDescricao(descricao);
                        }
                        dao.update(planetario);
                        System.out.println("Planetário atualizado com sucesso");
                        } else {
                        System.out.println("Planetário não encontrado");
                        }
                    break;

                case 4:
                    String confirmar = "";
                    System.out.print("Digite ID do planetário: ");
                    try {
                    idPlanetario = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("ID inválido");
                        scanner.nextLine();
                        break;
                    }
                    planetario = dao.recuperar(idPlanetario);
                    if (planetario != null) {
                        System.out.println(planetario.getIdPlanetario() + "\n=====" + planetario.getNome() + "\n=====" + planetario.getCep() + "\n=====" + planetario.getComplemento() + "\n=====" + planetario.getUrlFoto() + "\n=====" + planetario.getDescricao());
                        logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", planetario.getCep());
                        if (logradouro != null) {
                            System.out.println(logradouro);
                        }
                        scanner.nextLine();
                    } else {
                        System.out.println("Planetário não encontrado");
                        break;
                    }
                    
                    System.out.println("Pressione ENTER para confirmar a exclusão. Envie \"N\" para cancelar a exclusão");
                    confirmar = scanner.nextLine();
                        if (confirmar.equals("")) {
                            dao.delete(idPlanetario);
                            System.out.println("Planetário deletado com sucesso");
                        } else {
                            System.out.println("Exclusão cancelada");
                        }
                    break;
                case 5:
                    System.out.println("Volte sempre!");
                    System.exit(0);
                
                default:
                    System.out.println("Escolha inválida, tente novamente.");
            }
        }
    }
}
