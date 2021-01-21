package com.kevin.datastructures.map;

import com.kevin.datastructures.tree.printer.BinaryTreeInfo;
import com.kevin.datastructures.tree.printer.BinaryTrees;

import java.util.*;

public class HashMap<K,V> implements Map<K,V>{

    private int size;

    protected static class Node<K, V> {
        int hashCode;
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        public static int RED = 0;
        public static int BLACK = 1;

        private int color = RED;

        public Node(K key, V value, Node<K, V> parent, int hashcode) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hashCode = hashcode;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean isChild() {
            return parent != null;
        }

        public boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isRightChild() {
            return parent != null && parent.right == this;
        }

        public Node<K, V> sibling() {//兄弟
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            return (color == RED? "R_" : "") + "Node_" + key + "_" + value;
        }
    }

    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K,V>[] table;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        if (size == 0){
            return;
        }
        size = 0;
        table = new Node[DEFAULT_CAPACITY];
    }

    private int indexOf(K key) {
        return getHashcode(key) & (table.length - 1);
    }

    private int indexOf(Node<K, V> node) {
        return node.hashCode & (table.length - 1);
    }

    protected int getHashcode(K key) {
        if (key == null) {
            return 0;
        }
        int hashCode = key.hashCode();
        return hashCode ^ (hashCode >>> 16);
    }

    private void resize() {
        //装填因子小于等于0.75
        if ((float)size / table.length <= DEFAULT_LOAD_FACTOR) {
            return;
        }

        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : oldTable) {
            if (kvNode == null) {
                continue;
            }

            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }

                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = Node.RED;

        //取出对应的红黑树
        int index = indexOf(newNode);
        Node<K, V> root = table[index];
        if (root == null) {//是空树，就创建
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }

        //不是空树，就根据大小决定添加到哪个叶子节点上
        Node<K, V> node = root;
        Node<K, V> parent;

        K key1 = newNode.key;
        int h1 = newNode.hashCode;
        int compare;

        do {
            K key2 = node.key;
            int h2 = node.hashCode;

            if (h1 < h2) {
                compare = -1;
            } else if (h1 > h2) {
                compare = 1;
            } else if (key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && (compare = ((Comparable) key1).compareTo(key2)) != 0) {
            } else {
                compare = System.identityHashCode(key1) - System.identityHashCode(key2);
            }

            parent = node;
            if (compare < 0) {
                node = node.left;
            } else if (compare > 0){
                node = node.right;
            }
        } while (node != null);

        newNode.parent = parent;
        if (compare < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        fixAfterPut(newNode);
    }

    /**
     * 思路:
     * 1. 计算hash值
     * 2. 根据hash值定位到对应的数组index(也就是找到对应的红黑树)
     * 3. 往对应的这棵红黑树添加节点(重点难点是处理hash相等的情况)
     * @param key
     * @param value
     * @return oldValue
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public V put(K key, V value) {
        resize();

        //取出对应的红黑树
        int index = indexOf(key);
        Node<K, V> root = table[index];
        if (root == null) {//是空树，就创建
            root = createNode(key, value, null);
            table[index] = root;
            fixAfterPut(root);
            size++;
            return null;
        }

        //不是空树，就根据大小决定添加到哪个叶子节点上
        Node<K, V> node = root;
        Node<K, V> parent;

        K key1 = key;
        int h1 = getHashcode(key1);
        boolean search = false;
        int compare;

        do {//目的: 确定key是否已经存在对应的节点，如果存在，就替换已存在节点的key和value，否则，就确定要添加到哪个叶子节点上。
            K key2 = node.key;
//            int h2 = getHashcode(key2);
            int h2 = node.hashCode;

            if (h1 < h2) {
                compare = -1;//key对应的节点在左子树上
            } else if (h1 > h2) {
                compare = 1;//key对应的节点在右子树上
            } else if (Objects.equals(key1, key2)) {//h1 == h2 && key1.equals(key2)
                compare = 0;//key对应的节点就是当前的node，替换node的key和value就行
            } else if (key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && (compare = ((Comparable) key1).compareTo(key2)) != 0) {
                //key对应的节点在左子树上 || key对应的节点在右子树上

            //以上四种判断可以确定key对应的节点所在方位(左子树，右子树，或者当前的node节点)
            } else {//无法通过以上四种判断确定key对应的节点所在方位
                // 需要先搜索左右子树是否已经含有key对应的节点，如果有，就直接替换该节点的key和value，没有就按照内存地址来决定往左子树还是右子树的叶子节点上放置新节点

                //先搜索左右子树查找有没有这个key对应的节点，如果已经搜索过了，就不需要再搜索了
                if (!search) {
                    Node<K, V> ch, q;
                    search = true;
                    if (((ch = node.left) != null && (q = findNode(ch, key)) != null)
                            || ((ch = node.right) != null && (q = findNode(ch, key)) != null)) {
                        compare = 0;//说明左子树或者右子树上存在key对应的node
                        node = q;
                    } else {
                        //已经搜索过了，没有找到key对应的节点，说明是key对应的是新的节点。就按照内存地址决定是添加到左子树还是右子树的叶子节点上
                        compare = System.identityHashCode(key1) - System.identityHashCode(key2);
                    }
                } else {
                    //已经搜索过了，没有找到key对应的节点，说明是key对应的是新的节点。就按照内存地址决定是添加到左子树还是右子树的叶子节点上
                    compare = System.identityHashCode(key1) - System.identityHashCode(key2);
                }
            }

            parent = node;
            if (compare < 0) {
                node = node.left;
            } else if (compare > 0) {//compare > 0
                node = node.right;
            } else {//compare == 0;
                V oldValue = node.value;
                node.key = key;
                node.hashCode = getHashcode(key);
                node.value = value;
                return oldValue;
            }
        } while (node != null);

        Node<K, V> newNode = createNode(key, value, parent);
        if (compare < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        fixAfterPut(newNode);
        size++;
        return null;
    }

    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> currentNode;
        do {
            currentNode = node;
            if (node.parent == null) {//添加到根节点，或者上溢到根节点
//                root = node;
                black(node);
            } else {//非根节点
                if (isRed(node.parent)) {//parent 是红色
                    Node<K, V> grand = node.parent.parent;
                    Node<K, V> parent = node.parent;
                    if (isBlack(parent.sibling())) {//叔父节点是空或者黑色。没有溢出
                        //旋转操作
                        red(grand);
                        if (parent.isLeftChild()) {//L
                            if (node.isLeftChild()) {//LL
                                black(parent);
                            } else {//LR
                                black(node);
                                rotateLeft(parent);
                            }
                            rotateRight(grand);
                        } else {//R
                            if (node.isRightChild()) {//RR
                                black(parent);
                            } else {//RL
                                black(node);
                                rotateRight(parent);
                            }
                            rotateLeft(grand);
                        }
//                        black(grand.parent);
                    } else {//叔父节点红色，溢出
                        // 红 <- 黑 -> 红 -> new红, new红 <- 红 <- 黑 -> 红
                        red(grand);
                        black(parent.sibling());
                        black(parent);
                        node = grand;//不使用递归
//                        afterAdd(grand);
                    }
                } else {//parent 是黑色
                    //直接添加，不用调整修复。就满足了红黑树的性质
                }
            }
        } while (currentNode != node);
    }

    public Node<K,V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent, getHashcode(key));
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> rootNode = grand.parent;
        Node<K, V> parent = grand.right;
        Node<K, V> subTree = parent.left;

        parent.left = grand;
        grand.right = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> rootNode = grand.parent;
        Node<K, V> parent = grand.left;
        Node<K, V> subTree = parent.right;

        parent.right = grand;
        grand.left = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void afterRotate(Node<K, V> rootNode, Node<K, V> grand, Node<K, V> parent, Node<K, V> subTree) {
        if (grand.isLeftChild()) {
            rootNode.left = parent;
        } else if (grand.isRightChild()) {
            rootNode.right = parent;
        } else {
            table[indexOf(grand.key)] = parent;
        }

        if (subTree != null) {
            subTree.parent = grand;
        }
        grand.parent = parent;
        parent.parent = rootNode;
    }

    private int colorOf(Node<K, V> node) {
        return node == null ? Node.BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == Node.BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == Node.RED;
    }

    private void red(Node<K, V> node) {
        if (node != null) {
            node.color = Node.RED;
        }
    }

    private void black(Node<K, V> node) {
        if (node != null) {
            node.color = Node.BLACK;
        }
    }

    @Override
    public V get(K key) {
        Node<K, V> node = findNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return removeNode(findNode(key));
    }

    private V removeNode(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        Node<K, V> wantRemoveNode = node;
        V removeNodeValue = node.value;

        if (node.hasTwoChildren()) {//度为2的节点
            //度为2的节点就找前驱或者后继节点来替代当前节点
            Node<K, V> successor = successor(node);
            //replace
            node.value = successor.value;
            node.key = successor.key;
            node.hashCode = successor.hashCode;
            node = successor;//前驱或者后继节点的度必然不会是2
        }

        //叶子节点直接删除
        if (node.isLeaf()) {//度为0的节点
            if (node.parent == null) {//root节点
                table[indexOf(node)] = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            }
        } else {//度为1的节点
            if (node.parent == null) {//node.parent == null
                int index = indexOf(node);
                if (node.left != null) {
                    table[index] = node.left;
                } else {
                    table[index] = node.right;
                }
                table[index].parent = null;
            } else {
                Node<K, V> replaceNode;
                if (node.left != null) {
                    replaceNode = node.left;
                } else {
                    replaceNode = node.right;
                }

                Node<K, V> parent = node.parent;
                if (parent.left == node) {
                    parent.left = replaceNode;
                } else {
                    parent.right = replaceNode;
                }

                if (replaceNode != null) {
                    replaceNode.parent = parent;
                }
            }
        }
        fixAfterRemove(node);
        size--;

        afterRemove(wantRemoveNode, node);
        return removeNodeValue;
    }

    protected void afterRemove(Node<K, V> wantRemoveNode, Node<K, V> actualRemoveNode){

    }

    /**
     * 后继节点,与前驱相反，改left 为 right 就行
     * @param node
     * @return
     */
    protected Node<K, V> successor(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            while (node.parent != null && node.parent.right == node) {
                node = node.parent;
            }

//            if (node.parent != null) {
//                return node.parent;
//            } else {
//                return null;
//            }

            //简化
            return node.parent;

        }
    }

    private void fixAfterRemove(Node<K, V> node) {
        Node<K, V> currentNode;
        boolean underflow = false;
        do {
            currentNode = node;
            if (node.parent == null) {//删除的是根节点
                if (underflow) {//下溢不做处理
                    underflow = false;
                } else {//删除度为1或者0的根节点导致的。如果删除的是度为1的根节点，这个时候要将根节点的红色子节点染黑
                    if (isRed(node.left)) {
                        black(node.left);
                    }
                    if (isRed(node.right)) {
                        black(node.right);
                    }
                }
            } else {//删除的是非根节点
                if (isRed(node)) {//删除的是红色节点，不用调整，因为不会影响红黑树的性质

                } else {//删除的是黑色节点。
                    // 删除非根节点的黑色子节点，被删除的节点必定有兄弟（性质5）
                    // 借的顺序是    兄弟 --> 兄弟的儿子 --> 父亲

                    //1. 黑(黑色叶子节点，下溢的黑色节点也要当做黑色叶子节点处理)
                    if (underflow || isBlack(node.left) && isBlack(node.right)) {
                        underflow = false;
                        //需要找兄弟节点借，如果兄弟节点没有，就找父节点借，节点会下溢
                        Node<K, V> parent = node.parent;
                        Node<K, V> sibling;
                        if (parent.hasTwoChildren()) {//在下溢的时候
                            sibling = node.isLeftChild() ? parent.right : parent.left;
                        } else {//在正常删除节点的时候，必然有一个子节点
                            sibling = parent.left == null ? parent.right : parent.left;
                        }

                        //兄弟节点是红色（兄弟必定有黑儿子），将兄弟的黑儿子变成兄弟
                        if (isRed(sibling)) {
                            red(parent);
                            black(sibling);
                            if (sibling.isLeftChild()) {
                                rotateRight(parent);
                                sibling = parent.left;
                            } else {
                                rotateLeft(parent);
                                sibling = parent.right;
                            }
                        }

                        //兄弟节点都是黑色的
                        if (isRed(sibling.left) || isRed(sibling.right)) {//兄弟有红色子节点，兄弟可以借
                            if (sibling.isLeftChild()) {//L
                                //先看左边有没有红色子节点，有的话。优先按照LL处理
                                //没有的话就是LR。通过左旋可以转成LL
                                if (isBlack(sibling.left)) {//LR
                                    rotateLeft(sibling);
                                    sibling = sibling.parent;
                                }

                                //此时都转成了LL情况，统一按照LL情况处理

                                if (colorOf(parent) == Node.BLACK) {
                                    black(sibling);
                                } else {
                                    red(sibling);
                                }
                                rotateRight(parent);
                            } else {
                                //RL转成RR来处理
                                if (isBlack(sibling.right)) {//RL
                                    rotateRight(sibling);
                                    sibling = sibling.parent;
                                }

                                //此时都转成了RR情况，统一按照RR情况处理

                                if (colorOf(parent) == Node.BLACK) {
                                    black(sibling);
                                } else {
                                    red(sibling);
                                }
                                rotateLeft(parent);
                            }
                            black(parent);
                            black(parent.sibling());
                        } else {//兄弟只有黑色子节点或者兄弟没有子节点。兄弟无法借出，需要从父节点借（父节点是黑色会产生下溢）
                            if (isRed(parent)) {
                                black(parent);
                                red(sibling);
                            } else {
                                red(sibling);
                                node = parent;
                                //实际节点是红<-黑，黑色节点下溢了。需要再执行afterRemove，但是不能让它被当有红色子节点的黑色节点处理
                                //而是当做被没有子节点的黑色节点处理(黑)
                                underflow = true;
                            }
                        }
                    } else {//1. 红<-黑   2. 黑->红      度为1的黑色节点
                        //将红色子节点染黑
                        if (isRed(node.left)) {
                            black(node.left);
                        } else if (isRed(node.right)) {
                            black(node.right);
                        }
                    }

                }
            }
        } while (currentNode != node);
    }

    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        //for循环遍历所有红黑树
        if (size > 0) {
            Queue<Node<K, V>> nodeQueue = new ArrayDeque<>();
            for (Node<K, V> kvNode : table) {
                if (kvNode == null) {
                    continue;
                }

                //层序遍历
                nodeQueue.offer(kvNode);

                while (!nodeQueue.isEmpty()){
                    Node<K, V> node = nodeQueue.poll();

                    if (Objects.equals(node.value, value)) {
                        return true;
                    }

                    if (node.left != null) {
                        nodeQueue.offer(node.left);
                    }

                    if (node.right != null) {
                        nodeQueue.offer(node.right);
                    }
                }
            }
        }
        return false;
    }

    private Node<K, V> findNode(K key){
        Node<K, V> root = table[indexOf(key)];
        return root != null ? findNode(root, key) : null;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Node<K, V> findNode(Node<K,V> node, K key){
        K key1 = key;
        int h1 = getHashcode(key1);
        int compare;
        while (node != null) {
            K key2 = node.key;
            int h2 = node.hashCode;

            if (h1 < h2) {
                compare = -1;
            } else if (h1 > h2) {
                compare = 1;
            } else if (Objects.equals(key1, key2)) {//h1 == h2 && key1.equals(key2)
//                compare = 0;
                return node;//下面的手段都是为了确定节点可能在哪边子树
            } else if (key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && (compare = ((Comparable) key1).compareTo(key2)) != 0) {// h1 == h2 && key1 not equals key2
                //可以区分要找的节点是在左子树还是右子树
            } else {
                //无法区分要找的节点是在左子树还是右子树，只能两边都去找
                Node<K, V> ch, q;
                if (((ch = node.left) != null && (q = findNode(ch, key)) != null) || ((ch = node.right) != null && (q = findNode(ch, key)) != null)) {
                    return q;
                } else {
                    return null;
                }
            }

            if (compare < 0) {
                node = node.left;
            } else {//compare > 0
                node = node.right;
            }
        }
        return null;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        //层序遍历
        if (size == 0 || visitor == null) {
            return;
        }

        for (Node<K, V> kvNode : table) {
            if (kvNode == null) {
                continue;
            }

            Queue<Node<K, V>> nodeQueue = new LinkedList<>();
            nodeQueue.offer(kvNode);

            while (!nodeQueue.isEmpty()) {
                Node<K, V> node = nodeQueue.poll();

                boolean stop = visitor.visit(node.key, node.value);
                if (stop) {
                    return;
                }

                if (node.left != null) {
                    nodeQueue.offer(node.left);
                }

                if (node.right != null) {
                    nodeQueue.offer(node.right);
                }
            }
        }

    }

    private boolean isRBTree(Node<K, V> root) {
        if (!isBlack(root)) {//(1)
            return false;
        }

        Node<K, V> node = root;

        int rootNodeBlackHeight = 0;

        Node<K, V> calHeightNode = root;
        while (calHeightNode != null) {
            if (isBlack(calHeightNode)) {
                rootNodeBlackHeight++;
            }
            calHeightNode = calHeightNode.left;
        }
        rootNodeBlackHeight++;

        Deque<Node<K, V>> nodeStack = new ArrayDeque<>();
        do {
            while (node != null) {
                if (!node.hasTwoChildren()) {
                    Node<K, V> iteratorNode = node;
                    int blackCount = 0;
                    int preColor = Node.BLACK;
                    while (iteratorNode != null) {
                        if (isBlack(iteratorNode)) {
                            blackCount++;
                        } else {
                            if (preColor == Node.RED) {//(4)
                                throw new IllegalStateException("This is not a red-black tree. Because has continues reb node at " + node);
                            }

                        }
                        iteratorNode = iteratorNode.parent;
                    }
                    blackCount++;

                    if (blackCount != rootNodeBlackHeight) {//(5)
                        throw new IllegalStateException("This is not a red-black tree. " +
                                "Because blackCount = " + blackCount + " rootNodeBlackHeight = " + rootNodeBlackHeight + " at " + node);
                    }
                }

                nodeStack.push(node);
                node = node.left;
            }

            if (!nodeStack.isEmpty()) {
                Node<K, V> pop = nodeStack.pop();
                node = pop.right;
            }

        } while (node != null || !nodeStack.isEmpty());

        return true;
    }

    @SuppressWarnings({"unchecked"})
    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            isRBTree(root);
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>)node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>)node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }

}
