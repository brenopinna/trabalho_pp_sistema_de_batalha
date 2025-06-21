package dano;

public enum TipoDano {
  FISICO,
  MAGICO,
  PROJETIL;

  public String toString() {
    return switch (this) {
      case FISICO -> "físico";
      case MAGICO -> "mágico";
      case PROJETIL -> "projétil";
      default -> name();
    };
    /*
     * Equivale a isso:
     * switch(this){
     * case FISICO:
     * return "físico";
     * case MAGICO:
     * return "mágico";
     * case PROJETIL:
     * return "projétil";
     * default:
     * return name();
     * }
     */
  }
}