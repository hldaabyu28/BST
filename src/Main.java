import java.util.*;

class Node {
    int data;
    Node left, right;

    Node(int data) {
        this.data = data;
    }
}

class BST {
    Node root;

    // ---------- Insert ----------
    void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.data) node.left = insert(node.left, value);
        else if (value > node.data) node.right = insert(node.right, value);
        return node; // nilai duplikat diabaikan
    }

    // ---------- Traversal ----------
    void preorder(Node n, List<Integer> out) { // Root -> Kiri -> Kanan
        if (n == null) return;
        out.add(n.data);
        preorder(n.left, out);
        preorder(n.right, out);
    }

    void inorder(Node n, List<Integer> out) { // Kiri -> Root -> Kanan
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.data);
        inorder(n.right, out);
    }

    void postorder(Node n, List<Integer> out) { // Kiri -> Kanan -> Root
        if (n == null) return;
        postorder(n.left, out);
        postorder(n.right, out);
        out.add(n.data);
    }

    void printTraversals() {
        List<Integer> pre = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        List<Integer> post = new ArrayList<>();
        preorder(root, pre);
        inorder(root, in);
        postorder(root, post);
        System.out.println("Preorder  : " + pre);
        System.out.println("Inorder   : " + in);
        System.out.println("Postorder : " + post);
    }

    // ---------- Search (menampilkan jalur) ----------
    boolean search(int value) {
        Node cur = root;
        StringBuilder path = new StringBuilder();
        while (cur != null) {
            path.append(cur.data);
            if (value == cur.data) {
                System.out.println("Jalur: " + path + " -> " + value + " DITEMUKAN");
                return true;
            }
            path.append(" -> ");
            if (value < cur.data) {
                System.out.println(value + " < " + cur.data + ", ke kiri");
                cur = cur.left;
            } else {
                System.out.println(value + " > " + cur.data + ", ke kanan");
                cur = cur.right;
            }
        }
        System.out.println("Nilai " + value + " tidak ditemukan");
        return false;
    }

    // ---------- Delete (pengganti: inorder successor) ----------
    void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) return null;
        if (value < node.data) {
            node.left = delete(node.left, value);
        } else if (value > node.data) {
            node.right = delete(node.right, value);
        } else {
            // Kasus 1 dan 2: 0 atau 1 anak
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // Kasus 3: 2 anak -> cari successor (terkecil di subtree kanan)
            Node succ = node.right;
            while (succ.left != null) succ = succ.left;
            System.out.println("Pengganti (successor) : " + succ.data);
            node.data = succ.data;
            node.right = delete(node.right, succ.data);
        }
        return node;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] data = {50, 25, 75, 10, 35, 60, 90, 30, 40};

        BST tree = new BST();
        for (int v : data) tree.insert(v);

        System.out.println("=== Traversal ===");
        tree.printTraversals();

        System.out.println("\n=== Cari 40 ===");
        tree.search(40);

        System.out.println("\n=== Hapus 25 ===");
        tree.delete(25);
        tree.printTraversals();
    }
}
