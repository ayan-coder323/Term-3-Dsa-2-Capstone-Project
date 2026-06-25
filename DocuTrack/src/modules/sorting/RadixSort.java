package modules.sorting;

import java.util.ArrayList;
import models.Document;

public class RadixSort implements Sorting {

    @Override
    public ArrayList<Document> sort(ArrayList<Document> documents) {
        return sortById(documents);
    }

    public static ArrayList<Document> sortById(
            ArrayList<Document> documents) {

        ArrayList<Document> copy =
                new ArrayList<>(documents);

        if(copy.isEmpty())
            return copy;

        int max = copy.get(0).documentId;

        for(Document doc : copy) {

            if(doc.documentId > max)
                max = doc.documentId;
        }

        for(int exp=1;max/exp>0;exp*=10)
            countSort(copy, exp);

        return copy;
    }

    static void countSort(
            ArrayList<Document> docs,
            int exp) {

        int n = docs.size();
        Document output[] = new Document[n];
        int count[] = new int[10];

        for(int i=0;i<n;i++)
            count[(docs.get(i).documentId / exp) % 10]++;

        for(int i=1;i<10;i++)
            count[i] += count[i - 1];

        for(int i=n-1;i>=0;i--) {

            int digit =
                    (docs.get(i).documentId / exp) % 10;

            output[count[digit] - 1] = docs.get(i);
            count[digit]--;
        }

        for(int i=0;i<n;i++)
            docs.set(i, output[i]);
    }
}
