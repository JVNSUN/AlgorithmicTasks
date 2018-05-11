package OrderStatisticTree;

public class OSTNode<T extends Comparable<T>> {
    T data;
    boolean isRed;
    OSTNode<T> left;
    OSTNode<T> right;
    int size;

    static OSTNode nullNode;

    static {
        nullNode = new OSTNode(null);
        nullNode.isRed = false;
        nullNode.left = null;
        nullNode.right = null;
        nullNode.size = 0;
    }

    public boolean isEmpty() {
        return (this == nullNode);
    }

    public OSTNode(T x) { // constructor
        data = x;
        isRed = true;
        left = nullNode;
        right = nullNode;
        size = 1;
    }

    public OSTNode<T> find(T x) { // non-RECURSIVE find node with given key
        OSTNode<T> current = this;
        while (true) {
            if (current == nullNode) return current;
            if (current.data.compareTo(x) == 0) return current;
            else if (current.data.compareTo(x) < 0)
                current = current.right;
            else current = current.left;
        }
    } // end find()

    public String colorChar() {
        return (this.isRed ? "r" : "b" );
    }

    String label() {
        if (this == nullNode)
            return "nil";
        return this.size + this.colorChar() + " " + this.trimTitle();
    }

    private String trimTitle() {
        if (this.data.toString().length() > 10)
                return this.data.toString().substring(0, 10);

        return this.data.toString();
    }

    void print() {
        if (this.left != nullNode) {
            this.left.print();
        }
        System.out.println(this.data);
        if (this.right != nullNode) {
            this.right.print();
        }
    }

    public OSTNode<T> rotateToRight() {
        OSTNode<T> p = this.left; // left child of this node
        this.left = p.right;
        p.right = this;
        p.size = this.size;
        this.size = this.left.size + this.right.size + 1;
        return p;
    }

    public OSTNode<T> rotateToLeft() {
        OSTNode<T> p = this.right;
        this.right = p.left;
        p.left = this;
        p.size = this.size;
        this.size = this.left.size + this.right.size + 1;
        return p;
    }

    public OSTNode<T> doubleRotateToRight() {
        this.left = this.left.rotateToLeft();
        return this.rotateToRight();
    }

    public OSTNode<T> doubleRotateToLeft() {
        this.right = this.right.rotateToRight();
        return this.rotateToLeft();
    }


    public OSTNode<T> findMax() { // return the node with maximum key
        if (right != nullNode) return right.findMax();
        return this;
    }
}