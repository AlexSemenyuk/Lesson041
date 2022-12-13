package org.itstep;


import java.io.*;
import java.util.Arrays;

/**
 * Java. Lesson041. Task01
 * Считывание файла 10 потоками и запись в новый файл
 *
 * @author Semenyuk Alexander
 * Date 10.12.2022
 * Завдання: синхронізація роботи обчислювальних потоків.
 * Створити 10 обчислювальних потоків (Thread), що повинні працювати одночасно.
 * Кожен потік буде зчитувати порцію даних з потоку даних InputStream, і записувати в OutputStream.
 * Задача полягає в тому, щоб усі потоки працювали по черзі.
 * Для цього застосовуйте синхронізацію або через synchronized або за допомогою ReentrantLock
 * Продемонструвати роботу потоків на прикладі копіювання великого текстового файлу, наприклад, alica.txt.
 * Вихідний файл повинен мати той самий зміст, що і оригінальний.
 */
public class Task01 {


    public static void main(String[] args) throws IOException {
        int numberThreads = 10;
        String fileForRead = "alice.txt";
        String fileForWrite = "a.txt";

        Reader reader = new FileReader(fileForRead);
        Writer writer = new FileWriter(fileForWrite);
        MyThread[] threads = new MyThread[numberThreads];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread("MyThread №" + i, reader, writer);
        }
        Arrays.stream(threads).forEach(Thread::start);

        Arrays.stream(threads).forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        reader.close();
        writer.close();
    }
}
