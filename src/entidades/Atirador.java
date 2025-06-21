package entidades;

import java.util.ArrayList;
import java.util.List;

import dano.Dano;
import dano.TipoDano;

import acao.Acao;

abstract public class Atirador extends Entidade {
  private int projeteis;
  private int projeteisIniciais;

  public Atirador(int dano, double esquiva, int projeteisIniciais) {
    super(
        new Dano(dano, TipoDano.PROJETIL),
        65,
        esquiva,
        0.9,
        new ArrayList<>(List.of(TipoDano.FISICO)));
    this.projeteisIniciais = projeteisIniciais;
    this.projeteis = projeteisIniciais;
    this.adicionarAcao(new Acao(e -> this.recarregar(), "Recarregar"));
  }

  private boolean recarregar() {
    if (this.projeteis == this.projeteisIniciais)
      return false;
    this.projeteis += this.projeteisIniciais / 5;
    if (this.projeteis > this.projeteisIniciais) {
      this.projeteis = this.projeteisIniciais;
    }
    return true;
  }

  public boolean causarDano(Entidade alvo) {
    boolean teste = testarAtaque(alvo);
    if (teste) {
      alvo.receberDano(this.getDano());
      this.projeteis--;
    }
    return teste;
  }

  public String getStringAtributos() {
    return String.join("\n", super.getStringAtributos(),
        String.format("PROJÉTEIS: %d/%d", this.projeteis, this.projeteisIniciais));
  }
}
