package ru.geekbrains.seminar1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Класс подготовки игрового поля, а также описания логики игры.
 */
public class Map extends JPanel {
    //region FIELDS

    private static final Random RANDOM = new Random();
    private static final int HUMAN_DOT = 1;
    private static final int AI_DOT = 2;
    private static final int EMPTY_DOT = 0;
    private static final int PADDING = 10;

    private int gameStateType;
    private static final int STATE_GAME = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_DRAW = 3;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";

    private int width, height, cellWidth, cellHeight;
    private int mode, fieldSizeX, fieldSizeY, winLen;
    private int[][] field;
    private boolean gameWork;

    //endregion

    //region CONSTRUCTOR

    /**
     * Конструктор подготовки поля для игры.
     */
    Map() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gameWork) update(e);
            }
        });
    }

    //endregion

    //region METHODS

    /**
     * Инициализация поля.
     */
    private void initMap() {
        field = new int[fieldSizeX][fieldSizeY];
    }

    /**
     * Метод начала новой игры.
     *
     * @param mode   Формат игры (с человеком или с компьютером)
     * @param sizeX  Количество строк
     * @param sizeY  Количество столбцов
     * @param winLen Длина выигрышной комбинации
     */
    public void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        this.mode = mode;
        this.fieldSizeX = sizeX;
        this.fieldSizeY = sizeY;
        this.winLen = winLen;

        initMap();
        gameWork = true;
        gameStateType = STATE_GAME;

        repaint();
    }

    /**
     * Метод обновления игрового поля.
     *
     * @param mouseEvent Результат клика мышкой
     */
    private void update(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / cellWidth;
        int y = mouseEvent.getY() / cellHeight;

        if (!isValidCell(x, y) || !isEmptyCell(x, y)) return;

        field[x][y] = HUMAN_DOT;
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;

        aiTurn();
        repaint();
        checkEndGame(AI_DOT, STATE_WIN_AI);
    }

    /**
     * Проверка на валидность ячейки.
     *
     * @param x Координата х
     * @param y Координата у
     * @return Возвращает булевое значение валидна ячейка или нет
     */
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка на доступность ячейки.
     *
     * @param x Координата х
     * @param y Координата у
     * @return Возвращает булевое значение свободна ячейка или нет
     */
    private boolean isEmptyCell(int x, int y) {
        return field[x][y] == EMPTY_DOT;
    }

    /**
     * Ход компьютера.
     */
    private void aiTurn() {
        int x, y;

        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));

        field[x][y] = AI_DOT;
    }

    /**
     * Проверка на отсутствие пустых ячеек.
     *
     * @return Возвращает булевое значение есть пустые ячейки или нет
     */
    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }

        return true;
    }

    /**
     * Проверка на окончание игры.
     *
     * @param dot          Отметка игрока или компьютера
     * @param gameOverType Отметка текущей стадии игры
     * @return Возвращает булевое значение окончена игра или нет
     */
    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot)) {
            this.gameStateType = gameOverType;
            repaint();
            return true;
        } else if (isMapFull()) {
            this.gameStateType = STATE_DRAW;
            repaint();
            return true;
        }

        return false;
    }

    /**
     * Проверка на победу.
     *
     * @param dot Отметка игрока или компьютера
     * @return Возвращет булевое значение есть выигрышная комбинация или нет
     */
    private boolean checkWin(int dot) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLen, dot)) return true;
                if (checkLine(i, j, 1, 1, winLen, dot)) return true;
                if (checkLine(i, j, 0, 1, winLen, dot)) return true;
                if (checkLine(i, j, 1, -1, winLen, dot)) return true;
            }
        }

        return false;
    }

    /**
     * Проверка комбинации по горизонтали, вертикали и двум диагоналям.
     *
     * @param x   Коорлината х
     * @param y   Координата у
     * @param vx  Дельта по координате х
     * @param vy  Дельта по координате у
     * @param len Длина выигрышной комбинации
     * @param dot Отметка игрока или компьютера
     * @return Возвращает булевое значение есть выигрышная комбинация или нет
     */
    private boolean checkLine(int x, int y, int vx, int vy, int len, int dot) {
        int far_x = x + (len - 1) * vx;
        int far_y = y + (len - 1) * vy;

        if (!isValidCell(far_x, far_y)) return false;

        for (int i = 0; i < len; i++) {
            if (field[x + i * vx][y + i * vy] != dot) return false;
        }

        return true;
    }

    /**
     * Метод отрисовки игрового поля.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameWork) render(g);
    }

    /**
     * Отрисовка символов на игровом поле и вывод сообщения с результатом окончания игры.
     *
     * @param g Объект класса Graphics, который умеет рисовать
     */
    private void render(Graphics g) {
        width = getWidth();
        height = getHeight();
        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;

        g.setColor(Color.BLACK);

        for (int w = 0; w < fieldSizeX; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        for (int h = 0; h < fieldSizeY; h++) {
            int y = h * cellHeight;
            g.drawLine(0, y, width, y);
        }

        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (field[x][y] == EMPTY_DOT) continue;

                if (field[x][y] == HUMAN_DOT) {
                    g.drawLine(x * cellWidth + PADDING,
                            y * cellHeight + PADDING,
                            (x + 1) * cellWidth - PADDING,
                            (y + 1) * cellHeight - PADDING);
                    g.drawLine(x * cellWidth + PADDING,
                            (y + 1) * cellHeight - PADDING,
                            (x + 1) * cellWidth - PADDING,
                            y * cellHeight + PADDING);
                } else if (field[x][y] == AI_DOT) {
                    g.drawOval(x * cellWidth + PADDING,
                            y * cellHeight + PADDING,
                            cellWidth - PADDING * 2,
                            cellHeight - PADDING * 2);
                } else {
                    throw new RuntimeException("Unchecked value " + field[x][y] +
                            " in cell: x = " + x + ", y = " + y);
                }
            }
        }

        if (gameStateType != STATE_GAME) {
            showMessage(g);
        }
    }

    /**
     * Вывод сообщения о победе или ничье.
     *
     * @param g Объект класса Graphics, который умеет рисовать
     */
    private void showMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, getHeight() / 2, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));

        switch (gameStateType) {
            case STATE_DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2 + 60);
            case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, 20, getHeight() / 2 + 60);
            case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, 70, getHeight() / 2 + 60);
            default -> throw new RuntimeException("Unchecked gameOverState: " + gameStateType);
        }

        gameWork = false;
    }

    //endregion
}
