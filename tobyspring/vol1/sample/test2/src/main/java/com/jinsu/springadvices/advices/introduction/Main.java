package com.jinsu.springadvices.advices.introduction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MyTargetClass myObject = context.getBean(MyTargetClass.class);
//        System.out.println("Bean retrieved: " + myObject.getClass().getName());

        Lockable lockable = (Lockable) myObject;
//        System.out.println("Lockable retrieved: " + lockable.getClass().getName());

        if (!lockable.locked()) {
            myObject.setName("hello");
            System.out.println("Name set to: " + myObject.getName());
            lockable.lock();
            System.out.println("Object locked.");
        } else {
            try {
                myObject.setName("world");
            } catch (LockedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}