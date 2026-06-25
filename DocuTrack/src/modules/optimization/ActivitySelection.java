package modules.optimization;

import models.Document;

public class ActivitySelection {

    public static void select(
            Document documents[],
            int start[],
            int finish[]) {

        boolean selected[] =
                new boolean[documents.length];

        for(int i=0;i<documents.length;i++) {

            int min = -1;

            for(int j=0;j<documents.length;j++) {

                if(!selected[j] &&
                   (min == -1 || finish[j] < finish[min])) {

                    min = j;
                }
            }

            selected[min] = true;
        }

        int lastFinish = -1;

        System.out.println("Selected Document Activities:");

        for(int i=0;i<documents.length;i++) {

            int next = earliestUnprinted(selected, finish, lastFinish);

            if(next == -1)
                break;

            selected[next] = false;

            if(start[next] >= lastFinish) {

                System.out.println(documents[next] +
                        " | Start: " + start[next] +
                        ", Finish: " + finish[next]);

                lastFinish = finish[next];
            }
        }
    }

    static int earliestUnprinted(
            boolean selected[],
            int finish[],
            int lastFinish) {

        int index = -1;

        for(int i=0;i<selected.length;i++) {

            if(selected[i] &&
               finish[i] >= lastFinish &&
               (index == -1 || finish[i] < finish[index])) {

                index = i;
            }
        }

        return index;
    }
}
