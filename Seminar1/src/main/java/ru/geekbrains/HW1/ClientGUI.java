package ru.geekbrains.HW1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс клиентского окна.
 */
public class ClientGUI extends JFrame {
    //region FIELDS

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private static final int POSX = 600;
    private static final int POSY = 100;

    private static final String BTN_LOGIN = "Login";
    private static final String BTN_SEND = "Send";

    private final ServerWindow serverWindow;
    private final Logger logger = new Logger();

    private JTextField ip, port, login;
    private JPasswordField password;
    private JButton btnLogin;
    private JTextArea historyArea;
    private JTextField message;
    private JButton btnSend;

    private boolean isConnected = false;

    private final String messageTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    //endregion

    //region CONSTRUCTOR

    /**
     * Конструктор клиентского окна.
     *
     * @param serverWindow Сервер
     */
    ClientGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(POSX, POSY);
        setTitle("Client chat");

        add(createTopPanel(), BorderLayout.NORTH);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrolling = new JScrollPane(historyArea);
        add(scrolling);

        add(createBottomPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    //endregion

    //region METHODS

    /**
     * Создание верхней панели на клиентском окне, где необходимо заполнить поля:
     * IP-адрес и порт сервера, логин и пароль пользоваетля.
     *
     * @return Возвращает верхнюю панель
     */
    private Component createTopPanel() {
        JPanel panTop = new JPanel(new GridLayout(2, 3));

        ip = new JTextField("127.0.0.1");
        port = new JTextField("8080");
        login = new JTextField("Alena Belova");
        password = new JPasswordField("123456");

        panTop.add(ip);
        panTop.add(port);
        panTop.add(login);
        panTop.add(password);
        panTop.add(createLoginButton());

        return panTop;
    }

    /**
     * Создание кнопки подключения к серверу.
     *
     * @return Возвращает кнопку
     */
    private Component createLoginButton() {
        btnLogin = new JButton(BTN_LOGIN);

        btnLogin.addActionListener(e -> {
            if (serverWindow.getServerStatus() && !isConnected) {
                historyArea.append("Connection is established with Server 127.0.0.1:8080\n\n");
                isConnected = true;

                List<String> lines = logger.readHistory();
                if (lines != null) {
                    for (String line : lines) {
                        historyArea.append(line + "\n");
                    }
                }
            } else if (!serverWindow.getServerStatus() && !isConnected) {
                historyArea.append("505 ERROR! Server is not available\n\n");
            }
        });

        return btnLogin;
    }

    /**
     * Создание нижней панели на клиентском окне, где находится поле для ввода ссобщния и кнопка "отправить".
     *
     * @return Возвращает нижнюю панель
     */
    private Component createBottomPanel() {
        JPanel panBottom = new JPanel(new BorderLayout());
        message = new JTextField();

        message.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    btnSend.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        panBottom.add(message);
        panBottom.add(createSendButton(), BorderLayout.EAST);

        return panBottom;
    }

    /**
     * Создание кнопки отправки сообщения.
     *
     * @return Возвращает кнопку
     */
    private Component createSendButton() {
        btnSend = new JButton(BTN_SEND);

        btnSend.addActionListener(e -> {
            if (serverWindow.getServerStatus() && isConnected) {
                if (!message.getText().isEmpty()) {
                    String result = login.getText() + ": " + message.getText() + "\n";
                    historyArea.append(messageTime + " " + result);
                    logger.saveHistory(result);
                    message.setText("");
                }
            } else {
                historyArea.append("You are not connected.\n\n");
            }
        });

        return btnSend;
    }

    //endregion
}
