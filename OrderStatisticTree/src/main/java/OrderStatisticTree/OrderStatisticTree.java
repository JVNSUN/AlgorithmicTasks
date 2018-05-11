package OrderStatisticTree;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static OrderStatisticTree.OSTNode.nullNode;

public class OrderStatisticTree<T extends Comparable<T>> {

    private OSTNode<T> root;

    public OrderStatisticTree() {
        root = OSTNode.nullNode;
    }

    public int size() {
        return this.root.size;
    }

    public OSTNode<T> find(T x) {
        if (root.isEmpty()) return root;
        return root.find(x);
    }

    public void insert(T x) { // call recursive insert
        OSTNode<T> nnd = new OSTNode<>(x); // make new node

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
            root.size--;
        } else if (root.right.isEmpty()) {
            root = root.left;
            root.isRed = false;
            root.size--;
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

    public T osSelect(int i) {
        return osSelect(root, i);
    }

    public int osRank(OSTNode<T> x) {
        return x.left.size + 1 + osRank(x, root);
    }

    private int osRank(OSTNode<T> x, OSTNode<T> root) {
        int rank = 0;
        OSTNode<T> y = root;
        while (y != x) {
            if (x.data.compareTo(y.data) < 0)
                y = y.left;
            else {
                rank += (y.left.size + 1);
                y = y.right;
            }
        }
        return rank;
    }

    public void printTreeToFile() {
        final int height = (int)Math.ceil(Math.log(root.size)/Math.log(2)) + 2;
        final int width = 16 * (int)Math.pow(2, Math.ceil(Math.log(root.size)/Math.log(2))) + 10;
        int len = width * height * 2 + 2;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 1; i <= len; i++)
            sb.append(i < len - 2 && i % width == 0 ? "\n" : ' ');

        printTreeToFileR(sb, width / 2, 1, width / 4, width, root, " ");
        try {
            Files.delete(Paths.get("tree.txt"));
        } catch (IOException e) {
            System.out.println("Created a file <<tree.txt>> in the working directory");
        }
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("tree.txt"));
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printTreeToFileR(StringBuilder sb, int c, int r, int d, int w, OSTNode<T> n,
                          String edge) {
        if (n != null) {
            printTreeToFileR(sb, c - d, r + 2, d / 2, w, n.left, " /");

            String s = n.label();
            int idx1 = r * w + c - (s.length() + 1) / 2;
            int idx2 = idx1 + s.length();
            int idx3 = idx1 - w;
            if (idx2 < sb.length())
                sb.replace(idx1, idx2, s).replace(idx3, idx3 + 2, edge);

            printTreeToFileR(sb, c + d, r + 2, d / 2, w, n.right, "\\ ");
        }
    }

    private boolean insert(OSTNode<T> nnd, OSTNode<T> t, OSTNode<T> par) {
        // return true iff t is red and t has a red child
        if (par != OSTNode.nullNode)
            par.size++;
        if (nnd.data.compareTo(t.data) < 0) {
            if (t.left.isEmpty()) {
                t.left = nnd; //attach new node as leaf
                t.size++;
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
                t.size++;
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
        par.size--;
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

    private T osSelect(OSTNode<T> x, int i) {
        int r = x.left.size + 1;
        if (i == r)
            return x.data;
        else if (i < r)
            return osSelect(x.left, i);
        else
            return osSelect(x.right, i-r);
    }
}