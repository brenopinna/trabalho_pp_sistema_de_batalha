package entidades;

import acao.Acao;

public class Curandeiro extends Mistico {
  public Curandeiro() {
    super(60);
  }

  private boolean curar() {
    if (this.gastarMana(12) && this.testarMagia()) {
      this.setVida(12);
      return true;
    } else
      return false;
  }

  @Override
  protected void adicionarAcoes() {
    this.adicionarAcao(new Acao(e -> this.curar(), "Curar"));
  }
}
