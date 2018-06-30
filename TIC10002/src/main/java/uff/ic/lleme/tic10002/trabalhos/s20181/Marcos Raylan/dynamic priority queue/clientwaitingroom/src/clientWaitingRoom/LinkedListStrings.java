package clientWaitingRoom;

//Lista encadeada usada para armazenar as chaves UTILIZADAS ATÉ O MOMENTO
public class LinkedListStrings {

    Node head;
    Node it;

    class Node {

        String data;
        Node next;

        Node(String d) {
            data = d;
            next = null;
        }
    }

    public void push(String new_data) {
        Node new_node = new Node(new_data);
        new_node.next = head;
        head = new_node;
        it = head;
    }

    public String getNext() {
        if (it == null)
            return null;
        String str = it.data;
        it = it.next;
        return str;
    }

    public void defaultIT() {
        it = head;
    }
}
