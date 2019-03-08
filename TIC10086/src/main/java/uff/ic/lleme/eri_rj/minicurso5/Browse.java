package uff.ic.lleme.eri_rj.minicurso5;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

public class Browse {

    public static void main(String[] args) {
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";
        String url = "http://swlab.lleme.net:8080/cursos.html";

        // Browsing
        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, String.format(rdfTranslatorService, url), Lang.RDFXML);
        RDFDataMgr.write(System.out, model, Lang.N3);

        //Detecting KnowledgeGrouping
    }
}
