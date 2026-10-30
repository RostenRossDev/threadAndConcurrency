package com.example.useraggregation.sync.utils;

import com.example.useraggregation.sync.LockDemo;

public class MainLockDemo {

    static void main() {
        run();
    }

    public static void run() {
        LockDemo demo = new LockDemo();
        demo.deposit(50);
        demo.updateConfiguration("v2");
        System.out.println(demo.balance() + " / " + demo.readConfiguration());
    }
}
