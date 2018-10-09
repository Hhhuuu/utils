package ru.mamapapa.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.mamapapa.utils.PathUtils.addSlashToEnd;

/**
 * Вывод строк в файл
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class FileOutput implements Output {

    private final String fileName;
    private String path;

    public FileOutput(String fileName) {
        this.fileName = fileName;
        this.path = "";
    }

    public FileOutput withPath(String path) {
        this.path = addSlashToEnd(path);
        return this;
    }

    public String getUrl() {
        return path + fileName;
    }

    public void out(String text) {
        String fullFileName = getUrl();
        Path localPath = Paths.get(fullFileName);
        if (!localPath.toFile().exists()) {
            try {
                Files.createFile(localPath);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(fullFileName)) {
            byte[] buffer = text.getBytes();
            outputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
