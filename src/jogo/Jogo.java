package jogo;

import java.util.ArrayList;
import java.util.function.Function;

import entidades.Arqueiro;
import entidades.Berserk;
import entidades.Curandeiro;
import entidades.Entidade;
import entidades.Feiticeiro;
import entidades.Pistoleiro;

// Esse import estatico faz com que seja possivel pegar as
// funcoes sem usar o Menu.(...)
import static menu.Menu.*;

public class Jogo {
  private Entidade jogadorUm;
  private Entidade jogadorDois;
  private Entidade jogadorDaRodada;
  private Entidade jogadorAlvo;
  private boolean doisJogadores;
  static private ArrayList<Class<? extends Entidade>> classes = new ArrayList<>();

  public Jogo() {
    adicionarClasses();
    if (iniciarJogo())
      while (iniciarNovaRodada())
        ;
    else
      System.out.println("Erro no sistema. Reinicie e tente novamente.");
  }

  // TODO: separar o pacote entidades em jogo e usuario pra automatizar isso aqui
  private void adicionarClasses() {
    Jogo.classes.add(Berserk.class);
    Jogo.classes.add(Feiticeiro.class);
    Jogo.classes.add(Curandeiro.class);
    Jogo.classes.add(Arqueiro.class);
    Jogo.classes.add(Pistoleiro.class);
  }

  private boolean iniciarJogo() {
    limparTerminal();
    imprimirTitulo("SISTEMA DE BATALHA!");
    escolherUmOuDoisJogadores();
    limparTerminal();

    imprimirTitulo("ESCOLHA A CLASSE DO JOGADOR 1");
    this.jogadorUm = escolherClasse();
    limparTerminal();

    this.jogadorDaRodada = this.jogadorUm;

    if (this.doisJogadores) {
      imprimirTitulo("ESCOLHA A CLASSE DO JOGADOR 2");
      this.jogadorDois = escolherClasse();
    } else {
      this.jogadorDois = instanciarClasseAleatoria();
    }

    limparTerminal();

    this.jogadorAlvo = this.jogadorDois;
    if (this.jogadorUm == null || this.jogadorDois == null) {
      return false;
    }
    return true;
  }

  private void escolherUmOuDoisJogadores() {
    System.out.println("Deseja jogar contra outro jogador ou contra o computador?");
    while (true) {
      switch (lerOpcao("Contra outro jogador", "Contra o computador")) {
        case 1:
          this.doisJogadores = true;
          return;
        case 2:
          this.doisJogadores = false;
          return;
        case -1:
          System.out.println("Ocorreu um erro na leitura. Tente novamente.");
          break;
        default:
          System.out.println("Opção inválida. Tente novamente.");
      }
    }
  }

  private Entidade escolherClasse() {
    while (true) {
      int opt = lerOpcao(retornarNomesClasses());
      if (opt == -1) {
        System.out.println("Ocorreu um erro na leitura. Tente novamente.");
      } else if (opt < 1 || opt > classes.size()) {
        System.out.println("Opção inválida. Tente novamente.");
      } else {
        return instanciarClasse(opt - 1);
      }
    }
  }

  private Entidade instanciarClasseAleatoria() {
    int i = (int) Math.floor(Math.random() * Jogo.classes.size());
    return instanciarClasse(i);
  }

  private Entidade instanciarClasse(int indiceDaClasse) {
    try {
      return Jogo.classes.get(indiceDaClasse).getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      return null;
    }
  }

  private String fraseDeVitoria(Entidade vencedor) {
    // uma interface funcional de java para ter uma funcao auxiliar local
    Function<String[], String> fraseAleatoria = lista_str -> lista_str[(int) Math
        .floor(lista_str.length * Math.random())];

    String[] frasesVitoriaJogadorUm = {
        "O oponente caiu tão rápido que nem valeu aquecimento.",
        "Parecia um duelo, mas foi só um treino com saco de pancadas.",
        "Seu adversário lutou com coragem. Faltou só o resto.",
        "Se fosse uma competição de tropeço, ele ainda perderia.",
        "A vitória veio tão fácil que deu tempo de arrumar a armadura no meio da luta.",
        "Derrotou o inimigo com tanta classe que quase pareceu nobreza.",
        "O chão conheceu bem o rosto do seu adversário hoje.",
        "Ninguém esperava equilíbrio, mas isso aí foi massacre.",
        "A única coisa afiada no inimigo era o grito de socorro.",
        "Seu golpe final foi tão preciso que parecia rotina."
    };

    String[] frasesVitoriaJogadorDois = {
        "O oponente caiu sem nem entender de onde veio o golpe.",
        "Foi uma vitória limpa, rápida e cheia de vergonha pro lado perdedor.",
        "O inimigo tentou resistir... tentou, né.",
        "Parecia que o adversário tava ali só pra garantir o espetáculo.",
        "Derrotou com tanta firmeza que o eco do impacto vai assombrar o campo por dias.",
        "Ninguém saiu ileso, mas só um saiu de pé.",
        "A luta terminou e o silêncio do inimigo disse tudo.",
        "O adversário foi tão inútil quanto escudo de madeira podre.",
        "Foi mais um exemplo de como *não* se luta.",
        "O inimigo agora é parte da paisagem do campo de batalha."
    };

    String[] frasesDerrotaParaNPC = {
        "Parabéns, conseguiu perder pra algo que nem pensa direito.",
        "A criatura nem teve trabalho. Você caiu sozinho.",
        "Até a sombra do monstro te intimidou mais do que deveria.",
        "Derrota honesta, mas ainda assim ridícula.",
        "O inimigo tava mais perdido que você, e mesmo assim venceu.",
        "Você caiu pra uma ameaça que mal se move. Incrível.",
        "Dizem que essa criatura é fraca... agora dizem menos.",
        "A luta foi curta. A vergonha, eterna.",
        "O NPC vai usar sua cabeça como troféu de treino.",
        "Perder pra esse tipo de inimigo devia dar multa."
    };

    if (vencedor == this.jogadorUm) {
      return fraseAleatoria.apply(frasesVitoriaJogadorUm);
    } else if (vencedor == this.jogadorDois) {
      return fraseAleatoria.apply(frasesVitoriaJogadorDois);
    } else {
      return fraseAleatoria.apply(frasesDerrotaParaNPC);
    }
  }

  private boolean iniciarNovaRodada() {
    imprimirTitulo(
        "VEZ DO " + ((this.jogadorDaRodada == this.jogadorUm) ? "JOGADOR 1"
            : (this.doisJogadores ? "JOGADOR 2" : "NPC")));
    if (this.doisJogadores || (this.jogadorUm == this.jogadorDaRodada))
      escolherAcaoJogador();
    else
      executarAcaoNPC();
    imprimirAtributosJogadores();
    // aqui, so confiro se o jogador alvo morreu mesmo. no meu sistema, nao eh
    // possivel o jogador sofrer dano na rodada em que ele realiza a acao, entao
    // essa simplificacao eh possivel aqui.
    if (!this.jogadorAlvo.checarVivo()) {
      System.out.println(fraseDeVitoria(this.jogadorDaRodada));
      return false;
    }
    trocarJogadorDaRodada();
    aguardarEnter();
    limparTerminal();
    return true;
  }

  private void escolherAcaoJogador() {
    while (true) {
      int op = lerOpcao(this.jogadorDaRodada.getStringsAcoes());
      int tamanhoAcoes = this.jogadorDaRodada.getTamanhoAcoes();
      if (1 <= op && op <= tamanhoAcoes) {
        int indiceAcao = op - 1;
        this.jogadorDaRodada.executarAcao("jogador da rodada", indiceAcao, jogadorAlvo);
        break;
      } else if (op == -1) {
        System.out.println("Ocorreu um erro na leitura. Tente novamente.");
      } else {
        System.out.println("Opção inválida. Tente novamente.");
      }
    }
  }

  private void executarAcaoNPC() {
    this.jogadorDois.executarAcaoAleatoria("NPC", this.jogadorUm);
  }

  private void imprimirAtributosEntidade(Entidade e) {
    System.out.println(e.getStringAtributos());
  }

  private void imprimirAtributosJogadores() {
    imprimirTitulo("dados do jogador 1");
    imprimirAtributosEntidade(this.jogadorUm);

    imprimirTitulo("Dados do " + (this.doisJogadores ? "jogador 2" : "npc"));
    imprimirAtributosEntidade(this.jogadorDois);
  }

  private void trocarJogadorDaRodada() {
    if (this.jogadorDaRodada == this.jogadorUm) {
      this.jogadorDaRodada = this.jogadorDois;
      this.jogadorAlvo = this.jogadorUm;
    } else {
      this.jogadorDaRodada = this.jogadorUm;
      this.jogadorAlvo = this.jogadorDois;
    }
  }

  private String[] retornarNomesClasses() {
    ArrayList<String> strings = new ArrayList<>();
    for (Class<?> c : Jogo.classes) {
      strings.add(c.getSimpleName());
      // para cada classe em Jogo.classes, retorne o simpleName (o nome simples, sem
      // pacotes ou classes maes)
    }
    // novamente esbarramos nisso, a conversao de lista para array (mais
    // especificamente do tipo string!)
    return strings.toArray(new String[0]);
  }
}
