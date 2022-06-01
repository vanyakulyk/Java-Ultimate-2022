package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        List<Integer> list = Arrays.asList(2, 0, 99, 5, -4, 40, 22, 3, 2, -2, 0, 126, -98, -999, 376);
        MergeSort<Integer> mergeSort = new MergeSort<>(list);
        List<Integer> sorted = mergeSort.compute();
        System.out.println(sorted);
    }
}
