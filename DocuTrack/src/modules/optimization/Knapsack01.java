package modules.optimization;

import models.Document;

public class Knapsack01 {

    public static void optimize(
            Document documents[],
            int value[],
            int weight[],
            int capacity) {

        int n = documents.length;
        int dp[][] = new int[n + 1][capacity + 1];

        for(int i=1;i<=n;i++) {

            for(int w=0;w<=capacity;w++) {

                dp[i][w] = dp[i - 1][w];

                if(weight[i - 1] <= w) {

                    int include =
                            value[i - 1] +
                            dp[i - 1][w - weight[i - 1]];

                    if(include > dp[i][w])
                        dp[i][w] = include;
                }
            }
        }

        System.out.println("0/1 Knapsack Value: " +
                dp[n][capacity]);

        System.out.println("Selected Documents:");

        int w = capacity;

        for(int i=n;i>0;i--) {

            if(dp[i][w] != dp[i - 1][w]) {

                System.out.println(documents[i - 1]);
                w -= weight[i - 1];
            }
        }
    }
}
