package ca;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class for one dimensional cellular automata
 *
 * periodic boundaries
 *
 * values of cells are {0,1}
 *
 * @author tadaki
 */
abstract public class AbstractCA {

    protected final int cells[];
    protected final int n;//the number of cells

    protected int numDifference = 0;//the number of updated cells per une update
    protected int numR;//the number of cells with value 1
    protected final Random random;
    protected int rule = 0;//rule number

    /**
     *
     * @param n the number of cells
     * @param random
     */
    public AbstractCA(int n, Random random) {
        this.n = n;
        cells = new int[n];
        this.random = random;
    }

    /**
     * Setting rule number
     *
     * @param rule ルール番号
     */
    abstract public void setRule(int rule);

    /**
     * Random initialization where the probability of 1 is 1/2
     */
    public void initialize() {
        initialize(0.5);
    }

    /**
     * Random initialization where the probability of 1 is r
     *
     * @param r
     */
    public void initialize(double r) {
        numR = (int) (n * r);
        if (numR > n) {
            numR = n;
        }
        for (int i = 0; i < n; i++) {
            cells[i] = 0;
        }
        if (r <= 0) {
            initialSingle();
        } else {
            //array containing {0,...,n-1} in random order
            int ar[] = createRandomNumberList(n);
            for (int i = 0; i < numR; i++) {
                cells[ar[i]] = 1;
            }
        }
    }

    /**
     * return array containing {0,...,n-1} in random order
     *
     *
     * @param n
     * @return
     */
    protected int[] createRandomNumberList(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return shuffle(list);
    }

    /**
     * return array with given elements in random order
     *
     * @param list
     * @return
     */
    protected int[] shuffle(List<Integer> list) {
        int m = list.size();
        int output[] = new int[m];
        for (int i = 0; i < m; i++) {
            int k = random.nextInt(list.size());
            int v = list.remove(k);
            output[i] = v;
        }
        return output;
    }

    /**
     * Initialize cells with 1 at the center and 0 for others
     */
    public void initialSingle() {
        int k = n / 2 + 1;
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
        cells[k] = 1;
    }

    /**
     * Update states of cells
     *
     * @return
     */
    abstract public int[] update();

    /**
     * Convert states to string: 0->" ", 1->"*";
     *
     * @return
     */
    public String state2String() {
        String symbol[] = {" ", "*"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            sb.append(symbol[cells[i]]);
        }
        return sb.toString();
    }

    //**** setters and getters
    public int getRule() {
        return rule;
    }

    public int[] getCells() {
        return cells;
    }

    public int getN() {
        return n;
    }

    public int getNumDifference() {
        return numDifference;
    }

    public int getNumR() {
        return numR;
    }

}
