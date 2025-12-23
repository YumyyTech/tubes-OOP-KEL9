import backend.;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class VotingFrame extends JFrame {

    private Poll poll;
    private Voter voter;
    private final ButtonGroup group = new ButtonGroup();
    private ChartPanel chartPanel;
    private DefaultTableModel tableModel;

    public VotingFrame() {
        login();
        initPoll();
        initGUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    poll.saveToFile(new File("autosave_poll.txt"));
                } catch (IOException ignored) {}
            }
        });
    }

    private void login() {
        String name = JOptionPane.showInputDialog("Masukkan nama Anda:");
        if (name == null || name.trim().isEmpty()) System.exit(0);
        voter = new Voter(name);
    }

    private void initPoll() {
        File f = new File("autosave_poll.txt");
        try {
            poll = f.exists()
                ? Poll.loadFromFile(f)
                : new Poll("Bahasa Pemrograman Favorit?",
                    new String[]{"Java", "Python", "C++", "JavaScript", "PHP"});
        } catch (IOException e) {
            poll = new Poll("Bahasa Pemrograman Favorit?",
                new String[]{"Java", "Python", "C++", "JavaScript", "PHP"});
        }
    }

    private void initGUI() {
        setTitle("Sistem Voting Sederhana");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createVotePanel(), BorderLayout.WEST);
        add(createResultPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createVotePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder("Voting"));

        p.add(new JLabel(poll.getQuestion()));

        for (int i = 0; i < poll.getOptions().length; i++) {
            JRadioButton rb = new JRadioButton(poll.getOptions()[i]);
            rb.setActionCommand(String.valueOf(i));
            group.add(rb);
            p.add(rb);
        }

        JButton vote = new JButton("Vote");
        vote.addActionListener(e -> doVote());
        p.add(vote);

        return p;
    }

    private JPanel createResultPanel() {
        JPanel p = new JPanel(new BorderLayout());

        chartPanel = new ChartPanel(poll);
        p.add(chartPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"Opsi", "Jumlah Vote"}, 0
        );
        JTable table = new JTable(tableModel);
        refreshTable();

        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private JPanel createBottomPanel() {
        JPanel p = new JPanel();

        JButton save = new JButton("Save");
        JButton load = new JButton("Load");

        save.addActionListener(e -> savePoll());
        load.addActionListener(e -> loadPoll());

        p.add(save);
        p.add(load);
        return p;
    }

    private void doVote() {
        try {
            if (!voter.canVote()) throw new Exception("Anda sudah voting!");
            if (group.getSelection() == null) throw new Exception("Pilih salah satu opsi!");

            int idx = Integer.parseInt(group.getSelection().getActionCommand());
            poll.addVote(idx);
            voter.setVoted();

            refreshTable();
            chartPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < poll.getOptions().length; i++) {
            tableModel.addRow(new Object[]{
                poll.getOptions()[i],
                poll.getVoteCount(i)
            });
        }
    }

    private void savePoll() {
        try {
            poll.saveToFile(new File("manual_save.txt"));
        } catch (IOException ignored) {}
    }

    private void loadPoll() {
        try {
            poll = Poll.loadFromFile(new File("manual_save.txt"));
            chartPanel.setPoll(poll);
            refreshTable();
            voter = new Voter(voter.username);
            group.clearSelection();
        } catch (IOException ignored) {}
    }
}