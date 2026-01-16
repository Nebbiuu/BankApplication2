package bankAccountApp;

import junit.framework.TestCase;
import com.imt.mines.BankAccount;
import com.imt.mines.Person;

/**
 * Unit tests for BankAccount
 */
public class BankAccountTest extends TestCase {

    public void testDepositIncreasesBalance() {
        BankAccount acc = new BankAccount();
        // initial balance 0
        acc.depositMoney(100.0);
        assertEquals(100.0, acc.getBalance());
        // deposit zero should not change
        acc.depositMoney(0.0);
        assertEquals(100.0, acc.getBalance());
    }

    public void testWithdrawEdgeCasesAndSuccess() {
        BankAccount acc = new BankAccount();
        // set a withdraw limit so withdrawals can be evaluated
        acc.setWithdrawLimit(500.0);

        // negative withdraw should fail and not change balance
        boolean res = acc.withdrawMoney(-50.0);
        assertFalse(res);
        assertEquals(0.0, acc.getBalance());

        // withdraw more than balance should fail
        acc.depositMoney(50.0);
        res = acc.withdrawMoney(100.0);
        assertFalse(res);
        assertEquals(50.0, acc.getBalance());

        // successful withdrawal
        acc.depositMoney(200.0); // balance now 250
        res = acc.withdrawMoney(100.0);
        assertTrue(res);
        assertEquals(150.0, acc.getBalance());
    }

    public void testConvertToTextAndSetAccountHolder() {
        // create a person and an account with that person
        Person p = new Person("Alice", 'F', 25, 165f);
        BankAccount acc = new BankAccount(100.0, 500.0, "2026-01-16", p);

        // convert to text should include the balance and withdraw limit
        String txt = acc.convertToText(acc);
        assertTrue(txt.contains(String.valueOf(acc.getBalance())));
        assertTrue(txt.contains(String.valueOf(acc.getWithdrawLimit())));

        // setting account holder to null should not overwrite existing holder
        acc.setAccountHolder(null);
        assertEquals(p, acc.getAccountHolder());
    }

    public void testToStringContainsHolderAndBalance() {
        Person p = new Person("Bob", 'M', 30, 180f);
        BankAccount acc = new BankAccount();
        acc.setAccountHolder(p);
        acc.depositMoney(250.0);
        String s = acc.toString();
        assertTrue(s.contains("Your Balance") || s.contains(String.valueOf(acc.getBalance())));
        assertTrue(s.contains(p.getName()));
    }
}
