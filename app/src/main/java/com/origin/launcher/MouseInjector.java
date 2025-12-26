package com.origin.launcher;

import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;
import java.lang.reflect.Method;

public class MouseInjector {
    private static InputManager inputManager;
    private static Method injectMethod;
    
    static {
        try {
            inputManager = (InputManager) Class.forName("android.hardware.input.InputManager")
                .getMethod("getInstance").invoke(null);
            
            injectMethod = inputManager.getClass().getMethod("injectInputEvent", 
                MotionEvent.class, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void injectMouseMove(float dx, float dy) {
        if (inputManager == null || injectMethod == null) return;
        
        try {
            long now = SystemClock.uptimeMillis();
            MotionEvent event = MotionEvent.obtain(now, now, 
                MotionEvent.ACTION_HOVER_MOVE, dx * 0.5f, dy * 0.5f, 0);
            event.setSource(InputDevice.SOURCE_MOUSE);
            
            injectMethod.invoke(inputManager, event, 2);
            event.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}