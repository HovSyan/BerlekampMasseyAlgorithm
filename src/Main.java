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
    Polynomial c = new Polynomial(new int[]{1});  // result register properties
    Polynomial b = new Polynomial(new int[]{1});

    int L = 0;  // result register length
    int z = 1;
    int n = 0;  // current check position in s[] sequence
    int delta = 1;

    while(n < s.length) {
      int d = s[n];
      for (int i = 1; i <= L; i++) {
        d += c.getCoefficients()[i] * s[n - i];
      }
      d %= p;

      // s[L] = -(c[L] * s[0] + ... + c[1] * s[L - 1]).
      // d = s[L] + (c[L] * s[0] + ... + c[1] * s[L - 1]).
      // So d == 0 means that C(x) register properties generate
      // s[0], s[1], ..., s[L - 1], s[L] sequence and need to check the
      // next.
      // d != 0 means that C(x) register properties fail on s[L],
      // hence must be replaced.
      if (d != 0) {
        Polynomial t = c;

        int deltaInverse = getInverse(delta, p);
        c = new Polynomial(Polynomial.subtract(
          c,
          Polynomial.multiply(b, Polynomial.getOneVariablePolynomial(d * deltaInverse, z), p), p
        ).getCoefficients());

        if (2 * L <= n) {
          L = n + 1 - L;
          z = 1;
          b = t;
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

  public static int getInverse(int a, int m) {
    // Euclidean algorithm (extended)
    int m0 = m;
    int y = 1, x = 0;

    while (m > 1)
    {
      int q = m / a;
      int t = a;

      a = m % a;
      m = t;
      t = y;

      y = x - q * y;
      x = t;
    }

    // Make x positive
    if (x < 0)
      x += m0;

    return x;
  }
}
