package ru.mamapapa.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mamapapa.input.FileInput;
import ru.mamapapa.input.Input;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static ru.mamapapa.utils.ReaderUtils.getReader;

/**
 * Сервис настроек
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class PropertyService implements Property {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);
    private static final String FORMAT_MESSAGE = "Установлен параметр по умолчанию: {}, значение: {}";
    private static final String FAILED_TO_CONVERT_DATA = "Не удалось преобразовать данные";
    private static final String DEFAULT_PROPERTY_FILE_NAME = "application.properties";
    private static final String CURRENT_FOLDER = "./";

    private Properties properties;

    /**
     * Загрузка файла пропертей из ресурсов
     * По умолчанию берется файл @see DEFAULT_PROPERTY_FILE_NAME и текущая директория
     *
     * @throws Exception - возникает при неправильной загрузке файлов
     */
    @Override
    public void load() throws Exception {
        load(DEFAULT_PROPERTY_FILE_NAME, CURRENT_FOLDER);
    }

    /**
     * Загрузка файла пропертей из ресурсов
     * По умолчанию берется текущая директория
     *
     * @param fileName - имя файла настроек
     * @throws Exception - возникает при неправильной загрузке файлов
     */
    @Override
    public void load(String fileName) throws Exception {
        load(fileName, CURRENT_FOLDER);
    }

    /**
     * Загрузка файла пропертей из ресурсов
     *
     * @param fileName - имя файла настроек
     * @param folder   - директория с файлом настроек
     * @throws Exception - возникает при неправильной загрузке файлов
     */
    @Override
    public void load(String fileName, String folder) throws Exception {
        LOGGER.info("Инициализация файла настроек");
        Input input = new FileInput(fileName).withPath(folder);
        loadProperty(input.getInputStream());
    }

    private void loadProperty(InputStream inputStream) throws IOException {
        properties = new Properties();
        properties.load(getReader(inputStream));
    }

    @Override
    public String getString(Key key) {
        return getProperty(key);
    }

    @Override
    public String getString(Key key, String defaultValue) {
        try {
            return getString(key);
        } catch (Exception ignore) {
            LOGGER.debug(FORMAT_MESSAGE, key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public Long getLong(Key key) {
        String property = getProperty(key);
        return convertData(() -> Long.valueOf(property));
    }

    @Override
    public Long getLong(Key key, Long defaultValue) {
        try {
            return getLong(key);
        } catch (Exception ignore) {
            LOGGER.debug(FORMAT_MESSAGE, key, defaultValue);
        }
        return defaultValue;

    }

    @Override
    public Double getDouble(Key key) {
        String property = getProperty(key);
        return convertData(() -> Double.valueOf(property));
    }

    @Override
    public Double getDouble(Key key, Double defaultValue) {
        try {
            return getDouble(key);
        } catch (Exception ignore) {
            LOGGER.debug(FORMAT_MESSAGE, key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public Boolean getBoolean(Key key) {
        String property = getProperty(key);
        return convertData(() -> {
            if ("true".compareToIgnoreCase(property) == 0) {
                return TRUE;
            } else if ("false".compareToIgnoreCase(property) == 0) {
                return FALSE;
            } else {
                throw new IllegalArgumentException(FAILED_TO_CONVERT_DATA);
            }
        });
    }

    @Override
    public Boolean getBoolean(Key key, boolean defaultValue) {
        try {
            return getBoolean(key);
        } catch (Exception ignore) {
            LOGGER.debug(FORMAT_MESSAGE, key, defaultValue);
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    private <T> T convertData(Converter converter) {
        try {
            return (T) converter.convert();
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_TO_CONVERT_DATA);
        }
    }

    private String getProperty(Key key) {
        String keyValue = key.getValue();
        String value = properties.getProperty(keyValue);
        if (value == null) {
            throw new IllegalArgumentException(String.format("Не найдена настройка по ключу '%s'", keyValue));
        }
        LOGGER.debug("Получен параметр: {}, значение: {}", key, keyValue);
        return value;
    }
}
