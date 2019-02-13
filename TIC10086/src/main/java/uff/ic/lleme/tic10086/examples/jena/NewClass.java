package uff.ic.lleme.tic10086.examples.jena;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.WebContent;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.tdb.TDBFactory;
import uff.ic.swlab.commons.riot.JenaSchemaMgr;
import uff.ic.swlab.commons.util.DBpediaSearch;

public class NewClass {

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, MalformedURLException, IOException {

        {
            String DOMAIN = null;
            String DATASET_URL = null;
            String DEREF_PORT = null;
            String DEFAULT_NS = "http://" + DOMAIN + DEREF_PORT + "/resource/";
            String uri = DEFAULT_NS + "id-" + UUID.randomUUID();

            URL url = new URL(DATASET_URL);
            try (
                    InputStreamReader in = new InputStreamReader(url.openStream(), "Windows-1252");
                    BufferedReader buff = new BufferedReader(in);
                    CSVReader reader = new CSVReader(buff, ',', '"', 1);) {

                String[] nextLine;
                String uri1, uri2, uri3, name, street, number, neighborhood, telephone, latitude, longitude;
                while ((nextLine = reader.readNext()) != null)
                    try {
                        name = nextLine[0];
                        street = nextLine[1];
                        number = nextLine[2];
                        neighborhood = nextLine[3];
                        telephone = nextLine[4];
                        latitude = nextLine[5];
                        longitude = nextLine[6];

                    } catch (Exception e) {
                    }

            }
        }

        new File("./src/main/resources/dat/tdb/exemplo").mkdirs();
        String ns = "http://ifmt.br/resource/";
        String schemaNS = "http://schema.org/";

        Model schema = JenaSchemaMgr.getSchemaOrg();

        String assemblerFile = "./src/main/resources/conf/exemplo.ttl";
        Dataset dataset = TDBFactory.assembleDataset(assemblerFile);

        {
            dataset.begin(ReadWrite.WRITE);
            Model model = dataset.getDefaultModel();
            model.removeAll();
            model.getNsPrefixMap().clear();
            dataset.commit();
        }

        {
            dataset.begin(ReadWrite.WRITE);
            Model model = dataset.getDefaultModel();
            model.setNsPrefix("", "http://ifmt.br/resource/");
            model.setNsPrefix("schema", "http://schema.org/");

            String uri = ns + "id-" + UUID.randomUUID();

            model.createResource(uri)
                    .addProperty(schema.getProperty(schemaNS + "name"), "Cidade das Artes")
                    .addProperty(schema.getProperty(schemaNS + "telephone"), "4636436435");
            dataset.commit();
            dataset.end();
        }

        {
            dataset.begin(ReadWrite.READ);
            OutputStream out = new FileOutputStream(new File("./src/main/resources/dat/rdf/exemplo.ttl"));
            RDFDataMgr.write(out, dataset.getDefaultModel(), Lang.TURTLE);
            dataset.end();
        }

        {
            Model model = ModelFactory.createDefaultModel();
            DBpediaSearch.search("Cidade das Artes", 7, 0, model);
            OutputStream out = new FileOutputStream(new File("./src/main/resources/dat/rdf/dbpediaExemplo.ttl"));
            RDFDataMgr.write(out, model, Lang.TURTLE);
        }
        {
            //**********************************
            //*    A T E N Ç Ã O: data url     *
            //**********************************
            String FUSEKI_DATA_URL = "http://localhost:8080/fuseki/mashup/data";
            Model model = null;
            DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(FUSEKI_DATA_URL);
            accessor.putModel(model);
        }
        {
            Model model = ModelFactory.createDefaultModel();
            Model out = ModelFactory.createDefaultModel();
            String query = "construct ...";
            QueryExecution exec = QueryExecutionFactory.create(query, model);
            exec.execConstruct(out);
        }

        {
            Dataset draft = null;
            Dataset mashup = null;
            String DEFAULT_NS = null;
            String SAME_AS_RULES = null;

            draft.begin(ReadWrite.READ);
            Model draftModel = draft.getDefaultModel();

            mashup.begin(ReadWrite.WRITE);
            Model mashupModel = mashup.getDefaultModel();

            BufferedReader br2 = new BufferedReader(new FileReader(SAME_AS_RULES));
            List rules2 = Rule.parseRules(Rule.rulesParserFromReader(br2));

            Reasoner reasoner2 = new GenericRuleReasoner(rules2);
            InfModel inf2 = ModelFactory.createInfModel(reasoner2, draftModel);
        }
    }

    //******************************************************************
    //*    A T E N Ç Ã O: query url                                    *
    //* String sparql = "http://localhost:8080/fuseki/mashup/sparql";  *
    //******************************************************************
    private static Model loadDataset(String sparql, Model model) {
        String describeQuery = "construct {?s ?p ?o.} where {?s ?p ?o.}";
        try (QueryExecution exec = new QueryEngineHTTP(sparql, describeQuery);) {
            ((QueryEngineHTTP) exec).setModelContentType(WebContent.contentTypeRDFXML);
            exec.execConstruct(model);
        } catch (Exception e) {
        }
        return model;
    }
}
