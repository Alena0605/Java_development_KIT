package ru.geekbrains.seminar1;

import javax.swing.*;
import java.awt.*;

/**
 * Класс основного окна, в котором непосредственно будет проходить игра.
 */
public class GameWindow extends JFrame {
    //region FIELDS

    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;

    JButton btnStart, btnExit;
    SettingsWindow settingsWindow;
    Map map;

    //endregion

    //region CONSTRUCTOR

    /**
     * Конструктор основного окна.
     */
    GameWindow() {
        // При нажатии на крестик окно закрывается (по умолчанию оно становится невидимым)
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Задаём размеры окна
        setSize(WIDTH, HEIGHT);
        // Передаём объект, относительно которого будет размещено окно
        setLocationRelativeTo(null);
        // Название окна
        setTitle("TicTacToe");
        // Запрещаем менять размер окна
        setResizable(false);

        // Создаем кнопки для начала игры и выхода из программы
        btnStart = new JButton("New game");
        btnExit = new JButton("Exit");
        // Создаём окно настроек
        settingsWindow = new SettingsWindow(this);
        // Подготавливаем поле для игры
        map = new Map();

        // Делаем кнопки "кликабельными"
        // Кнопка выхода завершает программу
        btnExit.addActionListener(e -> System.exit(0));
        // Кнопка начала игры открывает окно с настройками
        btnStart.addActionListener(e -> settingsWindow.setVisible(true));

        // Создаём панель, на которой будут размещены кнопки
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);

        // Добавляем панель с кнопками на основное окно
        add(panBottom, BorderLayout.SOUTH);
        // Добавляем поле для игры на основное окно
        add(map);

        // Делаем основное окно видимым
        setVisible(true);
    }

    //endregion

    //region METHODS

    /**
     * Метод начала новой игры.
     *
     * @param mode   Формат игры (с человеком или с компьютером)
     * @param sizeX  Количество строк
     * @param sizeY  Количество столбцов
     * @param winLen Длина выигрышной комбинации
     */
    void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        map.startNewGame(mode, sizeX, sizeY, winLen);
    }

    //endregion
}
