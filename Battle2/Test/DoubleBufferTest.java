import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

public class DoubleBufferTest {

    private DoubleBuffer doubleBuffer;
    
    public void setUp() throws Exception {		//calls the main program
        this.doubleBuffer = new DoubleBuffer();
    }
   
    public void tearDown() throws Exception {
        this.doubleBuffer = null;
    }

    
    public void exitOnClick() throws NoSuchFieldException, IllegalAccessException {
        doubleBuffer.init();
        MouseEvent me = new MouseEvent(doubleBuffer, 0, 0, 0, 100, 100, 1, false);

        MouseListener[] listeners = doubleBuffer.getMouseListeners();
        listeners[0].mousePressed(me);

        Field f = doubleBuffer.getClass().getDeclaredField("frozen"); //NoSuchFieldException
        f.setAccessible(true);
        System.out.println("Buffering paused by mouse click.");
    }

    public void onStartANewThreadShouldStart() throws NoSuchFieldException, IllegalAccessException {
        doubleBuffer.start();

        Field f = doubleBuffer.getClass().getDeclaredField("animatorThread"); //NoSuchFieldException
        f.setAccessible(true);

        Thread anim = (Thread) f.get(doubleBuffer);
        
    }

}