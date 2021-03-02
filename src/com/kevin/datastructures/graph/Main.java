package com.kevin.datastructures.graph;

public class Main {
    public static void main(String[] args) {

//        Graph<String, Integer> graph = new ListGraph<>();
//
//        graph.addEdge("V1", "V0", 9);
//        graph.addEdge("V1", "V2", 3);
//        graph.addEdge("V2", "V0", 2);
//        graph.addEdge("V2", "V3", 5);
//        graph.addEdge("V3", "V4", 1);
//        graph.addEdge("V0", "V4", 6);
//
//        graph.bfs("V1");

//        test();

//        testBfs();
        testDfs();
    }

    static void testBfs() {
        Graph<Object, Double> graph = directedGraph(Data.BFS_02);
        graph.bfs(5, o -> {
            System.out.println(o);
            return false;
        });
    }

    static void testDfs() {
        Graph<Object, Double> graph = directedGraph(Data.DFS_02);
//        graph.dfs("a");//a b e f c
        graph.dfs("c", o -> {
            System.out.println(o);//c b e f
            return false;
        });
    }

    static void test() {
        ListGraph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("V0", "V1");
        graph.addEdge("V1", "V0");

        graph.addEdge("V0", "V2");
        graph.addEdge("V2", "V0");

        graph.addEdge("V0", "V3");
        graph.addEdge("V3", "V0");

        graph.addEdge("V1", "V2");
        graph.addEdge("V2", "V1");

        graph.addEdge("V2", "V3");
        graph.addEdge("V3", "V2");


//		graph.removeEdge("V0", "V4");

        graph.print();

        graph.removeVertex("V0");
        graph.removeVertex("V1");
//        graph.removeVertex("V2");

        graph.print();
    }

    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
        for (int i = 0; i < data.length; i++) {
            Object[] datum = data[i];
            if (datum.length == 1) {
                graph.addVertex(datum[0]);
            } else if (datum.length == 2) {
                graph.addEdge(datum[0], datum[1]);
            } else if (datum.length == 3) {
                double weight = Double.parseDouble(datum[2].toString());
                graph.addEdge(datum[0], datum[1], weight);
            }
        }

        return graph;
    }

    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
        for (int i = 0; i < data.length; i++) {
            Object[] datum = data[i];
            if (datum.length == 1) {
                graph.addVertex(datum[0]);
            } else if (datum.length == 2) {
                graph.addEdge(datum[0], datum[1]);
                graph.addEdge(datum[1], datum[0]);
            } else if (datum.length == 3) {
                double weight = Double.parseDouble(datum[2].toString());
                graph.addEdge(datum[0], datum[1], weight);
                graph.addEdge(datum[1], datum[0], weight);
            }
        }

        return graph;
    }
}
