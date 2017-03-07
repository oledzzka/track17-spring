package track.lessons.lesson1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Задание 1: Реализовать два метода
 *
 * Формат файла: текстовый, на каждой его строке есть (или/или)
 * - целое число (int)
 * - текстовая строка
 * - пустая строка (пробелы)
 *
 *
 * Пример файла - words.txt в корне проекта
 *
 * ******************************************************************************************
 *  Пожалуйста, не меняйте сигнатуры методов! (название, аргументы, возвращаемое значение)
 *
 *  Можно дописывать новый код - вспомогательные методы, конструкторы, поля
 *
 * ******************************************************************************************
 *
 */
public class CountWords {

    /**
     * Метод на вход принимает объект File, изначально сумма = 0
     * Нужно пройти по всем строкам файла, и если в строке стоит целое число,
     * то надо добавить это число к сумме
     * @param file - файл с данными
     * @return - целое число - сумма всех чисел из файла
     */
    public long countNumbers(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str;
        long sum = 0;
        while ((str = reader.readLine()) != null) {
            try {
                long num = Long.valueOf(str);
                sum += num;
            } catch (Exception e) {
                continue;
            }
        }
        reader.close();
        return sum;
    }


    /**
     * Метод на вход принимает объект File, изначально результат= ""
     * Нужно пройти по всем строкам файла, и если в строка не пустая и не число
     * то надо присоединить ее к результату через пробел
     * @param file - файл с данными
     * @return - результирующая строка
     */
    public String concatWords(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str;
        StringBuilder result = new StringBuilder();
        while ((str = reader.readLine()) != null) {
            if (str.trim().length() == 0) {
                continue;
            }
            try {
                Integer.parseInt(str);
            } catch (NumberFormatException e) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(str);
            }
        }
        if (result.length() == 0) {
            return null;
        }
        return result.toString();
    }

}
