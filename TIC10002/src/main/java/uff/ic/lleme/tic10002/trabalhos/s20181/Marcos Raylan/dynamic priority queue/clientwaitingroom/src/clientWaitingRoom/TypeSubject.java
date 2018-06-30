package clientWaitingRoom;

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
        super.Subject(sub.getType(), sub.getTitle(), sub.getUrgency());
    }

    //setter
    public void setDescription(String description) {
        this.description = description;
    }

    public void setProvidence(String providence) {
        this.providence = providence;
    }

    public void setDuration(double duration) {
        this.duration = duration / 15;
    }

    public void setSubject(Subject sub) {
        super.Subject(sub.getType(), sub.getTitle(), sub.getUrgency());
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
}
