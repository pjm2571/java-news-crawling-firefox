import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class Crawler {
    private static final String NAVER_SOCIETY_NEWS_URL = "https://news.naver.com/section/102";
    private static final String NAVER_LIFE_NEWS_URL = "https://news.naver.com/section/103";
    private static final String NAVER_IT_NEWS_URL = "https://news.naver.com/section/105";

    private final WebDriver webDriver;
    private final List<News> newsList = new ArrayList<>();

    public Crawler(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");  // headless 모드로 설정

        this.webDriver = new FirefoxDriver(options);  // 옵션을 사용하여 WebDriver 인스턴스 생성
    }

    public List<News> crawlingNews() {
        openBrowser(NAVER_IT_NEWS_URL);

        getHeadlineNews();

        closeBrowser();

        return newsList;
    }

    private void openBrowser(String Url) {
        // 매개변수로 전달된 경로에 대해 접속 & 크롬 창을 연다
        webDriver.get(Url);
    }

    private void closeBrowser() {
        // 크롬 드라이버 종료
        webDriver.quit();
    }

    private void getHeadlineNews() {
        // 크롬창이 열릴 때, 충분한 넓이로 열리지 않아 더보기 값이 가져와지지 않을때를 대비하여 '더보기' 버튼 클릭
        clickMoreInnerBtn();

        // 1) class 이름이 'sa_list'인 ul 태그 선택
        WebElement ulList = webDriver.findElement(By.className("sa_list"));

        // 2) sa_list 안의 모든 li 태그 선택
        List<WebElement> liItems = ulList.findElements(By.className("sa_item"));

        // 3) 선택된 모든 li 태그를 순회
        for (WebElement element : liItems) {
            // 4) sa_text 라는 이름의 className 첫번째 요소만을 선택 (findElement)
            WebElement saTextElement = element.findElement(By.className("sa_text"));

            // 5) 뉴스 링크 추출
            String newsUrl = getNewsUrl(saTextElement);

            // 6) 뉴스 헤드라인 텍스트 추출
            String newsText = getNewsText(saTextElement);

            // 뉴스 객체 생성
            News news = new News(newsUrl, newsText);

            // 뉴스 리스트에 추가
            newsList.add(news);
        }
    }

    private String getNewsUrl(WebElement saTextElement) {
        // 선택된 li 태그의 'sa_text_title'인 div 태그 선택
        WebElement newsLinkElement = saTextElement.findElement(By.className("sa_text_title"));

        // div 태그안의 a 태그 href 경로를 추출
        return newsLinkElement.getAttribute("href");
    }

    private String getNewsText(WebElement saTextElement) {
        // 선택된 li 태그의 'sa_text_strong'인 div 태그 선택
        WebElement newsTextElement = saTextElement.findElement(By.className("sa_text_strong"));

        // div 태그 안의 text 값을 추출
        return newsTextElement.getText();
    }

    private void clickMoreInnerBtn() {
        // '뉴스 더보기' 버튼 요소 선택
        WebElement moreInnerButton = webDriver.findElement(By.className("section_more_inner"));

        // '뉴스 더보기' 버튼 클릭
        moreInnerButton.click();
    }
}
