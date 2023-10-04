package ru.geekbrains.HW1;

public class Main {
    public static void main(String[] args) {
        ServerWindow server = new ServerWindow();
        new ClientGUI(server);
    }
}
