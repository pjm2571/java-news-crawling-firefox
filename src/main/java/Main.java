import java.util.List;

public class Main {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();

        List<News> newsList = crawler.crawlingNews();

        JsonWriter jsonWriter = new JsonWriter();

        jsonWriter.write(newsList);
    }
}
