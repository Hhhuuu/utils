package ru.mamapapa.property;

/**
 * Функиональный интерфейс для конвертации
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
@FunctionalInterface
public interface Converter<T> {
    /** Конвертирование данных */
    T convert();
}
