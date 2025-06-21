package menu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

abstract public class Menu {
  // essa keyword final impede que essa variavel seja modificada
  final public static Scanner sc = new Scanner(System.in);

  // um construtor privado obriga que nao seja possivel
  // instanciar com new Menu(). se nao tiver construtor,
  // ele por padrao cria um construtor publico sem corpo e
  // sem parametros, entao a melhor forma eh criar um privado.
  private Menu() {
  }

  public static void limparTerminal() {
    System.out.print("\033[H\033[2J");
  }

  private static void imprimirSinalInput() {
    System.out.print(">>> ");
  }

  public static void aguardarEnter() {
    System.out.println("Aperte [Enter] para prosseguir.");
    sc.nextLine(); // aguarda um enter para prosseguir
  }

  public static void imprimirTitulo(String texto) {
    String mensagem = String.join("\n",
        "-".repeat(texto.length() + 6),
        "   " + texto.toUpperCase(),
        "-".repeat(texto.length() + 6)).concat("\n");
    System.out.print(mensagem);
  }

  // function overloading: declarar a funcao com mesmo nome mas com assinatura
  // diferente
  // e o compilador consegue diferenciar na chamada!
  private static void imprimirMenu(List<String> strings) {
    for (int i = 1; i <= strings.size(); i++) {
      System.out.println(i + ". " + strings.get(i - 1));
    }
    imprimirSinalInput();
  }

  private static void imprimirMenu(String... strings) {
    imprimirMenu(Arrays.asList(strings));
  }

  // overloading novamente!!
  public static int lerOpcao(List<String> opcoes) {
    // essa linha eh muito interessante. o metodo toArray vai pegar a lista e
    // transformar em um array do mesmo tipo do array passado como parametro,
    // nesse caso o array passado foi new String[0], o que cria um array de tamanho
    // zero de elementos do tipo String. como nao tem tamanho suficiente pra
    // caber os elementos de opcoes, ele ajusta o tamanho para caber o necessario.
    // assim, conseguimos transformar o arrayList em um array, que eh aceito como
    // parametro de varargs (aquela sintaxe ...).
    return lerOpcao(opcoes.toArray(new String[0]));
  }

  public static int lerOpcao(String... opcoes) {
    imprimirMenu(opcoes);
    return lerOpcao(); // chama a versao sem parametro
  }

  private static int lerOpcao() {
    try {
      int ret = sc.nextInt();
      sc.nextLine();
      return ret;
    } catch (Exception e) {
      sc.nextLine(); // limpa buffer
      return -1;
    }
  }
}
