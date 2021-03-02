package com.kevin.datastructures.graph;

public interface Graph<V, W> {
    void addVertex(V v);
    void removeVertex(V v);

    void addEdge(V from, V to);
    void addEdge(V from, V to, W weight);

    void removeEdge(V from, V to);

    int verticesSize();
    int edgesSize();

    void bfs(V begin);
    void dfs(V begin);

    void print();
}
