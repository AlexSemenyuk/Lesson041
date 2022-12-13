package org.itstep;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread extends Thread {

    private final Reader reader;
    private final Writer writer;
    public static final int bufferVolue = 1000;
    Lock locker = new ReentrantLock();

    MyThread(String name, Reader reader, Writer writer) {
        super(name);
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        char[] buffer = new char[bufferVolue];
        int count;
        while (true) {
//            locker.lock();
            synchronized (reader) {
                try {
                    count = reader.read(buffer);
                    if (count > 0) {
                        writer.write(buffer, 0, count);
                        System.out.println("Thread " + getName() + " writes " + count + " bytes");
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
//            locker.unlock();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
