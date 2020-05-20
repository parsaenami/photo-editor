package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 2017/05/29 ,0029.
 */
public class Crop extends JPanel {
    BufferedImage image, selectedRect;
    JPanel jpanel;
    JFrame frame;
    private Shape shape = null;
    private Point endDrag;
    private Point startDrag;
    private ImagePanel.IClickListener listener;
    double fx, fy;


    interface IClickListener {
        void onCroppedListener(BufferedImage image);
    }

    public Crop(BufferedImage image, JPanel panel, JFrame frame, double fx, double fy) throws IOException {
//        jpanel = this;
//        this.jpanel = panel;
        this.image = image;
        this.fx = fx;
        this.fy = fy;

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startDrag = new Point((int) ((double) e.getX() / fx), (int) ((double) e.getY() / fy));
                endDrag = startDrag;
                System.out.println("Mouse Pressed");
                repaint();

            }

            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released");
                if (endDrag != null && startDrag != null) {
                    try {
                        shape = makeRectangle((int) ((double) startDrag.x / fx), (int) ((double) startDrag.y / fy), (int) ((double) e.getX() / fx), (int) ((double) e.getY() / fy));
                        listener.onCroppedListener(image.getSubimage(
                                (int) ((double) startDrag.x / fx) - Math.abs((int) ((double) e.getX() / fx) - (int) ((double) startDrag.x / fx)),
                                (int) ((double) startDrag.y / fy) - Math.abs((int) ((double) e.getY() / fy) - (int) ((double) startDrag.y / fy)),
                                Math.abs((int) ((double) e.getX() / fx) - (int) ((double) startDrag.x / fx)),
                                Math.abs((int) ((double) e.getY() / fy) - (int) ((double) startDrag.y / fy)))
                        );
                        startDrag = null;
                        endDrag = null;
                        repaint();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                endDrag = new Point((int) ((double) e.getX() / fx), (int) ((double) e.getY() / fy));
                repaint();
            }
        });


        JPanel selectedAreaPanel = new JPanel();
        frame.getContentPane().add(selectedAreaPanel);


        setOnCroppedListener(this::updateSelectedRegion);
        frame.add(this);
        if (selectedRect != null) {
            try {
                ImageIO.write(selectedRect, "png", new File("C:\\Users\\admin\\Desktop\\edited.jpg"));
                updateImagePanel(selectedRect);
                selectedAreaPanel.getGraphics().clearRect(0, 0, 221, 289);
                selectedRect = null;

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        panel = this;
    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

        if (shape != null) {
            g2.setPaint(Color.BLACK);
            g2.draw(shape);
            g2.setPaint(Color.YELLOW);
            g2.fill(shape);
        }

        if (startDrag != null && endDrag != null) {
            g2.setPaint(Color.LIGHT_GRAY);
            Shape r = makeRectangle((int) ((double) startDrag.x / fy), (int) ((double) startDrag.y / fy), (int) ((double) endDrag.x / fy), (int) ((double) endDrag.y / fy));
            g2.draw(r);
        }

    }

    public void updateImagePanel(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public void setOnCroppedListener(ImagePanel.IClickListener listener) {
        this.listener = listener;
    }

    public void updateSelectedRegion(BufferedImage bufferedImage) {
        selectedRect = bufferedImage;
    }

    //todo: append CropImage.java content to this class
}
