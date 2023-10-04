package ru.geekbrains.HW1;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс логирования.
 */
public class Logger {
    //region FIELDS

    private final String FILENAME = "log.txt";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Date date = new Date();

    //endregion

    //region CONSTRUCTOR

    public Logger() {
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //endregion

    //region METHODS

    /**
     * Метод сохранения сообщения.
     *
     * @param msg Переданное сообщение
     */
    public void saveHistory(String msg) {
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(String.format("[%s] %s", dateFormat.format(date), msg));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Метод чтения истории сообщений из файла.
     *
     * @return Возвращает список строк из файла
     */
    public List<String> readHistory() {
        List<String> lines = new ArrayList<>();

        try (FileReader fr = new FileReader(FILENAME);
             BufferedReader reader = new BufferedReader(fr)) {

            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return lines;
    }

    //endregion
}
