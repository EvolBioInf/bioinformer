package haubold.coalescent;

import java.util.*;

class NumericalTools {
  static Random ran = new Random();
  static double oldm = -1.0;
  static double alxm = 0.0;
  static double g = 0.0;
  static double sq = 0.0;

  public static double nextPoisson(double xm) {
    double em = 0.0;
    double t = 0.0;
    double y = 0.0;
    if (xm < 12.0) {
      if (xm != oldm) {
        oldm = xm;
        g = Math.exp(-xm);
      }
      em = -1.0;
      t = 1.0;
      do {
        ++em;
        t *= ran.nextDouble();
      } while (t > g);
    } else {
      if (xm != oldm) {
        oldm = xm;
        sq = Math.sqrt(2.0 * xm);
        alxm = Math.log(xm);
        g = xm * alxm - logGamma(xm + 1.0);
      }
      do {
        do {
          y = Math.tan(Math.PI * ran.nextDouble());
          em = sq * y + xm;
        } while (em < 0.0);
        em = Math.floor(em);
        t =
          0.9
          * (1.0 + y * y)
          * Math.exp(em * alxm - logGamma(em + 1.0) - g);
      }
      while (ran.nextDouble() > t);
    }
    return em;
  }

  public static double logGamma(double xx) {
    double gammln;
    int j;
    double tmp, x, y, ser;
    final double stp = 2.5066282746310005;
    final double[] cof = {
      76.18009172947146,
      -86.50532032941677,
      24.01409824083091,
      -1.231739572450155,
      0.1208650973866179e-2,
      -0.5395239384953e-5
    };
    x = xx;
    y = x;
    tmp = x + 5.5;
    tmp = (x+0.5)*Math.log(tmp)-tmp;
    ser = 1.000000000190015;
    for(j=0; j<cof.length; j++) {
      y += 1.0;
      ser += cof[j]/y;
    }
    gammln = tmp + Math.log(stp*ser/x);
    return gammln;
  }
}
