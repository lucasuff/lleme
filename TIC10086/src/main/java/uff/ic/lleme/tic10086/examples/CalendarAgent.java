package uff.ic.lleme.tic10086.examples;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import uff.ic.swlab.vocab.teaching.Teaching;

public class CalendarAgent {

    public static void main(String[] args) {
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";
        String sourceURL = "http://swlab.lleme.net:8080/cursos.html";
        String schemaURL = "http://swlab.lleme.net:8080/vocab/teaching#Course";

        // Browsing
        Model data = ModelFactory.createDefaultModel();
        RDFDataMgr.read(data, String.format(rdfTranslatorService, sourceURL), Lang.RDFXML);
        //RDFDataMgr.write(System.out, data, Lang.N3);

        {
            StmtIterator iter = data.listStatements(ResourceFactory.createResource(sourceURL), FOAF.topic, (RDFNode) null);
            while (iter.hasNext()) {
                Resource topic = iter.next().getObject().asResource();
                StmtIterator types = topic.listProperties(RDF.type);
                while (types.hasNext()) {
                    Resource _class = types.next().getObject().asResource();
                    if (_class.equals(Teaching.Discipline)
                            || _class.equals(Teaching.Course)
                            || _class.equals(Teaching.Test)
                            || _class.equals(Teaching.Report)
                            || _class.equals(Teaching.Class)) {
                        doSomething(topic);
                        System.out.println(topic.getURI());
                    }
                }

            }
        }

        {
            StmtIterator iter = data.listStatements(ResourceFactory.createResource(sourceURL), FOAF.primaryTopic, (RDFNode) null);
            while (iter.hasNext()) {
                Resource topic = iter.next().getObject().asResource();
                StmtIterator types = topic.listProperties(RDF.type);
                while (types.hasNext()) {
                    Resource _class = types.next().getObject().asResource();
                    if (_class.equals(Teaching.Discipline)
                            || _class.equals(Teaching.Course)
                            || _class.equals(Teaching.Test)
                            || _class.equals(Teaching.Report)
                            || _class.equals(Teaching.Class)) {
                        doSomething(topic);
                        System.out.println(topic.getURI());
                    }
                }

            }
        }

        //Detecting Events
        //OntModel schema = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //schema.read(schemaURL);
        //Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        //reasoner = reasoner.bindSchema(schema);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, data);
        //RDFDataMgr.write(System.out, infModel, Lang.N3);
    }

    private static void doSomething(Resource toppic) {

    }
}
