package election;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ElectionFactory
{
  public static FederalElection createFederalElection(InputStream in) throws IOException
  {
    BufferedReader reader;
    FederalElection result;
    String line;
    String[] split;

    if (in == null)
      throw new IOException("could not find input file!");

    result = new FederalElection();
    reader = new BufferedReader(new InputStreamReader(in));

    result.setTitle(reader.readLine());
    // skip header
    reader.readLine();
    line = reader.readLine();

    while (line != null && !line.startsWith("State"))
    {
      result.addCandidate(Candidate.parseCandidate(line));
      line = reader.readLine();
    }

    line = reader.readLine();

    while (line != null && !line.startsWith("Totals"))
    {
      result.addState(StateElection.parseStateElection(line, result.getCandidates()));
      line = reader.readLine();
    }

    split = line.split(",");

    for (int i = 0; i < result.getCandidates().size(); i++)
    {
      result.getCandidates().get(i).setVotes(Integer.parseInt(split[i + 2]));
    }

    return result;
  }
}
