package test.java.com.imt.mines.bank.bdd;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "test.java.com.imt.mines.bank.bdd", // PAQUET EXACT où se trouve BankAccountBasicSteps
    plugin = { "pretty" }
)
    
public class RunBankAccountCucumberTest {
    // Empty on purpose. JUnit + Cucumber will use the annotations above.
}
