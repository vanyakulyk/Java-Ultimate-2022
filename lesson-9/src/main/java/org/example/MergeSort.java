package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MergeSort<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {

    private final List<T> list;

    public MergeSort(List<T> list) {
        this.list = list;
    }

    @Override
    protected List<T> compute() {
        if (list.size() <= 1) {
            return list;
        }

        var middle = list.size() / 2;
        var left = new MergeSort<>(new ArrayList<>(list.subList(0, middle)));
        var right = new MergeSort<>(new ArrayList<>(list.subList(middle, list.size())));
        right.fork();

        return merge(left.compute(), right.join());
    }

    private List<T> merge(List<T> leftPart, List<T> rightPart) {
        int right = 0, left = 0, i = 0;
        while (left < leftPart.size() && right < rightPart.size()) {
            if (leftPart.get(left).compareTo(rightPart.get(right)) < 0) {
                list.set(i++, leftPart.get(left++));
            } else {
                list.set(i++, rightPart.get(right++));
            }

        }

        while (left < leftPart.size()) {
            list.set(i++, leftPart.get(left++));
        }

        while (right < rightPart.size()) {
            list.set(i++, rightPart.get(right++));
        }

        return list;
    }
}
