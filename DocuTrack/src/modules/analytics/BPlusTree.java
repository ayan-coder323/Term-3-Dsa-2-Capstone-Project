package modules.analytics;

import java.util.ArrayList;

public class BPlusTree {

    ArrayList<Integer> data;

   public BPlusTree() {

        data = new ArrayList<>();
    }

    public void insert(int key) {

        data.add(key);
    }

   public void rangeSearch(
            int start,
            int end) {

        System.out.println(
                "Range Result:");

        for(Integer x : data) {

            if(x >= start &&
               x <= end) {

                System.out.print(
                        x + " ");
            }
        }

        System.out.println();
    }
}
