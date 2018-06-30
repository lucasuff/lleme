package br.uff.ed.main;

import java.time.LocalDateTime;

public interface Priorizavel {

    public double priorizar(LocalDateTime atual);

    public LocalDateTime getPriorizador();
}
