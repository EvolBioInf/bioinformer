package haubold.probabilitydistribution;

import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;

public class ExtremeValueDistribution extends AbstractXYDataset implements XYDataset {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  double lambda = 1;
  double mu = 0;

  /** Use the translate to change the data and demonstrate dynamic data changes. */
  private double translate;

  /**
   * Default constructor.
   */
  public ExtremeValueDistribution(double lambda, double mu) {
    this.lambda = lambda;
    this.mu = mu;
    this.translate = 0.0;
  }

  /**
   * Returns the translation factor.
   *
   * @return  the translation factor.
   */
  public double getTranslate() {
    return this.translate;
  }

  /**
   * Sets the translation constant for the x-axis.
   *
   * @param translate  the translation factor.
   */
  public void setTranslate(double translate) {
    this.translate = translate;
    notifyListeners(new DatasetChangeEvent(this, this));
  }

  /**
   * Returns the x-value for the specified series and item.  Series are numbered 0, 1, ...
   *
   * @param series  the index (zero-based) of the series.
   * @param item  the index (zero-based) of the required item.
   *
   * @return the x-value for the specified series and item.
   */
  public Number getX(int series, int item) {
    return Double.valueOf(-3.0 + this.translate + (item / 3.0));
  }

  /**
   * Returns the y-value for the specified series and item.  Series are numbered 0, 1, ...
   *
   * @param series  the index (zero-based) of the series.
   * @param item  the index (zero-based) of the required item.
   *
   * @return the y-value for the specified series and item.
   */
  public Number getY(int series, int item) {
    double x, y;

    x = -3.0 + this.translate + (item / 3.0);

    if (series == 0) {
      y = lambda*Math.exp((mu-x)*lambda-Math.exp((mu-x)*lambda));
    }
    else {
      y = Math.exp(-Math.exp(mu-x)*lambda);
    }
    return Double.valueOf(y);
  }

  /**
   * Returns the number of series in the dataset.
   *
   * @return the number of series in the dataset.
   */
  public int getSeriesCount() {
    return 2;
  }

  /**
   * Returns the key for a series.
   *
   * @param series  the index (zero-based) of the series.
   *
   * @return The key for the series.
   */
  public Comparable getSeriesKey(int series) {
    if (series == 0) {
      return "Probability Density Function";
    }
    else if (series == 1) {
      return "Cumulative Density Function";
    }
    else {
      return "Error";
    }
  }

  /**
   * Returns the number of items in the specified series.
   *
   * @param series  the index (zero-based) of the series.
   * @return the number of items in the specified series.
   *
   */
  public int getItemCount(final int series) {
    return 35;
  }

  public double getLambda() {
    return lambda;
  }

  public void setLambda(double lambda) {
    notifyListeners(new DatasetChangeEvent(this, this));
    this.lambda = lambda;
  }

  public double getMu() {
    return mu;
  }

  public void setMu(double mu) {
    notifyListeners(new DatasetChangeEvent(this, this));
    this.mu = mu;
  }

}
