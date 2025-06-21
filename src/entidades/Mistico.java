package entidades;

import java.util.ArrayList;
import java.util.List;

import acao.Acao;
import dano.Dano;
import dano.TipoDano;

abstract public class Mistico extends Entidade {
  private int mana;
  private int manaInicial;

  public Mistico(int manaInicial) {
    super(
        new Dano(30, TipoDano.MAGICO),
        75,
        0.5,
        0.6,
        new ArrayList<>(List.of(TipoDano.MAGICO, TipoDano.PROJETIL)));
    this.mana = manaInicial;
    this.manaInicial = manaInicial;
    this.adicionarAcao(new Acao(e -> this.recuperarMana(), "Recuperar Mana"));
  }

  private boolean recuperarMana() {
    this.mana += 5;
    if (this.mana >= this.manaInicial) {
      this.mana = this.manaInicial;
      return false;
    } else
      return true;
  }

  protected boolean gastarMana(int quantidade) {
    this.mana -= quantidade;
    if (this.mana < 0) {
      this.mana += quantidade;
      return false;
    }
    return true;
  }

  protected boolean testarMagia() {
    double teste = ((double) this.mana / this.manaInicial);
    return teste > Math.random();
  }

  public String getStringAtributos() {
    return String.join("\n", super.getStringAtributos(),
        String.format("MANA: %d/%d", this.mana, this.manaInicial));
  }
}
