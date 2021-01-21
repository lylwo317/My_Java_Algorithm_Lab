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
 * 5. 从任一节点到其每个叶子(NIL节点)的所有简单路径都包含相同数目的黑色节点（也就是说黑色节点才是对应2-3-4树的高度）
 *
 * 红黑树添加删除的核心只有两个
 *
 * 空节点当做黑色节点处理的依据是。红黑树对应的2-3-4树，不管空节点还是黑节点都不是在当前的层里，所以统一当做黑色节点处理，可以节省很多不必要的判断逻辑
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
                str = "<<R>>_";
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
     *          i. 叔父节点是黑色或者空（1.空，。2.黑色，）
     *             a.空(说明这个是在叶子节点添加的情况)
     *               grand节点涂红
     *               按照节点的当前的类型进行旋转（分别有四种类型LL，LR，RR，RL）
     *               旋转后grand的sibling涂红
     *             b.黑色（说明是添加节点导致上溢的情况，出现了叔父节点为黑色的情况。对应的2-3-4树，不在同一层，所以跟空的情况一样处理）。
     *          ii.叔父节点是红色（添加的节点，将会导致该层溢出）
     *             (父节点 & 叔父节点) 涂黑
     *             grand涂红 当做新添加节点处理
     * </pre>
     *
     * @param node
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> currentNode;
        do {
            currentNode = node;
            if (node.parent == null) {//添加到根节点，或者上溢到根节点
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
     *
     * 可能会删除的节点有：
     * 1. 根节点：
     *      1.1 下溢导致的根节点删除
     *           ┌─25─┐
     *           │    │
     *          20    30
     *
     *          remove: 30(导致25下溢)
     *
     *           ┌─25
     *           │
     *         R_20
     *
     *          由于删除30会使得25产生下溢，但是下溢的是根节点，所以不需要处理。对应的2-3-4树的高度减1
     *
     *      1.2 非下溢导致的
     *          a.
     *           ┌─25
     *           │
     *         R_20
     *
     *          remove: 25
     *
     *          20
     *
     *          涂黑20(如果有红色子节点，要记得涂黑)
     *
     *          b.
     *          20
     *
     *          remove: 20
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
     *      2.1 红色叶子节点（40，53，58，62）
     *          直接删除即可
     *
     *      2.2 黑色节点
     *          2.2.1 黑色非叶子节点（57，63）-- 将红色叶子节点涂黑
     *
     *          2.2.2 黑色叶子节点（70（叶子），由于下溢导致被删除的黑色节点，也是需要当做叶子节点处理。）
     *          (根据红黑树的性质5，可知黑色节点对应的是2-3-4树的层级，所以必定还有其它黑色叶子节点在同一层级)
     *
     *              a.有黑色兄弟（在对应的2-3-4树中，被删除节点和他的兄弟在同一层）
     *
     *                i. 黑色兄弟的子节点是红色。向兄弟借
     *                  旋转让兄弟或者兄弟子节点替换parent，parent替换被删除叶子节点。使得红黑树对应的 2-3-4树最后一层保持不变
     *
     *                ii.黑色兄弟的子节点是黑色（空子节点和黑色字节点都当做黑色处理，因为无论哪一种，都无法借出）。向父亲借
     *                  将兄弟染红(向父节点借)
     *                    父亲是红色，就染黑
     *                    父亲是黑色，就下溢处理（这里特别要注意的地方是，下溢后，父节点就相当于新的被删除的节点，重新走afterRemove）
     *
     *              b.有红色兄弟（说明兄弟必然有黑色儿子）（在对应的2-3-4树中，被删除节点和他的兄弟在不在同一层，但是兄弟的黑儿子和被删除节点在同一层）
     *                       ┌──────77─────┐
     *                       │             │
     *                  ┌───55────┐      ┌─80─┐
     *                  │         │      │    │
     *                 17─┐    ┌─R_57─┐ 79 ┌─R_88─┐
     *                    │    │      │    │      │
     *                   R_29 56      62  84      93
     *                 remove: 77(实际删除的是后继节点79)
     *                       ┌──────79─────┐
     *                       │             │
     *                  ┌───55────┐      ┌─88─┐
     *                  │         │      │    │
     *                 17─┐    ┌─R_57─┐ 80─┐  93
     *                    │    │      │    │
     *                   R_29 56      62  R_84
     *                i.通过旋转将兄弟的黑儿子变成兄弟节点
     *                  按照a的情况处理
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
                        Node<E> parent = node.parent;
                        Node<E> sibling;
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

                                if (colorOf(parent) == RBNode.BLACK) {
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
}
