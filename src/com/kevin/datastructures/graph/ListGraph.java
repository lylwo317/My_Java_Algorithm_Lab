package com.kevin.datastructures.graph;

import java.util.*;

public class ListGraph<V, W> implements Graph<V, W> {
    private Map<V, Vertex<V, W>> vertexMap = new HashMap<>();
    private Set<Edge<V, W>> edgeSet = new HashSet<>();

    private static class Vertex<V, W> {
        V value;
        Set<Edge<V, W>> outEdges = new HashSet<>();
        Set<Edge<V, W>> inEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?, ?> vertex = (Vertex<?, ?>) o;
            return Objects.equals(value, vertex.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private static class Edge<V, W> {
        W weight;
        Vertex<V, W> from;
        Vertex<V, W> to;

        public Edge(Vertex<V, W> from, Vertex<V, W> to) {
            this(from, to, null);
        }

        public Edge(Vertex<V, W> from, Vertex<V, W> to, W weight) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return Objects.equals(from, edge.from) &&
                    Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "weight=" + weight +
                    ", from=" + from +
                    ", to=" + to +
                    '}';
        }
    }

    @Override

    public void addVertex(V v) {
        Vertex<V, W> vertex = vertexMap.get(v);
        if (vertex == null) {
            vertexMap.put(v, new Vertex<>(v));
        } else {
            vertexMap.put(v, vertex);
        }
    }

    @Override
    public void removeVertex(V v) {
        Vertex<V, W> vertex = vertexMap.remove(v);
        if (vertex == null) {
            return;
        }

        for (Iterator<Edge<V, W>> iterator = vertex.outEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, W> edge = iterator.next();
            edge.to.inEdges.remove(edge);
            iterator.remove();//edge.from.outEdges.remove(edge);
            edgeSet.remove(edge);
        }

        for (Iterator<Edge<V, W>> iterator = vertex.inEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, W> edge = iterator.next();
            edge.from.outEdges.remove(edge);
            iterator.remove();
            edgeSet.remove(edge);
        }
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, W weight) {
        Vertex<V, W> fromVertex = vertexMap.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertexMap.put(from, fromVertex);
        }

        Vertex<V, W> toVertex = vertexMap.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertexMap.put(to, toVertex);
        }

        Edge<V, W> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edgeSet.remove(edge);
        }

        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edgeSet.add(edge);
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, W> fromVertex = vertexMap.get(from);
        if (fromVertex == null) {
            return;
        }

        Vertex<V, W> toVertex = vertexMap.get(to);
        if (toVertex == null) {
            return;
        }

        Edge<V, W> edge = new Edge<>(fromVertex, toVertex);
        fromVertex.outEdges.remove(edge);
        toVertex.inEdges.remove(edge);
        edgeSet.remove(edge);
    }

    @Override
    public int verticesSize() {
        return vertexMap.size();
    }

    @Override
    public int edgesSize() {
        return edgeSet.size();
    }

    @Override
    public void print() {
        System.out.println("[顶点]-------------------\n");
        vertexMap.forEach((V v, Vertex<V, W> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
            System.out.println();
        });

        System.out.println("[边]-------------------");
        edgeSet.forEach((Edge<V, W> edge) -> {
            System.out.println(edge);
        });

//        System.out.println(this);
    }

    @Override
    public String toString() {
        String s = "";
        for (Iterator<Map.Entry<V, Vertex<V, W>>> iterator = vertexMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<V, Vertex<V, W>> next = iterator.next();
            s += next.getKey();
        }
        return "ListGraph{" +
                "vertexMap=" + vertexMap +
                ", edgeSet=" + edgeSet +
                '}';
    }
}
