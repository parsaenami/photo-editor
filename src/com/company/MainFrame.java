package com.company;

//import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private JPanel jPanel1, jPanel2, jPanel3;
    private JLabel jLabel;
    private JMenu jMenu1, jMenu2;
    private JMenuBar jMenuBar;
    private ImageIcon imageIcon;
    private Container container;
    Image tempImage;
    BufferedImage tempBufferedImage;
    JFrame jFrame;
    boolean clicked = false;
    double factorX, factorY;

    MainFrame() {
        jFrame = this;
        jMenuBar = new JMenuBar();
        jMenu1 = new JMenu("File");
        jMenu1.setMnemonic(KeyEvent.VK_F);
        jMenu2 = new JMenu("Edit");
        jMenu2.setMnemonic(KeyEvent.VK_E);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        JMenuItem menuItem1 = new JMenuItem("New", KeyEvent.VK_N);
        JMenuItem menuItem2 = new JMenuItem("Open", KeyEvent.VK_O);
        JMenuItem menuItem3 = new JMenuItem("Save", KeyEvent.VK_S);
        JMenuItem menuItem4 = new JMenuItem("Close", KeyEvent.VK_C);
        JMenuItem menuItem5 = new JMenuItem("Tools", KeyEvent.VK_T);
        JMenuItem menuItem6 = new JMenuItem("Undo", KeyEvent.VK_U);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK));
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_MASK));
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.ALT_MASK));
        menuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.ALT_MASK));
        menuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, InputEvent.ALT_MASK));
        menuItem6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, InputEvent.ALT_MASK));
        menuItem6.setEnabled(false);
//        setLayout(new BorderLayout(5, 5));
        jPanel1 = new JPanel(/*new GridBagLayout()*/new BorderLayout());
        jPanel1.setSize(900, 400);
        jPanel1.add(new JLabel());
        jPanel1.setBackground(Color.lightGray);
        jPanel1.setBorder(new EtchedBorder(Color.gray, Color.gray));
        jPanel1.setVisible(true);
//        jPanel1 = new MainPanels(PanelType.MAIN_PANEL);
        add(jPanel1, BorderLayout.CENTER);
        menuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    BufferedImage img1 = null;
                    try {
                        img1 = ImageIO.read(new File(file.getPath()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert img1 != null;
                    double imageWidth, imageHeight, panelWidth, panelHeight;
                    double widthCheck;
                    double heightCheck;
                    double minCheck;
                    int finalWidth = 0;
                    int finalHeight = 0;
                    imageWidth = img1.getWidth();
                    imageHeight = img1.getHeight();
                    panelWidth = jPanel1.getWidth();
                    panelHeight = jPanel1.getHeight();
                    widthCheck = (panelWidth / imageWidth);
                    heightCheck = (panelHeight / imageHeight);
                    minCheck = Double.min(widthCheck, heightCheck);
                    if (widthCheck >= 1 && heightCheck >= 1) {
                        finalWidth = (int) imageWidth;
                        finalHeight = (int) imageHeight;
                        factorX = 1;
                        factorY = 1;
                    } else if (widthCheck < 1 && heightCheck >= 1) {
                        finalWidth = (int) (imageWidth * widthCheck);
                        finalHeight = (int) (imageHeight * heightCheck);
                        factorX = widthCheck;
                        factorY = heightCheck;
                    } else if (widthCheck >= 1 && heightCheck < 1) {
                        finalWidth = (int) (imageWidth * heightCheck);
                        finalHeight = (int) (imageHeight * heightCheck);
                        factorX = heightCheck;
                        factorY = heightCheck;

                    } else if (widthCheck < 1 && heightCheck < 1) {
                        finalWidth = (int) (imageWidth * minCheck);
                        finalHeight = (int) (imageHeight * minCheck);
                        factorX = minCheck;
                        factorY = minCheck;
                    }
                    if (finalHeight > 320) {
                        finalWidth -= (finalHeight - 320);
                        finalHeight = 320;
                        factorX = finalWidth / img1.getWidth();
                        factorY = 320 / img1.getHeight();
                    }



                    Image dimg = img1.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
                    tempBufferedImage = img1;
                    tempImage = dimg;
//                    ImageIcon imageIcon = new ImageIcon(dimg);
//                    jLabel = new JLabel();
//                    jLabel.repaint(0, 0, jLabel.getWidth(), jLabel.getHeight());
//                    jLabel = new JLabel(imageIcon);

                    jLabel = new JLabel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2 = (Graphics2D) g;
                            g2.translate(jLabel.getSize().width / 2, jLabel.getSize().height / 2);
                            g2.drawImage(tempImage, -tempImage.getWidth(null) / 2, -tempImage.getHeight(null) / 2, null);
                        }
                    };

                    jPanel1.add(jLabel);
                    revalidate();
                    add(jPanel1);
                    jPanel1.setVisible(true);
                }
            }
        });
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    BufferedImage img1 = tempBufferedImage;

                    try {
                        File output = new File(file.getPath());
                        ImageIO.write(ImageToBufferedImage.toBufferedImage(tempImage), "jpg", output);


                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        menuItem4.addActionListener(new exitApp());
        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final boolean[] oc = {true, true, true, true, true};
//                jPanel3 = new MainPanels(PanelType.BOTTOM_PANEL);
                jPanel3 = new JPanel();
                jPanel3.setSize(900, 100);
                jPanel3.add(new JLabel());
                jPanel3.setBackground(Color.black);
                MainButtons jButton1 = new MainButtons("crop.jpg", "crop");
                MainButtons jButton2 = new MainButtons("rotation.jpg", "rotate");
                MainButtons jButton3 = new MainButtons("colors.jpg", "colors");
                MainButtons jButton4 = new MainButtons("filters.jpg", "filter");
                MainButtons jButton5 = new MainButtons("text.png", "add text");
                MainButtons jButton6 = new MainButtons("sticker.png", "add sticker");
                jPanel3.add(jButton1, BorderLayout.CENTER);
                jPanel3.add(jButton2, BorderLayout.CENTER);
                jPanel3.add(jButton3, BorderLayout.CENTER);
                jPanel3.add(jButton4, BorderLayout.CENTER);
                jPanel3.add(jButton5, BorderLayout.CENTER);
                jPanel3.add(jButton6, BorderLayout.CENTER);
                jPanel3.setBorder(new EtchedBorder(Color.white, Color.white));
                jButton4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        oc[1] = true;
                        oc[2] = true;
//                        jPanel2 = new MainPanels(PanelType.FILTER_PANEL);
                        jPanel2 = new JPanel();
                        jPanel2.setSize(600, 120);
                        jPanel2.setLocation(200, 470);
                        jPanel2.setBorder(new EtchedBorder(Color.black, Color.black));
                        jPanel2.add(new JLabel());
                        jPanel2.setBackground(Color.gray);
                        add(jPanel1, BorderLayout.CENTER, 0);
                        jPanel1.add(jPanel2, BorderLayout.SOUTH, 0);
//                        if (oc[0]) {
//                            jPanel2.setVisible(true);
//                            oc[0] = false;
//                        } else if (!oc[0]) {
//                            jPanel2.setVisible(false);
//                            add(jPanel1, BorderLayout.CENTER, 0);
//                            add(jPanel3, BorderLayout.SOUTH, 1);
//                            jPanel2.revalidate();
//                            jPanel2.repaint();
//                            revalidate();
//                            repaint();
//                            oc[0] = true;
//                        }
                        add(jPanel3, BorderLayout.SOUTH, 1);
                        revalidate();
                        repaint();
                        jPanel2.setVisible(true);
                    }
                });
                jButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        oc[0] = true;
                        oc[2] = true;
//                        jPanel2 = new MainPanels(PanelType.COLOR_PANEL);
                        jPanel2 = new JPanel();
                        jPanel2.setSize(600, 120);
                        jPanel2.setLocation(200, 470);
                        jPanel2.setBorder(new EtchedBorder(Color.black, Color.black));
                        jPanel2.add(new JLabel());
                        jPanel2.setBackground(Color.gray);
                        add(jPanel1, BorderLayout.CENTER, 0);
                        jPanel1.add(jPanel2, BorderLayout.SOUTH, 0);
//                        if (oc[1]) {
//                            jPanel2.setVisible(true);
//                            oc[1] = false;
//
//                        } else if (!oc[1]) {
//                            jPanel2.setVisible(false);
//                            add(jPanel1, BorderLayout.CENTER, 0);
//                            add(jPanel3, BorderLayout.SOUTH, 1);
//                            jPanel2.revalidate();
//                            jPanel2.repaint();
//                            revalidate();
//                            repaint();
//                            oc[1] = true;
//                        }
                        add(jPanel3, BorderLayout.SOUTH, 1);
                        revalidate();
                        repaint();
                        jPanel2.setVisible(true);
                    }
                });
                jButton2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        oc[0] = true;
                        oc[1] = true;
//                        jPanel2 = new MainPanels(PanelType.ROTATE_PANEL);
                        jPanel2 = new JPanel();
                        jPanel2.setSize(600, 50);
                        jPanel2.setLocation(200, 540);
                        jPanel2.setBorder(new EtchedBorder(Color.black, Color.black));
                        /*jPanel2.add(new JLabel());
                        jPanel1.setVisible(false);
                        new Rotate(jFrame, tempImage, jPanel2, jPanel1);
                        add(jPanel1, BorderLayout.CENTER, 0);
                        jPanel1.setVisible(true);
                        setVisible(true);
                        revalidate();
                        repaint();*/

                        jPanel2.setLayout(new BorderLayout());
                        JSlider slider = new JSlider(0, 360);
                        slider.setSize(new Dimension(200, 50));
                        slider.setMinorTickSpacing(5);
                        slider.setMajorTickSpacing(15);
                        slider.setBorder(new LineBorder(Color.black, 5));
                        slider.setValue(0);
                        slider.setOpaque(false);
                        slider.revalidate();
                        jPanel2.add(slider, BorderLayout.SOUTH);
                        slider.setVisible(true);
                        slider.setPaintTicks(true);
                        slider.setPaintLabels(true);


                        jLabel.setVisible(false);

                        JLabel label = new JLabel() {

                            public double Angle(int degree) {
//                                jFrame.repaint();
                                return Math.toRadians(degree) * 3.6;
                            }

                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2 = (Graphics2D) g;
                                g2.translate(jPanel1.getSize().width / 2, jPanel1.getSize().height / 2);
                                g2.rotate(Angle(slider.getValue() * 10 / 36));
                                g2.drawImage(tempImage, -tempImage.getWidth(null) / 2, -tempImage.getHeight(null) / 2, null);
                                slider.setToolTipText(String.valueOf(slider.getValue()));
//                                slider.setBorder(new LineBorder(Color.red, 5));
                                setVisible(true);
                                revalidate();
                                repaint();
//                                update(g2);
                            }

                        };
                        if (!clicked)
                            jPanel1.add(label);

                        clicked = true;
//                        label.revalidate();
//                        label.repaint();
//                        revalidate();
                        add(jPanel1);
//                        setVisible(true);
//                        revalidate();
//                        repaint();


//                        ImageView imageView = new ImageView(new Element() {
//                            @Override
//                            public Document getDocument() {
//                                return null;
//                            }
//
//                            @Override
//                            public Element getParentElement() {
//                                return null;
//                            }
//
//                            @Override
//                            public String getName() {
//                                return null;
//                            }
//
//                            @Override
//                            public AttributeSet getAttributes() {
//                                return null;
//                            }
//
//                            @Override
//                            public int getStartOffset() {
//                                return 0;
//                            }
//
//                            @Override
//                            public int getEndOffset() {
//                                return 0;
//                            }
//
//                            @Override
//                            public int getElementIndex(int offset) {
//                                return 0;
//                            }
//
//                            @Override
//                            public int getElementCount() {
//                                return 0;
//                            }
//
//                            @Override
//                            public Element getElement(int index) {
//                                return null;
//                            }
//
//                            @Override
//                            public boolean isLeaf() {
//                                return false;
//                            }
//                        });


//                        jLabel.setVisible(false);
                        jPanel2.setBackground(Color.gray);
//                        JSlider jSlider = new JSlider();
//                        jSlider.setVisible(true);
//                        jPanel2.add(jSlider, 0);
                        add(jPanel1, BorderLayout.CENTER, 0);
                        jPanel1.add(jPanel2, BorderLayout.SOUTH);
//                        if (oc[2]) {
//                            jPanel2.setVisible(true);
//                            oc[2] = false;
//                        } else if (!oc[2]) {
////                            jPanel2.remove(jSlider);
//                            jPanel2.setVisible(false);
////                            jSlider.setVisible(false);
////                            jSlider.revalidate();
////                            jSlider.repaint();
////                            jPanel2.revalidate();
////                            jPanel2.repaint();
////                            jPanel1.revalidate();
////                            jPanel1.repaint();
////                            revalidate();
////                            repaint();
//                            add(jPanel1, BorderLayout.CENTER, 0);
//                            add(jPanel3, BorderLayout.SOUTH, 0);
//                            jPanel2.revalidate();
//                            jPanel2.repaint();
//                            revalidate();
//                            repaint();
//                            oc[2] = true;
//                        }
                        add(jPanel3, BorderLayout.SOUTH, 1);
                        revalidate();
                        repaint();
                        jPanel2.setVisible(true);
                    }

                });
                jButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo: write your own crop code...
//                            new Crop(ImageToBufferedImage.toBufferedImage(tempImage), jPanel1, jFrame, factorX, factorY);
//                            jFrame.repaint();

                        jPanel1.addMouseListener(new MouseAdapter() {
                            Point start, end;
                            /**
                             * {@inheritDoc}
                             *
                             * @param e
                             */
                            @Override
                            public void mousePressed(MouseEvent e) {
                                super.mousePressed(e);
                                start = new Point(e.getX(), e.getY());
                                end = start;
                                System.out.println(start);
                                System.out.println("width : " + tempImage.getWidth(null) + "\nheight : " + tempImage.getHeight(null));
                            }

                            /**
                             * {@inheritDoc}
                             *
                             * @param e
                             */
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                super.mouseReleased(e);
                                jLabel.setVisible(false);
                                end = new Point(e.getX(), e.getY());
                                BufferedImage cropedImage = ImageToBufferedImage.toBufferedImage(tempImage).getSubimage(
                                        start.x - Math.abs(e.getX() - start.x) - 185,
                                        start.y - Math.abs(e.getY() - start.y) - 35,
                                        Math.abs(e.getX() - start.x),
                                        Math.abs(e.getY() - start.y));
                                System.out.println("w : " + Math.abs(e.getX() - start.x));
                                System.out.println("h : " + Math.abs(e.getY() - start.y));

//                                Shape shape = new Rectangle2D.Float(Math.min(start.x, end.x), Math.min(start.y, end.y), Math.abs(start.x - end.x), Math.abs(start.y - end.y));

                                start = null;
                                end = null;
                                tempImage = cropedImage;
                                jLabel = new JLabel(){
                                    @Override
                                    protected void paintComponent(Graphics g) {
                                        super.paintComponent(g);
                                        Graphics2D g2 = (Graphics2D) g;
//                                        if (shape != null) {
//                                            g2.setPaint(Color.BLACK);
//                                            g2.draw(shape);
//                                            g2.setPaint(Color.YELLOW);
//                                            g2.fill(shape);
//
//                                        }
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        g2.translate(jPanel1.getSize().width / 2, jPanel1.getSize().height / 2);

                                        g2.drawImage(cropedImage.getScaledInstance(cropedImage.getWidth(), cropedImage.getHeight(), Image.SCALE_SMOOTH), -tempImage.getWidth(null) / 2, -tempImage.getHeight(null) / 2, null);
                                    }
                                };
                                jPanel1.add(jLabel);
                                repaint();
                                jFrame.repaint();
                            }
                        });


                    }
                });
                add(jPanel3, BorderLayout.SOUTH, 1);
                revalidate();
                jPanel3.setVisible(true);
            }
        });
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                container.removeAll();
//                container.revalidate();
//                container.repaint();
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File("random.jpg"));
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
                assert image != null;
                Image dimg = image.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                tempImage = dimg;


//                ImageIcon imageIcon = new ImageIcon(dimg);
//                jLabel = new JLabel();
//                jLabel.repaint(0, 0, jLabel.getWidth(), jLabel.getHeight());
//                jLabel = new JLabel(imageIcon);
//                jPanel1.add(jLabel);

                jLabel = new JLabel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.translate(jLabel.getSize().width / 2, jLabel.getSize().height / 2);
                        g2.drawImage(tempImage, -tempImage.getWidth(null) / 2, -tempImage.getHeight(null) / 2, null);
                    }
                };
                jPanel1.add(jLabel);
                revalidate();
                add(jPanel1);
                jPanel1.setVisible(true);
            }
        });
        jMenu1.add(menuItem1);
        jMenu1.addSeparator();
        jMenu1.add(menuItem2);
        jMenu1.addSeparator();
        jMenu1.add(menuItem3);
        jMenu1.addSeparator();
        jMenu1.add(menuItem4);
        jMenu2.add(menuItem5);
        jMenu2.addSeparator();
        jMenu2.add(menuItem6);
        add(jMenuBar, BorderLayout.NORTH);
        setTitle("TEST");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 700);
//        setExtendedState(MainFrame.MAXIMIZED_BOTH);
        setLocation(170, 30);
        setVisible(true);
        setResizable(true);
    }

    static class exitApp implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
