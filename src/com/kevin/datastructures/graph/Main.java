package com.kevin.datastructures.graph;

public class Main {
    public static void main(String[] args) {
/*        Graph<String, Integer> graph = new ListGraph<>();

        graph.addEdge("v0", "v1");
        graph.addEdge("v1", "v2");
        graph.addEdge("v2", "v1");

        graph.print();*/
        test();
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
}
