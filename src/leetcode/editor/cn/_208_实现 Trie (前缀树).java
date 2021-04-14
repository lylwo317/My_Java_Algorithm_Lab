package leetcode.editor.cn;
//Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼
//写检查。 
//
// 请你实现 Trie 类： 
//
// 
// Trie() 初始化前缀树对象。 
// void insert(String word) 向前缀树中插入字符串 word 。 
// boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false
// 。 
// boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否
//则，返回 false 。 
// 
//
// 
//
// 示例： 
//
// 
//输入
//["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
//[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
//输出
//[null, null, true, false, true, null, true]
//
//解释
//Trie trie = new Trie();
//trie.insert("apple");
//trie.search("apple");   // 返回 True
//trie.search("app");     // 返回 False
//trie.startsWith("app"); // 返回 True
//trie.insert("app");
//trie.search("app");     // 返回 True
// 
//
// 
//
// 提示： 
//
// 
// 1 <= word.length, prefix.length <= 2000 
// word 和 prefix 仅由小写英文字母组成 
// insert、search 和 startsWith 调用次数 总计 不超过 3 * 104 次 
// 
// Related Topics 设计 字典树 
// 👍 592 👎 0

//https://leetcode-cn.com/problems/implement-trie-prefix-tree/

import com.kevin.datastructures.map.HashMap;
import com.kevin.datastructures.map.Map;

class _208_实现_Trie{

//leetcode submit region begin(Prohibit modification and deletion)
class Trie {
    private class Node{
        Node parent;
        Map<Character, Node> children;
        boolean isWord;

        public Node(Node parent) {
            this.parent = parent;
        }
    }

    private Node root = null;

    /** Initialize your data structure here. */
    public Trie() {
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        if (root == null) {
            root = new Node(null);
        }

        Node node = root;
        for (char c : word.toCharArray()) {
            if (node.children == null) {
                node.children = new HashMap<>();
            }

            Node child = node.children.get(c);
            if (child == null) {
                child = new Node(node);
                node.children.put(c, child);
            }
            node = child;
        }

        if (node != root) {
            node.isWord = true;
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node node = searchNode(word);
        return node != null && node.isWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private Node searchNode(String prefix) {
        if (root == null || prefix == null || prefix.length() == 0) {
            return null;
        }
        Node node = root;
        for (char c : prefix.toCharArray()) {
            if (node.children != null) {
                node = node.children.get(c);
                if (node == null) {
                    return null;
                }
            } else {
                return null;
            }
        }
        return node;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
//leetcode submit region end(Prohibit modification and deletion)

}