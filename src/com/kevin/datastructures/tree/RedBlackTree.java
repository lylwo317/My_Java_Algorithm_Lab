package com.kevin.datastructures.tree;

import com.kevin.datastructures.tree.printer.BinaryTreeInfo;
import com.kevin.datastructures.tree.printer.BinaryTrees;

import java.util.*;

/**
 * 红黑树五条性质：
 * 1. 根节点必须是黑色
 * 2. 节点的颜色只有红色和黑色
 * 3. 叶子节点（NIL)节点都是黑色
 * 4. 红色节点的子节点必须是黑色（也就是不能出现两个连续的红色）
 * 5. 从任一节点到其每个叶子（NIL）节点的所有简单路径都包含相同数目的黑色节点（也就是说黑色节点才是对应2-3-4树的高度）
 *
 * Author: Kevin Xie
 * Email: lylwo317@gmail.com
 * @param <E>
 */
public class RedBlackTree<E> implements BinaryTreeInfo {

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((RBNode<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((RBNode<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }

    private static class RBNode<E> {
        public static int RED = 0;
        public static int BLACK = 1;

        private int color = RED;

        E element;
        RBNode<E> left;
        RBNode<E> right;
        RBNode<E> parent;

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

        public RBNode<E> sibling() {//兄弟
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        public RBNode(E element, RBNode<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            String nodeStr =  element + "_p(" + parentString + ")";
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + nodeStr;
        }
    }

    protected int size;

    protected RBNode<E> root;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    private Comparator<E> comparator;

    public RedBlackTree() {
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 添加一个元素要做一些这些：
     * 1.检查元素是否为空，空就不添加，因为添加空的元素，无法比较大小
     * 2.是否是第一个节点,是的话直接root = newNode
     * 3.不是第一个节点,找到合适的位置(叶子节点)，然后添加上去，如果发现有相同的，就直接替换
     *
     * @param element
     */
    public void add(E element){
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }

        if (root == null) {
            root = createNode(element, null);
//            afterAdd(root);
            afterAdd2(root);
            size++;
            return;
        }

        RBNode<E> node = root;
        RBNode<E> parent;
        int compare;
        do {
            compare = compare(element, node.element);
            if (compare == 0) {
                node.element = element;
                return;
            }

            parent = node;
            if (compare < 0) {
                node = node.left;
            } else {//compare > 0
                node = node.right;
            }
        } while (node != null);

        RBNode<E> newNode = createNode(element, parent);
        if (compare < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
//        afterAdd(newNode);
        afterAdd2(newNode);
        size++;
    }

    /**
     * 找前驱节点，找比当前节点小的所有节点中最大的节点
     *
     * node
     * if(有左子树）{
     *    node.left.right.right...（左子树最右(最大)节点）
     * } else {
     *    //当前节点没有左子树
     *    if(当前节点是父节点的右子节点) {//父节点就是前驱
     *       node.parent
     *    } else if(当前节点是父节点的左子节点){//就往祖父节点找，直到找到祖父节点的右子节点为止
     *       node.parent.parent....(parent.right == )
     *    } else { //没有前驱节点
     *       return null //也就是最左边的叶子节点
     *    }
     * }
     *
     * @param node
     * @return
     */
    protected RBNode<E> predecessor(RBNode<E> node) {
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        } else {
            while (node.parent != null && node.parent.left == node) {
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

    /**
     * 后继节点,与前驱相反，改left 为 right 就行
     * @param node
     * @return
     */
    protected RBNode<E> successor(RBNode<E> node) {
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

    public boolean contains(E element) {
        return findNode(element) != null;
    }

    /**
     *  通过，前序，中序，后序，层序遍历等手段找到元素
     */
    private RBNode<E> findNode(E element) {
        if (element == null) {
            return null;
        }
        Stack<RBNode<E>> nodeStack = new Stack<>();
        RBNode<E> node = root;
        do {
            while (node != null) {
                nodeStack.push(node);
                node = node.left;
            }

            if (!nodeStack.isEmpty()) {
                RBNode<E> pop = nodeStack.pop();
                if (pop.element.equals(element)) {
                    return pop;
                }
                node = pop.right;
            }

        } while (node != null || !nodeStack.isEmpty());
        return null;
    }

    /**
     * 根据 element 找到Node，然后删除。
     *
     * @param element
     */
    public void remove(E element) {
        removeNode(findNode(element));
    }

    /**
     * 删除节点
     *
     * 1. 节点的度为2
     *      找到前驱或者后继节点，然后将前驱或者后继节点覆盖当前节点，接着将前驱或者后继节点删除即可
     * 2. 节点的度为1
     *      删除，然后将子节点接到父节点上
     * 3. 节点的度为0
     *      直接删除
     *
     * 综上所述，本质上删除的都是度为0或者1的节点
     *
     */
    private void removeNode(RBNode<E> node) {
        if (node == null) {
            return;
        }
        if (node.left != null && node.right != null) {//度为2的节点
            //如果直接删除，这个时候有两棵子树就好不连接到父节点了。这个时候要连接到父节，就应该在左子树中找到最大的节点（右子树中找到最小节点）
            //来代替原来节点的位置。（这样不但保持了二叉搜索树的性质，还解决了删除后连接到父节点的问题）

            //度为2的节点就找前驱或者后继节点来替代当前节点
            RBNode<E> successor = successor(node);
            //replace
            node.element = successor.element;
            node = successor;//前驱或者后继节点的度必然不会是2
        }

        //叶子节点直接删除

        RBNode<E> child = null;
        RBNode<E> parent = null;

        if (node.isLeaf()) {//度为0的节点
            if (node == root) {
                root = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                parent = node.parent;
            }
        } else {//度为1的节点
            if (node == root) {//node.parent == null
                if (node.left != null) {
                    root = node.left;
                } else {
                    root = node.right;
                }
                root.parent = null;
                child = root;
            } else {
                RBNode<E> replaceNode;
                if (node.left != null) {
                    replaceNode = node.left;
                } else {
                    replaceNode = node.right;
                }
                child = replaceNode;

                RBNode<E> p = node.parent;
                parent = p;
                if (p.left == node) {
                    p.left = replaceNode;
                } else {
                    p.right = replaceNode;
                }

                if (replaceNode != null) {
                    replaceNode.parent = p;
                }
            }
        }
//        afterRemove(node);
        if (isBlack(node)) {
            afterRemove2(child, parent);
        }
        size--;
    }

    private void rotateLeftAndUpdateRoot(RBNode<E> grand) {
        RBNode<E> rootNode = grand.parent;
        RBNode<E> parent = grand.right;
        RBNode<E> subTree = parent.left;

        parent.left = grand;
        grand.right = subTree;

        afterRotate(rootNode, grand, parent, subTree);

        if (rootNode == null) {
            root = parent;
        }
    }

    private void rotateRightAndUpdateRoot(RBNode<E> grand) {
        RBNode<E> rootNode = grand.parent;
        RBNode<E> parent = grand.left;
        RBNode<E> subTree = parent.right;

        parent.right = grand;
        grand.left = subTree;

        afterRotate(rootNode, grand, parent, subTree);

        if (rootNode == null) {
            root = parent;
        }
    }

    private void rotateLeft(RBNode<E> grand) {
        RBNode<E> rootNode = grand.parent;
        RBNode<E> parent = grand.right;
        RBNode<E> subTree = parent.left;

        parent.left = grand;
        grand.right = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void rotateRight(RBNode<E> grand) {
        RBNode<E> rootNode = grand.parent;
        RBNode<E> parent = grand.left;
        RBNode<E> subTree = parent.right;

        parent.right = grand;
        grand.left = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void afterRotate(RBNode<E> rootNode, RBNode<E> grand, RBNode<E> parent, RBNode<E> subTree) {
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
    private void afterAdd(RBNode<E> node) {
        RBNode<E> currentNode;
        do {
            currentNode = node;
            if (node.parent == null) {//添加到根节点，或者上溢到根节点
                root = node;
                black(node);
            } else {//非根节点
                if (isRed(node.parent)) {//parent 是红色
                    RBNode<E> grand = node.parent.parent;
                    RBNode<E> parent = node.parent;
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
                    } else {//叔父节点红色，溢出
                        // 红 <- 黑 -> 红 -> new红, new红 <- 红 <- 黑 -> 红
                        red(grand);
                        black(parent.sibling());
                        black(parent);
                        node = grand;//不使用递归
                    }
                } else {//parent 是黑色
                    //直接添加，不用调整修复。就满足了红黑树的性质
                }
            }
        } while (currentNode != node);
    }

    /**
     * 通过维护红黑树性质来修复添加操作
     *
     * 问：什么情况需要修复？（换句话说也就是什么情况会破坏红黑树的性质）
     * 答：被添加节点的父节点是红色，或者被添加节点是根节点，就需要修复。
     *
     * @param
     */
    private void afterAdd2(RBNode<E> newNode) {
        RBNode<E> parent = newNode.parent;
        RBNode<E> grandParent;
        while ((parent != null && isRed(parent)) && parent != root) {
            grandParent = parent.parent;

            RBNode<E> sibling;
            if (parent.isLeftChild()) {
                sibling = parent.sibling();
                if (newNode.isRightChild()) {
                    swapColor(parent, newNode);
                    rotateLeftAndUpdateRoot(parent);
                    parent = newNode;
                }
                if (isBlack(sibling)) {
                    black(parent);
                    red(grandParent);
                    rotateRightAndUpdateRoot(grandParent);
                    break;
                } else {//isRed(sibling)
                    red(grandParent);
                    black(parent);
                    black(sibling);
                    newNode = grandParent;
                    parent = newNode.parent;
                }
            } else {
                sibling = parent.sibling();
                if (newNode.isLeftChild()) {
                    swapColor(parent, newNode);
                    rotateRightAndUpdateRoot(parent);
                    parent = newNode;
                }
                if (isBlack(sibling)) {
                    black(parent);
                    red(grandParent);
                    rotateLeftAndUpdateRoot(grandParent);
                    break;
                } else {//isRed(sibling)
                    red(grandParent);
                    black(parent);
                    black(sibling);
                    newNode = grandParent;
                    parent = newNode.parent;
                }
            }
        }
        black(root);
    }

    private void swapColor(RBNode<E> nodeA, RBNode<E> nodeB) {
        int pColor = nodeA.color;
        nodeA.color = nodeB.color;
        nodeB.color = pColor;
    }

    /**
     * 通过维护红黑树性质来修复删除操作
     *
     * 问：什么情况需要修复？（换句话说也就是什么情况会破坏红黑树的性质）
     * 答：被删除节点是黑色，就需要修复。因为黑色节点删除，必然会导致不满足红黑树性质5。
     *
     * @param nodeX 代替被删除节点位置的子节点
     * @param nodeXParent 被删除节点的父节点
     */
    private void afterRemove2(RBNode<E> nodeX, RBNode<E> nodeXParent) {
        while (isBlack(nodeX) && nodeX != root) {
            if (nodeXParent.left == nodeX) {//兄弟在右边
                RBNode<E> sibling = nodeXParent.right;//w
                //case 1
                if (isRed(sibling)) {//兄弟是红色，也就是说不在对应的2-3-4树的同一层。通过旋转将红色兄弟的黑色儿子变成兄弟
                    black(sibling);
                    red(nodeXParent);
                    rotateLeftAndUpdateRoot(nodeXParent);
                    sibling = nodeXParent.right;
                }

                //case 2
                if (isBlack(sibling.left) && isBlack(sibling.right)) {//兄弟没得借。也就是对应2-3-4树下溢的情况。也就是向父节点借
                    red(sibling);
                    //black(nodeXParent);
                    nodeX = nodeXParent;
                    nodeXParent = nodeX.parent;
                } else {//兄弟必然有红色子节点，可以跟兄弟借
                    //case 3
                    if (isBlack(sibling.right)) {//右边没有红色子节点，就通过旋转来使得右边有红色子节点
                        black(sibling.left);
                        red(sibling);
                        rotateRightAndUpdateRoot(sibling);
                        sibling = nodeXParent.right;
                    }

                    //case 4
                    //到这里，兄弟右边必然有红色子节点
                    black(sibling.right);
                    sibling.color = colorOf(nodeXParent);
                    black(nodeXParent);
                    rotateLeftAndUpdateRoot(nodeXParent);
                    //结束循环。
                    break;
                }
            } else {//兄弟在左边
                RBNode<E> sibling = nodeXParent.left;
                if (isRed(sibling)) {//兄弟是红色，也就是说不在对应的2-3-4的同一层。通过旋转将红色兄弟的黑色儿子变成兄弟
                    black(sibling);
                    red(nodeXParent);
                    rotateRightAndUpdateRoot(nodeXParent);
                    sibling = nodeXParent.left;
                }

                if (isBlack(sibling.left) && isBlack(sibling.right)) {//兄弟没得借。也就是对应2-3-4树下溢的情况。也就是向父节点借
                    red(sibling);
                    //black(nodeXParent);
                    nodeX = nodeXParent;
                    nodeXParent = nodeX.parent;
                } else {//兄弟必然有红色子节点，可以跟兄弟借
                    if (isBlack(sibling.left)) {//左边没有红色子节点，就通过旋转来使得右边有红色子节点
                        black(sibling.right);
                        red(sibling);
                        rotateLeftAndUpdateRoot(sibling);
                        sibling = nodeXParent.left;
                    }

                    //到这里，兄弟左边必然有红色子节点
                    black(sibling.left);
                    sibling.color = colorOf(nodeXParent);
                    black(nodeXParent);
                    rotateRightAndUpdateRoot(nodeXParent);
                    //要停止循环了，不要再继续了
//                    nodeX = root;
                    break;
                }
            }
        }

        black(nodeX);
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
    private void afterRemove(RBNode<E> node) {
        RBNode<E> currentNode;
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
                        RBNode<E> parent = node.parent;
                        RBNode<E> sibling;
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

    @SuppressWarnings("unchecked")
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }

        return ((Comparable<E>) e1).compareTo(e2);
    }

    private int colorOf(RBNode<E> node) {
        return node == null ? RBNode.BLACK : node.color;
    }

    private boolean isBlack(RBNode<E> node) {
        return colorOf(node) == RBNode.BLACK;
    }

    private boolean isRed(RBNode<E> node) {
        return colorOf(node) == RBNode.RED;
    }

    private void red(RBNode<E> node) {
        if (node != null) {
            node.color = RBNode.RED;
        }
    }

    private void black(RBNode<E> node) {
        if (node != null) {
            node.color = RBNode.BLACK;
        }
    }

    /**
     * 检查当前红黑树是否符合红黑树的第1,4,5点性质
     */
    public void checkRBTreeProperties() {
        if (!isBlack(root)) {//不符合性质(1)
            throw new IllegalStateException("root node color isn't black. root = " + root);
        }

        if (root == null) {
            return;
        }

        //黑节点数量
        int rootNodeBlackHeight = 0;

        RBNode<E> calHeightNode = root;
        while (calHeightNode != null) {
            if (isBlack(calHeightNode)) {
                rootNodeBlackHeight++;
            }
            calHeightNode = calHeightNode.left;
        }
        rootNodeBlackHeight++;//加入NIL节点到统计的总数中

        Queue<RBNode<E>> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {

            RBNode<E> node = nodeQueue.poll();

            if (!node.hasTwoChildren()) {//计算度为1或者度为0的节点到根节点的黑色节点数量是否等于rootNodeBlackHeight，以及是否存在两个连续红色节点
                RBNode<E> iteratorNode = node;
                int blackCount = 0;
                int preColor = RBNode.BLACK;
                while (iteratorNode != null) {//往根节点遍历，看是否存在连续两个红色节点
                    if (isBlack(iteratorNode)) {
                        blackCount++;//统计黑色节点数量
                    } else {//当前遍历的节点是红色
                        if (preColor == RBNode.RED) {//前一个节点也是红色，不满足性质(4)
                            throw new IllegalStateException("This is not a red-black tree. Because has continues reb node at " + node);
                        }
                    }
                    preColor = iteratorNode.color;
                    iteratorNode = iteratorNode.parent;
                }
                blackCount++;//加入NIL节点到统计的总数中

                if (blackCount != rootNodeBlackHeight) {//黑色节点数量不相等，不满足性质(5)
                    throw new IllegalStateException("This is not a red-black tree. " +
                            "Because blackCount = " + blackCount + " rootNodeBlackHeight = " + rootNodeBlackHeight + " at " + node);
                }
            }

            if (node.left != null) {
                nodeQueue.offer(node.left);
            }

            if (node.right != null) {
                nodeQueue.offer(node.right);
            }
        }
    }

    public RBNode<E> createNode(E element, RBNode<E> parent) {
        return new RBNode<>(element, parent);
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> rbtree = new RedBlackTree<>();
		for (int i = 0; i < 1000; i++) {
			Random ran = new Random();
			HashSet<Integer> hs = new HashSet<>();
			for (;;) {
				int tmp = ran.nextInt(1000)+1;
				hs.add(tmp);
				if(hs.size() == 100) break;
			}

			for (Integer datum : hs.toArray(new Integer[0])) {
				System.out.println("add: " + datum);
				rbtree.add(datum);
//                BinaryTrees.println(rbtree);
				rbtree.checkRBTreeProperties();
			}

			for (Integer datum : hs.toArray(new Integer[0])) {
				System.out.println("remove: " + datum);
				rbtree.remove(datum);
//                BinaryTrees.println(rbtree);
                rbtree.checkRBTreeProperties();
			}

		}
    }
}
