package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Aug 10, 2003; time: 7:38:01 PM.
 *
 * Description:
 */
public class GlobinDistanceData extends DistanceMatrix {


  double[][] distanceMatrix = {
    {0.00000, 0.19582, 0.92649, 0.95132, 1.70997, 2.21710, 2.93518},
    {0.19582, 0.00000, 1.00069, 0.98124, 1.80166, 2.20537, 2.83780},
    {0.92649, 1.00069, 0.00000, 0.13226, 1.24964, 1.89301, 2.50005},
    {0.95132, 0.98124, 0.13226, 0.00000, 1.33734, 1.97139, 2.53306},
    {1.70997, 1.80166, 1.24964, 1.33734, 0.00000, 2.04978, 2.19454},
    {2.21710, 2.20537, 1.89301, 1.97139, 2.04978, 0.00000, 2.55598},
    {2.93518, 2.83780, 2.50005, 2.53306, 2.19454, 2.55598, 0.00000},
  };

  String[] taxa = { "HBB_HUMAN", "HBB_HORSE", "HBA_HUMAN", "HBA_HORSE",
                    "GLB5_PETMA", "MYG_PHYCA", "LGB2_LUPLU"
                  };

  public GlobinDistanceData() {
    super.distanceMatrix = this.distanceMatrix;
    super.taxa = this.taxa;
  }
}