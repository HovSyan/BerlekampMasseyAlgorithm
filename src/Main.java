/*
 * Created by Hov Sahakyan (syanhovhannes@gmail.com)
 * */

import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int n = getIntegerInput("n");
    int p = getIntegerInput("p");
    int[] s = new int[n];

    for(int i = 0; i < s.length; i++) {
      s[i] = getIntegerInput("s" + i);
    }

    int[] result = computeLinearComplexity(s, p);
    String resultPolynomial = getPolynomialFromArray(result);

    System.out.println("L = " + result.length);
    System.out.println("C(X) = " + resultPolynomial);
  }

  public static int[] computeLinearComplexity(int[] s, int p) {
    return berlecampMessayAlgorithm(s, p);
  }

  private static int[] berlecampMessayAlgorithm(int[] s, int p) {
    final int n = s.length;

    int N = 0;
    int L = 0;
    int m = -1;

    int[] b = new int[n];
    int[] t = new int[n];
    int[] c = new int[n];

    b[0] = 1;
    c[0] = 1;

    while (N < n) {
      int d = s[N];
      for(int i = 1; i <= L; i++) {
        d = ((s[N - i] * c[i]) + d) % p;
      }

      if(d != 0) {
        System.arraycopy(c, 0, t, 0, c.length);

        for(int j = 0; j < n - N + m; j++) {
          c[N - m + j] = (c[N - m + j] + b[j]) % p;
        }

        if(2 * L <= N) {
          L = N + 1 - L;
          m = N;
          System.arraycopy(t, 0, b, 0, t.length);
        }
      }

      N++;
    }

    return Arrays.copyOfRange(c, 1, L + 1);
  }

  private static int getIntegerInput(String variableName) {
    Scanner stdIn = new Scanner(System.in);

    if (variableName != null) {
      System.out.print(variableName + " = ");
    }

    return stdIn.nextInt();
  }

  private static String getPolynomialFromArray(int[] arr) {
    StringBuilder result = new StringBuilder("1");

    for(int i = 0; i < arr.length; i++) {
      if(arr[i] == 1) {
        result.append(" + ").append("x");
        if(i != 0) {
          result.append("^").append(i + 1);
        }
        continue;
      }

      if(arr[i] != 0) {
        result.append(" + ").append(arr[i]).append("x");
        if(i != 0) {
          result.append("^").append(i + 1);
        }
      }
    }

    return result.toString();
  }
}
