package interfaces;

import java.io.IOException;

public interface FileReader {
    String readAllText(String filePath) throws IOException;
}
