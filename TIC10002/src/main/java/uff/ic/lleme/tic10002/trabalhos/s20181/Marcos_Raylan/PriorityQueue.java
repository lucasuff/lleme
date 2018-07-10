package uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan;

import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.ClientInfo;

/*Name: Marcos Raylan Sousa Matos
 Class: PriorityQueue
 Due Date: 10/05/2018
 Date Submitted: 14/06/2018
 */
public class PriorityQueue {

    private int heapSize;
    private ClientInfo[] myHeap = null;

    //constructor
    public PriorityQueue(int size) {
        myHeap = new ClientInfo[size];
        heapSize = size;
    }

    //getter
    public int getSize() {
        return heapSize;
    }

    public String getClientName(ClientInfo[] arr, int index) {
        return arr[index].getName();
    }

    public double getClientPriority(ClientInfo[] arr, int index) {
        return arr[index].getPriority();
    }

    public int getParent(int child) {
        return (child - 1) / 2;
    }

    //setter
    public void setSize(int size) {
        myHeap = new ClientInfo[size];
        heapSize = size;
    }

    //methods
    public void insert(ClientInfo info) {

    }

    public void swap(int index1, int index2, ClientInfo[] arr) {
        ClientInfo temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public void MaxHeapify(ClientInfo[] array, int i) {

        int largest = 0;

        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left <= heapSize && array[left].getPriority() > array[i].getPriority())
            largest = left;
        else
            largest = i;

        if (right <= heapSize && array[right].getPriority() > array[largest].getPriority())
            largest = right;
        if (largest != i) {
            swap(i, largest, array);
            MaxHeapify(array, largest);
        }

    }//end MaxHeapify

    public void BuildMaxHeap(ClientInfo[] array) {
        heapSize = array.length - 1;

        for (int i = (array.length / 2) - 1; i >= 0; i--)
            MaxHeapify(array, i);

    }//end BuildMaxHeap

    public void HeapSort(ClientInfo[] array) {

        BuildMaxHeap(array);
        for (int i = array.length - 1; i >= 0; i--) {
            //swap
            swap(0, i, array);
            heapSize = heapSize - 1;
            MaxHeapify(array, 0);
        }
        reverse(array);
        //Collections.reverse(Arrays.asList(array));
    }//end HeapSort

    public void MaxHeapInsert(ClientInfo array[], ClientInfo key) {

        heapSize = heapSize + 1;

        double ninf = Double.NEGATIVE_INFINITY;
        array[heapSize].setPriority(ninf);

        HeapIncreaseKey(array, heapSize, key);

    }//end MaxHeapInsert

    public void HeapIncreaseKey(ClientInfo[] array, int i, ClientInfo key) {

        if (key.getPriority() < array[i].getPriority())
            System.out.printf("Error. New key is smaller than current key.\n");

        array[i].setPriority(key.getPriority());
        array[i].setName(key.getName());

        //Realiza subida do elemento enquanto for maior que o pai do elemento corrente
        while (i > 0 && array[getParent(i)].getPriority() < array[i].getPriority()) {
            swap(i, getParent(i), array);
            i = getParent(i);
        }

    }//end HeapIncreaseKey

    public ClientInfo HeapExtractMax(ClientInfo[] array) {

        heapSize = array.length - 1;
        if (heapSize < 0)
            System.out.printf("Error. Heap is underflown\n");
        ClientInfo max = new ClientInfo();

        max.setName(array[0].getName());
        max.setPriority(array[0].getPriority());

        array[0].setName(array[heapSize].getName());
        array[0].setPriority(array[heapSize].getPriority());

        --heapSize;
        MaxHeapify(array, 0);

        return max;

    }//end HeapExtractMax*

    public ClientInfo HeapMaximum(ClientInfo[] array) {
        return array[0];

    }//end HeapMaximum

    public void printHeap(ClientInfo[] array) {

        //if(heapSize > 0) {
        for (int i = 0; i < array.length; i++) {
            System.out.print((i + 1) + ". ");
            array[i].print();
        }
        System.out.printf("\n");
        //}
    }//end printHeap

    public void reverse(ClientInfo[] t) {
        for (int x = 0; x < t.length / 2; x++) {
            ClientInfo y = t[x];
            t[x] = t[t.length - 1 - x];
            t[t.length - 1 - x] = y;
        }
    }
}// end Priority Queue
