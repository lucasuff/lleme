package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.src;

import java.text.ParseException;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.src.model.Atendimento;

public class HashTable {

    private static int INITIAL_SIZE = 371;
    private EntryList[] entries = new EntryList[INITIAL_SIZE];

    public HashTable() {
        for (int i = 0; i < entries.length; i++)
            entries[i] = new EntryList(i);
    }

    public void put(String key, Atendimento value) throws ParseException {
        int hash = 0;
        if (entries[0] != null)
            hash = getHash(key);
        final EntryListNode hashEntry = new EntryListNode(value);
        if (hash > entries.length) {
            System.out.println("DEBUG EXPANSION: Hash " + hash);
            expandHashTable();
        }
        entries[hash].add(hashEntry);
    }

    /**
     * Returns 'null' if the element is not found.
     *
     * @param key
     * @return
     */
    public Atendimento[] get(String key) {
        int hash = getHash(key);
        if (entries[hash] != null)
            return entries[hash].toArray();
        return null;
    }

    public int getHash(String key) {
        // Actually date from key
        int day = Integer.parseInt(String.valueOf(key.charAt(8))
                + String.valueOf(key.charAt(9)));
        int month = Integer.parseInt(String.valueOf(key.charAt(5))
                + String.valueOf(key.charAt(6)));
        int year = Integer.parseInt(String.valueOf(key.charAt(0)
                + String.valueOf(key.charAt(1))
                + String.valueOf(key.charAt(2))
                + String.valueOf(key.charAt(3))));

        //Hash Function
        return ((month - 1) * 31 + (day - 1) + ((year - 2018) * INITIAL_SIZE));
    }

    private void expandHashTable() {
        int newSize = ((entries.length / INITIAL_SIZE) + 1) * INITIAL_SIZE;
        EntryList[] newHashTable = new EntryList[newSize];
        System.arraycopy(entries, 0, newHashTable, 0, entries.length);
        entries = newHashTable;
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        hashTableStr.append("HASH TABLE:");
        for (EntryList entry : entries) {
            hashTableStr.append("\n\tbucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
        }
        return hashTableStr.toString();
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    public static class EntryList {

        int slotNumber;
        EntryListNode first;
        int size = 0;

        public EntryList(int slotNumber) {
            this.slotNumber = slotNumber;
        }

        public void add(EntryListNode e) {
            if (first == null)
                //Insere primeiro
                first = e;
            else {
                //Insere no final
                EntryListNode actual = first;
                while (actual.next != null)
                    actual = actual.next;
                actual.next = e;
            }
            size++;
        }

        public int size() {
            return size;
        }

        public Atendimento[] toArray() {
            Atendimento[] aux = new Atendimento[size];
            int i = 0;
            if (size > 0) {
                EntryListNode actual = first;
                do {
                    aux[i] = actual.getValue();
                    actual = actual.next;
                    i++;
                } while (actual != null);
            }
            return aux;
        }

        @Override
        public String toString() {
            if (size > 0) {
                int i = 0;
                EntryListNode actual = first;
                String s = "";
                while (actual.next != null) {
                    Atendimento a = actual.getValue();
                    s += "[" + a.toShortString() + "] -> ";
                    actual = actual.next;
                    i++;
                }
                return s;
            }
            return "Empty";
        }
    }

    public static class EntryListNode {

        Atendimento value;
        EntryListNode next;

        public EntryListNode(Atendimento value) {
            this.value = value;
        }

        public Atendimento getValue() {
            return value;
        }

        public void setValue(Atendimento value) {
            this.value = value;
        }

        public EntryListNode getNext() {
            return next;
        }

        public void setNext(EntryListNode next) {
            this.next = next;
        }
    }
}
