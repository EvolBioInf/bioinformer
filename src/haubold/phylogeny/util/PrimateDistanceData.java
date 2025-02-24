package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 8, 2003; time: 9:35:33 AM.
 *
 * Description: Source of sample data for phylogeny reconstruction using distance methods.
 */
public class PrimateDistanceData extends DistanceMatrix {
  double[][] distanceMatrix = { { 0, 0.015, 0.045, 0.143, 0.198 }, {
      0.015, 0, 0.03, 0.126, 0.179
    }, {
      0.045, 0.03, 0, 0.092, 0.179
    }, {
      0.143, 0.126, 0.092, 0, 0.179
    }, {
      0.198, 0.179, 0.179, 0.179, 0
    }
  };
  String[] taxa = { "Human", "Chimpanzee", "Gorilla", "Orangutan", "Gibbon" };

  public PrimateDistanceData() {
    super.distanceMatrix = this.distanceMatrix;
    super.taxa = this.taxa;
  }

}
