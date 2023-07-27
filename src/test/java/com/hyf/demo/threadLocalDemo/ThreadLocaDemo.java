package com.hyf.demo.threadLocalDemo;

public class ThreadLocaDemo {
 
    private static ThreadLocal<String> localVar = new ThreadLocal<String>();
 

    public static void main(String[] args) throws InterruptedException {
        //公共变量
        String str = "hello world";

        new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set(str);
                //打印当前线程中本地内存中本地变量的值
                System.out.println(str + " :" + localVar.get());
                localVar.set("我被改变了");
                System.out.println(str + " :" + localVar.get());

                localVar.remove();
            }
        }).start();


        Thread.sleep(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set(str);
                //打印当前线程中本地内存中本地变量的值
                System.out.println(str + " :" + localVar.get());
                localVar.set("b线程的threadlocal被改了，但是不影响其他线程");
                System.out.println(str + " :" + localVar.get());
                localVar.remove();
            }
        }).start();

        Thread.sleep(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set(str);
                System.out.println("我是c线程 = " + localVar.get());
                localVar.remove();
            }
        }).start();




    }
}
