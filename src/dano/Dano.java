package dano;

public class Dano {
  private int base;
  private int atual;
  private TipoDano tipo;

  public Dano(int quantidade, TipoDano tipo) {
    this.base = this.atual = quantidade;
    this.tipo = tipo;
  }

  public int getQuantidade() {
    return atual;
  }

  public void setQuantidade(int quantidade) {
    this.atual = quantidade >= this.base * 2 ? this.base * 2 : quantidade;
  }

  public TipoDano getTipo() {
    return tipo;
  }

  public String toString() {
    return this.atual + " de dano do tipo " + this.tipo;
  }
}