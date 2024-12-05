package interfaces;

import java.util.List;

public interface Extractor<T> {
    List<T> extractAll(String text);
}
