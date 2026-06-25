package modules.graph;

public class DFS {

    public static void dfs(Graph g, int node, boolean visited[]) {

        visited[node] = true;

        System.out.print(node + " ");

        for(Integer neighbour :
                g.adj[node]) {

            if(!visited[neighbour]) {

                dfs(g, neighbour, visited);
            }
        }
    }
}