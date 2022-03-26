import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encrypt {
    //существует ли исходный файл
    public static boolean origFileExist;
    //алфавит
    public static final Character[] ALPHABET = {1, 2, 3, 4, 5, 6, 7, '\n', ' ', '!', '"', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'Ё', '«', 'ё', '»', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т',
            'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э',
            'ю', 'я'};


    //проверяем есть ли символ в алфавите
    public static boolean existInAlphabet(char symbol) {
        //переводим массив в список
        List<Character> charList = new ArrayList<>(Arrays.asList(ALPHABET));
        //возвращаем true если символ существует в алфавите
        if (charList.contains(symbol)) return true;
        else return false;
    }

    //находим позицию изначального символа в алфавите
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

    //находим символ на позиции смещенной на заданный сдвиг
    public static char getSymbol(int posSymbol, int key) {
        //проверка закольцованного алфавита
        //сдвигаем на key позиций вперед
        char symbol = ALPHABET[(ALPHABET.length+posSymbol+key) % ALPHABET.length];
        return symbol;
    }



    public static void encryptFile(String originalFile, String encryptionFile, int key) {
        //шифровка////////////////////////////////////////////////////////////////////////////////////////////////////
        String text=null;

        //читаем из originalFile, записываем в encryptionFile
        //если файл по заданному адресу существует
        if (Files.exists(Path.of(originalFile))) {
            origFileExist = true;//файл существует
            try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                 FileWriter writer = new FileWriter(encryptionFile);) {
                while ((text = reader.readLine()) != null) {
                    char[] arrayText = text.toCharArray();
                    for (int i = 0; i < text.length(); i++) {
                        //если символ есть в алфавите
                        if (Encrypt.existInAlphabet(arrayText[i])) {
                            //находим позицию в алфавите
                            int position = Encrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length - 1, arrayText[i]);
                            //меняем исходный символ на символ сдвинутый на ключ
                            arrayText[i] = Encrypt.getSymbol(position, key);
                        }
                    }
                    //переводим массив символов в строку
                    String encryptText = new String(arrayText);
                    writer.write(encryptText + "\n");
                }
                System.out.println("Файл успешно зашифрован");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            origFileExist = false;
            System.out.println("Файл оригинала по заданному адресу не существует");
        }
    }
}
