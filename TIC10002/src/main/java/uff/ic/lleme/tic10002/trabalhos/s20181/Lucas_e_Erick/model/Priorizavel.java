package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model;

import java.time.LocalDateTime;

public interface Priorizavel {

    public double priorizar(LocalDateTime atual);

    public LocalDateTime getPriorizador();
}
