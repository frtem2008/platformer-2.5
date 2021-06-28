import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

public class Player {
    private static final Platform[] platforms;
    private static final MovingPlatform[] movingPlatforms;
    private static final Barrier[] barriers;
    public static int deaths;
    public static boolean godMode;
    public static Keyboard keyboard;
    public static int maxSpeed;
    public static double fallingHeight;
    public static String ohh;
    public static double spawnX;
    public static double spawnY;
    public static double x;
    public static double y;
    public static ArrayList<Double> spawnpointsX;
    public static ArrayList<Double> spawnpointsY;
    public static String[] chat;
    public static int chatLast;
    public static int bonusCount = 0;
    private static double size;
    private static double gravitation;
    private static boolean jump;
    public static double speed;
    private static double maxFallingHeight;
    private static int frame;
    private static boolean onBarrier;
    private static boolean fromRight;
    private static boolean fromLeft;
    private static boolean fromUp;
    private static boolean fromDown;

    static {
        platforms = Main.platforms;
        movingPlatforms = Main.movingPlatforms;
        barriers = Main.Barriers;
        deaths = 0;
        godMode = false;
        keyboard = new Keyboard();
        maxSpeed = 5;
        fallingHeight = 0.0D;
        ohh = "";
        spawnX = -1000;
        spawnY = -400;
        x = spawnX;
        y = spawnY;
        spawnpointsX = new ArrayList<>();
        spawnpointsY = new ArrayList<>();
        chat = new String[6];
        chatLast = 0;
        size = 60.0D;
        gravitation = 0.0D;
        jump = true;
        speed = 80.0D;
        maxFallingHeight = 550.0D;
        frame = 1;
        onBarrier = false;
        fromRight = false;
        fromLeft = false;
        fromUp = false;
        fromDown = false;
    }

    public Player() {
    }

    public static void respawn() {
        speed = 0.0D;
        gravitation = 0.0D;
        fallingHeight = 0.0D;
        x = spawnX;
        y = spawnY;
        maxFallingHeight = 510.0D;
        maxSpeed = 5;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public void move() {
        keyboard.update();
        if (chatLast >= chat.length) {
            for (int i = 1; i < chat.length; ++i) {
                chat[i] = chat[i - 1];
            }

            chatLast = 0;
        }

        if (!godMode) {
            if (speed > 0.0D) {
                fromRight = true;
                fromLeft = false;
            } else if (speed < 0.0D) {
                fromLeft = true;
                fromRight = false;
            }

            if (gravitation > 0.0D) {
                fromUp = true;
                fromDown = false;
            } else if (gravitation < 0.0D) {
                fromUp = false;
                fromDown = true;
            }

            if (frame % 60 != 0) {
                ++frame;
            } else {
                frame = 1;
            }


            if (Barrier.Collides(Main.player, barriers) == -1 && Barrier.Collides(Main.player, Main.platformBarriers) == -1) {
                onBarrier = false;
            } else {
                onBarrier = true;
                speed = 0.0D;
                fallingHeight = 0.0D;
                if (fromRight && keyboard.getLeft() && !keyboard.getRight() && !keyboard.getUp()) {
                    jump = true;
                    speed = -5.0D;
                    if (gravitation <= 7.0D) {
                        gravitation += -8.0D;
                    }
                }

                if (fromLeft && !keyboard.getLeft() && keyboard.getRight() && !keyboard.getUp()) {
                    jump = true;
                    speed = 5.0D;
                    gravitation += -8.0D;
                }
            }

            if ((Platform.Collides(Main.player, platforms) == -1 && !MovingPlatform.collides(Main.player, movingPlatforms))) {
                jump = true;
            } else {
                Main.begScene = false;
                int var10001;
                if (fallingHeight > maxFallingHeight) {
                    var10001 = (int) fallingHeight;
                    System.out.println("Вы упали с высоты " + var10001 + " и разбились (максимальная высота: " + maxFallingHeight + ")");
                    chat[chatLast] = "Player fell from a high place!!!";
                    ++chatLast;
                    respawn();
                    ++deaths;
                }

                if (Platform.Collides(Main.player, platforms) != -1) {
                    fromUp = false;
                    fromDown = true;
                    Platform cur = platforms[Platform.Collides(Main.player, platforms)];
                    if (cur.isSpawnpoint()) {
                        this.setSpawnpoint(cur.getPlatformX() + cur.getWidth() / 2.0D, cur.getPlatformY() - size - 50.0D);
                    }
                }
                if (Platform.Collides(Main.player, platforms) == platforms.length - 1) {
                    Main.endMessage = "YOU WON!!!";
                    java.util.Timer t = new java.util.Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.exit(100);
                        }
                    }, 5000);


                }
                jump = false;
                fallingHeight = 0.0D;
                gravitation = 0.0D;
                if (Barrier.Collides(Main.player, barriers) != -1) {
                    onBarrier = true;
                    speed = 0.0D;
                    fallingHeight = 0.0D;
                    /*if (fromRight && keyboard.getLeft()) {
                        x -= 10.0D;
                    }

                    if (fromLeft && keyboard.getRight()) {
                        x += 10.0D;
                    }*/
                    if (fromRight && keyboard.getLeft() && !keyboard.getRight() && !keyboard.getUp()) {
                        jump = true;
                        speed = -5.0D;
                        if (gravitation <= 7.0D) {
                            gravitation += -8.0D;
                        }
                    }

                    if (fromLeft && !keyboard.getLeft() && keyboard.getRight() && !keyboard.getUp()) {
                        jump = true;
                        speed = 5.0D;
                        gravitation += -8.0D;
                    }
                } else if (Barrier.Collides(Main.player, Main.platformBarriers) != -1) {
                    onBarrier = true;
                    speed = 0.0D;
                    fallingHeight = 0.0D;
                    if (fromRight && keyboard.getLeft() && !keyboard.getRight() && !keyboard.getUp()) {
                        jump = true;
                        speed = -5.0D;
                        if (gravitation <= 7.0D) {
                            gravitation += -8.0D;
                        }
                    }

                    if (fromLeft && !keyboard.getLeft() && keyboard.getRight() && !keyboard.getUp()) {
                        jump = true;
                        speed = 5.0D;
                        gravitation += -8.0D;
                    }
                } else {
                    if (keyboard.getLeft() && !keyboard.getRight() && !jump) {
                        speed -= 2.0D;
                    }

                    if (!keyboard.getLeft() && keyboard.getRight() && !jump) {
                        speed += 2.0D;
                    }
                }

                if (!keyboard.getLeft() && !keyboard.getRight()) {
                    if (speed > 0.0D) {
                        speed -= 1D;
                    } else if (speed < 0.0D) {
                        speed += 1D;
                    }
                }

                if (keyboard.getShift() && gravitation < 10.0D) {
                    fallingHeight /= 1.5D;
                }

                if (fallingHeight > 501.0D) {
                    var10001 = (int) fallingHeight;
                    System.out.println("Вы упали с высоты " + var10001 + " и разбились (максимальная высота: " + maxFallingHeight + ")");
                    chat[chatLast] = "Player fell from a high place!!!";
                    ++chatLast;
                    respawn();
                    ++deaths;
                }

                if (Arrow.collides(Main.player, Main.arrows) || Platform.Collides(Main.player, new Platform[]{Main.first}) == 0) {
                    maxSpeed = 10;
                    maxFallingHeight = 800.0D;
                } else if (maxSpeed > 5) {
                    maxSpeed = (int) ((double) maxSpeed - 0.2D);
                    maxFallingHeight = 501.0D;
                }

                if (Math.abs(speed) > 0.0D && keyboard.getShift()) {
                    speed = 0.0D;
                }

                if (Math.abs(speed) > 4.0D) {
                    ohh = "";
                }

                if (100.0D <= fallingHeight && fallingHeight <= 200.0D) {
                    ohh = "oh";
                }

                if (201.0D <= fallingHeight && fallingHeight <= 300.0D) {
                    ohh = "ooh";
                }

                if (fallingHeight >= 301.0D) {
                    ohh = "oooh";
                }
            }

            if (fallingHeight >= 5000.0D) {
                System.out.println("Вы упали вниз");
                chat[chatLast] = "Player fell into the void!!!";
                ++chatLast;
                respawn();
                ++deaths;
            }

            if (keyboard.getUp() && !jump && !onBarrier) {
                jump = true;
                gravitation += -10.0D;
            }

            if (speed > (double) maxSpeed) {
                speed = (double) maxSpeed;
            }

            if (speed < (double) (-maxSpeed)) {
                speed = (double) (-maxSpeed);
            }

            if (!jump && !keyboard.getLeft() && !keyboard.getRight() && speed != 0.0D) {
                if (speed > 0.0D && speed > 1.0D) {
                    speed -= 1D;
                } else if (speed > 0.0D && speed < 1.0D) {
                    speed = 0.0D;
                }

                if (speed < 0.0D && speed < -1.0D) {
                    speed += 1D;
                } else if (speed < 0.0D && speed > -1.0D) {
                    speed = 0.0D;
                }
            }

            if (keyboard.getQ()) {
                System.out.println("Выход");
                System.exit(20);
            }

            if (keyboard.getS()) {
                this.setSpawnpoint(x, y);
            }
            if (gravitation > 0.0D) {
                fallingHeight += Math.abs(gravitation);
            }

            if (Platform.Collides(Main.player, new Platform[]{Main.first}) == -1) {
                if (Platform.Collides(Main.player, platforms) == -1)
                    gravitation += 0.4D;
                if (!Arrow.collides(Main.player, Main.arrows)) {
                    if (Platform.Collides(Main.player, platforms) != -1)
                        maxSpeed = 5;
                }
            } else {
                maxSpeed = 30;
                speed = 30;
            }
            if (Platform.Collides(Main.player, new Platform[]{Main.firstJump}) == 0) {
                System.out.println("1");
                jump = true;
                gravitation += -2.0D;
                maxFallingHeight = 1000;
                Main.begScene = false;
            } else {
            }
            x += speed;
            y += gravitation;

        } else {
            if (keyboard.getLeft() && !keyboard.getRight()) {
                x -= 50.0D;
            }

            if (!keyboard.getLeft() && keyboard.getRight()) {
                x += 50.0D;
            }

            if (keyboard.getUp()) {
                y -= 50.0D;
            }

            if (keyboard.getS()) {
                y += 50.0D;
            }
        }

        if (keyboard.getE()) {
            godMode = true;
        }

        if (keyboard.getR()) {
            godMode = false;
        }

        if (keyboard.getF11() && frame % 5 == 0) {
            if (Display.isFullScreen) {
                Display.frame.setSize(Display.w, Display.h);
            } else {
                Display.frame.setExtendedState(6);
            }

            Display.isFullScreen = !Display.isFullScreen;
        }

        if (keyboard.getF()) {
            chat[chatLast] = "[Debug] Player x: " + x + " ; player y: " + (y - 10000.0D);
            ++chatLast;
            System.out.println("Player x: " + x + " ; player y: " + (y - 10000.0D));
        }

        if (keyboard.getX()) {
            chatLast = 1;
            Arrays.fill(chat, "");
            chat[0] = "[Debug] Cleared chat";
        }

        if (keyboard.getC()) {
            this.cancelSpawnpoint();
        }

        if (keyboard.getV()) {
            respawn();
        }

        if (keyboard.getG()) {
            this.setSpawnpoint((Double) spawnpointsX.get(0), (Double) spawnpointsY.get(0));
        }

        if (keyboard.getZ()) {
            Main.playerSkin = 1;
        }
        if (fromDown){
            if (fromLeft){
                Main.playerSkin = 5;
            }else {
                Main.playerSkin = 3;
            }
        }else{
            if (fromLeft){
                Main.playerSkin = 6;
            }else {
                Main.playerSkin = 4;
            }
        }
        if (keyboard.getB()) {
            Main.playerSkin = 2;
        }

    }

    private void cancelSpawnpoint() {
        if (spawnpointsX.size() > 2) {
            spawnX = (Double) spawnpointsX.get(spawnpointsX.size() - 2);
            spawnY = (Double) spawnpointsY.get(spawnpointsY.size() - 2);
            spawnpointsX.remove(spawnpointsX.size() - 1);
            spawnpointsY.remove(spawnpointsY.size() - 1);
            int var10002 = (int) spawnX;
            chat[chatLast] = "[Debug] Set player's spawnpoint on " + var10002 + "; " + ((int) spawnY - 10000);
            ++chatLast;
            System.out.println("Set spawnpoint on " + spawnX + "; " + spawnY);
        } else {
            chat[chatLast] = "[Debug] Nothing changed";
            ++chatLast;
            System.out.println("Nothing changed");
        }

    }

    private void setSpawnpoint(double x, double y) {
        int a = 0;

        for (int i = 0; i < spawnpointsX.size(); ++i) {
            if (spawnpointsX.get(i) == x && (Double) spawnpointsY.get(i) == y) {
                ++a;
                break;
            }
        }

        if (a == 0) {
            spawnX = x;
            spawnY = y;
            int var10002 = (int) spawnX;
            chat[chatLast] = "[Debug] Set player's spawnpoint on " + var10002 + "; " + ((int) spawnY - 10000);
            ++chatLast;
            System.out.println("Set spawnpoint on " + spawnX + "; " + spawnY);
            spawnpointsX.add(x);
            spawnpointsY.add(y);
        }

    }
}
