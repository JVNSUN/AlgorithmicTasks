package optimalBST;

import domain.Film;

import java.util.ArrayList;

public class OptimalBST {
    public ArrayList<Node> data = new ArrayList<>();

    private Node root;

    public int size() {
        return this.root.size;
    }

    public void print() {
        this.root.print();
    }


    private Integer roots[][];
    private ArrayList<Node> p, q;

    public void build() {
        int n = data.size() / 2;

        Double e[][] = new Double[n+2][n+1];
        Double w[][] = new Double[n+2][n+1];
        roots = new Integer[n+1][n+1];

        p = new ArrayList<>();
        p.add(null);
        q = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i%2 == 0) {
                q.add(data.get(i));
            }
            else {
                p.add(data.get(i));
            }
        }

        for (int i = 1; i < n+2; i++) {
            e[i][i-1] = q.get(i-1).data.getProbability();
            w[i][i-1] = q.get(i-1).data.getProbability();
        }

        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n-l+1; i++) {
                int j = i + l - 1;
                e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j-1] + p.get(j).data.getProbability() + q.get(j).data.getProbability();
                for (int r = i; r <= j; r++) {
                    double t = e[i][r-1] + e[r+1][j] + w[i][j];
                    if (t < e[i][j]) {
                        e[i][j] = t;
                        roots[i][j] = r;
                    }
                }
            }
        }

        root = buildTree(1, roots.length - 1);
    }

    private Node buildTree(int i, int j) {
        if (i > j) {
            return q.get(j);
        }
        int index = roots[i][j];
        Node iRoot = p.get(index);
        iRoot.left = buildTree(i, index-1);
        iRoot.right = buildTree(index+1, j);
        return iRoot;
    }

    public Film find(Film film) {
        Node current = root;
        while (true) {
            if (film.compareTo(current.data) == 0) {
                return current.data;
            }
            else if (film.compareTo(current.data) < 0) {
                current = current.left;
                if (current.left == null) {
                    return current.data;
                }
            }
            else {
                current = current.right;
                if (current.left == null) {
                    return current.data;
                }
            }
        }
    }
}