package ru.geekbrains.HW1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс серверного окна.
 */
public class ServerWindow extends JFrame {
    //region FIELDS

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private static final int POSX = 200;
    private static final int POSY = 100;

    private static final String BTN_START = "Start Server";
    private static final String BTN_STOP = "Stop Server";

    private final JTextArea log;
    private JButton btnStart, btnStop;

    private boolean isServerWorking = false;

    //endregion

    //region CONSTRUCTOR

    /**
     * Конструктор серверного окна.
     */
    ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(POSX, POSY);
        setTitle("Chat server");

        log = new JTextArea("Server 127.0.0.1:8080\n\n");
        log.setFont(new Font("Times new roman", Font.PLAIN, 18));
        log.setEditable(false);

        add(log);

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(createButtonStart());
        panBottom.add(createButtonStop());

        add(panBottom, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isServerWorking) {
                    isServerWorking = false;
                }

                super.windowClosing(e);
            }
        });

        setVisible(true);
    }

    //endregion

    //region GETTER

    /**
     * Получение текущего состояния сервера.
     *
     * @return Возвращает true, если сервер работает, false - если отключён
     */
    public boolean getServerStatus() {
        return isServerWorking;
    }

    //endregion

    //region METHODS

    /**
     * Метод создания кнопки старта сервера.
     *
     * @return Возвращает кнопку
     */
    private Component createButtonStart() {
        btnStart = new JButton(BTN_START);

        btnStart.addActionListener(e -> {
            if (!isServerWorking) {
                isServerWorking = true;
                log.append("Server starts working...\n");
            } else {
                log.append("Server is already running\n");
            }
        });

        return btnStart;
    }

    /**
     * Метод создания кнопки остановки сервера.
     *
     * @return Возвращает кнопку
     */
    private Component createButtonStop() {
        btnStop = new JButton(BTN_STOP);

        btnStop.addActionListener(e -> {
            if (isServerWorking) {
                isServerWorking = false;
                log.append("Server stopping...\n");
            } else {
                log.append("Server was already stopped\n");
            }
        });

        return btnStop;
    }

    //endregion
}
