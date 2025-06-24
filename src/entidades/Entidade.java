package entidades;

import java.util.ArrayList;

import acao.Acao;
import dano.Dano;
import dano.TipoDano;

abstract public class Entidade {
  private Dano dano;
  private int vida;
  private int vidaInicial;
  private double esquiva;
  private double precisao;
  private ArrayList<TipoDano> resistencias;
  private ArrayList<Acao> acoes;

  public int getVida() {
    return vida;
  }

  public int setVida(Dano dano) {
    int danoRecebido = this.resistencias.contains(dano.getTipo()) ? dano.getQuantidade() / 2 : dano.getQuantidade();
    this.vida -= danoRecebido;
    if (this.vida <= 0)
      this.vida = 0;
    return danoRecebido;
  }

  public void setVida(int aumento) {
    this.vida += aumento;
  }

  public Dano getDano() {
    return dano;
  }

  public void setDano(int quantidade) {
    this.dano.setQuantidade(quantidade);
  }

  public Entidade(Dano dano, int vida, double esquiva, double precisao, ArrayList<TipoDano> resistencias) {
    this.dano = dano;
    this.vidaInicial = vida;
    this.vida = vida;
    this.esquiva = Math.max(0.0, Math.min(0.5, esquiva));
    this.precisao = Math.max(0.1, Math.min(0.9, precisao));
    this.resistencias = resistencias;
    this.acoes = new ArrayList<Acao>();
    this.adicionarAcao(new Acao(e -> this.causarDano(e), "Atacar"));
    adicionarAcoes();
  }

  protected double getEsquiva() {
    return esquiva;
  }

  protected void setEsquiva(double diminuicao) {
    this.esquiva = ((this.esquiva - diminuicao) < 0 ? 0 : (this.esquiva - diminuicao));
  }

  protected double getPrecisao() {
    return this.precisao;
  }

  protected void setPrecisao(double diminuicao) {
    this.precisao = ((this.precisao - diminuicao) < 0 ? 0 : (this.precisao - diminuicao));
  }

  public boolean checarVivo() {
    return this.vida != 0;
  }

  protected boolean testarAtaque(Entidade alvo) {
    double chanceDeAcerto = Math.max(0.05, Math.min(0.95, this.precisao * (1 - alvo.getEsquiva() / 4)));
    // se a chance de acerto for alta, a chance disso acontecer eh maior
    if (chanceDeAcerto > Math.random()) {
      return true;
    }
    return false;
  }

  protected boolean causarDano(Entidade alvo) {
    boolean teste = testarAtaque(alvo);
    if (teste) {
      alvo.receberDano(dano);
    }
    return teste;
  }

  protected void receberDano(Dano dano) {
    this.setVida(dano);
  }

  protected void adicionarAcao(Acao acao) {
    this.acoes.add(acao);
  }

  abstract protected void adicionarAcoes();

  public int getTamanhoAcoes() {
    return this.acoes.size();
  }

  public ArrayList<String> getStringsAcoes() {
    ArrayList<String> strings = new ArrayList<>();
    for (Acao a : this.acoes) {
      strings.add(a.toString());
    }
    return strings;
  }

  public void executarAcaoAleatoria(String nomeDoExecutor, Entidade alvo) {
    int indiceAleatorio = (int) Math.floor(Math.random() * this.acoes.size());
    this.executarAcao(nomeDoExecutor, indiceAleatorio, alvo);
  }

  public void executarAcao(String nomeDoExecutor, int indiceAcao, Entidade alvo) {
    Acao acao = this.acoes.get(indiceAcao);

    if (!acao.executa(alvo)) {
      System.out.println("O " + nomeDoExecutor + " falhou na ação [ " + acao + " ]");
    } else {
      System.out.println("O " + nomeDoExecutor + " realizou a a ação [ " + acao + " ] com sucesso!");
    }
  }

  public String getStringAtributos() {
    return String.join("\n",
        String.format("VIDA: %d/%d", this.vida, this.vidaInicial),
        String.format("DANO: %d", this.dano.getQuantidade()),
        String.format("ESQUIVA: %.2f", this.esquiva),
        String.format("PRECISAO: %.2f", this.precisao));
  }
}
