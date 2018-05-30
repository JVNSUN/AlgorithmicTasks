package optimalBST;

import domain.Film;

public class Node {
    public Film data;
    Node left;
    Node right;
    int size;

    public Node(Film x) { // constructor
        data = x;
        left = null;
        right = null;
    }

    void print() {
        if (this.left != null) {
            this.left.print();
        }
        System.out.println(this.data);
        if (this.right != null) {
            this.right.print();
        }
    }
}
