package uff.ic.lleme.tic10086.exercises;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.StmtIterator;

public class CalendarAgent {

    public static void main(String[] args) {
        String rdfTranslatorService = "http://rdf-translator.appspot.com/convert/rdfa/xml/%1$s";
        String sourceURL = "http://swlab.lleme.net:8080/cursos.html";
        String schemaURL = "http://swlab.lleme.net:8080/vocab/teaching#Course";

        //1) Read source URL
        Model data;

        //2) Detect known resources (Disciplines, Courses, Tests, Reports, Classes)
        StmtIterator iter;

        //3) Get calendar events
    }
}
