package com.kevin.datastructures.tree.other;

import com.kevin.datastructures.tree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 红黑树
 * 红黑树是一种平衡德二叉查找树，它有以下特性：
 * 1.每个节点或者是红色，或者是黑色
 * 2.根节点是黑色
 * 3.每个叶子节点是黑色，这里的叶子节点是指为空的叶子节点
 * 4.如果一个节点是红的，那么它的子节点必须是黑的
 * 5.从一个节点到该节点的子孙叶子节点的所有路径上包含相同数目的黑色节点
 * @param <E> 树节点值类型
 */
public class RedAndBlackTree<E extends Comparable<E>> implements BinaryTreeInfo {
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((TreeNode)node).leftChild;
    }

    @Override
    public Object right(Object node) {
        return ((TreeNode)node).rightChild;
    }

    @Override
    public Object string(Object node) {
        return node;
    }

    /**
     * 红黑树节点
     */
    private class TreeNode {
        //父节点
        private TreeNode parent;
        //左孩子
        private TreeNode leftChild;
        //右孩子
        private TreeNode rightChild;
        //节点值
        private E value;
        //颜色
        private String color;

        /**
         * 无参构造函数，默认初始化颜色为红色
         */
        public TreeNode(){
            this.color = "red";
        }

        /**
         * 有参构造函数
         * 默认初始化颜色为红色
         * @param parent 父节点
         * @param leftChild 左孩子
         * @param rightChild 右孩子
         * @param value 节点值
         */
        public TreeNode(TreeNode parent,TreeNode leftChild,TreeNode rightChild,E value,String color){
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.value = value;
            this.color = color;
        }

        @Override
        public String toString() {
            return (isBlack(this) ? "" : "R_") + value;
        }
    }
    //根节点
    private TreeNode root;

    /**
     * 无参构造函数
     */
    public RedAndBlackTree(){

    }

    /**
     * 左旋
     * @param node 需要进行左旋的节点
     */
    public void leftRotate(TreeNode node){
        //新建tmpNode节点，把node的右孩子赋给tmpNode
        TreeNode tmpNode = node.rightChild;
        if(tmpNode == null){
            return;
        }
        TreeNode lchild = tmpNode.leftChild;
        if(lchild != null){
            //将tmpNode的左孩子设为node的右孩子
            node.rightChild = lchild;
            //将tmpNode的左孩子的父亲设为node
            tmpNode.leftChild.parent = node;
        }
        else{
            //将node的右孩子设为空
            node.rightChild = null;
        }
        //将node的父亲设为tmpNode的父亲
        tmpNode.parent = node.parent;
        //判断如果node的父亲是空，则设tmpNode为root节点
        if(node.parent == null){
            this.root = tmpNode;
        }
        //如果node是它父亲的左孩子，那么将tmpNode设为node父亲的左孩子
        else if(node == (node.parent.leftChild)){
            node.parent.leftChild = tmpNode;
        }
        //否则将tmpNode设为node父亲的右孩子
        else{
            node.parent.rightChild = tmpNode;
        }
        //将node设为tmpNode的左孩子
        tmpNode.leftChild = node;
        //将node的父节点设为tmpNode
        node.parent = tmpNode;
    }

    /**
     * 右旋
     * @param node 需要右旋的节点
     */
    public void rightRotate(TreeNode node){
        //新建tmpNode节点，把node的左孩子赋给tmpNode
        TreeNode tmpNode = node.leftChild;
        if(tmpNode == null){
            return;
        }
        TreeNode rchild = tmpNode.rightChild;
        if(rchild != null) {
            //将tmpNode的右孩子设为node的左孩子
            node.leftChild = tmpNode.rightChild;
            //将tmpNode右孩子的父节点设为node
            tmpNode.rightChild.parent = node;
        }
        else{
            //将node的左孩子设为空
            node.leftChild = null;
        }
        //将node的父亲设为tmpNode的父亲
        tmpNode.parent = node.parent;
        //判断如果node的父亲是空，则设tmpNode为root节点
        if(node.parent == null){
            this.root = tmpNode;
        }
        //如果node是它父亲的左孩子，那么将tmpNode设为node父亲的左孩子
        else if(node == (node.parent.leftChild)){
            node.parent.leftChild = tmpNode;
        }
        //否则将tmpNode设为node父亲的右孩子
        else{
            node.parent.rightChild = tmpNode;
        }
        //将node设为tmpNode的右孩子
        tmpNode.rightChild = node;
        //将node的父节点设为tmpNode
        node.parent = tmpNode;
    }

    /**
     * 打印红黑树,根据层次遍历思想，使用两个队列
     * @param tree 需要打印的红黑树
     */
    public void printTree(RedAndBlackTree<E> tree){
        TreeNode root = tree.root;
        if(root == null){
            return;
        }
        //list1队列用作层次遍历用
        LinkedList<TreeNode> list1 = new LinkedList<TreeNode>();
        //list2队列用作最后打印用
        LinkedList<TreeNode> list2 = new LinkedList<TreeNode>();
        //将根节点入队列list1
        list1.addLast(root);
        //如果队列list1不为空，则继续循环
        while(list1.size() != 0){
            //list1出队列
            TreeNode node = list1.removeFirst();
            //将节点存进list2队列
            list2.addLast(node);
            //将节点左右孩子分别入队列list1，如果为空则入节点值为*的
            if(node.leftChild != null){
                list1.addLast(node.leftChild);
            }
            else{
                TreeNode tmpNode = new TreeNode(null,null,null,(E)"*","black");
                list1.addLast(tmpNode);
            }
            if(node.rightChild != null){
                list1.addLast(node.rightChild);
            }
            else{
                TreeNode tmpNode = new TreeNode(null,null,null,(E)"*","black");
                list1.addLast(tmpNode);
            }
            //判断如果队列list1不为空且均为*,代表遍历完成，将list1中剩余节点值均入队列list2中
            boolean flag = true;
            if(list1.size() == 0){
                flag = false;
            }
            for(int num = 0;num < list1.size();num++){
                if(!list1.get(num).value.equals("*")){
                    flag = false;
                    break;
                }
            }
            if(flag == true){
                while(list1.size() != 0){
                    TreeNode tmp = list1.removeFirst();
                    list2.addLast(tmp);
                }
                break;
            }
        }
        //打印list2中最终结果，每打印2的n次方个数打印一个空格（n从0开始递增）
        int i = 1,j = 0;
        while(list2.size() != 0){
            TreeNode treeNode = list2.removeFirst();
            System.out.print(treeNode.value + ":" +treeNode.color  + " ");
            if(i == Math.pow(2,j)){
                System.out.println();
                i = 0;
                j++;
            }
            i++;
        }
        System.out.println();
    }

    /**
     * 插入新节点
     * @param value 要插入节点的值
     */
    public void put(E value){
        TreeNode node = new TreeNode(null,null,null,value,"red");
        TreeNode index = this.root;
        TreeNode parent = null;
        //寻找插入位置
        if(index == null){
            this.root = node;
        }
        else{
            while(index != null){
                parent = index;
                if(value.compareTo(index.value) < 0){
                    index = index.leftChild;
                }
                else if(value.compareTo(index.value) > 0){
                    index = index.rightChild;
                }
                else{
                    return;
                }
            }
            //将node的parent设为parent
            node.parent = parent;
            //如果value小于parent的节点值，则将node置为parent的左孩子，否则置为右孩子
            if(value.compareTo(parent.value) < 0){
                parent.leftChild = node;
            }
            else{
                parent.rightChild = node;
            }
        }
        //进行调整
        insertAdjust(node);
    }

    /**
     * 新增节点后调整
     * @param node 需要调整的节点
     */
    public void insertAdjust(TreeNode node){
        //如果节点为根节点，则将节点颜色变为黑色
        if(this.root == node){
            this.root.color = "black";
        }
        //如果当前节点的父节点颜色为红色
        else if(node.parent != null && node.parent.color.equals("red")){
            //如果父节点是左孩子
            if(node.parent.parent != null && node.parent == node.parent.parent.leftChild){
                //将tmpNode设为当前节点的叔叔节点
                TreeNode tmpNode = node.parent.parent.rightChild;
                //如果叔叔节点是红色
                if(tmpNode != null && tmpNode.color.equals("red")){
                    //将父节点设为黑色
                    node.parent.color = "black";
                    //将叔叔节点设为黑色
                    tmpNode.color = "black";
                    //将祖父节点设为红色
                    tmpNode.parent.color = "red";
                    //将祖父节点设为当前节点
                    node = tmpNode.parent;
                    insertAdjust(node);
                }
                //如果叔叔节点是黑色，而且当前节点是右孩子
                else if(node == node.parent.rightChild){
                    //将父节点设为当前节点，并进行左旋
                    node = node.parent;
                    leftRotate(node);
                    //接着进行调整
                    insertAdjust(node);
                }
                //如果叔叔节点是黑色，而且当前节点是左孩子
                else{
                    //将父节点设为黑色
                    node.parent.color = "black";
                    //将祖父节点设为红色
                    node.parent.parent.color = "red";
                    //将祖父节点进行右旋
                    rightRotate(node.parent.parent);
                }
            }
            else if(node.parent.parent != null){
                //将tmpNode设为当前节点的叔叔节点
                TreeNode tmpNode = node.parent.parent.leftChild;
                //如果叔叔节点是红色
                if(tmpNode != null && tmpNode.color.equals("red")){
                    //将父节点设为黑色
                    node.parent.color = "black";
                    //将叔叔节点设为黑色
                    tmpNode.color = "black";
                    //将祖父节点设为红色
                    tmpNode.parent.color = "red";
                    //将祖父节点设为当前节点
                    node = tmpNode.parent;
                    insertAdjust(node);
                }
                //如果叔叔节点是黑色，而且当前节点是左孩子
                else if(node == node.parent.leftChild){
                    //将父节点设为当前节点，并进行右旋
                    node = node.parent;
                    rightRotate(node);
                    //接着进行调整
                    insertAdjust(node);
                }
                //如果叔叔节点是黑色，而且当前节点是右孩子
                else{
                    //将父节点设为黑色
                    node.parent.color = "black";
                    //将祖父节点设为红色
                    node.parent.parent.color = "red";
                    //将祖父节点进行左旋
                    leftRotate(node.parent.parent);
                }
            }
        }
    }

    /**
     * 删除节点
     * @param value 需要删除的节点值
     */
    public void remove(E value){
        //查找要删除的节点
        TreeNode node = search(this,value);
        //如果node的左孩子和右孩子都不为空，则寻找node的后继结点(右子树的最小节点)，将后继结点赋给replace
        TreeNode replace = null;
        if(node.leftChild != null && node.rightChild != null) {
            replace = node.rightChild;
            while (replace.leftChild != null) {
                replace = replace.leftChild;
            }
        }
        else{
            replace = node;
        }
        TreeNode tmpNode = null;
        //如果replace为叶子节点，则直接删除
        if(replace.leftChild == null && replace.rightChild == null){
            //如果replace为根节点，则直接置空
            if(replace == this.root){
                this.root = null;
                return;
            }
            else {
                //用tmpNode空叶子节点代替replace节点，否则调整会报空指针
                tmpNode = new TreeNode(replace.parent,null,null,(E)"*","black");
                if (replace == replace.parent.leftChild) {
                    replace.parent.leftChild = tmpNode;
                } else {
                    replace.parent.rightChild = tmpNode;
                }
            }
        }
        //如果replace左子树不为空，则用replace左子树替代replace
        else if (replace.leftChild != null){
            tmpNode = replace.leftChild;
            if(replace == this.root){
                this.root = tmpNode;
                tmpNode.parent = null;
            }
            else if(replace == replace.parent.leftChild){
                replace.parent.leftChild = tmpNode;
                tmpNode.parent = replace.parent;
            }
            else{
                replace.parent.rightChild = tmpNode;
                tmpNode.parent = replace.parent;
            }
        }
        //如果replace右子树不为空，则用replace右子树替代replace
        else{
            tmpNode = replace.rightChild;
            if(replace == this.root){
                this.root = tmpNode;
                tmpNode.parent = null;
            }
            else if(replace == replace.parent.leftChild){
                replace.parent.leftChild = tmpNode;
                tmpNode.parent = replace.parent;
            }
            else{
                replace.parent.rightChild = tmpNode;
                tmpNode.parent = replace.parent;
            }
        }
        //如果replace的值不等于node的值，则将repleace的节点值赋给node
        if(replace.value.compareTo(node.value) != 0){
            node.value = replace.value;
        }
        //如果replace为黑色节点，则对tmpNode进行删除调整
        if(replace.color.equals("black")){
            deleteAdjust(tmpNode);
        }
        //如果tmpNode是空节点，则将它删除
        if(tmpNode.value.equals("*")){
            if(tmpNode == tmpNode.parent.leftChild){
                tmpNode.parent.leftChild = null;
            }
            else{
                tmpNode.parent.rightChild = null;
            }
        }
    }

    /**
     * 查找节点
     * @param value 需要查找的节点值
     * @return 节点
     */
    public TreeNode search(RedAndBlackTree<E> tree,E value){
        if(tree.root == null){
            return null;
        }
        if(value.compareTo(tree.root.value) == 0){
            return tree.root;
        }
        else if(value.compareTo(tree.root.value) < 0){
            RedAndBlackTree<E> leftTree = new RedAndBlackTree<E>();
            leftTree.root = tree.root.leftChild;
            return search(leftTree,value);
        }
        else{
            RedAndBlackTree<E> rightTree = new RedAndBlackTree<E>();
            rightTree.root = tree.root.rightChild;
            return search(rightTree,value);
        }
    }

    /**
     * 删除节点后调整
     * @param node 需要调整的节点
     */
    public void deleteAdjust(TreeNode node){
        //如果node是红色节点，则将node设为黑色
        if(node.color.equals("red")){
            node.color = "black";
//            checkRBTreeProperties();
        }
        //如果node是黑色节点，且不是根节点
        else if(node != this.root){
            //如果node是父亲的左孩子
            if(node == node.parent.leftChild){
                //将node父亲的右孩子赋给tmpNode
                TreeNode tmpNode = node.parent.rightChild;
                //如果node的兄弟节点tmpNode为红色
                if(tmpNode.color.equals("red")){
                    //将node的兄弟tmpNode设为黑色
                    tmpNode.color = "black";
                    //将node的父亲设为红色
                    node.parent.color = "red";
                    //将node的父亲进行左旋
                    leftRotate(node.parent);
                    //重新将tmpNode设为node的兄弟
                    tmpNode = node.parent.rightChild;
                }
                //如果node的兄弟节点tmpNode是黑色，而且tmpNode两个孩子节点都是黑色
                if(tmpNode.color.equals("black") && (tmpNode.leftChild == null || tmpNode.leftChild.color.equals("black")) && (tmpNode.rightChild == null || tmpNode.rightChild.color.equals("black"))){
                    //将node的兄弟节点设为红色，递归对node节点的父亲进行操作
                    tmpNode.color = "red";
                    deleteAdjust(node.parent);
                }
                //如果node的兄弟节点tmpNode是黑色，而且tmpNode的左孩子是红色，右孩子是黑色
                if(tmpNode.color.equals("black") && tmpNode.leftChild != null && tmpNode.leftChild.color.equals("red") && (tmpNode.rightChild == null || tmpNode.rightChild.color.equals("black"))){
                    //将tmpNode的左孩子设为黑色
                    tmpNode.leftChild.color = "black";
                    //将tmpNode设为红色
                    tmpNode.color = "red";
                    //将tmpNode进行右旋
                    rightRotate(tmpNode);
                    //重新将tmpNode设为node的兄弟节点
                    tmpNode = node.parent.rightChild;
                }
                //如果node的兄弟节点tmpNode是黑色，而且tmpNode的右孩子是红色
                if(tmpNode.color.equals("black") && tmpNode.rightChild != null && tmpNode.rightChild.color.equals("red")){
                    //将node父节点的颜色赋给兄弟节点tmpNode
                    tmpNode.color = node.parent.color;
                    //将node的父节点设为黑色
                    node.parent.color = "black";
                    //将tmpNode的右孩子设为黑色
                    tmpNode.rightChild.color = "black";
                    //对node的父亲进行左旋
                    leftRotate(node.parent);
                }
            }
            //如果node是父亲的右孩子
            else{
                //将node父亲的左孩子赋给tmpNode
                TreeNode tmpNode = node.parent.leftChild;
                //如果node的兄弟节点tmpNode为红色
                if(tmpNode.color.equals("red")){
                    //将node的兄弟tmpNode设为黑色
                    tmpNode.color = "black";
                    //将node的父亲设为红色
                    node.parent.color = "red";
                    //将node的父亲进行右旋
                    rightRotate(node.parent);
                    //重新将tmpNode设为node的兄弟
                    tmpNode = node.parent.leftChild;
                }
                //如果node的兄弟节点tmpNode是黑色，而且tmpNode两个孩子节点都是黑色
                if(tmpNode.color.equals("black") && (tmpNode.leftChild == null || tmpNode.leftChild.color.equals("black")) && (tmpNode.rightChild == null || tmpNode.rightChild.color.equals("black"))){
                    //将node的兄弟节点设为红色，递归对node节点的父亲进行操作
                    tmpNode.color = "red";
                    deleteAdjust(node.parent);
                }
                //如果node的兄弟节点tmpNode是黑色，而且tmpNode的右孩子是红色，左孩子是黑色
                if(tmpNode.color.equals("black") && tmpNode.rightChild != null && tmpNode.rightChild.color.equals("red") && (tmpNode.leftChild == null || tmpNode.leftChild.color.equals("black"))){
                    //将tmpNode的右孩子设为黑色
                    tmpNode.rightChild.color = "black";
                    //将tmpNode设为红色
                    tmpNode.color = "red";
                    //将tmpNode进行左旋
                    leftRotate(tmpNode);
                    //重新将tmpNode设为node的兄弟节点
                    tmpNode = node.parent.leftChild;
                }
                //如果node的兄弟节点tmpNode是黑色，而且tmpNode的左孩子是红色
                if(tmpNode.color.equals("black") && tmpNode.leftChild != null && tmpNode.leftChild.color.equals("red")){
                    //将node父节点的颜色赋给兄弟节点tmpNode
                    tmpNode.color = node.parent.color;
                    //将node的父节点设为黑色
                    node.parent.color = "black";
                    //将tmpNode的左孩子设为黑色
                    tmpNode.leftChild.color = "black";
                    //对node的父亲进行右旋
                    rightRotate(node.parent);
                }
            }
        }
//        checkRBTreeProperties();
    }

    private boolean isBlack(TreeNode node) {
        if (node == null) {
            return true;
        }

        return node.color.equals("black");
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

        TreeNode calHeightNode = root;
        while (calHeightNode != null) {
            if (isBlack(calHeightNode)) {
                rootNodeBlackHeight++;
            }
            calHeightNode = calHeightNode.leftChild;
        }
        rootNodeBlackHeight++;//加入NIL节点到统计的总数中

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {

            TreeNode node = nodeQueue.poll();

            if (node.leftChild == null || node.rightChild == null) {//计算度为1或者度为0的节点到根节点的黑色节点数量是否等于rootNodeBlackHeight，以及是否存在两个连续红色节点
                TreeNode iteratorNode = node;
                int blackCount = 0;
                String preColor = "black";
                while (iteratorNode != null) {//往根节点遍历，看是否存在连续两个红色节点
                    if (isBlack(iteratorNode)) {
                        blackCount++;//统计黑色节点数量
                    } else {//当前遍历的节点是红色
                        if (preColor.equals("red")) {//前一个节点也是红色，不满足性质(4)
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

            if (node.leftChild != null) {
                nodeQueue.offer(node.leftChild);
            }

            if (node.rightChild != null) {
                nodeQueue.offer(node.rightChild);
            }
        }
    }

}