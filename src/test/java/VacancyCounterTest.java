import static org.testng.Assert.assertEquals;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

import java.util.List;

public class VacancyCounterTest {

    @Test
    public static void main(String[] args) throws InterruptedException {

        String department = "Research & Development";
        String language = "English";
        int vacancies = 14;

        WebDriverManager.chromedriver().setup();

        ChromeDriver driver = new ChromeDriver();
        driver.get("https://cz.careers.veeam.com/vacancies");
        driver.manage().window().maximize();


        // Cookies are kinda in the way of clicking the dropdown menu.
        List<WebElement> accept_cookies = driver.findElements(
                By.xpath("//*[@id=\"cookiescript_accept\"]"));
        if (! accept_cookies.isEmpty())
            accept_cookies.get(0).click();

        driver.findElement(By.xpath("//button[@id='sl']")).click();

        List<WebElement> elements = driver.findElements(
                By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div/*"));

        WebElement departmentElement = null;
        for (WebElement element : elements) {
            if (department.equals(element.getText())) {
                departmentElement = element;
            }
        }

        assertNotNull(departmentElement);
        departmentElement.click();

        Thread.sleep(2 * 1000);

        driver.get(driver.getCurrentUrl() + language);

//        int vacancies = Integer.parseInt(driver.findElement(
//                By.xpath("//*[@id=\"root\"]/div/div[1]/div/h3/span")).getText());

        List<WebElement> listOfVacancies = driver.findElements(
                By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/div[2]/div/*"));

        System.out.println(listOfVacancies.size());
        assertEquals(vacancies, listOfVacancies.size());

        driver.wait(3 * 1000);

        driver.close();
    }

}
