package entidades;

import acao.Acao;

public class Feiticeiro extends Mistico {
  public Feiticeiro() {
    super(40);
  }

  private boolean cegar(Entidade alvo) {
    if (this.gastarMana(8) && this.testarMagia()) {
      alvo.setEsquiva(0.1);
      alvo.setPrecisao(0.1);
      return true;
    } else
      return false;
  }

  @Override
  protected void adicionarAcoes() {
    this.adicionarAcao(new Acao(e -> this.cegar(e), "Cegar"));
  }
}
