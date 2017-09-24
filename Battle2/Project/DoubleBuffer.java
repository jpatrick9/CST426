import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Modified version of code from https://stackoverflow.com/questions/10508042/how-do-you-double-buffer-in-java-for-a-game
//but used in an applet instead (referenced from https://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html)

public class DoubleBuffer extends Applet implements Runnable, MouseListener {

    private static final int FPS = 20;	//determines speed at which applet and buffers operate

    private int frameNumber = -1;
    private int delay;
    private Thread anim;
    private boolean frozen = false;

    private boolean fillColumnTop = true;

    private Dimension offDimension;
    private Image offImage;
    private Graphics offGraphics;

    @Override
    public void init() {
        delay = 1000 / FPS;
        addMouseListener(this);
    }

    @Override
    public void start() {	//begins animation
        if (!frozen) {
            if (anim == null) {
                anim = new Thread(this);
            }
            anim.start();
        }
    }

    @Override
    public void stop() {    //interrupts buffering, on method call
        anim = null;
        offGraphics = null;
        offImage = null;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (Thread.currentThread() == anim) { //iterate through animation frames
           
            frameNumber++; 
            repaint();
            try {							//artificial delay meant to combat flickering
                startTime += delay;
                Thread.sleep(Math.max(0,
                        startTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public void update(Graphics g) {
        Dimension d = getSize();
        boolean fillSquare;
        boolean fillNextFrame;
        int rowWidth = 0;
        int x = 0, y = 0;
        int w, h;
        int tmp;
        if ((offGraphics == null)					//prepares offscreen incoming squares for display 
                || (d.width != offDimension.width)
                || (d.height != offDimension.height)) {
            offDimension = d;
            offImage = createImage(d.width, d.height);
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setColor(getBackground());			//processes the previous buffer
        offGraphics.fillRect(0, 0, d.width, d.height);
        offGraphics.setColor(Color.black);
        fillSquare = fillColumnTop;			//sets width of square and fills it
        fillColumnTop = !fillColumnTop;
        int squareSize = 20;
        tmp = frameNumber % squareSize;
        if (tmp == 0) {
            w = squareSize;
            fillNextFrame = !fillSquare;
        } else {
            w = tmp;
            fillNextFrame = fillSquare;
        }
        while (x < d.width) {
            int colHeight = 0;
            while (y < d.height) {
                colHeight += squareSize;
                if (colHeight > d.height) {	//manages squares' overlap with border
                    h = d.height - y;
                } else {
                    h = squareSize;
                }
                if (fillSquare) {						//fills in black squares
                    offGraphics.fillRect(x, y, w, h);
                    fillSquare = false;
                } else {
                    fillSquare = true;
                }
                y += h;
            }

            //Determine x, y, and w for the next go around.
            x += w;
            y = 0;
            w = squareSize;
            rowWidth += w;
            if (rowWidth > d.width) {
                w = d.width - x;
            }
            fillSquare = fillColumnTop;
            fillColumnTop = !fillColumnTop;
        }
        fillColumnTop = fillNextFrame;
        g.drawImage(offImage, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (frozen) {
            frozen = false;
            start();
        } else {
            frozen = true;	//alternative to deleting the back buffer
            anim = null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}