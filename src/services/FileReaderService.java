package services;

import interfaces.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderService implements FileReader {
    @Override
    public String readAllText(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException(filePath);
        }

        return Files.readString(path);
    }
}
