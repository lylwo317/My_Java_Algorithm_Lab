package com.kevin.datastructures.tree;

import java.util.Comparator;
import java.util.Stack;

/**
 * 二叉搜索树
 * @param <E>
 */
public class BinarySearchTree<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 添加一个元素要做一些这些：
     * 1.检查元素是否为空，空就不添加，因为添加空的元素，无法比较大小
     * 2.是否是第一个节点,是的话直接root = newNode
     * 3.不是第一个节点,找到合适的位置，然后添加上去，如果发现有相同的，就直接替换
     *
     * 复杂度：O(h) == O(logN)
     * 最坏时间复杂度：O(h) == O(n)，退化成链表的时候
     * @param element
     */
    @Override
    public void add(E element){
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }

        if (root == null) {
            root = createNode(element, null);
            afterAdd(root);
            size++;
            return;
        }

        Node<E> node = root;
        Node<E> parent;
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

        Node<E> newNode = createNode(element, parent);
        if (compare < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        afterAdd(newNode);
        size++;
    }

    protected void afterAdd(Node<E> node) {

    }

    protected void afterRemove(Node<E> node) {

    }

    /**
     * 根据 element 找到Node，然后删除。
     *
     * 复杂度：O(h) == O(logN)
     * 最坏时间复杂度：O(h) == O(n)，退化成链表的时候
     * @param element
     */
    @Override
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
    private void removeNode(Node<E> node) {
        if (node == null) {
            return;
        }
        if (node.left != null && node.right != null) {//度为2的节点
            //如果直接删除，这个时候有两棵子树就好不连接到父节点了。这个时候要连接到父节，就应该在左子树中找到最大的节点（右子树中找到最小节点）
            //来代替原来节点的位置。（这样不但保持了二叉搜索树的性质，还解决了删除后连接到父节点的问题）

            //度为2的节点就找前驱或者后继节点来替代当前节点
            Node<E> successor = successor(node);
            //replace
            node.element = successor.element;
            node = successor;//前驱或者后继节点的度必然不会是2
        }

        //叶子节点直接删除
        if (node.isLeaf()) {//度为0的节点
            if (node == root) {
                root = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            }
        } else {//度为1的节点
            if (node == root) {//node.parent == null
                if (node.left != null) {
                    root = node.left;
                } else {
                    root = node.right;
                }
                root.parent = null;
            } else {
                Node<E> replaceNode;
                if (node.left != null) {
                    replaceNode = node.left;
                } else {
                    replaceNode = node.right;
                }

                Node<E> parent = node.parent;
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
        afterRemove(node);
        size--;
    }

    public boolean contains(E element) {
        return findNode(element) != null;
    }

    /**
     *  通过，前序，中序，后序，层序遍历等手段找到元素
     */
    private Node<E> findNode(E element) {
        if (element == null) {
            return null;
        }
        Stack<Node<E>> nodeStack = new Stack<>();
        Node<E> node = root;
        do {
            while (node != null) {
                nodeStack.push(node);
                node = node.left;
            }

            if (!nodeStack.isEmpty()) {
                Node<E> pop = nodeStack.pop();
                if (pop.element.equals(element)) {
                    return pop;
                }
                node = pop.right;
            }

        } while (node != null || !nodeStack.isEmpty());
        return null;
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }

        return ((Comparable<E>) e1).compareTo(e2);
    }
}
