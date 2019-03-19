package old.uff.ic.lleme.tic10086.exercises.old;

import java.util.HashSet;
import java.util.Set;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.WebContent;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VOID;
import uff.ic.swlab.vocab.teaching.Teaching;

public class Query {

    public static void main(String[] args) {
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";

        String studentName = "Willian Alecsander Farias Costa";
        String siteURL = "http://swlab.lleme.net:8080/cursos.html";

        // **************************************************************************************************************
        // First step: Browse a Web resource
        //
        Model data = ModelFactory.createDefaultModel();
        RDFDataMgr.read(data, String.format(rdfTranslatorService, siteURL), Lang.RDFXML);
        RDFDataMgr.write(System.out, data, Lang.TTL);
        //
        // **************************************************************************************************************

        // **************************************************************************************************************
        // Second step: Search for topics of interest in the the Web resource
        //
        Set<String> datasets = new HashSet<>();
        Set<String> sparqlEndpoints = new HashSet<>();
        Property[] topicProperties = {FOAF.topic, FOAF.primaryTopic};

        for (Property property : topicProperties) {
            StmtIterator iter = data.listStatements(ResourceFactory.createResource(siteURL), property, (RDFNode) null);
            while (iter.hasNext()) {
                Resource topic = iter.next().getObject().asResource();
                String topicURI = topic.getURI();
                StmtIterator types = topic.listProperties(RDF.type);

                while (types.hasNext()) {
                    Resource _class = types.next().getObject().asResource();

                    // *************************************************************************************
                    // if the Web resource is about something of interest, handle it (get container dataset)
                    //
                    if (_class.equals(Teaching.Discipline)
                            || _class.equals(Teaching.Course)
                            || _class.equals(Teaching.Test)
                            || _class.equals(Teaching.Report)
                            || _class.equals(Teaching.Class))
                        // store container datasets <<<<<<<<<<<<<<<<<<<<<<<<<<
                        datasets.add(getInDataset(topicURI, data));
                    // *************************************************************************************
                }

            }
        }
        // **************************************************************************************************************

        // **************************************************************************************************************
        // Third step: For each container dataset get the sparql endpoint
        //
        for (String datasetURI : datasets)
            if (datasetURI != null)
                sparqlEndpoints.add(getSparqlEndpoint(datasetURI));
        // **************************************************************************************************************

        // **************************************************************************************************************
        // Fourth step: query the SPARQL endpoints to get available assignments
        //
        for (String sparqlEndpoint : sparqlEndpoints) {
            Model assigments = getAssigments(sparqlEndpoint, studentName);
            RDFDataMgr.write(System.out, assigments, RDFFormat.TURTLE_PRETTY);
        }
        // **************************************************************************************************************

    }

    /**
     * Use existing backlinks, recommended by the VoID specification
     * <a href="http://www.w3.org/TR/void/#backlinks">(http://www.w3.org/TR/void/#backlinks)</a>,
     * to find the container dataset of the topic.
     *
     * @param topic The topic resource for which one wants to find the
     * container.
     * @param data The RDF graph that supposedly contains the backlink for the
     * resource.
     * @return The URI of the container dataset.
     */
    private static String getInDataset(String topicURI, Model data) {
        Resource topic = ResourceFactory.createResource(topicURI);

        StmtIterator iter = data.listStatements(topic, VOID.inDataset, (RDFNode) null);
        String datasetURI = null;
        while (iter.hasNext()) {
            datasetURI = iter.next().getObject().asResource().getURI();
            break;
        }
        return datasetURI;
    }

    /**
     * Use dataset URI dereferencing
     * <a href="https://www.w3.org/TR/void/#void-file">(https://www.w3.org/TR/void/#void-file)</a>,
     * to get the sparqlEndpoint of the given dataset.
     *
     * @param datasetURI The URI of the dataset for which to find the SPARQL
     * endpoint.
     * @return The URI of the SPARQL endpoint.
     */
    private static String getSparqlEndpoint(String datasetURI) {
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

    private static Model getAssigments(String sparqlEndpoint, String name) {
        String query = ""
                + "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX text: <http://jena.apache.org/text#>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "prefix void: <http://rdfs.org/ns/void#>\n"
                + "prefix icaltzd: <http://www.w3.org/2002/12/cal/icaltzd#>\n"
                + "prefix particip: <http://purl.org/vocab/participation/schema#>\n"
                + "prefix myvoid: <http://swlab.lleme.net:8080/void.ttl#>\n"
                + "prefix teach: <http://swlab.lleme.net:8080/vocab/teaching#>\n"
                + "prefix rsc: <http://swlab.lleme.net:8080/resource/>\n"
                + "prefix BR: <http://www.w3.org/2002/12/cal/tzd/America/Sao_Paulo#>\n"
                + "\n"
                + "construct {\n"
                + "  ?asmt ?p ?o.\n"
                + "  ?o ?p2 ?o2.\n"
                + "  ?group particip:role ?role.\n"
                + "  ?role particip:role_at ?group.\n"
                + "  ?group teach:hasAssignment ?asmt.\n"
                + "  ?role rdfs:label ?lbl.\n"
                + "}\n"
                + "from <urn:x-arq:DefaultGraph>\n"
                + "from <urn:x-arq:UnionGraph>\n"
                + "where {\n"
                + "  ?sdt text:query (\"(%1$s)\" 1).\n"
                + "  ?sdt a foaf:Person.\n"
                + "  ?role particip:holder ?sdt.\n"
                + "  {{?role a teach:UndergraduateStudent} union {?role a teach:Teacher}}\n"
                + "  ?group particip:role ?role.\n"
                + "  ?group teach:hasAssignment ?asmt.\n"
                + "  ?asmt ?p ?o.\n"
                + "  optional {?o ?p2 ?o2}\n"
                + "  optional {?role rdfs:label ?lbl}\n"
                + "}";
        query = String.format(query, name);

        final Model result = ModelFactory.createDefaultModel();
        try (final QueryExecution exec = new QueryEngineHTTP(sparqlEndpoint, query, HttpClients.createDefault())) {
            ((QueryEngineHTTP) exec).setModelContentType(WebContent.contentTypeRDFXML);
            exec.execConstruct(result);
        }
        return result;
    }
}
