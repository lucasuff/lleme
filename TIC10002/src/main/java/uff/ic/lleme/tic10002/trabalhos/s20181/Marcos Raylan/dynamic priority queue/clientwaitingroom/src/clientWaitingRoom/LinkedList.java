package clientWaitingRoom;

public class LinkedList<T> {

    private LinkedListNode<T> first = null;
    private int size = 0;

    /**
     * Insere o elemento na frente da lista
     *
     * @param node
     */
    public void insert(LinkedListNode<T> node) {
        node.setNext(first);
        first = node;
        size++;
    }

    /**
     * Remove o elemento da frente da lista
     *
     * @param node
     */
    public void remove() {
        if (first.getNext() != null)
            first = first.getNext();
        else
            first = null;
        size--;
    }

    /**
     * Percorre recurssivamente a lista e mostra o conteúdo do nó
     *
     * @param node
     */
    private void printList(LinkedListNode<T> node) {
        System.out.println("Node is " + node.getValue());
        if (node.getNext() != null)
            printList(node.getNext());
    }

    public void print() {
        printList(first);
    }

    /**
     * Retorna o tamanho da lista encadeada
     *
     * @param void
     */
    public int size() {
        return size;
    }

    class LinkedListNode<T> {

        private T value;
        private LinkedListNode<T> next;

        public LinkedListNode(T value) {
            this.value = value;
        }

        public void setNext(LinkedListNode<T> next) {
            this.next = next;
        }

        public LinkedListNode<T> getNext() {
            return next;
        }

        public T getValue() {
            return value;
        }
    }
}
