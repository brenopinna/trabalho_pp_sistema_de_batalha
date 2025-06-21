package acao;

import entidades.Entidade;

public class Acao {
  private IAcao acao;

  private String stringAcao;

  public Acao(IAcao acao, String stringAcao) {
    this.acao = acao;
    this.stringAcao = stringAcao;
  }

  public boolean executa(Entidade e) {
    return this.acao.apply(e);
  }

  public IAcao getAcao() {
    return acao;
  }

  public String toString() {
    return this.stringAcao;
  }
}