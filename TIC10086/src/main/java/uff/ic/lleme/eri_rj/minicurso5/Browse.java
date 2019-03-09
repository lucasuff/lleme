package uff.ic.lleme.eri_rj.minicurso5;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

public class Browse {

    public static void main(String[] args) {
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";
        String dataURL = "http://swlab.lleme.net:8080/cursos.html";
        String schemaURL = "http://swlab.lleme.net:8080/vocab/teaching#Course";

        // Browsing
        Model data = ModelFactory.createDefaultModel();
        RDFDataMgr.read(data, String.format(rdfTranslatorService, dataURL), Lang.RDFXML);
        //RDFDataMgr.write(System.out, data, Lang.N3);

        //Detecting Events
        OntModel schema = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        schema.read(schemaURL);

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel infModel = ModelFactory.createInfModel(reasoner, data);
        //RDFDataMgr.write(System.out, infModel, Lang.N3);

        ResIterator iter = infModel.listSubjectsWithProperty(RDF.type, "http://swlab.lleme.net:8080/vocab/teaching#Course");
        while (iter.hasNext())
            System.out.println(iter.next().getURI());

    }
}
