import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class BruteForce {
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


   public static void bruteForceFile (String encryptionFile, String bruteFile) {
        //расшифровка   Brute Force////////////////////////////////////////////////////////////////////////////////////
        String text=null;
        String bruteForceText="";

        //читаем из encryptionFile, записываем в bruteFile
        //если файл по заданному адресу существует
        if (Files.exists(Path.of(encryptionFile))) {
            Decrypt.encFileExist = true;
            try (BufferedReader reader = new BufferedReader(new FileReader(encryptionFile));
                 FileWriter writer = new FileWriter(bruteFile);){
                //считываем из файла весь текст
                while ((text = reader.readLine())!=null) {
                    char[] arrayBruteForce = text.toCharArray();
                    //переводим массив символов в строку
                    bruteForceText = bruteForceText + new String(arrayBruteForce) + "\n";
                }

               for (int i = 0; i < Encrypt.ALPHABET.length; i++) {
                    //находим позицию запятой ','
                    int posComma = Encrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, ',');
                    //находим символ, которым заменяется запятая ',' при ключе i
                    char symComma = Encrypt.getSymbol(posComma, i);

                    //находим позицию точки '.'
                    int posDot = Encrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, '.');
                    //находим символ, которым заменяется точка '.' при ключе i
                    char symDot = Encrypt.getSymbol(posDot, i);

                   //находим позицию '!'
                   int posQues = Encrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, '!');
                   //находим символ, которым заменяется '!' при ключе i
                   char symQues = Encrypt.getSymbol(posQues, i);

                   //находим позицию 'в'
                   int posV = Encrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, 'в');
                   //находим символ, которым заменяется 'в' при ключе i
                   char symV = Encrypt.getSymbol(posV, i);

                    //находим позицию пробела ' '
                    int posSP = Encrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, ' ');
                    //находим символ, которым заменяется пробел '.' при ключе i
                    char symSP = Encrypt.getSymbol(posSP, i);



                    //substr1 = сочетание запятой и пробела
                    String substr1 = String.valueOf(symComma)  + String.valueOf(symSP);
                    //substr2 = сочетание точки и пробела
                    String substr2 = String.valueOf(symDot)  + String.valueOf(symSP);
                   //substr3 = сочетание восклицательного знака и пробела
                   String substr3 = String.valueOf(symQues)  + String.valueOf(symSP);
                   //substr4 = сочетание " в "
                   String substr4 = String.valueOf(symSP) + String.valueOf(symV)  + String.valueOf(symSP);

                   //ищем первое вхождение
                   int indexDecryptComma = bruteForceText.indexOf(substr1);
                    //ищем первое вхождение
                    int indexDecryptDot = bruteForceText.indexOf(substr2);
                   //ищем первое вхождение
                   int indexDecryptQues = bruteForceText.indexOf(substr3);
                   //ищем первое вхождение
                   int indexDecryptV = bruteForceText.indexOf(substr4);
                   //ищем последнее вхождение
                   int indexLastDecryptComma = bruteForceText.lastIndexOf(substr1);
                   //ищем последнее вхождение
                   int indexLastDecryptDot = bruteForceText.lastIndexOf(substr2);
                   //ищем последнее вхождение
                   int indexLastDecryptQues = bruteForceText.lastIndexOf(substr3);
                   //ищем последнее вхождение
                   int indexLastDecryptV = bruteForceText.lastIndexOf(substr4);

                    //если данные сочетания встречаются в зашифрованном файле, тогда начинаем расшифровку
                    if (bruteForceText.contains(substr1) && bruteForceText.contains(substr2) && bruteForceText.contains(substr3)
                    && bruteForceText.contains(substr4)) {
                        char[] arrayBruteForce = bruteForceText.toCharArray();
                        for (int j = 0; j < bruteForceText.length(); j++) {
                            //находим позицию в алфавите
                            int position = Decrypt.getPosition(Encrypt.ALPHABET, 0, Encrypt.ALPHABET.length-1, arrayBruteForce[j]);
                            //меняем исходный символ на символ сдвинутый на ключ
                            arrayBruteForce[j] = Decrypt.getSymbol(position, i);/////////////////////////////////
                        }

                        //переводим массив символов в строку
                        String resultText = new String(arrayBruteForce);
                        //ищем первое вхождение ", " в расшифрованном тексте
                        int indexComma = resultText.indexOf(", ");
                        //ищем первое вхождение ". " в расшифрованном тексте
                        int indexDot = resultText.indexOf(". ");
                        //ищем первое вхождение "! " в расшифрованном тексте
                        int indexQues = resultText.indexOf("! ");
                        //ищем первое вхождение " в " в расшифрованном тексте
                        int indexV = resultText.indexOf(" в ");
                        //ищем последнее вхождение ", " в расшифрованном тексте
                        int indexLastComma = resultText.lastIndexOf(", ");
                        //ищем последнее вхождение ". " в расшифрованном тексте
                        int indexLastDot = resultText.lastIndexOf(". ");
                        //ищем последнее вхождение "! " в расшифрованном тексте
                        int indexLastQues = resultText.lastIndexOf("! ");
                        //ищем последнее вхождение " в " в расшифрованном тексте
                        int indexLastV = resultText.lastIndexOf(" в ");

                        //если позиция первого и последнего вхождения зашифрованного сочетания ", " и ". " и "! " и " в " в исходном тексте =
                        // позиции первого и последнего вхождения в расшифрованном тексте ", " и ". " и "! " и " в "
                        if (indexDecryptComma==indexComma && indexDecryptDot==indexDot && indexDecryptQues==indexQues &&
                                indexDecryptV==indexV && indexLastDecryptComma==indexLastComma &&
                                indexLastDecryptDot==indexLastDot && indexLastDecryptQues==indexLastQues &&
                                indexLastDecryptV==indexLastV) {
                            //расшифровываем файл с подходящим ключом i
                            Decrypt.decryptFile(encryptionFile, bruteFile, i);
                            break;
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Decrypt.encFileExist = false;
            System.out.println("Зашифрованный файл по заданному адресу не существует");
        }
    }
}
