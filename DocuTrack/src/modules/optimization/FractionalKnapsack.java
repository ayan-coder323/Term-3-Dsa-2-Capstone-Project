package modules.optimization;

import models.Document;

public class FractionalKnapsack {

    public static void optimize(
            Document documents[],
            int value[],
            int weight[],
            int capacity) {

        boolean used[] =
                new boolean[documents.length];

        double totalValue = 0;

        System.out.println("Fractional Knapsack Selection:");

        while(capacity > 0) {

            int best = -1;

            for(int i=0;i<documents.length;i++) {

                if(!used[i] &&
                   weight[i] > 0 &&
                   (best == -1 ||
                    value[i] * weight[best] >
                    value[best] * weight[i])) {

                    best = i;
                }
            }

            if(best == -1)
                break;

            used[best] = true;

            if(weight[best] <= capacity) {

                capacity -= weight[best];
                totalValue += value[best];

                System.out.println("Full: " +
                        documents[best]);
            }
            else {

                double fraction =
                        capacity * 1.0 / weight[best];

                totalValue += value[best] * fraction;

                System.out.printf(
                        "Partial %.2f: %s\n",
                        fraction,
                        documents[best]);

                capacity = 0;
            }
        }

        System.out.printf("Total Value: %.2f\n",
                totalValue);
    }
}
