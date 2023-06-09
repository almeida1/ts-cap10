package com.fatec.scel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//@SpringBootTest
public class REQ03MantemAlunoTests {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;
	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
		driver = new ChromeDriver(chromeOptions);
		// driver = new ChromeDriver();
		driver.get("https://scel-web.herokuapp.com");
		// driver.get("http://localhost:8080");
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}
	@AfterEach
	public void tearDown() {
		driver.quit();
	}
	@Test
	public void ct01_cadastrar_aluno_com_sucesso() {
		// ******************************************************************
		// dado que o usuario esta autenticado e autorizado
		// ******************************************************************
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Alunos")).click();
		espera();
		// ******************************************************************
		// quando o usuario cadastra um aluno que não está cadastrado
		// ******************************************************************
		driver.findElement(By.id("ra")).click();
		driver.findElement(By.id("ra")).sendKeys("1111");
		driver.findElement(By.id("nome")).click();
		driver.findElement(By.id("nome")).sendKeys("jose");
		driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
		driver.findElement(By.id("cep")).sendKeys("04280130");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		espera();
		// ******************************************************************
		// entao o sistema apresenta as informações do aluno para consulta
		// ******************************************************************
		assertTrue(driver.getPageSource().contains("Rua Frei João"));
		assertEquals(("Lista de alunos"),driver.findElement(By.id("titulopagina")).getText());
		assertTrue(driver.getPageSource().contains("1111"));
		// *********************************************************************************
		// teardown - exclusao do registro
		// *********************************************************************************
		//driver.findElement(By.linkText("Excluir")).click();
	}
	public void espera() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}