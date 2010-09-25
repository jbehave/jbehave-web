package org.jbehave.web.webdriver;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class LocalSwingNotifier implements Notifier {

    private JFrame frame;
    private JLabel label;
    private int width;
    private int height;
    private int x;
    private int y;

    /**
     * Builder-style way to set the preferred size for the notification frame
     * @param width width
     * @param height height
     * @return the notifier
     */
    public LocalSwingNotifier sized(final int width, final int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Builder-style way to set the preferred location for the notification frame
     * @param x x position on screen
     * @param y y position on screen
     * @return the notifier
     */
    public LocalSwingNotifier located(final int x, final int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * New Notifier frame of default size - 380 * 85
     */
    public LocalSwingNotifier() {
        sized(380, 85);
        located(0, 0); // origin by default
    }

    private void initialize() {
        frame = new JFrame();
        label = new JLabel();
        frame.setAlwaysOnTop(true);
        frame.setSize(width, height);
        frame.setLocation(x, y);
        frame.setUndecorated(true);
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new BorderLayout());
        label.setBorder(new EmptyBorder(3,3,3,3));
        panel.add(label, BorderLayout.CENTER);

        MouseInputAdapter mia = new MouseInputAdapter() {
            private Point mousePressedScreenCoords;
            private Point mousePressedCompCoords;
            public void mouseReleased(MouseEvent e) {
                mousePressedScreenCoords = null;
                mousePressedCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mousePressedScreenCoords = e.getLocationOnScreen();
                mousePressedCompCoords = e.getPoint();
            }
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                x = mousePressedScreenCoords.x + (currCoords.x - mousePressedScreenCoords.x) - mousePressedCompCoords.x;
                y = mousePressedScreenCoords.y + (currCoords.y - mousePressedScreenCoords.y) - mousePressedCompCoords.y;
                frame.setLocation(x,y);
            }
        };

        frame.addMouseListener(mia);
        frame.addMouseMotionListener(mia);

        frame.setVisible(true);
    }

    public synchronized void notify(String message) {
        if (frame == null) {
            initialize();
        }
        label.setText("<html>" + message + "</html>");
    }

    public synchronized void close() {
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
            frame = null;
            label = null;
        }
    }
}
