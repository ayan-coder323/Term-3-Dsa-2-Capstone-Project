package modules.sorting;

import java.util.ArrayList;
import models.Document;

public class MergeSort implements Sorting {

    @Override
    public ArrayList<Document> sort(ArrayList<Document> documents) {
        return sortById(documents);
    }

    public static ArrayList<Document> sortById(
            ArrayList<Document> documents) {

        ArrayList<Document> copy =
                new ArrayList<>(documents);

        mergeSort(copy, 0, copy.size() - 1);

        return copy;
    }

    static void mergeSort(
            ArrayList<Document> docs,
            int left,
            int right) {

        if(left >= right)
            return;

        int mid = (left + right) / 2;

        mergeSort(docs, left, mid);
        mergeSort(docs, mid + 1, right);
        merge(docs, left, mid, right);
    }

    static void merge(
            ArrayList<Document> docs,
            int left,
            int mid,
            int right) {

        ArrayList<Document> temp =
                new ArrayList<>();

        int i = left;
        int j = mid + 1;

        while(i <= mid && j <= right) {

            if(docs.get(i).documentId <=
               docs.get(j).documentId)
                temp.add(docs.get(i++));
            else
                temp.add(docs.get(j++));
        }

        while(i <= mid)
            temp.add(docs.get(i++));

        while(j <= right)
            temp.add(docs.get(j++));

        for(i=0;i<temp.size();i++)
            docs.set(left + i, temp.get(i));
    }
}
