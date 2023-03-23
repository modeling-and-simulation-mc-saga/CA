
import ca.CA;
import java.io.PrintStream;
import java.io.IOException;
import java.util.Random;

/**
 * コマンドラインからのCAクラスの実行
 *
 * @author tadaki
 */
public class CLIMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int n = 100;//the number of celss
        int rule = 90;//rule number
        int tmax = 100;//time steps
        CA ca = new CA(n,rule, new Random(48L));//CA instance
        ca.initialSingle();//initialize with 1 at the center
        try (PrintStream out = new PrintStream("output.txt")) {
            out.println(ca.state2String());
            for (int t = 0; t < tmax; t++) {
                ca.update();
                out.println(ca.state2String());
            }
        }
    }

}
