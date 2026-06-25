package modules.path;

public class FloydWarshall {

    public static void allPairsShortestPath(
            int cost[][],
            String names[]) {

        int n = cost.length;
        int distance[][] = new int[n][n];

        for(int i=0;i<n;i++) {

            for(int j=0;j<n;j++) {

                distance[i][j] = cost[i][j];
            }
        }

        for(int k=0;k<n;k++) {

            for(int i=0;i<n;i++) {

                for(int j=0;j<n;j++) {

                    if(distance[i][k] != Dijkstra.INF &&
                       distance[k][j] != Dijkstra.INF &&
                       distance[i][k] + distance[k][j] < distance[i][j]) {

                        distance[i][j] =
                                distance[i][k] + distance[k][j];
                    }
                }
            }
        }

        System.out.println("Floyd-Warshall Matrix:");

        for(int i=0;i<n;i++) {

            System.out.print(i + "(" + names[i] + ") -> ");

            for(int j=0;j<n;j++) {

                if(distance[i][j] == Dijkstra.INF)
                    System.out.print("INF ");
                else
                    System.out.print(distance[i][j] + " ");
            }

            System.out.println();
        }
    }
}
