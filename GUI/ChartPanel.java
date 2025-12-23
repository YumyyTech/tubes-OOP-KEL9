import backend.Poll;
import javax.swing.*;
import java.awt.*;

public class ChartPanel extends JPanel {

    private Poll poll;
    private final Color[] colors = {
        Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA
    };

    public ChartPanel(Poll poll) {
        this.poll = poll;
        setPreferredSize(new Dimension(500, 250));
        setBackground(Color.WHITE);
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int total = poll.getTotalVotes();
        if (total == 0) {
            g.drawString("Belum ada vote", 200, 120);
            return;
        }

        int max = 1;
        for (int i = 0; i < poll.getOptions().length; i++) {
            max = Math.max(max, poll.getVoteCount(i));
        }

        int x = 50;
        for (int i = 0; i < poll.getOptions().length; i++) {
            int h = poll.getVoteCount(i) * 150 / max;
            g.setColor(colors[i % colors.length]);
            g.fillRect(x, 200 - h, 60, h);
            g.setColor(Color.BLACK);
            g.drawString(poll.getOptions()[i], x, 220);
            g.drawString("" + poll.getVoteCount(i), x + 20, 190 - h);
            x += 90;
        }
    }
}
