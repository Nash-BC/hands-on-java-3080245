package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountExpection;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank International !");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Accounts account = DataSource.getAccounts(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your passowrd");
    String password = scanner.next();

    Customer customer = null;

    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error : " + e.getMessage());
    }

    return customer;
  }

  private void showMenu(Customer customer, Accounts account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("========================================================");
      System.out.println("Please select one of the following options");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: Check Balance");
      System.out.println("4: Exit");
      System.out.println("========================================================");

      selection = scanner.nextInt();
      double amount = 0;
      switch (selection) {
        case 1:
          System.out.println("How much would you like to deposit ?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountExpection e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 2:
          System.out.println("How much would you like to withdraw ?");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountExpection e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again");
          }
          break;
        case 3:
          System.out.println("Your account balance is : $" + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          if (customer.isAuthenticated() == false) {
            System.out.println("Thank for your visite, see you soon !");
          }
          break;

        default:
          System.out.println("Please enter a correct value");
          break;

      }
    }

  }

}