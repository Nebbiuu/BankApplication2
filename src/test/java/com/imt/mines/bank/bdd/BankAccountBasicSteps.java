package test.java.com.imt.mines.bank.bdd;
import static org.junit.Assert.assertEquals;
import com.imt.mines.BankAccount;   // adjust import if your BankAccount is in another package
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class BankAccountBasicSteps {
    private BankAccount account;
    private int observedBalance;

    @Given("I have a new bank account")
    public void i_have_a_new_bank_account() {
        account = new BankAccount();
    }

    @When("I check its balance")
    public void i_check_its_balance() {
        // TODO: read the balance from the account and store it in observedBalance
        observedBalance = (int) account.getBalance();
    }

    @Then("the balance should be {int}")
    public void the_balance_should_be(Integer expected) {
        // TODO: assert that observedBalance equals expected
        // Example: assertEquals(expected.intValue(), observedBalance);
        assertEquals(expected.intValue(), observedBalance);
    }
}