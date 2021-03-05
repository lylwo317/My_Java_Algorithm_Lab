package com.kevin.datastructures.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Graph<V, W> {
    public abstract void addVertex(V v);
    public abstract void removeVertex(V v);

    public abstract void addEdge(V from, V to);
    public abstract void addEdge(V from, V to, W weight);

    public abstract void removeEdge(V from, V to);

    public abstract int verticesSize();
    public abstract int edgesSize();

    public abstract void bfs(V begin, VertexVisitor<V> visitor);
    public abstract void dfs(V begin, VertexVisitor<V> visitor);

    /**
     * 拓扑排序
     * @return
     */
    public abstract List<V> topologicalSorting();

    /**
     * 最小生成树
     * @return
     */
    public abstract Set<EdgeInfo<V, W>> minimumSpanningTree();

    public abstract Map<V, W> shortestPath(V begin);

    protected WeightManager<W> weightManager = null;

    public void setWeightManager(WeightManager<W> weightManager){
        this.weightManager = weightManager;
    }


    interface WeightManager<W> {
        int compare(W w1, W w2);
        W zero();
        W add(W w1, W w2);
    }


    static final class EdgeInfo<V, W> {
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

    public abstract void print();
}
