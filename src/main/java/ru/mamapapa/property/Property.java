package ru.mamapapa.property;

/**
 * Интерфейс получения настроек
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public interface Property<T extends Key> {
    void load() throws Exception;

    void load(String fileName) throws Exception;

    void load(String fileName, String folder) throws Exception;

    String getString(T key);

    String getString(T key, String defaultValue);

    Long getLong(T key);

    Long getLong(T key, Long defaultValue);

    Double getDouble(T key);

    Double getDouble(T key, Double defaultValue);

    Boolean getBoolean(T key);

    Boolean getBoolean(T key, boolean defaultValue);
}
