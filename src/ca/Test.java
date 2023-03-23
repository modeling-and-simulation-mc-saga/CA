package ca;

/**
 *
 * @author tadaki
 */
public class Test {
    public static void main(String args[]){
        int x = 107;
        int n = 32;
        int a[]=d2b(x,n);
        for(int i=n-1;i>=0;i--){
            System.out.print(a[i]);
        }
        System.out.println();
        System.out.println(b2d(a,n));
    }
    
    public static int[] d2b(int x,int n){
        String s = Integer.toBinaryString(x);
        System.out.println(s);
        int output[] = new int[n];
        for (int i=0;i<s.length();i++){
            String c = String.valueOf(s.charAt(i));
            output[s.length()-1-i] = Integer.parseInt(c);
        }
        return output;
    }
    
    public static int b2d(int x[],int n){
        StringBuilder sb= new StringBuilder();
        for (int i=n-1;i>=0;i--){
            sb.append(x[i]);
        }
        String s = sb.toString();
        int v = Integer.parseInt(s,2);
        return v;
    }
}
