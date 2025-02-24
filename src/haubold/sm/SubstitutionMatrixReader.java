package haubold.sm;
import java.io.*;

/**
 * Description: Read Substitution Matrices.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 24, 2004; time: 4:01:45 PM.
 */
public class SubstitutionMatrixReader {
  String matrixPath = "haubold/resources/substitutionmatrices/";

  public SubstitutionMatrixReader() {

  }

  public SubstitutionMatrix getSubstitutionMatrix(String name) throws Exception {
    String line;
    String[] arguments;
    int numValues = 24;
    int[][] values;
    int i,j,n;
    SubstitutionMatrix sm = new SubstitutionMatrix();
    InputStream stream = ClassLoader.getSystemResourceAsStream(matrixPath + name);
    BufferedReader br = new BufferedReader(new InputStreamReader(stream));
    line = br.readLine();
    // Read through header
    while(br.ready() && line.matches("^#.*")) {
      line = br.readLine();
    }
    // Read amino acids
    arguments = line.split("\\s+");
    sm.aminoAcids = new String[arguments.length-1];
    for(i=0,n=arguments.length-1; i<n; i++) {
      sm.aminoAcids[i] = arguments[i+1];
    }
    values = new int[numValues][numValues+1];
    i=0;
    while(br.ready() && i<values.length) {
      line = br.readLine();
      arguments = line.split("\\s+");
      for(j=1,n=arguments.length; j<n; j++) {
        values[i][j-1] = Integer.parseInt(arguments[j]);
      }
      i++;
    }
    sm.setValues(values);
    return sm;
  }

  /**
   * @return
   */
  public String getMatrixPath() {
    return matrixPath;
  }

  /**
   * @param string
   */
  public void setMatrixPath(String string) {
    matrixPath = string;
  }

}
