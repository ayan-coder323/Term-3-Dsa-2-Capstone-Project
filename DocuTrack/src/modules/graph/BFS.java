package modules.graph;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    public static void bfs(Graph g,
                           int start) {

        boolean visited[] =
                new boolean[g.vertices];

        Queue<Integer> queue =
                new LinkedList<>();

        visited[start] = true;

        queue.add(start);

        while(!queue.isEmpty()) {

            int node = queue.poll();

            System.out.print(node + " ");

            for(Integer neighbour :
                    g.adj[node]) {

                if(!visited[neighbour]) {

                    visited[neighbour] = true;

                    queue.add(neighbour);
                }
            }
        }

        System.out.println();
    }
}
