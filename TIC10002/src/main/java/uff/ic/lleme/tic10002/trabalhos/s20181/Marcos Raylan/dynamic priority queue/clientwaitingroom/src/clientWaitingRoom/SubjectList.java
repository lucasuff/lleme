package clientWaitingRoom;

///*
public class SubjectList {

    Node head;
    Node it;
    int size = 0;

    class Node {

        TypeSubject data;
        Node next;

        Node(TypeSubject d) {
            data = d;
            next = null;
        }
    }

    public void push(TypeSubject new_data) {
        Node new_node = new Node(new_data);
        new_node.next = head;
        head = new_node;
        it = head;
        size++;
    }

    //methods
    public void addSubject(TypeSubject item) {
        this.push(item);
    }

    public TypeSubject getNext() {
        if (it == null)
            return null;
        TypeSubject item = it.data;
        it = it.next;
        return item;
    }

    public void defaultIT() {
        it = head;
    }

    //getter
    public TypeSubject getI(int i) {
        TypeSubject buffer = it.data;
        int j = 0;
        while (j < i) {
            buffer = getNext();
            j++;
        }
        defaultIT();
        return buffer;
    }

    public double getPriority(int i) {
        return getI(i).getDuration() + getI(i).getUrgency();
    }

    public double getTime(int i) {
        return getI(i).getDuration() * 15;
    }

    public int getType(int i) {
        return getI(i).getType();
    }

    public String getDescription(int i) {
        return getI(i).getDescription();
    }

    public void getItem(int i) {
        System.out.format("%11s %30s %30s %30s %15s %15s",
                getI(i).getType(), getI(i).getTitle(), getI(i).getDescription(), getI(i).getProvidence(), getI(i).getDuration() * 15, getI(i).getUrgency() * 10);
        System.out.println();
    }

    //public int getSize(){return size;}
    public int size() {
        return size;
    }
}
//*/

/*

import java.util.ArrayList;
import java.util.List;
import clientWaitingRoom.LinkedListStrings.Node;

public class SubjectList {
	List<TypeSubject> subjects;
	LinkedList<TypeSubject> list;

	//constructor
	public SubjectList(){
		this.subjects = new ArrayList<TypeSubject>();
		list = new LinkedList<TypeSubject>();
	}

	//getter
	public TypeSubject getI(int i) {
		return subjects.get(i);
	}

	public double getPriority(int i){
    	return getI(i).getDuration() + getI(i).getUrgency();
	}
	public double getTime(int i){
    	return getI(i).getDuration()*15;
    }
	public int getType(int i){
    	return getI(i).getType();
    }
	public String getDescription(int i) {
		return getI(i).getDescription();
	}
	public void getItem(int i){
		System.out.format("%11s %30s %30s %30s %15s %15s",
				getI(i).getType(), getI(i).getTitle(), getI(i).getDescription(), getI(i).getProvidence(), getI(i).getDuration()*15, getI(i).getUrgency()*10);
        System.out.println();
	}
	public int getSize(){
    	return subjects.size();
    }

	//methods
	public void addSubject(TypeSubject item){
    	this.subjects.add( item );
    }
	public int size(){
    	return subjects.size();
    }
}
//*/
//import clientWaitingRoom.Statistic.LinkedList;
//LinkedList<String> list = new LinkedList<String>();
//list.insert(new LinkedListNode<String>("Manish"));
//list.insert(new LinkedListNode<String>("Pandit"));
//list.insert(new LinkedListNode<String>("Tanvi"));
//list.insert(new LinkedListNode<String>("Monika"));
//list.print();
//list.remove();
//LinkedListStrings subject;
