package entidades;

import java.util.ArrayList;
import java.util.List;

import dano.Dano;
import dano.TipoDano;

public class Berserk extends Entidade {
  private double raiva;

  public Berserk() {
    super(
        new Dano(18, TipoDano.FISICO),
        90,
        0.2,
        0.75,
        new ArrayList<>(List.of(TipoDano.MAGICO)));
    this.raiva = 0;
  }

  @Override
  public void receberDano(Dano dano) {
    int danoRecebido = this.setVida(dano);
    if (this.checarVivo())
      incrementarRaiva(danoRecebido);
  }

  private void incrementarRaiva(int danoRecebido) {
    this.raiva += (double) danoRecebido / this.getVida() * 1.2;
    if (this.raiva > 1)
      this.raiva = 1;
    int antigoDano = this.getDano().getQuantidade();
    int novoDano = (int) Math.floor(antigoDano * (1 + this.raiva));
    this.setDano(novoDano);
  }

  @Override
  protected void adicionarAcoes() {
  }

  @Override
  public String getStringAtributos() {
    return String.join("\n", super.getStringAtributos(),
        String.format("RAIVA: %.2f", this.raiva));
  }
}
