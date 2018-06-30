package clientWaitingRoom;

public class HashTable {

    private static int INITIAL_SIZE = 100;
    private HashEntry[] entries = new HashEntry[INITIAL_SIZE];
    LinkedListStrings myUsedKeys = new LinkedListStrings();

    public void put(String key, String description, Double duration) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, description, duration);
        if (entries[hash] == null) {
            entries[hash] = hashEntry;

            // Armazena CHAVES utlizadas na hash
            myUsedKeys.push(key);
        } // Se já houver uma entrada no hash atual
        // adiciona entrada à lista encadeada.
        else {
            HashEntry temp = entries[hash];
            while (temp.next != null)
                temp = temp.next;
            temp.next = hashEntry;
        }
    }

    // Retorna 'null' se o elemento não foi encontrado, c.c retorna a "descrição do assunto" com chave "key"
    public String get(String key) {
        int hash = getHash(key);
        if (entries[hash] != null) {
            HashEntry temp = entries[hash];

            // Checa a entrada na lista encadeada
            // for the given 'key'
            while (!temp.key.equals(key)
                    && temp.next != null)
                temp = temp.next;
            return temp.description;
        }

        return null;
    }

    //Retorna 'null' se o elemento não foi encontrado, c.c retorna a "duração do assunto" com chave "key"
    public double getDuration(String key) {
        int hash = getHash(key);
        if (entries[hash] != null) {
            HashEntry temp = entries[hash];

            // Checar a entrada na lista encadeada para obter a "key"
            while (!temp.key.equals(key)
                    && temp.next != null)
                temp = temp.next;
            return temp.duration;
        }

        return -1;
    }

    private int getHash(String key) {
        // Implementação do hashcode.
        return key.hashCode() % INITIAL_SIZE;
    }

    public static class HashEntry {

        String key;					// Armazena o tipo(key) do serviço
        String description;			// Armazena a Descrição deste serviço
        double duration;			// Armazena a duração do serviço

        // Lista encadeada com as mesmas entradas de hash
        HashEntry next;

        // key (tipo de assunto)    description(descrição do assunto)		duração do assunto
        public HashEntry(String key, String description, double duration) {
            this.key = key;
            this.description = description;
            this.duration = duration;

            this.next = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + description + ", " + duration + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if (entry == null)
                continue;
            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while (temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    public int size() {
        return INITIAL_SIZE;
    }

    public double getTime(String key) {
        int hash = getHash(key);
        if (entries[hash] != null) {
            HashEntry temp = entries[hash];
            double value = 0;
            int qtd = 0;
            // Checar a entrada na lista encadeada para obter a "key"
            while (temp != null) {
                if (temp.key.equals(key)) {
                    value += temp.duration;
                    qtd++;
                }
                temp = temp.next;
            }
            return value / qtd;
        }

        return 0.0;
    }

    public LinkedListStrings getUsedKeys() {
        return myUsedKeys;
    }

    public void resetIT() {
        myUsedKeys.defaultIT();
    }
}
