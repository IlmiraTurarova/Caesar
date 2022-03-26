import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static final String originalFile = "./src/files/original.txt";
    public static final String encryptionFile = "./src/files/encryption.txt";
    public static final String decryptionFile = "./src/files/decryption.txt";
    public static final String bruteFile = "./src/files/brute.txt";

    public static void main(String[] args) {
        //создаем фрэйм
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Криптоанализатор");

        Appearance demo = new Appearance(); // создаём класс
        frame.setContentPane(demo.createContentPane()); // запускаем метод описывающий внешний вид

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }


    //выбираем режим Шифровка или Расшифровка
    public static void getNumber (int mode, int key) {
            if (mode==1) {
                //Шифровка текста//////////////////////////////////////////////////////////////////////////////////////
                    //ключ равен остатку от деления, введенного пользователем ключа, на длину алфавита
                    key = key%Encrypt.ALPHABET.length;
                    Encrypt.encryptFile(originalFile, encryptionFile, key);
            } else if (mode==2) {
                //Расшифровка с помощью ключа//////////////////////////////////////////////////////////////////////////
                key = key%Encrypt.ALPHABET.length;
                Decrypt.decryptFile(encryptionFile, decryptionFile, key);
            }
    }

    //Режим Расшифровка методом Brute Force
    public static void getNumber (int mode) {
        if (mode==3) {
            //Расшифровка с помощью brute force///////////////////////////////////////////////////////////////////
            BruteForce.bruteForceFile(encryptionFile, bruteFile);
        }
    }

}
