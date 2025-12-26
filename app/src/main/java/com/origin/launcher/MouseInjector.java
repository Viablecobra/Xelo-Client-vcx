package com.origin.launcher;

import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.os.Build;

public class MouseInjector {
    private static InputManager inputManager;
    
    static {
        try {
            inputManager = (InputManager) Class.forName("android.hardware.input.InputManager")
                .getMethod("getInstance").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void injectMouseMove(float dx, float dy) {
        if (inputManager == null) return;
        
        long now = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(now, now, 
            MotionEvent.ACTION_HOVER_MOVE, dx * 0.5f, dy * 0.5f, 0);
        event.setSource(InputDevice.SOURCE_MOUSE);
        
        int mode = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mode = InputManager.INJECT_INPUT_EVENT_MODE_ASYNC;
        } else {
            mode = 2;
        }
        
        inputManager.injectInputEvent(event, mode);
        event.recycle();
    }
}