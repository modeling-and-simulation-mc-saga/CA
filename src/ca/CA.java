package ca;

import java.util.Random;

/**
 * One dimensional cellular automata
 * 
 * v = {0,1}, r = 1, periodic boundary
 *
 * @author tadaki
 */
public class CA extends AbstractCA{

    private int[] ruleMap;//map corresponding the rule number

    /**
     * @param n the number of celss
     * @param rule rule number
     * @param random
     */
    public CA(int n, int rule, Random random) {
        super(n,random);
        this.rule = rule % 256;
        ruleMap = mkRuleMap(this.rule);//generating rule map
    }

    /**
     * Setting rule number
     *
     * @param rule
     */
    @Override
    public void setRule(int rule) {
        this.rule = rule % 256;
        ruleMap = mkRuleMap(this.rule);
    }

    /**
     * Generating rule map
     *
     * @param ruleNumber
     * @return
     */
    public final int[] mkRuleMap(int ruleNumber) {
        int newRuleMap[] = new int[8];
        for (int i = 0; i < 8; i++) {//checking ruleNumber from lower bits
            //lowest bit
            int r = 1 & ruleNumber;
            //store the value at newRuleMap[i]
            newRuleMap[i] = r;
            //right bit shift (divide by 2)
            ruleNumber = ruleNumber >> 1;
        }
        return newRuleMap;
    }

    /**
     * update states of celss
     *
     * @return 
     */
    @Override
    public int[] update() {
        int cellsDummy[] = new int[n];//dummy cells
        for (int i = 0; i < n; i++) {//Describe source in this loop
            //evaluating cell numbers for left and right neighbors
            //note periodic boundaries
            int right = (i + 1) % n;//right neighbor
            int left = (i - 1 + n) % n;//left neighbor
            //k= 4*s_{i-1}+2*s{i}+s_{i+1}
            int k = 4 * cells[left] + 2 * cells[i] + cells[right];
            //store the next state in cellsDummy
            cellsDummy[i] = ruleMap[k];
        }

        //Count the number of cells which the value changes
        numDifference = 0;
        for (int i = 0; i < n; i++) {
            numDifference += (cells[i] + cellsDummy[i]) % 2;
        }
        //Copy dummy to cell
        System.arraycopy(cellsDummy, 0, cells, 0, n);
        return cellsDummy;
    }


    /**
     * Converting ruleMap to string
     *
     * @param ruleMapDummy
     * @return
     */
    public static String ruleMap2String(int ruleMapDummy[]) {
        String nl = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append("rule-").append(ruleMap2Int(ruleMapDummy)).append(nl);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    int r = 4 * i + 2 * j + k;
                    sb.append("(").append(i).append(j).append(k).append(")->");
                    sb.append(ruleMapDummy[r]);
                    sb.append(nl);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Convert ruleMap to rule number
     *
     * @param ruleMapDummy
     * @return
     */
    public static int ruleMap2Int(int ruleMapDummy[]) {
        int k = 0;
        int m = 1;
        for (int i = 0; i < 8; i++) {
            k += m * ruleMapDummy[i];
            m *= 2;
        }
        return k;
    }


    public int[] getRuleMap() {
        return ruleMap;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 100;//the number of cells
        int tmax = 100;//the time steps
        int ruleNumber = 90;//rule number
        CA sys = new CA(n,ruleNumber, new Random(48L));
        sys.initialSingle();
//        sys.initialize(0.6);
        //print ruleMap
        System.out.println(CA.ruleMap2String(sys.getRuleMap()));
        //time evolution
        for (int t = 0; t < tmax; t++) {
            System.out.println(sys.state2String());
            sys.update();
        }
    }
}
