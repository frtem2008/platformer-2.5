import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static final int a = 10000;
    public static final int b = -10000;
    public static final Platform learn1 = new Platform(-12800, 10600, 1200, 50);
    public static final Platform learn2 = new Platform(-11400, 10600, 300, 50);
    public static final Platform learn3 = new Platform(-10880, 10600, 1400, 50);
    public static final Platform learn4 = new Platform(-9160, 10600, 700, 50, true);
    public static final Platform learn5 = new Platform(-8200, 11000, 700, 50);
    public static int frameCounter = 1;
    public static int backgrNum = 1;
    public static String endMessage = "";
    public static int playerSkin = 3;
    public static Object[] level = Generator.generateLevel(-1);
    public static Platform first;
    public static Platform firstJump = new Platform(-100, -430, 50, 50);
    public static boolean begScene = true;
    public static Platform[] platforms = new Platform[1];
    public static Barrier[] Barriers = new Barrier[0];
    public static Barrier[] platformBarriers;
    public static Arrow[] arrows = new Arrow[0];
    public static MovingPlatform[] movingPlatforms;
    public static Player player;
    public static Bird[] birds = new Bird[100];

    static {
        platforms[0] = (Platform) level[0];
        for (int i = 0; i < level.length; i++) {
            if (level[i] instanceof Platform) {
                platforms = addAll((Platform) level[i]);
            } else if (level[i] instanceof Arrow) {
                arrows = addAll((Arrow) level[i]);
            } else if (level[i] instanceof Barrier) {
                Barriers = addAll((Barrier) level[i]);
            }
        }

        platformBarriers = new Barrier[platforms.length * 2];
        movingPlatforms = new MovingPlatform[0];
        player = new Player();
        birds[0] = new Bird(3000, 80);
        int lastBird = 3000;
        for (int i = 1; i < birds.length; i++) {
            birds[i] = new Bird(lastBird + Math.random() * 1000 + 200, Math.random() * 80 + 50);
            lastBird += birds[i].x;
        }
    }
    public Image Birdd;
    public ImageIcon birdLeft1;
    public ImageIcon birdLeft2;
    public ImageIcon birdLeft3;
    public ImageIcon birdLeft4;
    public ImageIcon birdLeft5;
    public ImageIcon birdLeft6;
    public ImageIcon birdLeft7;
    public ImageIcon birdLeft8;
    public ImageIcon birdLeft9;
    public ImageIcon birdLeft10;
    public ImageIcon PLAYER2;
    public ImageIcon PLAYER3;
    public ImageIcon PLAYER4;
    public ImageIcon PLAYER5;
    public ImageIcon PLAYER6;
    public ImageIcon fon1;
    public ImageIcon fon2;
    public ImageIcon fon3;
    public ImageIcon fon4;
    public ImageIcon pl1;
    public ImageIcon pl2;
    public ImageIcon pl3;
    public ImageIcon pl4;

    JFrame frame;
    private Image platform;
    private Image arrow;
    private Image fon;
    private Image PLAYER;

    public Main() {
    }

    public static double toScreenX(double x) {
        return (double) (Display.frame.getWidth() / 2) + (x - player.getX());
    }

    public static double toScreenY(double y) {
        return (double) (Display.frame.getHeight() / 2) + (y - player.getY());
    }

    public static void fillBarriers() {
        for (int i = 0; i < platformBarriers.length; ++i) {
            Platform cur;
            if (i % 2 == 0) {
                cur = platforms[i / 2];
                platformBarriers[i] = new Barrier(cur.getPlatformX(), cur.getPlatformY() + 10.0D, cur.getPlatformY() + cur.getHeight() - 10.0D);
            } else {
                cur = platforms[(i - 1) / 2];
                platformBarriers[i] = new Barrier(cur.getPlatformX() + cur.getWidth(), cur.getPlatformY() + 10.0D, cur.getPlatformY() + cur.getHeight() - 10.0D);
            }
        }

    }

    public static ImageIcon createIcon(String path) {
        return new ImageIcon(Objects.requireNonNull(Main.class.getResource(path)));
    }

    public static Platform[] addAll(Platform[]... a) {
        ArrayList<Platform> pl = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Platform[] platforms = pl.toArray(new Platform[pl.size()]);
        return platforms;
    }

    public static Platform[] addAll(Platform... a) {
        ArrayList<Platform> pl = new ArrayList<>(Arrays.asList(platforms));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Platform[] platforms = pl.toArray(new Platform[pl.size()]);
        return platforms;
    }

    public static Arrow[] addAll(Arrow... a) {
        ArrayList<Arrow> pl = new ArrayList<>(Arrays.asList(arrows));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Arrow[] Arrows = pl.toArray(new Arrow[pl.size()]);
        return Arrows;
    }

    public static Barrier[] addAll(Barrier... a) {
        ArrayList<Barrier> pl = new ArrayList<>(Arrays.asList(Barriers));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Barrier[] Barriers = pl.toArray(new Barrier[pl.size()]);
        return Barriers;
    }

    public static Arrow[] addAll(Arrow[]... a) {
        ArrayList<Arrow> pl = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Arrow[] Arrows = pl.toArray(new Arrow[pl.size()]);
        return Arrows;
    }

    public void startDrawing(JFrame frame) throws InterruptedException {
        this.frame = frame;
        frame.createBufferStrategy(2);
        this.loadImages();

        while (true) {
            frameCounter++;
            if (frameCounter >= 60) {
                frameCounter = 1;
            }
            long frameLength = 1000 / 60;
            long start = System.currentTimeMillis();
            BufferStrategy bs = frame.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
            this.updateImages();
            this.draw(g);

            Player.keyboard.update();
            player.move();
            Bird.move(birds);
            bs.show();
            g.dispose();
            long end = System.currentTimeMillis();
            long len = end - start;

            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }

            Keyboard keyboard = Player.keyboard;
            frame.addKeyListener(keyboard);

        }
    }

    public void draw(Graphics g) {

        JProgressBar jpb = new JProgressBar();
        jpb.setMinimum(0);
        jpb.setMaximum(100);
        jpb.setStringPainted(true);
        jpb.setBounds(500, 100, Display.frame.getWidth(), 50);
        Platform f = (Platform) Main.level[0];
        Platform last = (Platform) Main.level[Main.level.length - 1];
        int length = (int) (last.getPlatformX() + last.getWidth() - f.getWidth() - f.getPlatformX());
        jpb.setValue((int) (player.getX() / length * 100));
        if (jpb.getValue() >= 0 && jpb.getValue() < 25) {
            backgrNum = 1;
        } else if (jpb.getValue() > 25 && jpb.getValue() < 50) {
            backgrNum = 2;
        } else if (jpb.getValue() > 50 && jpb.getValue() < 75) {
            backgrNum = 3;
        } else if (jpb.getValue() > 75 && jpb.getValue() < 100) {
            backgrNum = 4;
        }
        if (jpb.getValue() % 25 == 0) {
            g.setColor(Color.black);
            g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        }

        g.drawImage(this.fon, 0, 0, Display.frame.getWidth(), Display.frame.getHeight(), null);
        frame.add(jpb);
        jpb.update(g);
        jpb.setVisible(true);

        for (int i = 0; i < Barriers.length; ++i) {
            Barrier Barrier = Barriers[i];
            g.setColor(Color.red);
            g.drawLine((int) toScreenX(Barrier.getX()), (int) toScreenY(Barrier.getY1()), (int) toScreenX(Barrier.getX()), (int) toScreenY(Barrier.getY2()));
        }
        first = new Platform(-1000, -350, 900, 50);
        g.drawImage(platform, (int) toScreenX(first.getPlatformX()), (int) toScreenY(first.getPlatformY()), (int) first.getWidth(), 50, null);
        for (int i = 0; i < platforms.length; ++i) {
            Platform Platform1 = platforms[i];
            if (toScreenX(Platform1.getPlatformX()) - player.getX() <= 1000) {
                g.drawImage(this.platform, (int) toScreenX(Platform1.getPlatformX()), (int) toScreenY(Platform1.getPlatformY()), (int) Platform1.getWidth(), (int) Platform1.getHeight(), null);
            }
        }

        for (int i = 0; i < arrows.length; ++i) {
            Arrow arrow = arrows[i];
            g.drawImage(this.arrow, (int) toScreenX(arrow.getArrowX()), (int) toScreenY(arrow.getArrowY()), arrow.getLength(), arrow.getWidth(), null);
        }
        g.setColor(Color.BLACK);
        //g.fillOval((int) toScreenX(player.getX()), (int) toScreenY(player.getY()), (int) player.getSize(), (int) player.getSize());
        g.drawImage(PLAYER, (int) toScreenX(player.getX()), (int) toScreenY(player.getY()), (int) player.getSize(), (int) player.getSize(), null);
        g.setColor(Color.red);
        g.setFont(new Font("bruh", Font.BOLD, 24));
        g.drawString(Player.ohh, (int) toScreenX(player.getX()), (int) toScreenY(player.getY() + player.getSize() + 10.0D));
        double h = Player.fallingHeight;
        if (h <= 100.0D) {
            g.setColor(new Color(2127910));
        } else if (100.0D < h && h <= 400.0D) {
            g.setColor(new Color(16754722));
        } else if (h > 400.0D) {
            g.setColor(Color.red);
        }
        for (int i = 0; i < birds.length; i++) {
            Bird bird = birds[i];
            g.drawImage(Birdd, (int) bird.x, (int) bird.y, (int) 600, (int) 300, null);
        }
        g.drawString(String.valueOf((int) Player.fallingHeight), 100, 100);
        g.setColor(Color.red);
        g.drawString("Deaths: " + Player.deaths, 100, 150);
        g.setColor(new Color(50, 120, 170));
        g.setColor(new Color(3178108));
        String[] chat = Player.chat;

        for (int i = 0; i < Player.chatLast; ++i) {
            g.drawString(chat[i], 15, 350 + i * 50);
        }

        g.setColor(new Color(187, 136, 13, 255));
        g.drawString("To go left/right use a/d or left arrow/ right arrow", (int) toScreenX(-12850.0D), (int) toScreenY(10400.0D));
        g.drawString("To jump use w/space/up arrow", (int) toScreenX(-12250.0D), (int) toScreenY(10400.0D));
        g.drawString("Jump here", (int) toScreenX(-11695.0D), (int) toScreenY(10540.0D));
        g.drawString("And here", (int) toScreenX(-11195.0D), (int) toScreenY(10540.0D));
        g.drawString("This red line is a barrier", (int) toScreenX(-10680.0D), (int) toScreenY(10300.0D));
        g.drawString("You can't go through it", (int) toScreenX(-10680.0D), (int) toScreenY(10350.0D));
        g.drawString("but if you press left when you jump from the left side", (int) toScreenX(-10880.0D), (int) toScreenY(10400.0D));
        g.drawString("you can push off it", (int) toScreenX(-10680.0D), (int) toScreenY(10450.0D));
        g.drawString("Jump", (int) toScreenX(-10000.0D), (int) toScreenY(10540.0D));
        g.drawString("Press left", (int) toScreenX(-9925.0D), (int) toScreenY(10428.0D));
        g.drawString("Go --->", (int) toScreenX(-9900.0D), (int) toScreenY(10560.0D));
        g.drawString("Jump", (int) toScreenX(-9504.0D), (int) toScreenY(10560.0D));
        g.drawString("You're on the edge of the platform", (int) toScreenX(-9440.0D), (int) toScreenY(10400.0D));
        g.drawString("You can push off it as a barrier ", (int) toScreenX(-9440.0D), (int) toScreenY(10450.0D));
        g.drawString("Now you can go!!!(just jump into the void) ", (int) toScreenX(-9000.0D), (int) toScreenY(10450.0D));
        g.setColor(new Color(46, 26, 115));
        drawCenteredString(g, endMessage, new java.awt.Rectangle(frame.getWidth(), frame.getHeight()), new Font("bruh", Font.BOLD, 100));

    }

    public void drawCenteredString(Graphics g, String text, java.awt.Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void updateImages() {
        if (playerSkin == 1) {
            this.PLAYER = PLAYER2.getImage();
        } else if (playerSkin == 3) {
            this.PLAYER = PLAYER3.getImage();
        } else if (playerSkin == 4) {
            this.PLAYER = PLAYER4.getImage();
        } else if (playerSkin == 5) {
            this.PLAYER = PLAYER5.getImage();
        } else if (playerSkin == 6) {
            this.PLAYER = PLAYER6.getImage();
        }
        if (backgrNum == 1) {
            this.fon = fon1.getImage();
            this.platform = pl1.getImage();
        } else if (backgrNum == 2) {
            this.fon = fon2.getImage();
            this.platform = pl2.getImage();
        } else if (backgrNum == 3) {
            this.fon = fon3.getImage();
            this.platform = pl3.getImage();
        } else if (backgrNum == 4) {
            this.fon = fon4.getImage();
            this.platform = pl4.getImage();
        }

        if (frameCounter >= 0 && frameCounter <= 6) {
            Birdd = birdLeft1.getImage();
        } else if (frameCounter > 6 && frameCounter <= 12) {
            Birdd = birdLeft2.getImage();
        } else if (frameCounter > 12 && frameCounter <= 18) {
            Birdd = birdLeft3.getImage();
        } else if (frameCounter > 18 && frameCounter <= 24) {
            Birdd = birdLeft4.getImage();
        } else if (frameCounter > 24 && frameCounter <= 30) {
            Birdd = birdLeft5.getImage();
        } else if (frameCounter > 30 && frameCounter <= 36) {
            Birdd = birdLeft6.getImage();
        } else if (frameCounter > 36 && frameCounter <= 42) {
            Birdd = birdLeft7.getImage();
        } else if (frameCounter > 42 && frameCounter <= 48) {
            Birdd = birdLeft8.getImage();
        } else if (frameCounter > 48 && frameCounter <= 54) {
            Birdd = birdLeft9.getImage();
        } else if (frameCounter > 54 && frameCounter <= 60) {
            Birdd = birdLeft10.getImage();
        }
    }

    public void loadImages() {
        pl1 = createIcon("Images/Platform5.png");
        pl2 = createIcon("Images/Platform 6.png");
        pl3 = createIcon("Images/Platform 3.png");
        pl4 = createIcon("Images/Platform 8.png");
        fon1 = createIcon("Images/рассвет.jpg");
        fon2 = createIcon("Images/день.jpg");
        fon3 = createIcon("Images/фон3.jpg");
        fon4 = createIcon("Images/ночь.png");
        birdLeft1 = createIcon("Images/bird left 1.png");
        birdLeft2 = createIcon("Images/bird left 2.png");
        birdLeft3 = createIcon("Images/bird left 3.png");
        birdLeft4 = createIcon("Images/bird left 4.png");
        birdLeft5 = createIcon("Images/bird left 5.png");
        birdLeft6 = createIcon("Images/bird left 6.png");
        birdLeft7 = createIcon("Images/bird left 7.png");
        birdLeft8 = createIcon("Images/bird left 8.png");
        birdLeft9 = createIcon("Images/bird left 9.png");
        birdLeft10 = createIcon("Images/bird left 10.png");
        ImageIcon ar = createIcon("Images/Arrow.png");
        PLAYER2 = createIcon("Images/Player2.png");
        PLAYER3 = createIcon("Images/Player eyes right up.png");
        PLAYER4 = createIcon("Images/Player eyes right down.png");
        PLAYER5 = createIcon("Images/Player eyes left up.png");
        PLAYER6 = createIcon("Images/Player eyes left down.png");

        ImageIcon platform2 = createIcon("Images/Platform 2.png");
        this.arrow = ar.getImage();
    }
}
