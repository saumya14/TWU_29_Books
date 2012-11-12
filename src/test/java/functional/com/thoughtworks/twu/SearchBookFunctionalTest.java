package functional.com.thoughtworks.twu;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.*;

public class SearchBookFunctionalTest {
    WebDriver webDriver;

    @Before
    public void setUp() {
        webDriver = new HtmlUnitDriver();
        CommonSteps.login(webDriver,"test.twu","Th0ughtW0rks@12");
    }

    @Test
    public void shouldDisplaySearchBox() {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        assertThat(webDriver.findElement(By.name("searchValue")).isDisplayed(), is(true));
    }

    @Test
    public void shouldDisplayDropBox(){
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        assertThat(webDriver.findElement(By.name("searchType")).isDisplayed(), is(true));
    }

    @Test
    public void shouldDisplaySearchButton() {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        assertThat(webDriver.findElement(By.id("search")).isDisplayed(), is(true));
    }

    @Ignore
    public void shouldDisplayBookDivInfoWhenGoButtonIsClicked() {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("9780316228534");
        webDriver.findElement(By.id("searchByISBN")).click();
        webDriver.findElement(By.id("search")).submit();
        assertThat(webDriver.findElement(By.className("book-list")).isDisplayed(), is(true));
    }

    @Test
    public void shouldNotDisplayByWhenBookDoesntHaveAuthor()
    {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("Boys' Life");
        webDriver.findElement(By.id("searchByTitle")).click();
        webDriver.findElement(By.id("search")).submit();
        assertThat(webDriver.findElement(By.className("book-author"))
                .getText(), is(""));
    }

    @Test
    public void shouldDisplayBookAuthorAndTitleWhenGoButtonIsClicked() {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("9780316228534");
        webDriver.findElement(By.id("searchByISBN")).click();
        webDriver.findElement(By.id("search")).submit();
        assertThat(webDriver.findElement(By.className("book-picture"))
                .isDisplayed
                (), is(true));
        assertThat(webDriver.findElement(By.className("book-title")).isDisplayed(), is(true));
        assertThat(webDriver.findElement(By.className("book-author")).isDisplayed(), is(true));
    }

    @Test
    public void shouldDisplayErrorMessageWhenBookIsNotFound() {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("fasdfafasd");
        webDriver.findElement(By.id("searchByTitle")).click();
        webDriver.findElement(By.id("search")).submit();
        assertThat(webDriver.findElement(By.id("error")).getText(), is("No books were found matching your search " +
                "criteria. Please try again with a new search criteria."));
    }

    @Test
    public void shouldRememberThePreviousSearchType() {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("fasdfafasd");
        webDriver.findElement(By.id("searchByISBN")).click();
        webDriver.findElement(By.id("search")).submit();

        final WebElement searchByISBN = webDriver.findElement(By.id("searchByISBN"));
        assertTrue(searchByISBN.isSelected());

    }

    @Test
    public void shouldStayOnSamePageOnRefresh() throws Exception {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.navigate().refresh();
        assertEquals("Search Results", webDriver.getTitle());

    }

    @Test
    public void shouldDisplayAMaximumOf20Results() throws Exception {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("Harry Potter");
        webDriver.findElement(By.id("searchByTitle")).click();
        webDriver.findElement(By.id("search")).submit();

        List<WebElement> list = webDriver.findElements(By.className("book"));
        assertThat(list.size(), is(lessThanOrEqualTo(20)));
    }

    @Test
    public void shouldDisplayErrorMessageWhenNoValueInputted() throws
            Exception {
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue"));
        webDriver.findElement(By.id("searchByTitle")).click();
        webDriver.findElement(By.id("search")).submit();
        assertThat(webDriver.findElement(By.id("error")).getText(),
                is("Please input a value for your search, and try again."));


    }

    @Test
    public void shouldSearchByAuthor(){
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("Paulo Coelho");
        webDriver.findElement(By.id("searchByAuthor")).click();
        webDriver.findElement(By.id("search")).submit();
        assertThat(webDriver.findElement(By.className("book-list")).isDisplayed(), is(true));
    }

    @Test
    public void shouldDisplayTheSortOrderOfResults(){
        webDriver.get("http://127.0.0.1:8080/twu/search_book");
        webDriver.findElement(By.name("searchValue")).sendKeys("Agile Samurai");
        webDriver.findElement(By.id("searchByTitle")).click();
        webDriver.findElement(By.id("search")).submit();
        assertEquals("Your search was sorted by relevance.", webDriver.findElement(By.tagName("p")).getText());

    }

    @After
    public void tearDown() {
        webDriver.close();
    }


}
