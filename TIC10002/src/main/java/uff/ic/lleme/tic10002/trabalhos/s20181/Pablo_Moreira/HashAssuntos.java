/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira;

import uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model.Assunto;

/**
 *
 * @author pablomoreira
 */
class HashAssuntos {

    private ListaDeAssuntos[] tabelaHash;

    public HashAssuntos(TipoAssunto[] tiposDeAssunto) {

        int maxTipo = -1;
        for (int i = 0; i < tiposDeAssunto.length; i++)
            if (tiposDeAssunto[i].getTipo() > maxTipo)
                maxTipo = tiposDeAssunto[i].getTipo();

        tabelaHash = new ListaDeAssuntos[maxTipo + 1];

        for (TipoAssunto tipoAssunto : tiposDeAssunto)
            tabelaHash[tipoAssunto.getTipo()] = new ListaDeAssuntos(tipoAssunto);
    }

    public void inserir(Assunto assunto) {

        tabelaHash[assunto.getTipo()].Inserir(assunto);

    }

    public ListaDeAssuntos obterAssuntosPorTipo(TipoAssunto tipo) {
        return tabelaHash[tipo.getTipo()];
    }

}
