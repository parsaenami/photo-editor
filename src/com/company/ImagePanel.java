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

public class ImagePanel extends JPanel {

    private Shape shape = null;
    private Point endDrag;
    private Point startDrag;
    private BufferedImage img;
    private IClickListener listener;

    interface IClickListener {
        void onCroppedListener(BufferedImage image);
    }

    public ImagePanel(BufferedImage img) throws IOException {

//        img = ImageIO.read(new File(inputImage));

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startDrag = new Point(e.getX(), e.getY());
                endDrag = startDrag;
                System.out.println("Mouse Pressed");
                repaint();
            }

            public void mouseReleased(MouseEvent e) {

                System.out.println("Mouse released");
                if (endDrag != null && startDrag != null) {
                    try {
                        shape = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                        listener.onCroppedListener(img.getSubimage(
                                startDrag.x - Math.abs(e.getX() - startDrag.x),
                                startDrag.y - Math.abs(e.getY() - startDrag.y),
                                Math.abs(e.getX() - startDrag.x),
                                Math.abs(e.getY() - startDrag.y))
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

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                endDrag = new Point(e.getX(), e.getY());
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, 0, 0, null);
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
            Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            g2.draw(r);
        }

    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    public void updateImagePanel(BufferedImage image) {
        this.img = image;
        repaint();
    }

    public void setOnCroppedListener(IClickListener listener) {
        this.listener = listener;
    }
}
