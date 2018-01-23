package com.example.administrator.xposeddemo;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInit implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam){
        //app启动时调用
        if (lpparam.packageName.equals("com.example.administrator.xposeddemo")) {
            XposedHelpers.findAndHookMethod("com.example.administrator.xposeddemo.MainActivity", lpparam.classLoader, "OnCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Class c = lpparam.classLoader.loadClass("com.example.administrator.xposeddemo.MainActivity");
                    Field textView = c.getDeclaredField("textView");
                    textView.setAccessible(true);
                    TextView textView1= (TextView) textView.get(param.thisObject);
                    textView1.setText("Hello Xposed");
                }
            });
        }
    }
}