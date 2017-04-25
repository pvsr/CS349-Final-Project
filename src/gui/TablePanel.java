package gui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import election.Candidate;
import election.FederalElection;
import election.StateElection;

/**
 * A panel that detailed election results.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class TablePanel extends JPanel
{
  /**
   * Explicit value constructor.
   *
   * @param election The election to show
   */
  public TablePanel(FederalElection election)
  {
    super(new BorderLayout());

    List<Candidate> cands;
    List<StateElection> states;
    Map<Candidate, Integer> results;
    String[] columnNames;
    Object[][] data;
    int i, j;
    int columns;

    cands = election.getCandidates();
    states = election.getStates();
    columns = 3 + 2 * cands.size();

    i = 0;
    columnNames = new String[columns];
    columnNames[i++] = "State";
    columnNames[i++] = "Electoral votes";

    for (Candidate cand : cands)
    {
      columnNames[i++] = cand.getPresCand();
      columnNames[i++] = "Vote %";
    }

    columnNames[i++] = "Total votes";

    data = new Object[states.size()][];

    i = 0;
    for (StateElection state : states)
    {
      j = 0;
      data[i] = new Object[columns];
      results = state.getResults();
      data[i][j++] = state.getState().getName();
      data[i][j++] = state.getElectoralVotes();
      for (Candidate cand : cands)
      {
        data[i][j++] = results.get(cand).intValue();
        data[i][j++] = String.format("%.2f%%",
            100.0 * results.get(cand).intValue() / state.getTotalVotes());
      }

      data[i][j++] = state.getTotalVotes();
      i++;
    }

    JTable table = new JTable(data, columnNames);
    table.setFillsViewportHeight(true);
    JScrollPane scrollPane = new JScrollPane(table);

    add(scrollPane, BorderLayout.CENTER);
  }
}
