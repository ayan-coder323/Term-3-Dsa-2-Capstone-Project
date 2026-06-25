package modules.graph;

import java.util.*;
import models.Document;

public class Graph {

    int vertices;
    ArrayList<Integer>[] adj;

    public Graph(int vertices) {

        this.vertices = vertices;

        adj = new ArrayList[vertices];

        for(int i=0;i<vertices;i++) {

            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source,
                        int destination) {

        if(source < 0 || source >= vertices ||
           destination < 0 || destination >= vertices) {

            System.out.println("Invalid document number");
            return;
        }

        adj[source].add(destination);

        adj[destination].add(source);
    }

    public void resize(int newVertices) {

        ArrayList<Integer>[] newAdj =
                new ArrayList[newVertices];

        for(int i=0;i<newVertices;i++) {

            newAdj[i] = new ArrayList<>();
        }

        int limit =
                Math.min(vertices, newVertices);

        for(int i=0;i<limit;i++) {

            for(Integer neighbour : adj[i]) {

                if(neighbour < newVertices) {

                    newAdj[i].add(neighbour);
                }
            }
        }

        vertices = newVertices;
        adj = newAdj;
    }

    public void removeVertex(int removedVertex) {

        if(removedVertex < 0 || removedVertex >= vertices)
            return;

        ArrayList<Integer>[] newAdj =
                new ArrayList[vertices - 1];

        for(int i=0;i<vertices-1;i++) {

            newAdj[i] = new ArrayList<>();
        }

        for(int oldVertex=0;oldVertex<vertices;oldVertex++) {

            if(oldVertex == removedVertex)
                continue;

            int newVertex =
                    oldVertex > removedVertex ?
                    oldVertex - 1 :
                    oldVertex;

            for(Integer oldNeighbour : adj[oldVertex]) {

                if(oldNeighbour == removedVertex)
                    continue;

                int newNeighbour =
                        oldNeighbour > removedVertex ?
                        oldNeighbour - 1 :
                        oldNeighbour;

                newAdj[newVertex].add(newNeighbour);
            }
        }

        vertices--;
        adj = newAdj;
    }

    public void display() {

        for(int i=0;i<vertices;i++) {

            System.out.print(i + " -> ");

            for(Integer x : adj[i]) {

                System.out.print(x + " ");
            }

            System.out.println();
        }
    }

    public void displayDocuments(List<Document> documents) {

        for(int i=0;i<vertices;i++) {

            System.out.print(i + " (" +
                    documents.get(i).title + ") -> ");

            for(Integer x : adj[i]) {

                System.out.print(x + " (" +
                        documents.get(x).title + ") ");
            }

            System.out.println();
        }
    }
}
