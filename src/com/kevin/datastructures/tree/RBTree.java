package com.kevin.datastructures.tree;

import java.util.Comparator;

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
            return str + element.toString();
        }
    }

    private int colorOf(Node<E> node) {
        return node == null ? RBNode.BLACK : ((RBNode<E>)node).color;
    }

    private boolean isBlack(Node<E> node){
        return colorOf(node) == RBNode.BLACK;
    }

    private boolean isRed(Node<E> node){
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
                    red(grand);
                    if (isBlack(parent.sibling())) {//叔叔节是时空。没有溢出
                        //旋转操作
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
                    } else {//叔叔节点红色，溢出
                        // 红 <- 黑 -> 红 -> new红, new红 <- 红 <- 黑 -> 红
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
     *
     * @param node
     * @param hasReplace 处理溢出的时候，防止将子节点染黑，导致不平衡
     */
    private void afterRemove(Node<E> node, boolean hasReplace){
        //添加到根节点，或者上溢到根节点
//        Node<E> currentNode;
//        currentNode = node;
        if (node.parent == null) {//删除的是根节点。出现这种情况，只有两种：一种是只有根节点，一种是下溢导致根节点被删除

        } else {//非根节点
            if (isBlack(node)) {//删除的是黑色节点
                //1. 红<-黑   2. 黑->红   3. 黑
                if (isBlack(node.left) && isBlack(node.right) || !hasReplace) {//度为0的黑色节点
                    //需要找兄弟节点借，如果兄弟节点没有，就找父节点借，节点会下溢
                    Node<E> parent = node.parent;
                    Node<E> sibling = parent.left != null ? parent.left : parent.right;

                    if (isRed(sibling)) {
                        red(parent);
                        black(sibling);
                        if (sibling.isLeftChild()) {
                            rotateRight(parent);
                        } else {
                            rotateLeft(parent);
                        }
                        sibling = sibling.right;
                    }

                    //兄弟节点都是黑色的
                    if (sibling.isLeaf()) {//兄弟无法借出，需要从父节点借
                        if (isRed(parent)) {
                            black(parent);
                            red(sibling);
                        } else {
                            red(sibling);
                            afterRemove(parent, false);
                        }
                    } else {//兄弟可以借
                        if (sibling.isLeftChild()) {//L
                            if (sibling.right != null) {//LR
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
                            if (sibling.left != null) {//RL
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
                    }
                } else {//度为1的黑色节点
                    if (node.left != null) {
                        black(node.left);
                    } else {
                        black(node.right);
                    }
                }

            } else {//删除的是红色节点，不用调整，因为不会影响红黑树的性质
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        afterRemove(node, true);
    }
}
