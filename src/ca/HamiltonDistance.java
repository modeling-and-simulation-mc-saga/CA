package ca;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author tadaki
 */
public class HamiltonDistance {

    private final CA sys;
    private final int[] initialCells;
    private final List<Integer> distance;
    private final List<Integer> distanceFromInitial;
    private final List<String> history;

    public HamiltonDistance(int n, int ruleNumber) {
        sys = new CA(n,ruleNumber, new Random(48L));
        sys.initialize(0.5);
        initialCells = getCellCopy();
        distance = Collections.synchronizedList(new ArrayList<>());
        distanceFromInitial = Collections.synchronizedList(new ArrayList<>());
        history = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Integer> evalDistance(int tmax) {
        for (int t = 0; t < tmax; t++) {
            update();
        }
        return distance;
    }

    private int[] getCellCopy() {
        int[] cellsd = sys.getCells();
        int[] cells = new int[sys.getN()];
        System.arraycopy(cellsd, 0, cells, 0, cellsd.length);
        return cells;
    }

    public void update() {
        int[] cells = getCellCopy();
        sys.update();
        int[] cellsNew = sys.getCells();
        int s = 0;
        for (int i = 0; i < cells.length; i++) {
            s += (cells[i] + cellsNew[i]) % 2;
        }
        distance.add(s);
        int ss = 0;
        for (int i = 0; i < cells.length; i++) {
            ss += (initialCells[i] + cellsNew[i]) % 2;
        }
        history.add(sys.state2String());
        distanceFromInitial.add(ss);
    }

    public List<Integer> getDistanceFromInitial() {
        return distanceFromInitial;
    }

    public List<String> getHistory() {
        return history;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int n = 100;
        int tmax = 100;
        int rules[] = {90, 94, 54, 132};
        for (int ruleNumber : rules) {
            HamiltonDistance sys = new HamiltonDistance(n, ruleNumber);
            List<Integer> list = sys.evalDistance(tmax);
            List<Integer> list2 = sys.getDistanceFromInitial();
            String filename = "distance-" + String.valueOf(ruleNumber) + ".txt";
            try (PrintStream out = new PrintStream(filename)) {
                out.println("# rule-" + String.valueOf(ruleNumber));
                for (int i = 0; i < list.size(); i++) {
                    out.println((i + 1) + " " + list.get(i) + " " + list2.get(i));
                }
            }
            filename = "history-" + String.valueOf(ruleNumber) + ".txt";
            List<String> history = sys.getHistory();
            try (PrintStream out = new PrintStream(filename)) {
                history.forEach(h -> out.println(h));
            }
        }
    }

}
