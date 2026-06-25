package modules.path;

public class Dijkstra extends PathFinder {

    public static void shortestPath(
            int cost[][],
            int source,
            int destination,
            String names[]) {

        int n = cost.length;
        int distance[] = new int[n];
        int parent[] = new int[n];
        boolean visited[] = new boolean[n];

        for(int i=0;i<n;i++) {

            distance[i] = INF;
            parent[i] = -1;
        }

        distance[source] = 0;

        for(int count=0;count<n-1;count++) {

            int current =
                    minDistance(distance, visited);

            if(current == -1)
                break;

            visited[current] = true;

            for(int next=0;next<n;next++) {

                if(!visited[next] &&
                   cost[current][next] != INF &&
                   distance[current] + cost[current][next] < distance[next]) {

                    distance[next] =
                            distance[current] + cost[current][next];

                    parent[next] = current;
                }
            }
        }

        if(distance[destination] == INF) {

            System.out.println("No optimized path available");
            return;
        }

        System.out.println("Minimum Cost: " +
                distance[destination]);

        System.out.print("Optimized Path: ");
        printPath(destination, parent, names);
        System.out.println();
    }

    static int minDistance(
            int distance[],
            boolean visited[]) {

        int min = INF;
        int index = -1;

        for(int i=0;i<distance.length;i++) {

            if(!visited[i] && distance[i] < min) {

                min = distance[i];
                index = i;
            }
        }

        return index;
    }

    static void printPath(
            int node,
            int parent[],
            String names[]) {

        if(parent[node] != -1) {

            printPath(parent[node], parent, names);
            System.out.print(" -> ");
        }

        System.out.print(node + "(" + names[node] + ")");
    }
}
