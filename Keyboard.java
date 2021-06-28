import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private static boolean[] keys = new boolean[66568];
    private static boolean left;
    private static boolean right;
    private static boolean up;
    private static boolean space;
    private static boolean shift;
    private static boolean f11;
    private static boolean s;
    private static boolean e;
    private static boolean r;
    private static boolean f;
    private static boolean q;
    private static boolean c;
    private static boolean v;
    private static boolean g;
    private static boolean x;
    private static boolean z;
    private static boolean b;

    public Keyboard() {
    }

    public boolean getB() {
        return b;
    }

    public void update() {
        b = keys[KeyEvent.VK_B];
        z = keys[KeyEvent.VK_Z];
        left = keys[37] || keys[65];
        right = keys[39] || keys[68];
        up = keys[38] || keys[87] || keys[32];
        space = keys[32];
        shift = keys[16];
        f11 = keys[122];
        s = keys[83];
        e = keys[69];
        r = keys[82];
        f = keys[70];
        q = keys[81];
        c = keys[67];
        v = keys[86];
        g = keys[71];
        x = keys[88] || keys[127];
    }

    public boolean getZ() {
        return z;
    }

    public boolean getX() {
        return x;
    }

    public boolean getG() {
        return g;
    }

    public boolean getV() {
        return v;
    }

    public boolean getC() {
        return c;
    }

    public boolean getQ() {
        return q;
    }

    public boolean getF() {
        return f;
    }

    public boolean getE() {
        return e;
    }

    public boolean getR() {
        return r;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getF11() {
        return f11;
    }

    public boolean getS() {
        return s;
    }

    public boolean getShift() {
        return shift;
    }

    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent event) {
    }
}
