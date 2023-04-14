package bank;

import bank.exceptions.AmountExpection;

public class Accounts {
  int id;
  String type;
  double balance;

  public Accounts(int id, String type, double balance) {
    this.id = id;
    this.type = type;
    this.balance = balance;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(Double amount) throws AmountExpection {
    if (amount < 1) {
      throw new AmountExpection("The minimum deposit is 1.00");
    } else {
      double newBalance = balance + amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }

  }

  public void withdraw(Double amount) throws AmountExpection {
    if (amount < 0) {
      throw new AmountExpection("The minimum withdrow must be greater than 0.00");
    } else if (amount > getBalance()) {
      throw new AmountExpection("You do not have sufficient funds for this withdrawal.");
    } else {
      double newBalance = balance - amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }

  }

}
