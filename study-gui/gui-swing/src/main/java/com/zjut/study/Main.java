package com.zjut.study;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("hello world");
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
