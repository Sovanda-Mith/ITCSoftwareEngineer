import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class EX2 implements NativeKeyListener {
    private static boolean isRunning = true;
    private static String message = "HitMe!";

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
            try {
                isRunning = false;
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        } else {
            message = NativeKeyEvent.getKeyText(e.getKeyCode()).toLowerCase();
        }
    }

    public static void main(String[] args) throws InterruptedException, NativeHookException {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new EX2());

        while (isRunning) {
            // if (lastKey.isEmpty()) {
            // System.out.print("HitMe!");
            // }
            System.out.println(message);
            Thread.sleep(200);
        }
        System.out.println("\n\nThanks you!");
        GlobalScreen.unregisterNativeHook();
    }

}
