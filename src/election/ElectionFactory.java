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

    int i = 1;
    split = line.split(",");
    result.setTotalElectoralVotes(Integer.parseInt(split[i++]));

    for (int j = 0; j < result.getCandidates().size(); j++)
    {
      result.getCandidates().get(j).setVotes(Integer.parseInt(split[i++]));
    }

    result.setTotalVotes(Integer.parseInt(split[i++]));

    return result;
  }
}
