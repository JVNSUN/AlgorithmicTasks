import CLI.*;
import tree.RedBlackTree;

public class Main {
    public static void main(String[] args) {
        //Menu.start();
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(23);
        redBlackTree.insert(41);
        redBlackTree.remove(23);
        redBlackTree.checkRedBlack();
        System.out.println(redBlackTree.size());
    }
}
