import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonWriter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("news.json");

    public void write(List<News> newsList) {
        try {
            Map<String, String> existingNews = new HashMap<>();

            // 파일이 존재하면 기존 데이터를 읽어오기
            if (file.exists()) {
                existingNews = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {
                });
            }

            // 새로운 뉴스 추가
            for (News news : newsList) {
                existingNews.put(news.newsLink(), news.newsText());
            }

            // 업데이트된 리스트를 파일에 쓰기
            objectMapper.writeValue(file, existingNews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
