package OrderStatisticTree;

class OSTNode<T extends Comparable<T>> {
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
    }

    boolean isEmpty() {
        return (this == nullNode);
    }

    public OSTNode(T x) { // constructor
        data = x;
        isRed = true;
        left = nullNode;
        right = nullNode;
        size = 1;
    }

    public int size() {
        if (this == nullNode) return 0;
        else return 1 + left.size() + right.size();
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

    public OSTNode<T> insert(T x) {
        if (this == nullNode) return new OSTNode<T>(x);
        if (x.compareTo(this.data) < 0) {
            this.left = this.left.insert(x);
        } else { // branch right
            this.right = this.right.insert(x);
        }
        return this;
    } // end insert

    public OSTNode<T> remove(T x) {
        if (this == nullNode) return nullNode; // Item not found; do nothing
        if (x.compareTo(this.data) < 0) {
            this.left = this.left.remove(x);
        } else if (x.compareTo(this.data) > 0) {
            this.right = this.right.remove(x);
        } else if (this.left == nullNode) {
            return this.right;
        } else if (this.right == nullNode) {
            return this.left;
        } else { // Two children
            this.data = (this.left).deleteMax(this).data;
        }
        return this;
    }

    void print() {
        if (this.left != nullNode) {
            this.left.print();
        }
        System.out.println(this.data + " " + this.size + this.isRed);
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

    public OSTNode<T> deleteMax(OSTNode<T> par) {
        // delete the link from parent par and return the node with maximum key
        if (right != nullNode) return right.deleteMax(this);
        if (par.right == this) par.right = left;
        else par.left = left;
        return this;
    }

}