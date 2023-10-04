package ru.geekbrains.seminar1;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна с настройками, где можно выбрать формат игры (с человеком или с компьютером),
 * задать размер поля и выбрать длину выигрышной комбинации.
 */
public class SettingsWindow extends JFrame {
    //region FIELDS

    private static final String BTN_START = "Start new game";
    private static final String LABEL_CHOICE_MODE = "Выберите режим игры";
    private static final String BTN_HUMAN_VS_HUMAN = "Играть против человека";
    private static final String BTN_HUMAN_VS_AI = "Играть против компьютера";
    private static final String LABEL_CHOICE_SIZE = "Выберите размер поля";
    private static final String SIZE_PREFIX = "Установленный размер поля: ";
    private static final String LABEL_CHOICE_WIN_LENGTH = "Выберите длину выигрышной комбинации";
    private static final String WIN_LENGTH_PREFIX = "Установленная длина: ";
    private static final int MODE_HVH = 0;
    private static final int MODE_HVA = 1;

    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 10;

    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;

    GameWindow gameWindow;

    JRadioButton btnPvsP, btnPvsAI;
    JButton btnStart;
    JLabel labelCurSize, labelCurWinLen;
    JSlider sliderSize, sliderWinLen;

    //endregion

    //region CONSTRUCTOR

    /**
     * Конструктор окна с настройками.
     *
     * @param gameWindow Основное окно
     */
    SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        // Центрируем окно с настройками относительно основного окна
        int centerGameWindowX = gameWindow.getX() + gameWindow.getWidth() / 2;
        int centerGameWindowY = gameWindow.getY() + gameWindow.getHeight() / 2;
        setLocation(centerGameWindowX - WIDTH / 2, centerGameWindowY - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);

        // Добавляем на окно с настройками панель с выбором режима и условий игры
        add(createMainPanel());
        // Добавляем на окно с настройками кнопку начала игры
        add(createButtonStart(), BorderLayout.SOUTH);
    }

    //endregion

    //region METHODS

    /**
     * Метод создания основной панели с настройками.
     *
     * @return Возвращает готовую панель
     */
    private Component createMainPanel() {
        JPanel panSetting = new JPanel(new GridLayout(3, 1));

        panSetting.add(createChoiceModePanel());
        panSetting.add(createChoiceSizePanel());
        panSetting.add(createChoiceWinLengthPanel());

        return panSetting;
    }

    /**
     * Метод создания кнопки для старта игры.
     *
     * @return Возвращает кнопку
     */
    private Component createButtonStart() {
        btnStart = new JButton(BTN_START);

        btnStart.addActionListener(e -> {
            setVisible(false);
            startGame();
        });

        return btnStart;
    }

    /**
     * Метод, запускающий новую игру.
     */
    private void startGame() {
        int mode;

        if (btnPvsP.isSelected()) {
            mode = MODE_HVH;
        } else if (btnPvsAI.isSelected()) {
            mode = MODE_HVA;
        } else {
            throw new RuntimeException("Unknown game mode!");
        }

        int size = sliderSize.getValue();
        int winLen = sliderWinLen.getValue();
        gameWindow.startNewGame(mode, size, size, winLen);
    }


    /**
     * Метод для выбора режима игры (играть против человека или против компьютера).
     *
     * @return Возвращает панель с настройками режима игры
     */
    private Component createChoiceModePanel() {
        JPanel panChoiceMode = new JPanel(new GridLayout(3, 1));
        JLabel labelChoiceMode = new JLabel(LABEL_CHOICE_MODE);

        ButtonGroup bgChoiceMode = new ButtonGroup();
        btnPvsP = new JRadioButton(BTN_HUMAN_VS_HUMAN);
        btnPvsAI = new JRadioButton(BTN_HUMAN_VS_AI);
        bgChoiceMode.add(btnPvsP);
        bgChoiceMode.add(btnPvsAI);
        btnPvsAI.setSelected(true);

        panChoiceMode.add(labelChoiceMode);
        panChoiceMode.add(btnPvsP);
        panChoiceMode.add(btnPvsAI);

        return panChoiceMode;
    }

    /**
     * Метод для выбора размера поля.
     *
     * @return Возвращает панель с настройками изменения размера поля
     */
    private Component createChoiceSizePanel() {
        JPanel panChoiceSize = new JPanel(new GridLayout(3, 1));
        JLabel labelChoiceSize = new JLabel(LABEL_CHOICE_SIZE);
        labelCurSize = new JLabel(SIZE_PREFIX + MIN_SIZE);
        sliderSize = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);

        sliderSize.addChangeListener(e -> {
            int curSize = sliderSize.getValue();
            labelCurSize.setText(SIZE_PREFIX + curSize);
            sliderWinLen.setMaximum(curSize);
        });

        panChoiceSize.add(labelChoiceSize);
        panChoiceSize.add(labelCurSize);
        panChoiceSize.add(sliderSize);

        return panChoiceSize;
    }

    /**
     * Метод для выбора длины выигрышной комбинации.
     *
     * @return Возвращает панель с настройками изменения условия победы
     */
    private Component createChoiceWinLengthPanel() {
        JPanel panChoiceWinLen = new JPanel(new GridLayout(3, 1));
        JLabel labelChoiceWinLen = new JLabel(LABEL_CHOICE_WIN_LENGTH);
        labelCurWinLen = new JLabel(WIN_LENGTH_PREFIX + MIN_SIZE);
        sliderWinLen = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);

        sliderWinLen.addChangeListener(e -> labelCurWinLen.setText(WIN_LENGTH_PREFIX + sliderWinLen.getValue()));

        panChoiceWinLen.add(labelChoiceWinLen);
        panChoiceWinLen.add(labelCurWinLen);
        panChoiceWinLen.add(sliderWinLen);

        return panChoiceWinLen;
    }

    //endregion
}
