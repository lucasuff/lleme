package uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model;

//import java.util.ArrayList;
//import java.util.List;
public class Subject {

    protected int type;
    protected String title;
    protected double urgency;

    //constructor
    public void Subject(int type, String title, double urgency) {
        this.type = type;
        this.title = title;
        this.urgency = urgency;
    }

    //setter
    public void setType(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
        //this.title = "******";
    }

    public void setUrgency(double urgency) {
        this.urgency = urgency / 10;
    }

    //getter
    public int getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public double getUrgency() {
        return this.urgency;
    }

    public boolean authenticate(int urgency) {
        if (0 <= urgency && urgency <= 10) {
            System.out.println("Acesso Permitido!");
            return true;
        } else {
            System.out.println("Acesso Negado!");
            return false;
        }
    }
}

/*
public class TypeSubject extends Subject {
    //private int type;
    private String description;
    private String providence;
    private double duration;

    //constructor
	public TypeSubject(String description, String providence, double duration) {
    	this.description = description;
    	this.providence = providence;
    	this.duration = duration / 15;
    }
	//constructor
    public TypeSubject(String description, String providence, double duration, Subject sub) {
    	this.description = description;
    	this.providence = providence;
    	this.duration = duration / 15;
    	super.Subject( sub.getType(), sub.getTitle(), sub.getUrgency());
    }

    //setter
    public void setDescription(String description){
    	this.description = description;
    }
    public void setProvidence(String providence){
    	this.providence = providence;
    }
    public void setDuration(double duration){
    	this.duration = duration / 15;
    }
    public void setSubject(Subject sub) {
    	super.Subject( sub.getType(), sub.getTitle(), sub.getUrgency());
    }

    //getter
    public String getDescription() {
    	return this.description;
    }
    public String getProvidence() {
    	return this.providence;
    }
    public double getDuration() {
    	return this.duration;
    }
    @Override
    public double getUrgency() {
    	return this.urgency;
    }

    // methods ...
}*/
