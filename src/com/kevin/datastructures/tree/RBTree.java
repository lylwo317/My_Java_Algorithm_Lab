package com.kevin.datastructures.tree;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;

/**
 * <pre>
 * 红黑树子性质：
 * 1. 根节点必须是黑色
 * 2. 节点的颜色只有红色和黑色
 * 3. 叶子节点(NIL节点)都是黑色
 * 4. 红色节点的子节点必须是黑色（也就是不能出现两个连续的红色）
 * 5. 从任一节点到其每个叶子(NIL节点)的所有简单路径都包含相同数目的黑色节点
 * </pre>
 *
 * @param <E>
 */
public class RBTree<E> extends BinarySearchTree<E> {
    public RBTree() {
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    private static class RBNode<E> extends Node<E> {
        public static int RED = 0;
        public static int BLACK = 1;

        private int color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
/*
            String parentStr = null;
            parentStr = parent == null ?  "null" : parent.element.toString();
*/
            return str + element.toString(); //+ "_P("+ parentStr + ")";
        }
    }

    private int colorOf(Node<E> node) {
        return node == null ? RBNode.BLACK : ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == RBNode.BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RBNode.RED;
    }

    private void red(Node<E> node) {
        if (node != null) {
            ((RBNode<E>) node).color = RBNode.RED;
        }
    }

    private void black(Node<E> node) {
        if (node != null) {
            ((RBNode<E>) node).color = RBNode.BLACK;
        }
    }

    public boolean isRBTree() {
        if (!isBlack(root)) {//(1)
            return false;
        }

        Node<E> node = root;

        int rootNodeBlackHeight = 0;

        Node<E> calHeightNode = root;
        while (calHeightNode != null) {
            if (isBlack(calHeightNode)) {
                rootNodeBlackHeight++;
            }
            calHeightNode = calHeightNode.left;
        }
        rootNodeBlackHeight++;

        Deque<Node<E>> nodeStack = new ArrayDeque<>();
        do {
            while (node != null) {
                if (!node.hasTwoChildren()) {
                    Node<E> iteratorNode = node;
                    int blackCount = 0;
                    int preColor = RBNode.BLACK;
                    while (iteratorNode != null) {
                        if (isBlack(iteratorNode)) {
                            blackCount++;
                        } else {
                            if (preColor == RBNode.RED) {//(4)
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
                Node<E> pop = nodeStack.pop();
                node = pop.right;
            }

        } while (node != null || !nodeStack.isEmpty());

        return true;
    }

    private void rotateLeft(Node<E> grand) {
        Node<E> rootNode = grand.parent;
        Node<E> parent = grand.right;
        Node<E> subTree = parent.left;

        parent.left = grand;
        grand.right = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void rotateRight(Node<E> grand) {
        Node<E> rootNode = grand.parent;
        Node<E> parent = grand.left;
        Node<E> subTree = parent.right;

        parent.right = grand;
        grand.left = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void afterRotate(Node<E> rootNode, Node<E> grand, Node<E> parent, Node<E> subTree) {
        if (grand.isLeftChild()) {
            rootNode.left = parent;
        } else if (grand.isRightChild()) {
            rootNode.right = parent;
        } else {//根节点
            root = parent;
        }

        //update node.parent
        if (subTree != null) {
            subTree.parent = grand;
        }
        grand.parent = parent;
        parent.parent = rootNode;
    }

    @Override
    public Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    /**
     * 新添加的节点默认都是--红色
     * <pre>
     * 1. 添加到根节点：
     *      1.1 直接添加，然后染黑
     * 2. 添加到非根节点
     *      2.1 parent是黑色
     *          直接添加
     *      2.2 parent是红色
     *          i. 叔父节点是黑色或者空（1.空，说明这个是在叶子节点添加的情况。2.黑色，说明是添加节点导致上溢的情况，出现了叔父为节点为黑色的情况）
     *             1.空
     *               grand节点涂红
     *               按照节点的当前的类型进行旋转（分别有四种类型LL，LR，RR，RL）
     *               旋转后grand的sibling涂红
     *             2.黑色（对应的2-3-4树，不在同一层，所以跟空的情况一样处理）。
     *          ii.叔父节点是红色（添加的节点，将会导致该层溢出）
     *             (父节点 & 叔父节点) 涂黑
     *             grand涂红 当做新添加节点处理
     * </pre>
     *
     * @param node
     */
    @Override
    protected void afterAdd(Node<E> node) {
        //添加到根节点，或者上溢到根节点
        Node<E> currentNode;
        do {
            currentNode = node;
            if (node.parent == null) {
                root = node;
                black(node);
            } else {//非根节点
                if (isRed(node.parent)) {//parent 是红色
                    Node<E> grand = node.parent.parent;
                    Node<E> parent = node.parent;
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

    /**
     * <pre>
     *          ┌────────60───────┐
     *          │                 │
     *      ┌─R_55─┐           ┌─R_65─┐
     *      │      │           │      │
     *   ┌─50─┐    57─┐     ┌─63      70
     *   │    │       │     │
     * R_40  R_53    R_58 R_62
     *
     * 可能会删除的节点有：
     * 1. 根节点：
     *      1.1. 下溢导致的根节点删除
     *           ┌─251─┐
     *           │     │
     *          240   255
     *
     *          remove: 255
     *
     *          (underflow 251)//按照删除根节点处理
     *           ┌─251
     *           │
     *         R_240
     *
     *          由于删除255会使得251产生下溢，但是下溢的是根节点，所以不需要处理
     *
     *      1.2. 非下溢导致的
     *          a.
     *          251─┐
     *              │
     *            R_255
     *
     *          remove: 251
     *
     *          255
     *
     *          涂黑255(如果有红色子节点，要记得涂黑)
     *
     *          b.
     *          255
     *
     *          remove: 255
     *
     *          只有一个根节点不需要涂黑
     * 2. 非根节点：
     *                 ┌────────60───────┐
     *                 │                 │
     *             ┌─R_55─┐           ┌─R_65─┐
     *             │      │           │      │
     *          ┌─50─┐    57─┐     ┌─63      70
     *          │    │       │     │
     *        R_40  R_53    R_58 R_62
     *      2.1. 红色叶子节点（40，53，58，62）-- 直接删除即可
     *
     *      2.2. 黑色非叶子节点（57，63）-- 将红色叶子节点涂黑
     *
     *      2.3. 黑色叶子节点（70（叶子））,必有兄弟
     *          a.有黑色兄弟
     *            i. 兄弟的子节点是红色
     *              旋转让兄弟或者兄弟子节点替换parent，parent替换被删除叶子节点。使得红黑树对应的 2-3-4树最后一层保持不变
     *            ii.兄弟的子节点是黑色（空子节点也是黑色）
     *              将兄弟染红(向父节点借)
     *                父亲是红色，就染黑
     *                父亲是黑色，就下溢处理
     *          b.有红色兄弟
     *            i.通过旋转将兄弟的黑儿子变成兄弟节点
     *              按照a的情况处理
     *
     * 处理溢出的时候，防止将黑色节点的红色子节点染黑，导致不平衡。
     * </pre>
     *
     * @param node
     */
    @Override
    public void afterRemove(Node<E> node) {
        Node<E> currentNode;
        boolean underflow = false;
        do {
            currentNode = node;
            if (node.parent == null) {//删除的是根节点
                if (underflow) {
                    underflow = false;//下溢不做处理
                } else {//删除度为1或者0的根节点导致的，这个时候要将红色子节点染黑
                    if (isRed(node.left)) {
                        black(node.left);
                    }
                    if (isRed(node.right)) {
                        black(node.right);
                    }
                }
            } else {//删除的是非根节点
                if (isBlack(node)) {//删除的是黑色节点。
                    // 删除非根节点的黑色子节点，被删除的节点必定有兄弟（性质5）
                    // 借的顺序是    兄弟 --> 兄弟的儿子 --> 父亲

                    //1. 黑(黑色叶子节点 下溢的黑色节点也要当做黑色叶子节点处理)
                    if (underflow || isBlack(node.left) && isBlack(node.right)) {
                        underflow = false;
                        //需要找兄弟节点借，如果兄弟节点没有，就找父节点借，节点会下溢
                        Node<E> parent = node.parent;
                        Node<E> sibling;
                        if (parent.hasTwoChildren()) {//在下溢的时候
                            sibling = node.isLeftChild() ? parent.right : parent.left;
                        } else {//在正常删除节点的时候，必然有一个子节点
                            sibling = parent.left == null ? parent.right : parent.left;
                        }
                        //boolean isLeft = parent.left == null || node.isLeftChild();//有可能有兄弟，也有可能没有兄弟

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
                                //先看左边有没有红色子节点，有的话。优先LL处理
                                //没有的话就LR处理
                                if (isRed(sibling.left)) {//LL

                                } else {//LR
                                    rotateLeft(sibling);
                                    sibling = sibling.parent;
                                }

                                if (colorOf(parent) == RBNode.BLACK) {
                                    black(sibling);
                                } else {
                                    red(sibling);
                                }
                                rotateRight(parent);
                            } else {
                                if (isRed(sibling.right)) {//RR

                                } else {//RL
                                    rotateRight(sibling);
                                    sibling = sibling.parent;
                                }

                                if (colorOf(parent) == RBNode.BLACK) {
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
//                                afterRemove(parent, false);
//                                hasReplace = false;
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

                } else {//删除的是红色节点，不用调整，因为不会影响红黑树的性质
                }
            }
        } while (currentNode != node);
    }
}
