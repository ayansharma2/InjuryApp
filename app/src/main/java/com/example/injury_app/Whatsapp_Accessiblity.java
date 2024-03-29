package com.example.injury_app;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class Whatsapp_Accessiblity extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(getRootInActiveWindow() == null){
            return;
        }

        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        //get edit text if message from whats app
        List<AccessibilityNodeInfoCompat> messageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        if(messageNodeList == null || messageNodeList.isEmpty())
            return;


        //checking if message field if filled with text and ending with our suffix

        AccessibilityNodeInfoCompat messageField = messageNodeList.get(0);
        if(messageField == null || messageField.getText().length() == 0 || !messageField.getText().toString().endsWith(" Send by Safety App"))
            return;


        // get whatsapp send message button node list
        List<AccessibilityNodeInfoCompat> sendMessageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if(sendMessageNodeList == null || sendMessageNodeList.isEmpty())
            return;

        AccessibilityNodeInfoCompat sendMessage = sendMessageNodeList.get(0);
        if(!sendMessage.isVisibleToUser())
            return;


        //fire send button
        sendMessage.performAction(AccessibilityNodeInfo.ACTION_CLICK);




        try{
            Thread.sleep(2000);
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(2000);
            performGlobalAction(GLOBAL_ACTION_BACK);
        }catch (InterruptedException ignored) {}



    }

    @Override
    public void onInterrupt() {

    }
}
