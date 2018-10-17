import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF wuf;
    private boolean[] openSiteGrid;
    private int size;

    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException();

        this.size = N;
        wuf = new WeightedQuickUnionUF((N * N) + 2);
        openSiteGrid = new boolean[(N * N) + 2];
    }

    private int getIndexOfElementinGrid(int i, int j) {
        if (isIndicesInvalid(i, j)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return (i - 1) * this.size + j;
    }

    private boolean isIndicesInvalid(int i, int j) {
        return (i < 1 || i > this.size || j < 1 || j > this.size);
    }

    private void connect(int i, int j) {
        wuf.union(i, j);
    }

    public void open(int i, int j) {
        if (isIndicesInvalid(i, j)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int index = getIndexOfElementinGrid(i, j);
        if (!openSiteGrid[index]) openSiteGrid[index] = true;
        if (i == 1) connect(index, 0);
        if (i == this.size) wuf.union(index, (this.size * this.size) + 1);
        if (i > 1 && isOpen(i - 1, j)) connect(index, getIndexOfElementinGrid(i - 1, j));
        if (i < this.size && isOpen(i + 1, j)) connect(index, getIndexOfElementinGrid(i + 1, j));
        if (j > 1 && isOpen(i, j - 1)) connect(index, getIndexOfElementinGrid(i, j - 1));
        if (j < this.size && isOpen(i, j + 1)) connect(index, getIndexOfElementinGrid(i, j + 1));
    }

    public boolean isOpen(int i, int j) {
        if (isIndicesInvalid(i, j)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int index = getIndexOfElementinGrid(i, j);
        return openSiteGrid[index];
    }

    public boolean isFull(int i, int j) {
        if (isIndicesInvalid(i, j)) throw new java.lang.IndexOutOfBoundsException();

        int index = getIndexOfElementinGrid(i, j);
        return wuf.connected(index, 0);
    }

    public boolean percolates() {
        return wuf.connected(this.size * this.size + 1, 0);
    }
}
