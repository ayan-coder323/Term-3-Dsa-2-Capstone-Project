package modules.sorting;

import java.util.ArrayList;
import models.Document;

public class QuickSort implements Sorting {

    @Override
    public ArrayList<Document> sort(ArrayList<Document> documents) {
        return sortByTitle(documents);
    }

    public static ArrayList<Document> sortByTitle(
            ArrayList<Document> documents) {

        ArrayList<Document> copy =
                new ArrayList<>(documents);

        quickSort(copy, 0, copy.size() - 1);

        return copy;
    }

    static void quickSort(
            ArrayList<Document> docs,
            int low,
            int high) {

        if(low < high) {

            int pivot =
                    partition(docs, low, high);

            quickSort(docs, low, pivot - 1);
            quickSort(docs, pivot + 1, high);
        }
    }

    static int partition(
            ArrayList<Document> docs,
            int low,
            int high) {

        String pivot =
                docs.get(high).title.toLowerCase();

        int i = low - 1;

        for(int j=low;j<high;j++) {

            if(docs.get(j).title.toLowerCase()
                    .compareTo(pivot) <= 0) {

                i++;
                swap(docs, i, j);
            }
        }

        swap(docs, i + 1, high);

        return i + 1;
    }

    static void swap(
            ArrayList<Document> docs,
            int i,
            int j) {

        Document temp = docs.get(i);
        docs.set(i, docs.get(j));
        docs.set(j, temp);
    }
}
