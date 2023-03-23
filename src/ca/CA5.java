package ca;

import java.util.Random;

/**
 *
 * One dimensional cellular automata
 *
 * v = {0,1}, r = 2, periodic boundary
 *
 * @author tadaki
 */
public class CA5 extends AbstractCA {

    private int[] ruleMap;//map corresponding the rule number

    /**
     * @param n the number of celss
     * @param rule rule number
     * @param random
     */
    public CA5(int n, int rule, Random random) {
        super(n, random);
        this.rule = rule % (int) Math.pow(2, 32);
        ruleMap = mkRuleMap(this.rule);//generating rule map
    }

    /**
     * Setting rule number
     *
     * @param rule
     */
    @Override
    public void setRule(int rule) {
        this.rule = rule % (int) Math.pow(2, 32);
        ruleMap = mkRuleMap(this.rule);
    }

    /**
     * Generating rule map
     *
     * @param ruleNumber
     * @return
     */
    public static int[] mkRuleMap(int ruleNumber) {
        int numDigit = 32;
        int newRuleMap[] = new int[numDigit];
        for (int i = 0; i < numDigit; i++) {//checking ruleNumber from lower bits
            //lowest bit
            int r = ruleNumber % 2;
            //store the value at newRuleMap[i]
            newRuleMap[i] = r;
            //divide by 2
            ruleNumber /= 2;
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
        for (int i = 0; i < n; i++) {
            //evaluating cell numbers for left and right neighbors
            //note periodic boundaries
            int right2 = (i + 2) % n;//right
            int right = (i + 1) % n;//right
            int left = (i - 1 + n) % n;//left
            int left2 = (i - 2 + n) % n;//left
            //k= 16*s_{i-2}+8*s_{i-1}+4*s{i}+2*s_{i+1}+s_{i+2}
            int k = 16 * cells[left2] + 8 * cells[left]
                    + 4 * cells[i] + 2 * cells[right] + cells[right2];
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
        //改行コード(OS毎に異なる)
        String nl = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append("rule-").append(ruleMap2Int(ruleMapDummy)).append(nl);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            int r = 16 * i + 8 * j + 4 * k + 2 * l + m;
                            sb.append("(").append(i).append(j).append(k).
                                    append(l).append(m).append(")->");
                            sb.append(ruleMapDummy[r]);
                            sb.append(nl);
                        }
                    }
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
        for (int i = 0; i < 32; i++) {
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
        int n = 150;//the number of cells
        int tmax = 150;//time steps
        int ruleNumber = 393410541;//rule number
        CA5 sys = new CA5(n, ruleNumber, new Random(48L));
        sys.initialize(0.1);
        //print ruleMap
        System.out.println(CA5.ruleMap2String(sys.getRuleMap()));
        //time evolution
        for (int t = 0; t < tmax; t++) {
            System.out.println(sys.state2String());
            sys.update();
        }
    }
}
