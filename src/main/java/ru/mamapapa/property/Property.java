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

    String getString(Key key);

    String getString(Key key, String defaultValue);

    Long getLong(Key key);

    Long getLong(Key key, Long defaultValue);

    Double getDouble(Key key);

    Double getDouble(Key key, Double defaultValue);

    Boolean getBoolean(Key key);

    Boolean getBoolean(Key key, boolean defaultValue);
}
