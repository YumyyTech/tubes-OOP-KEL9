/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.ram.qwfdgdnfnhv;

/**
 *
 * @author rank
 */
import javax.swing.*;
import java.awt.*;

public class ChartPanel extends JPanel {

    private Poll poll;
    private Color[] colors = {
        new Color(52, 152, 219), new Color(231, 76, 60), new Color(46, 204, 113),
        new Color(155, 89, 182), new Color(241, 196, 15), new Color(26, 188, 156),
        new Color(230, 126, 34), new Color(149, 165, 166), new Color(52, 73, 94),
        new Color(22, 160, 133)
    };

    public ChartPanel(Poll poll) {
        this.poll = poll;
        setPreferredSize(new Dimension(500, 250));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int total = poll.getTotalVotes();
        if (total == 0) {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("Belum ada vote", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }

        int max = 1;
        for (int i = 0; i < poll.getOptions().length; i++) {
            max = Math.max(max, poll.getVoteCount(i));
        }

        int barWidth = Math.max(40, (getWidth() - 100) / poll.getOptions().length - 10);
        int x = 60;

        for (int i = 0; i < poll.getOptions().length; i++) {
            int height = poll.getVoteCount(i) * 150 / max;
            
            // Gambar batang grafik dengan gradient
            GradientPaint gradient = new GradientPaint(
                x, getHeight() - 40 - height, colors[i % colors.length],
                x, getHeight() - 40, colors[i % colors.length].darker()
            );
            g2d.setPaint(gradient);
            g2d.fillRect(x, getHeight() - 40 - height, barWidth, height);
            
            // Border batang
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, getHeight() - 40 - height, barWidth, height);

            // Label opsi
            String optionText = poll.getOptions()[i];
            if (optionText.length() > 10) {
                optionText = optionText.substring(0, 10) + "...";
            }
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            
            // Putar teks 45 derajat jika terlalu panjang
            FontMetrics fm = g2d.getFontMetrics();
            if (fm.stringWidth(optionText) > barWidth) {
                g2d.rotate(Math.toRadians(45), x + barWidth/2, getHeight() - 20);
                g2d.drawString(optionText, x - 10, getHeight() - 20);
                g2d.rotate(Math.toRadians(-45), x + barWidth/2, getHeight() - 20);
            } else {
                g2d.drawString(optionText, x + (barWidth - fm.stringWidth(optionText))/2, getHeight() - 20);
            }

            // Jumlah vote di atas batang
            String voteCount = String.valueOf(poll.getVoteCount(i));
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            int textWidth = g2d.getFontMetrics().stringWidth(voteCount);
            g2d.drawString(voteCount, x + (barWidth - textWidth)/2, getHeight() - 45 - height);

            x += barWidth + 25;
        }
    }
}