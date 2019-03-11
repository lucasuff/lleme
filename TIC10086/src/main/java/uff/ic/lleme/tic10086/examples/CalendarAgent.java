package uff.ic.lleme.tic10086.examples;

import java.util.HashSet;
import java.util.Set;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VOID;
import uff.ic.swlab.vocab.teaching.Teaching;

public class CalendarAgent {

    public static void main(String[] args) {
        String meuNome = "Mateus";
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";
        String sourceURL = "http://swlab.lleme.net:8080/cursos.html";
        String schemaURL = "http://swlab.lleme.net:8080/vocab/teaching#Course";

        // ********************************************************************************
        // Browse a Web resource
        //
        Model data = ModelFactory.createDefaultModel();
        RDFDataMgr.read(data, String.format(rdfTranslatorService, sourceURL), Lang.RDFXML);
        //
        // ********************************************************************************

        // **************************************************************************************************************
        // Analyse topics of the the Web resource
        //
        Set<String> datasets = new HashSet<>();
        Set<String> sparqlEndpoints = new HashSet<>();
        Property[] topicProperties = {FOAF.topic, FOAF.primaryTopic};

        for (Property property : topicProperties) {
            StmtIterator iter = data.listStatements(ResourceFactory.createResource(sourceURL), property, (RDFNode) null);
            while (iter.hasNext()) {
                Resource topic = iter.next().getObject().asResource();
                StmtIterator types = topic.listProperties(RDF.type);

                while (types.hasNext()) {
                    Resource _class = types.next().getObject().asResource();

                    // *******************************************************
                    // if the Web resource is about something known, handle it
                    //
                    if (_class.equals(Teaching.Discipline)
                            || _class.equals(Teaching.Course)
                            || _class.equals(Teaching.Test)
                            || _class.equals(Teaching.Report)
                            || _class.equals(Teaching.Class))
                        // store container datasets
                        datasets.add(extractDatasetURI(topic, data));
                    // *******************************************************
                }

            }
        }
        // **************************************************************************************************************

        // ********************************************************
        // For each container dataset
        //
        for (String datasetURI : datasets)
            if (datasetURI != null)
                sparqlEndpoints.add(getSparqlEndpoints(datasetURI));

        System.out.println(sparqlEndpoints);
        // ********************************************************

        //Detecting Events
        //OntModel schema = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //schema.read(schemaURL);
        //Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        //reasoner = reasoner.bindSchema(schema);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, data);
        //RDFDataMgr.write(System.out, infModel, Lang.N3);
    }

    /**
     * Use existing backlinks, recommended by the VoID specification
     * <a href="http://www.w3.org/TR/void/#backlinks">(http://www.w3.org/TR/void/#backlinks)</a>,
     * to find the container dataset of the topic.
     *
     * @param topic The topic resource for which one wants to find the
     * container. dataset
     * @param data The RDF graph that supposedly contains the backlink for the
     * resource.
     * @return The dataset URI
     */
    private static String extractDatasetURI(Resource topic, Model data) {
        StmtIterator iter = data.listStatements(topic, VOID.inDataset, (RDFNode) null);
        String datasetURI = null;
        while (iter.hasNext()) {
            datasetURI = iter.next().getObject().asResource().getURI();
            break;
        }
        return datasetURI;
    }

    private static String getSparqlEndpoints(String datasetURI) {
        Resource dataset = ResourceFactory.createResource(datasetURI);

        Model voidData = ModelFactory.createDefaultModel();
        RDFDataMgr.read(voidData, datasetURI);

        StmtIterator iter = voidData.listStatements(dataset, VOID.sparqlEndpoint, (RDFNode) null);
        String sparqlEndpointURL = null;
        while (iter.hasNext()) {
            sparqlEndpointURL = iter.next().getObject().asResource().getURI();
            break;
        }
        return sparqlEndpointURL;
    }

    private static Model getICalEvent(String sparqlEndpoint) {
        return null;
    }
}
