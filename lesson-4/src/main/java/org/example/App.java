package org.example;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        HashTable<String, String> hashTable = new HashTable<>();

        hashTable.put("one", "Dima");
        hashTable.put("two", "Masha");
        hashTable.put("three", "Kostya");
        hashTable.put("four", "Rita");

        hashTable.printTable();
    }
}
