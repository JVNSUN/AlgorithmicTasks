import OrderStatisticTree.OrderStatisticTree;

public class Main {
    public static void main(String[] args) {
        //Menu.start();
        OrderStatisticTree<Integer> redBlackTree = new OrderStatisticTree<Integer>();
        redBlackTree.insert(23);
        redBlackTree.insert(41);
        redBlackTree.insert(42);
        redBlackTree.insert(12);
        redBlackTree.print();
    }
}
