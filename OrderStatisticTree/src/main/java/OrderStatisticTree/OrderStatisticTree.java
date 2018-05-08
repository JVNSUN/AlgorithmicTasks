package OrderStatisticTree;

import static OrderStatisticTree.OSTNode.nullNode;

public class OrderStatisticTree<T extends Comparable<T>> {

    private OSTNode<T> root;

    public OrderStatisticTree() {
        root = OSTNode.nullNode;
    }
    public void clear() {
        root = nullNode;
    }

    public boolean isEmpty() {
        return root.isEmpty();
    }

    public int size() {
        return root.size();
    }

    public OSTNode<T> find(T x) {
        if (root.isEmpty()) return root;
        return root.find(x);
    }

    public void insert(T x) { // call recursive insert
        OSTNode<T> nnd = new OSTNode<T>(x); // make new node

        if (root.isEmpty()) root = nnd;
        else if (x.compareTo(root.data) < 0) {
            if (root.left.isEmpty()) {
                root.left = nnd;
                root.size++;
            }
            else if (insert(nnd, root.left, root)) {
                // red-red violation exists at root.left and its children
                if (root.right.isRed) { // red uncle case:
                    root.right.isRed = root.left.isRed = false;
                } else { // black uncle case:			    	   
                    root.isRed = true;
                    if (nnd.data.compareTo(root.left.data) < 0) {
                        root = root.rotateToRight();
                    } else {
                        root = root.doubleRotateToRight();
                    }
                } // else
            }
        } else if (root.right.isEmpty()) {
            root.right = nnd;
            root.size++;
        } else if (insert(nnd, root.right, root)) {
            // red-red violation exists at root.right and its children
            if (root.left.isRed) { // red uncle case:
                root.right.isRed = root.left.isRed = false;
            } else { // black uncle case:		    	   
                root.isRed = true;
                if (nnd.data.compareTo(root.right.data) >= 0) {
                    root = root.rotateToLeft();
                } else {
                    root = root.doubleRotateToLeft();
                }
            } // else
        }
        root.isRed = false;
    } // end insert()

    public void remove(T x) {
        if (root.isEmpty()) return;
        if (x.compareTo(root.data) < 0) {
            if (remove(x, root.left, root, nullNode)) {
                root.isRed = false;
            }
        } else if (x.compareTo(root.data) > 0) {
            if (remove(x, root.right, root, nullNode)) {
                root.isRed = false;
            }
        } else if (root.left.isEmpty()) {
            root = root.right;
            root.isRed = false;
        } else if (root.right.isEmpty()) {
            root = root.left;
            root.isRed = false;
        } else { // Two children
            T maxValueInLeft = root.left.findMax().data;
            root.data = maxValueInLeft;
            if (remove(maxValueInLeft, root.left, root, nullNode)) {
                root.isRed = false;
            }
        }
    }

    public void print() {
        this.root.print();
    }



    private boolean insert(OSTNode<T> nnd, OSTNode<T> t, OSTNode<T> par) {
        // return true iff t is red and t has a red child
        if (par != OSTNode.nullNode)
            par.size++;
        if (nnd.data.compareTo(t.data) < 0) {
            if (t.left.isEmpty()) {
                t.left = nnd; //attach new node as leaf
            } else if (insert(nnd, t.left, t)) {
                // red-red violation exists at t.left and its children
                if (t.right.isRed) { // red uncle case:
                    t.right.isRed = t.left.isRed = false;
                    t.isRed = true;
                } else { // black uncle case:
                    OSTNode<T> nt;

                    if (nnd.data.compareTo(t.left.data) < 0) {
                        nt = t.rotateToRight();
                    } else {
                        nt = t.doubleRotateToRight();
                    }
                    t.isRed = true;
                    nt.isRed = false;
                    if (nt.data.compareTo(par.data) < 0)
                        par.left = nt;
                    else par.right = nt;
                } //if
            }

            return (t.isRed && t.left.isRed);
        } else { // branch right
            if (t.right.isEmpty()) {
                t.right = nnd; // attach new node as leaf
            } else if (insert(nnd, t.right, t)) {
                // red-red violation exists at t.right and its children
                if (t.left.isRed) { // red uncle case:
                    t.right.isRed = t.left.isRed = false;
                    t.isRed = true;
                } else { // black uncle case:
                    OSTNode<T> nt;

                    if (nnd.data.compareTo(t.right.data) >= 0) {
                        nt = t.rotateToLeft();
                    } else {
                        nt = t.doubleRotateToLeft();
                    }
                    t.isRed = true;
                    nt.isRed = false;
                    if (nt.data.compareTo(par.data) < 0) par.left = nt;
                    else par.right = nt;
                } // else
            }

            return (t.isRed && t.right.isRed);
        } // else
    } // end insert
    
    private boolean remove(T x, OSTNode<T> t, OSTNode<T> par, OSTNode<T> gpar) {
        if (t.isEmpty()) return false;
        if (x.compareTo(t.data) < 0) {
            if (remove(x, t.left, t, par)) {
                return fixRBDelete(t, par, gpar);
            }
        } else if (x.compareTo(t.data) > 0) {
            if (remove(x, t.right, t, par)) {
                return fixRBDelete(t, par, gpar);
            }
        } else if (t.left.isEmpty()) {
            if (t == par.left)
                par.left = t.right;
            else
                par.right = t.right;
            if (t.isRed)
                return false;
            else if (t.right.isRed) {
                t.right.isRed = false;
                return false;
            } else {
                return fixRBDelete(t.right, par, gpar);
            }
        } else if (t.right.isEmpty()) {
            if (t == par.left)
                par.left = t.left;
            else
                par.right = t.left;
            if (t.isRed)
                return false;
            else if (t.left.isRed) {
                t.left.isRed = false;
                return false;
            } else {
                return fixRBDelete(t.left, par, gpar);
            }
        } else {
            T maxValueInLeft = t.left.findMax().data;
            t.data = maxValueInLeft;
            if (remove(maxValueInLeft, t.left, t, par)) {
                return fixRBDelete(t, par, gpar);
            }
        }
        return false;
    }

    private boolean fixRBDelete(OSTNode<T> t, OSTNode<T> par, OSTNode<T> gpar) {
        if (t.isRed) {
            t.isRed = false;
            return false;
        }
        if (t == par.left) {
            if (par.right.isRed) {
                par.right.isRed = false;
                par.isRed = true;
                if (par == gpar.left) {
                    gpar.left = par.rotateToLeft();
                    gpar = gpar.left;
                } else if (par == gpar.right) {
                    gpar.right = par.rotateToLeft();
                    gpar = gpar.left;
                } else {
                    root = par.rotateToLeft();
                    gpar = root;
                }
            }
            if (!par.right.left.isRed && !par.right.right.isRed) {
                par.right.isRed = true;
                if (par.isRed) {
                    par.isRed = false;
                    return false;
                }
                return true;
            }
            if (!par.right.right.isRed) {
                par.right.left.isRed = false;
                par.right.isRed = true;
                par.right = par.right.rotateToRight();
            }
            if (par.right.right.isRed) {
                par.right.isRed = par.isRed;
                par.isRed = false;
                par.right.right.isRed = false;
                if (par == gpar.left)
                    gpar.left = par.rotateToLeft();
                else if (par == gpar.right)
                    gpar.right = par.rotateToLeft();
                else
                    root = par.rotateToLeft();
                root.isRed = false;
                return false;
            }
        } else {
            if (par.left.isRed) {
                par.left.isRed = false;
                par.isRed = true;
                if (par == gpar.left) {
                    gpar.left = par.rotateToRight();
                    gpar = gpar.left;
                } else if (par == gpar.right) {
                    gpar.right = par.rotateToRight();
                    gpar = gpar.right;
                } else {
                    root = par.rotateToRight();
                    gpar = root;
                }
            }
            if (!par.left.left.isRed && !par.left.right.isRed) {
                par.left.isRed = true;
                if (par.isRed) {
                    par.isRed = false;
                    return false;
                }
                return true;
            }
            if (!par.left.left.isRed) {
                par.left.right.isRed = false;
                par.left.isRed = true;
                par.left = par.left.rotateToLeft();
            }
            if (par.left.left.isRed) {
                par.left.isRed = par.isRed;
                par.isRed = false;
                par.left.left.isRed = false;
                if (par == gpar.left)
                    gpar.left = par.rotateToRight();
                else if (par == gpar.right)
                    gpar.right = par.rotateToRight();
                else
                    root = par.rotateToRight();
                root.isRed = false;
                return false;
            }
        }
        return false;
    }
}