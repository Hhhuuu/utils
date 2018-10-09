package ru.mamapapa.output;

/**
 * Вывод строк в консоль
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class ConsoleOutput implements Output {
    @Override
    public void out(String text) {
        System.out.println(text);
    }
}
