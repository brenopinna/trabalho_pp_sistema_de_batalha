package acao;

import java.util.function.Function;

import entidades.Entidade;

public interface IAcao extends Function<Entidade, Boolean> {
}