package com.itzyf.qrcode;

import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;

public class QrCodeMain {

    public static final String FILE_PATH = System.getProperty("java.io.tmpdir") + "/qrcode.jpg";

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("二维码生成器");
        // Setting the width and height of frame
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        JLabel label = new JLabel(getIcon());

        label.setBounds(50, 20, 500, 500);
        panel.add(label);


        JTextField code = new JTextField(100);
        code.setBounds(50, 530, 500, 40);
        code.setFont(new Font("宋体", Font.PLAIN, 24));
        code.setText("");
        panel.add(code);

        code.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            private void update() {
                createQrCode(code.getText());
                label.setIcon(getIcon());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private static ImageIcon getIcon() {
        ImageIcon image;
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return null;
            }
            image = new ImageIcon(ImageIO.read(file));
            image.setImage(image.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
            image = new ImageIcon();
        }
        return image;
    }

    private static void createQrCode(String text) {
        if ("".equals(text)) {
            return;
        }
        try {
            QrCodeCreateUtil.createQrCode(new FileOutputStream(new File(FILE_PATH)), text, 900, "JPEG");
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

}