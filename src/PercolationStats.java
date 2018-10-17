import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] res;
    private int openSites;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();

        res = new double[trials];

        for (int i = 0; i < trials; i++) {
            res[i] = calculate(n);
        }
    }    // perform trials independent experiments on an n-by-n grid

    private double calculate(int n) {
        this.openSites = 0;
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int i = StdRandom.uniform(n) + 1;
            int j = StdRandom.uniform(n) + 1;
            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
                openSites++;
            }
        }
        return (double) openSites / (n * n);
    }

    public double mean() {
        return StdStats.mean(res);
    }                          // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(res);
    }                       // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(res.length));
    }                 // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(res.length));
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("The mean is-->" + percolationStats.mean());
        System.out.println("The standard deviation is-->" + percolationStats.stddev());
        System.out.println("The 95% confidence interval is-->" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }      // test client (described below)
}
