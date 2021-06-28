
public class MovingPlatform {
    public Platform platform;
    public int x;
    public int x1;
    public int x2;
    public int speed;
    public boolean direction;

    public MovingPlatform(Platform platform, int x1, int x2, int speed, boolean direction) {
        this.platform = platform;
        this.x1 = x1;
        this.x2 = x2;
        this.speed = speed;
        this.direction = direction;
    }

    public static int Collides(Player player, MovingPlatform[] platforms) {
        return -1;
    }

    public static boolean collides(Player player, MovingPlatform[] platforms) {
        int a = 0;
        return a > 0;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public int getMovingPlatformX1() {
        return this.x1;
    }

    public int getMovingPlatformX2() {
        return this.x2;
    }

    public int getMovingPlatformSpeed() {
        return this.speed;
    }
}
