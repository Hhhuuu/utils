package ru.mamapapa.utils;

import java.io.*;

/**
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class ReaderUtils {
    public static final String UTF_8 = "UTF8";

    private ReaderUtils() {
        throw new UnsupportedOperationException("Вызов конструктора запрещен!");
    }

    public static InputStreamReader getReader(InputStream inputStream) {
        try {
            return new InputStreamReader(inputStream, UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Неизвестная кодировка", e);
        }
    }
}
