import javax.swing.*; // импортируем весь свинг, дабы не заморачиваться(мы ведь только учимся))
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*; // и весь авт аналогично
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Appearance { // задаст внешний вид кнопок нашего окна.
    private JLabel encLabel;//надпись об успешном шифровании
    private JLabel decLabel;//надпись об успешном расшифровании
    private JLabel brLabel;//надпись об успешном расшифровании методом brute force
    private JTextField keyField;//поле ввода ключа
    private String key;//вводимый пользователем ключ

    public JPanel createContentPane (){

        //Создаём панель, а всё остальное размещаем уже на этой панели
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);

        //Добавим текст
        JLabel infLabel = new JLabel("<html> <br>" +
                "Выберите режим программы <br><br>" +
                "При выборе \"Шифровка\" введите Ключ для шифровки <br>" +
                "<br></html>");
        infLabel.setLocation(10, -50); //координаты текста
        infLabel.setSize(400, 200); //размер области с текстом
        infLabel.setHorizontalAlignment(0);
        infLabel.setForeground(Color.darkGray);
        totalGUI.add(infLabel);

        //Создаём кнопку Шифровка
        JButton encodeButton = new JButton("Шифровка");
        encodeButton.setLocation(70, 100); // это координаты кнопки
        encodeButton.setSize(120,30 ); // это размер кнопки
        //обработка нажатия кнопки
        ActionListener actionListener = new EncActionListener();
        encodeButton.addActionListener(actionListener);
        totalGUI.add(encodeButton);

        //Добавим текст Ключ
        JLabel keyLabel = new JLabel("Ключ");
        keyLabel.setLocation(200, 100); //координаты текста
        keyLabel.setSize(50, 30); //размер области с текстом
        keyLabel.setHorizontalAlignment(0);
        keyLabel.setForeground(Color.darkGray);
        totalGUI.add(keyLabel);

        //Добавим поле ввода ключа
        keyField = new JTextField();
        PlainDocument doc = (PlainDocument) keyField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        keyField.setLocation(250, 100); //координаты поля ввода
        keyField.setSize(70, 30); //размер области с полем ввода
        keyField.setHorizontalAlignment(0);
        keyField.setForeground(Color.darkGray);
        totalGUI.add(keyField);

        //Создаём кнопку Расшифровка
        JButton decodeButton = new JButton("Расшифровка");
        decodeButton.setLocation(120, 150); // это координаты кнопки
        decodeButton.setSize(120,30 ); // это размер кнопки
        //обработка нажатия кнопки
        ActionListener actionListenerForDecode = new DecActionListener();
        decodeButton.addActionListener(actionListenerForDecode);
        totalGUI.add(decodeButton);

        //Создаём кнопку Brute Force
        JButton bruteButton = new JButton("Brute Force");
        bruteButton.setLocation(120, 200); // это координаты кнопки
        bruteButton.setSize(120,30 ); // это размер кнопки
        //обработка нажатия кнопки
        ActionListener actionListenerForBrute = new BruteActionListener();
        bruteButton.addActionListener(actionListenerForBrute);
        totalGUI.add(bruteButton);

        //Добавим текст об успешном шифровании
        encLabel = new JLabel();
        encLabel.setLocation(5, 250); //координаты текста
        encLabel.setSize(400, 30); //размер области с текстом
        encLabel.setHorizontalAlignment(0);
        encLabel.setForeground(Color.darkGray);
        totalGUI.add(encLabel);

        //Добавим текст об успешном расшифровании
        decLabel = new JLabel();
        decLabel.setLocation(25, 280); //координаты текста
        decLabel.setSize(300, 30); //размер области с текстом
        decLabel.setHorizontalAlignment(0);
        decLabel.setForeground(Color.darkGray);
        totalGUI.add(decLabel);

        //Добавим текст об успешном расшифровании методом Brute Force
        brLabel = new JLabel();
        brLabel.setLocation(25, 310); //координаты текста
        brLabel.setSize(300, 30); //размер области с текстом
        brLabel.setHorizontalAlignment(0);
        brLabel.setForeground(Color.darkGray);
        totalGUI.add(brLabel);

        totalGUI.setOpaque(true);
        return totalGUI;
    }

    //нажатие кнопки Шифрование
    public class EncActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //берем ключ из поля ввода
            key = keyField.getText();
            if (!key.isEmpty()) {
                //значение ключа вводится в поле ввода как строка, поэтому переводим в int
                int totalKey = Integer.parseInt(key);
                Solution.getNumber(1, totalKey);

                //проверка на наличие файла
                if (Encrypt.origFileExist) {
                    encLabel.setText("Файл успешно зашифрован c ключом " + key);
                } else {encLabel.setText("Файл оригинала по заданному адресу не существует");}
            } else {
                encLabel.setText("Для шифрования введите ключ и нажмите Шифрование");
            }
        }
    }

    //нажатие кнопки Расшифрование
    public class DecActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //можно расшифровать только если есть зашифрованный файл
            if (key!=null && key!="") {
                //значение ключа вводится в поле ввода как строка, поэтому переводим в int
                int totalKey = Integer.parseInt(key);
                Solution.getNumber(2, totalKey);

                //проверка на наличие файла
                if (Decrypt.encFileExist) {
                    decLabel.setText("Файл успешно расшифрован c ключом " + key);
                } else {decLabel.setText("Зашифрованный файл по заданному адресу не существует");}
            } else {
                decLabel.setText("Сначала зашифруйте файл");
            }
        }
    }

    //нажатие кнопки Расшифрование методом Brute Force
    public class BruteActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //можно расшифровать только если есть зашифрованный файл
            if (key!=null && key!="") {
                Solution.getNumber(3);

                //проверка на наличие файла
                if (Decrypt.encFileExist) {
                    brLabel.setText("Файл успешно расшифрован методом Brute Force");
                } else {decLabel.setText("Зашифрованный файл по заданному адресу не существует");}
            } else {
                brLabel.setText("Сначала зашифруйте файл");
            }
        }
    }




    //в поле ввода ключа можно вводить только числа
    class DigitFilter extends DocumentFilter {
        private static final String DIGITS = "\\d+";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

            if (string.matches(DIGITS)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
            if (string.matches(DIGITS)) {
                super.replace(fb, offset, length, string, attrs);
            }
        }
    }
}
