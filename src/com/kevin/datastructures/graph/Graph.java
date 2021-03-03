package com.kevin.datastructures.graph;

import java.util.List;
import java.util.Set;

public interface Graph<V, W> {
    void addVertex(V v);
    void removeVertex(V v);

    void addEdge(V from, V to);
    void addEdge(V from, V to, W weight);

    void removeEdge(V from, V to);

    int verticesSize();
    int edgesSize();

    void bfs(V begin, VertexVisitor<V> visitor);
    void dfs(V begin, VertexVisitor<V> visitor);

    /**
     * 拓扑排序
     * @return
     */
    List<V> topologicalSorting();

    /**
     * 最小生成树
     * @return
     */
    Set<EdgeInfo<V, W>> minimumSpanningTree();

    final class EdgeInfo<V, W> {
        private V from;
        private V to;
        private W weight;
        public EdgeInfo(V from, V to, W weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        public V getFrom() {
            return from;
        }
        public void setFrom(V from) {
            this.from = from;
        }
        public V getTo() {
            return to;
        }
        public void setTo(V to) {
            this.to = to;
        }
        public W getWeight() {
            return weight;
        }
        public void setWeight(W weight) {
            this.weight = weight;
        }
        @Override
        public String toString() {
            return "EdgeInfo [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }


    interface VertexVisitor<V>{
        boolean visit(V v);
    }

    void print();
}
