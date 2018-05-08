package tree;

public class RedBlackTree {

    private TreeNode Root;

    public TreeNode getRoot() {
        return Root;
    }

    // Constructs a new empty red-black tree.
    public RedBlackTree() {
        Root = TreeNode.nullnode;
    }
    public void clear() {
        Root = TreeNode.nullnode;
    }

    public boolean isEmpty() {
        return Root.isEmpty();
    }

    public int size() {
        return Root.size();
    }

    public TreeNode find(int key) {
        if (Root.isEmpty()) return Root;
        return Root.find(key);
    }

    public void checkRedBlack() {
        if (Root.isRed)
            System.out.println("The root is red");
        Root.bheight();
    }

    public void insert(int id) { // call recursive insert
        TreeNode nnd = new TreeNode(id); // make new node

        if (Root.isEmpty()) Root = nnd;
        else if (id < Root.iData) {
            if (Root.left.isEmpty())
                Root.left = nnd;
            else if (insert(nnd, Root.left, Root)) {
                // red-red violation exists at Root.left and its children
                if (Root.right.isRed) { // red uncle case:
                    Root.right.isRed = Root.left.isRed = false;
                } else { // black uncle case:			    	   
                    Root.isRed = true;
                    if (nnd.iData < Root.left.iData) {
                        Root = Root.rotateToRight();
                    } else {
                        Root = Root.doubleRotateToRight();
                    }
                } // else
            }
        } else if (Root.right.isEmpty()) {
            Root.right = nnd;
        } else if (insert(nnd, Root.right, Root)) {
            // red-red violation exists at Root.right and its children
            if (Root.left.isRed) { // red uncle case:
                Root.right.isRed = Root.left.isRed = false;
            } else { // black uncle case:		    	   
                Root.isRed = true;
                if (nnd.iData >= Root.right.iData) {
                    Root = Root.rotateToLeft();
                } else {
                    Root = Root.doubleRotateToLeft();
                }
            } // else
        }
        Root.isRed = false;
    } // end insert()



    public void remove(int id) {
        if (Root.isEmpty()) return;
        if (id < Root.iData) {
            if (remove(id, Root.left, Root, TreeNode.nullnode)) {
                Root.isRed = false;
            }
        } else if (id > Root.iData) {
            if (remove(id, Root.right, Root, TreeNode.nullnode)) {
                Root.isRed = false;
            }
        } else if (Root.left.isEmpty()) {
            Root = Root.right;
            Root.isRed = false;
        } else if (Root.right.isEmpty()) {
            Root = Root.left;
            Root.isRed = false;
        } else { // Two children
            int maxValueInLeft = Root.left.findMax().iData;
            Root.iData = maxValueInLeft;
            if (remove(maxValueInLeft, Root.left, Root, TreeNode.nullnode)) {
                Root.isRed = false;
            }
        }
    }

    /* *************************************************** *
     *  PRIVATE METHODS                                    *
     * *************************************************** */

    /* Inserts a node into tree and returns a boolean */
    private boolean insert(TreeNode nnd, TreeNode t, TreeNode par) {
        // return true iff t is red and t has a red child

        if (nnd.iData < t.iData) {
            if (t.left.isEmpty()) {
                t.left = nnd; //attach new node as leaf
            } else if (insert(nnd, t.left, t)) {
                // red-red violation exists at t.left and its children
                if (t.right.isRed) { // red uncle case:
                    t.right.isRed = t.left.isRed = false;
                    t.isRed = true;
                } else { // black uncle case:
                    TreeNode nt;

                    if (nnd.iData < t.left.iData) {
                        nt = t.rotateToRight();
                    } else {
                        nt = t.doubleRotateToRight();
                    }
                    t.isRed = true;
                    nt.isRed = false;
                    if (nt.iData < par.iData) par.left = nt;
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
                    TreeNode nt;

                    if (nnd.iData >= t.right.iData) {
                        nt = t.rotateToLeft();
                    } else {
                        nt = t.doubleRotateToLeft();
                    }
                    t.isRed = true;
                    nt.isRed = false;
                    if (nt.iData < par.iData) par.left = nt;
                    else par.right = nt;
                } // else
            }

            return (t.isRed && t.right.isRed);
        } // else
    } // end insert
    
    private boolean remove(int id, TreeNode t, TreeNode par, TreeNode gpar) {
        if (t.isEmpty()) return false;
        if (id < t.iData) {
            if (remove(id, t.left, t, par)) {
                return fixRBDelete(t, par, gpar);
            }
        } else if (id > t.iData) {
            if (remove(id, t.right, t, par)) {
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
            int maxValueInLeft = t.left.findMax().iData;
            t.iData = maxValueInLeft;
            if (remove(maxValueInLeft, t.left, t, par)) {
                return fixRBDelete(t, par, gpar);
            }
        }
        return false;
    }

    private boolean fixRBDelete(TreeNode t, TreeNode par, TreeNode gpar) {
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
                    Root = par.rotateToLeft();
                    gpar = Root;
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
                    Root = par.rotateToLeft();
                Root.isRed = false;
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
                    Root = par.rotateToRight();
                    gpar = Root;
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
                    Root = par.rotateToRight();
                Root.isRed = false;
                return false;
            }
        }
        return false;
    }
    
}