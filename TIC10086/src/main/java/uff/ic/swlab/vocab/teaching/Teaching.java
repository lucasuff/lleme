package uff.ic.swlab.vocab.teaching;

import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class Teaching {

    public static final String uri = "http://swlab.lleme.net:8080/vocab/teaching#";

    public static String getURI() {
        return uri;
    }

    protected static final Resource resource(String local) {
        return ResourceFactory.createResource(uri + local);
    }

    protected static final Property property(String local) {
        return ResourceFactory.createProperty(uri, local);
    }

    public static Property li(int i) {
        return property("_" + i);
    }

    public static final Resource Assigment = Teaching.Init.Assigment();
    public static final Resource Course = Teaching.Init.Course();
    public static final Resource StudentGroup = Teaching.Init.StudentGroup();
    public static final Resource Class = Teaching.Init.Class();
    public static final Resource Presentation = Teaching.Init.Presentation();
    public static final Resource Report = Teaching.Init.Report();
    public static final Resource Test = Teaching.Init.Test();
    public static final Resource Discipline = Teaching.Init.Discipline();
    public static final Resource Event = Teaching.Init.Event();
    public static final Resource Material = Teaching.Init.Material();
    public static final Resource Teacher = Teaching.Init.Teacher();
    public static final Resource UndergraduateStudent = Teaching.Init.UndergraduateStudent();

    public static final Property hasAssignment = Teaching.Init.hasAssignment();
    public static final Property hasMaterial = Teaching.Init.hasMaterial();
    public static final Property hasStudentGroup = Teaching.Init.hasStudentGroup();
    public static final Property isStudentGroupOf = Teaching.Init.isStudentGroupOf();

    public static final Property hasGrade = Teaching.Init.hasGrade();
    public static final Property hasGrading = Teaching.Init.hasGrading();

    public static class Init {

        public static Resource Assigment() {
            return resource("Assignment");
        }

        public static Resource Course() {
            return resource("Course");

        }

        public static Resource StudentGroup() {
            return resource("StudentGroup");

        }

        public static Resource Class() {
            return resource("Class");

        }

        public static Resource Presentation() {
            return resource("Presentation");

        }

        public static Resource Report() {
            return resource("Report");

        }

        public static Resource Test() {
            return resource("Test");

        }

        public static Resource Discipline() {
            return resource("Discipline");

        }

        public static Resource Event() {
            return resource("Event");

        }

        public static Resource Material() {
            return resource("Material");

        }

        public static Resource Teacher() {
            return resource("Teacher");

        }

        public static Resource UndergraduateStudent() {
            return resource("UndergraduateStudent");

        }

        public static Property hasAssignment() {
            return property("hasAssignment");
        }

        public static Property hasMaterial() {
            return property("hasMaterial");
        }

        public static Property hasStudentGroup() {
            return property("hasStudentGroup");
        }

        public static Property isStudentGroupOf() {
            return property("isStudentGroupOf");
        }

        public static Property hasGrade() {
            return property("hasGrade");
        }

        public static Property hasGrading() {
            return property("hasGrading");
        }

    }

    @SuppressWarnings("hiding")
    public static final class Nodes {

        public static final Node Assigment = Teaching.Init.Assigment().asNode();
        public static final Node Course = Teaching.Init.Course().asNode();
        public static final Node StudentGroup = Teaching.Init.StudentGroup().asNode();
        public static final Node Class = Teaching.Init.Class().asNode();
        public static final Node Presentation = Teaching.Init.Presentation().asNode();
        public static final Node Report = Teaching.Init.Report().asNode();
        public static final Node Test = Teaching.Init.Test().asNode();
        public static final Node Discipline = Teaching.Init.Discipline().asNode();
        public static final Node Event = Teaching.Init.Event().asNode();
        public static final Node Material = Teaching.Init.Material().asNode();
        public static final Node Teacher = Teaching.Init.Teacher().asNode();
        public static final Node UndergraduateStudent = Teaching.Init.UndergraduateStudent().asNode();
        public static final Node hasAssignment = Teaching.Init.hasAssignment().asNode();
        public static final Node hasMaterial = Teaching.Init.hasMaterial().asNode();
        public static final Node hasStudentGroup = Teaching.Init.hasStudentGroup().asNode();
        public static final Node isStudentGroupOf = Teaching.Init.isStudentGroupOf().asNode();
        public static final Node hasGrade = Teaching.Init.hasGrade().asNode();
        public static final Node hasGrading = Teaching.Init.hasGrading().asNode();
    }
}
