package modules.optimization;

public class LIS {

    public static void display(int values[]) {

        if(values.length == 0) {

            System.out.println("No documents available");
            return;
        }

        int n = values.length;
        int dp[] = new int[n];
        int parent[] = new int[n];
        int best = 0;

        for(int i=0;i<n;i++) {

            dp[i] = 1;
            parent[i] = -1;

            for(int j=0;j<i;j++) {

                if(values[j] < values[i] &&
                   dp[j] + 1 > dp[i]) {

                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if(dp[i] > dp[best])
                best = i;
        }

        System.out.println("LIS Length: " +
                dp[best]);

        System.out.print("Increasing Document ID Sequence: ");
        printSequence(values, parent, best);
        System.out.println();
    }

    static void printSequence(
            int values[],
            int parent[],
            int index) {

        if(index == -1)
            return;

        printSequence(values, parent, parent[index]);
        System.out.print(values[index] + " ");
    }
}
