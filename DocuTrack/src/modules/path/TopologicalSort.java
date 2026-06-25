package modules.path;

import java.util.ArrayList;
import java.util.Stack;

public class TopologicalSort {

    public static void sort(
            int cost[][],
            String names[]) {

        int n = cost.length;
        boolean visited[] = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for(int i=0;i<n;i++) {

            if(!visited[i])
                dfs(i, cost, visited, stack);
        }

        System.out.println("Topological Order:");

        while(!stack.isEmpty()) {

            int vertex = stack.pop();
            System.out.print(vertex + "(" + names[vertex] + ") ");
        }

        System.out.println();
    }

    static void dfs(
            int node,
            int cost[][],
            boolean visited[],
            Stack<Integer> stack) {

        visited[node] = true;

        for(Integer next : neighbours(node, cost)) {

            if(!visited[next])
                dfs(next, cost, visited, stack);
        }

        stack.push(node);
    }

    static ArrayList<Integer> neighbours(
            int node,
            int cost[][]) {

        ArrayList<Integer> list =
                new ArrayList<>();

        for(int i=0;i<cost.length;i++) {

            if(cost[node][i] != Dijkstra.INF &&
               cost[node][i] != 0) {

                list.add(i);
            }
        }

        return list;
    }
}
