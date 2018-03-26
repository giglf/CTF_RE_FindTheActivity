package ctf.green.findtheactivity;

/**
 * Created by giglf on 18-3-14.
 */
public class ARC4 {
    private byte state[] = new byte[256];
    private int x = 0, y = 0;

    public ARC4(byte[] seed) {
        for (int i = 0; i< 256; i++) {
            state[i] = (byte)i;
        }
        int i1 =0, i2 = 0;

        for (int i = 0; i< 256; i++) {
            i2 = ((int)seed[i1] + (int)state[i] + i2) & 0xff;
            byte tmp = state[i];
            state[i] = state[i2];
            state[i2] = tmp;
            i1 = (i1 + 1) % seed.length;
        }
    }

    public void translate(byte[] buf, int off, int end) {
        for (int i=off; i < end; i++) {
            x = (x+1) & 0xff;
            y = (y + (int)state[x]) & 0xff;
            byte tmp = state[x];
            state[x] = state[y];
            state[y] = tmp;
            int xorIndex = ((int)state[x]+(int)state[y]) & 0xff;
            buf[i] = (byte)(buf[i] ^ state[xorIndex]);
        }
        return;
    }

    public void translate(byte[] buf, int len) {
        translate(buf, 0, len);
    }

    public void translate(byte[] buf) {
        translate(buf, 0, buf.length);
    }
}
