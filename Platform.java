public class Platform {
    private final int defaultX;
    private final int defaultY;
    private double platformX = 0.0D;
    private double platformY = 0.0D;
    private int width = 100;
    private int height = 50;
    private Rectangle r;
    private boolean isSpawnpoint = false;

    public void setSpawnpoint(boolean spawnpoint) {
        isSpawnpoint = spawnpoint;
    }

    public Platform(Platform platform){
        this.platformX = platform.getPlatformX();
        this.platformY = platform.getPlatformY();
        this.defaultX = (int) platform.getPlatformX();
        this.defaultY = (int) platform.getPlatformY();
        this.width = (int) platform.getWidth();
        this.height = (int) platform.getHeight();
        this.isSpawnpoint = platform.isSpawnpoint();
        this.r = platform.getRect();
    }
    public Platform(int x, int y, int width, int height) {
        this.platformX = (double) x;
        this.platformY = (double) y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = width;
        this.height = height;
        this.r = new Rectangle((double) x, (double) y, (double) width, (double) height);
    }

    public Platform(int x, int y, int Length, int Width, boolean isSpawnpoint) {
        this.platformX = (double) x;
        this.platformY = (double) y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = Length;
        this.width = Width;
        this.r = new Rectangle((double) x, (double) y, (double) Length, (double) Width);
        this.isSpawnpoint = isSpawnpoint;
    }

    public static int Collides(Player player, Platform[] platforms) {
        int b = -1;

        for (int i = 0; i < platforms.length; ++i) {
            Platform platform = platforms[i];
            double x0 = player.getX();
            double y0 = player.getY();
            double x1 = player.getX() + player.getSize();
            double y1 = player.getY() + player.getSize();
            double x2 = platform.platformX;
            double y2 = platform.platformY;
            double x3 = platform.platformX + (double) platform.width;
            double y3 = platform.platformY + (double) platform.height;
            if (!(x0 > x3) && !(x1 < x2) && !(y0 > y3) && !(y1 < y2)) {
                b = i;
            }
        }

        return b;
    }

    public boolean isSpawnpoint() {
        return this.isSpawnpoint;
    }

    public Rectangle getRect() {
        return this.r;
    }

    public double getHeight() {
        return this.r.h;
    }

    public double getPlatformX() {
        return this.r.x;
    }

    public double getPlatformY() {
        return this.r.y;
    }


    public double getWidth() {
        return this.r.w;
    }
}
