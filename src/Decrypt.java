import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Decrypt {
    //существует ли зашифрованный файл
    public static boolean encFileExist;

    //находим позицию зашифрованного символа в алфавите
    public static int getPosition(Character array[], int firstElement, int lastElement, int elementToSearch) {

        //пока последний элемент больше первого
        if (lastElement >= firstElement) {
            int middle = (firstElement + lastElement)/2;

            //если средний элемент - искомый элемент, вернуть его индекс
            if (array[middle] == elementToSearch)
                return middle;

            //если средний элемент больше искомого
            //вызываем метод рекурсивно, где последний элемент будет = средний - 1
            if (array[middle] > elementToSearch)
                return getPosition(array, firstElement, middle - 1, elementToSearch);

            //если средний элемент меньше искомого
            //вызываем метод рекурсивно, где первый элемент = средний + 1
            return getPosition(array, middle + 1, lastElement, elementToSearch);
        }

        return -1;
    }

    //расшифровываем - находим символ на позиции смещенной на заданный сдвиг
    public static char getSymbol(int posSymbol, int key) {
        //проверка закольцованного алфавита
        //сдвигаем на key позиций назад
        char symbol = Encrypt.ALPHABET[(Encrypt.ALPHABET.length + posSymbol-key) % Encrypt.ALPHABET.length];
        return symbol;
    }

    public static void decryptFile(String encryptionFile, String decryptionFile, int key) {
        //расшифровка/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String text=null;

        //читаем из encryptionFile, записываем в decryptionFile
        //если файл по заданному адресу существует

        if (Files.exists(Path.of(encryptionFile))) {
            encFileExist = true;//файл существует
            try (BufferedReader reader = new BufferedReader(new FileReader(encryptionFile));
                 FileWriter writer = new FileWriter(decryptionFile);){
                while ((text = reader.readLine())!=null) {
                    char[] arrayEncryptText = text.toCharArray();
                    for (int i = 0; i < text.length(); i++) {
                        //находим позицию в алфавите
                        int position = Decrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, arrayEncryptText[i]);
                        //меняем исходный символ на символ сдвинутый на ключ
                        arrayEncryptText[i] = Decrypt.getSymbol(position, key);
                    }

                    //переводим массив символов в строку
                    String decryptText = new String(arrayEncryptText);
                    writer.write(decryptText+"\n");
                }
                System.out.println("Файл успешно расшифрован");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            encFileExist = false;
            System.out.println("Зашифрованный файл по заданному адресу не существует");
        }
    }
}
