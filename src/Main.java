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

    Polynomial resultPolynomial = berlecampMessayAlgorithm(s, p);

    System.out.printf("L = %d\n", resultPolynomial.length() - 1);
    System.out.printf("C(X) = %s\n", resultPolynomial);
  }

  private static Polynomial berlecampMessayAlgorithm(int[] s, int p) {
    Polynomial c = new Polynomial(new int[]{1});
    Polynomial b = new Polynomial(new int[]{1});

    int L = 0;
    int z = 1;
    int n = 0;
    int delta = 1;

    while(n < s.length) {
      int d = s[n];
      for (int i = 1; i <= L; i++) {
        d += c.getCoefficients()[i] * s[n - i];
      }
      d %= p;

      if (d != 0) {
        Polynomial t = new Polynomial(c.getCoefficients());

        int deltaInverse = getInverse(delta, p);
        c = new Polynomial(Polynomial.subtract(c,
          Polynomial.multiply(b, Polynomial.getOneVariablePolynomial(d * deltaInverse, z), p), p
        ).getCoefficients());

        if (2 * L <= n) {
          L = n + 1 - L;
          z = 1;
          b = new Polynomial(t.getCoefficients());
          delta = d;
        } else {
          z++;
        }
      } else {
        z++;
      }

      n++;
    }

    return c;
  }

  private static int getIntegerInput(String variableName) {
    Scanner stdIn = new Scanner(System.in);

    if (variableName != null) {
      System.out.print(variableName + " = ");
    }

    return stdIn.nextInt();
  }

  public static int getInverse(int n, int modulo) {
    for(int i = 1; i < modulo; i++) {
      if((i * n) % modulo == 1) {
        return i;
      }
    }

    return 0;
  }
}
