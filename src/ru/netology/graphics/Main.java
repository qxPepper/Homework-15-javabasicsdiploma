package ru.netology.graphics;

import ru.netology.graphics.image.TGConverter;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TGConverter();

        //Сервер лучше конвертирует, но удобнее работать с сохранением в файл.

        /*GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем*/

        // Или то же, но с сохранением в файл:
        PrintWriter fileWriter = new PrintWriter(new File("converted-image.txt"));
        converter.setMaxRatio(2.0);
        converter.setMaxWidth(200);
        converter.setMaxHeight(300);
        //fileWriter.write(converter.convert("phillip.png"));
        fileWriter.write(converter.convert("https://i.ibb.co/6DYM05G/edu0.jpg"));
        fileWriter.close();
    }
}
