package uff.ic.lleme.eri_rj.minicurso5;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

public class Browse {

    public static void main(String[] args) {
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";
        String url = "http://swlab.lleme.net:8080/cursos.html";
        String schemaURL = "http://swlab.lleme.net:8080/vocab/teaching#Course";

        // Browsing
        Model data = ModelFactory.createDefaultModel();
        RDFDataMgr.read(data, String.format(rdfTranslatorService, url), Lang.RDFXML);
        //RDFDataMgr.write(System.out, data, Lang.N3);

        //Detecting Events
        Model schema = FileManager.get().loadModel("http://swlab.lleme.net:8080/vocab/teaching#Course");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, infmodel);
        ExtendedIterator<Individual> iter = ontModel.listIndividuals(schema.getResource("http://www.w3.org/2002/12/cal/icaltzd.rdf#Vevent"));
        while (iter.hasNext())
            System.out.println(iter.next().getURI());

    }
}
