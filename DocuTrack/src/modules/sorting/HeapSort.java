package modules.sorting;

import java.util.ArrayList;
import models.Document;

public class HeapSort implements Sorting {

    @Override
    public ArrayList<Document> sort(ArrayList<Document> documents) {
        return sortByAuthor(documents);
    }

    public static ArrayList<Document> sortByAuthor(
            ArrayList<Document> documents) {

        ArrayList<Document> copy =
                new ArrayList<>(documents);

        int n = copy.size();

        for(int i=n/2-1;i>=0;i--)
            heapify(copy, n, i);

        for(int i=n-1;i>0;i--) {

            swap(copy, 0, i);
            heapify(copy, i, 0);
        }

        return copy;
    }

    static void heapify(
            ArrayList<Document> docs,
            int n,
            int i) {

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if(left < n &&
           docs.get(left).author.compareToIgnoreCase(
                   docs.get(largest).author) > 0)
            largest = left;

        if(right < n &&
           docs.get(right).author.compareToIgnoreCase(
                   docs.get(largest).author) > 0)
            largest = right;

        if(largest != i) {

            swap(docs, i, largest);
            heapify(docs, n, largest);
        }
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
