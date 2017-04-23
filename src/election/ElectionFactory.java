package election;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A factory that builds FederalElections.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ElectionFactory
{
  /**
   * Explicit value constructor.
   * 
   * @param in The location of the input file.
   * @return The new FederalElection.
   * @throws IOException
   */
  public static FederalElection createFederalElection(InputStream in)
      throws IOException
  {
    BufferedReader reader;
    InputStreamReader isr;
    FederalElection result;
    String line;
    String[] split;

    if (in == null)
      throw new IOException("could not find input file!");

    result = new FederalElection();
    isr = new InputStreamReader(in);
    reader = new BufferedReader(isr);

    // use only the first entry of the line
    result.setTitle(reader.readLine().split(",")[0]);

    // skip header
    reader.readLine();
    line = reader.readLine();

    while (line != null && !line.startsWith("State"))
    {
      result.addCandidate(Candidate.parseCandidate(line));
      line = reader.readLine();
    }

    // skip header
    line = reader.readLine();

    while (line != null && !line.startsWith("Totals"))
    {
      result.addState(
          StateElection.parseStateElection(line, result.getCandidates()));
      line = reader.readLine();
    }

    int i = 1;
    split = line.split(",");
    result.setTotalElectoralVotes(Integer.parseInt(split[i++]));

    for (int j = 0; j < result.getCandidates().size(); j++)
    {
      result.getCandidates().get(j).setVotes(Integer.parseInt(split[i++]));
    }

    result.setTotalVotes(Integer.parseInt(split[i++]));

    reader.close();
    isr.close();
    in.close();

    return result;
  }
}
