package com.github.guitsilva.rebels.view;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.github.guitsilva.rebels.controller.CentralIntelligence;
import com.github.guitsilva.rebels.model.Rebel;
import com.github.guitsilva.rebels.view.utils.Console;
import com.github.guitsilva.rebels.view.utils.Prompt;

public class Menu {

  private final Prompt prompt;
  private final CentralIntelligence intelligence;

  private String menuWarning;

  public Menu() {
    this.prompt = new Prompt();
    this.intelligence = new CentralIntelligence();
    this.menuWarning = "";
  }

  private void printMenu() {
    System.out.println();
    System.out.println("#################################");
    System.out.println("#      REBELS REGISTRATION      #");
    System.out.println("#################################");
    System.out.println();
    System.out.printf("   Number of Rebels: %02d%n",
        this.intelligence.getNumberOfRebels());

    if (!this.menuWarning.isBlank()) {
      System.out.println(" ----------------------------- ");
      System.out.printf("   %s%n", this.menuWarning);
    }

    System.out.println();
    System.out.println("#################################");
    System.out.println("#                               #");
    System.out.println("#  Choose an option:            #");
    System.out.println("#                               #");
    System.out.println("#  [n] New Rebel                #");
    System.out.println("#  [s] Save Rebels              #");
    System.out.println("#  [e] Exit                     #");
    System.out.println("#                               #");
    System.out.println("#################################");
    System.out.println();
  }

  public void print() {

    String option;

    do {

      Console.clear();

      this.printMenu();

      option = this.prompt.read();

      switch (option.toLowerCase()) {
        case "n":
          this.newRebel();
          break;

        case "s":
          this.saveRebels();
          break;

        case "e":
          continue;

        default:
          this.emitWarning("Invalid option. Try again!");
      }

    } while (!option.equalsIgnoreCase("e"));
  }

  private void newRebel() {
    Rebel rebel = new NewRebel().get();

    boolean rebelAccepted = this.intelligence.evaluateRebel(rebel);

    if (rebelAccepted) {
      this.emitWarning(String.format("%s is a rebel now!", rebel.getName()));
    } else {
      this.emitWarning(String.format("%s was refused.", rebel.getName()));
    }
  }

  private void saveRebels() {
    String reportFileName = "rebels.csv";
    String reportContent = "";

    System.out.println();
    System.out.println("Choose an order for the report:");
    System.out.println("[n] - name");
    System.out.println("[a] - age");
    System.out.println("[r] - race");

    boolean invalidOrderOption;

    do {

      String option = this.prompt.read();

      switch (option.toLowerCase()) {
        case "n":
          invalidOrderOption = false;
          this.intelligence.sortRebels((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
          break;

        case "a":
          invalidOrderOption = false;
          this.intelligence.sortRebels((a, b) -> a.getAge() - b.getAge());
          break;

        case "r":
          invalidOrderOption = false;
          this.intelligence.sortRebels((a, b) -> a.getRace().compareTo(b.getRace()));
          break;

        default:
          invalidOrderOption = true;
          System.out.println("Invalid option. Try again!");
      }

    } while (invalidOrderOption);

    reportContent = this.intelligence.getRebelsReport();

    System.out.printf("%nRebels report:%n");
    System.out.print(String.format("%n%s%n", reportContent));

    boolean invalidSaveOption;

    do {

      String option = this.prompt.read(
          String.format("Save to file (%s) [y/n]? ", reportFileName));

      switch (option.toLowerCase()) {
        case "y":
          invalidSaveOption = false;
          try {
            this.intelligence.persistRebelsReport(reportFileName);
          } catch (FileNotFoundException | UnsupportedEncodingException e) {
            this.emitWarning("Unable to generate report.");
            break;
          }

          this.emitWarning(String.format("%s generated!", reportFileName));
          break;

        case "n":
          invalidSaveOption = false;
          break;

        default:
          invalidSaveOption = true;
          System.out.println("Invalid option. Try again!");
      }

    } while (invalidSaveOption);
  }

  private void emitWarning(String warning) {
    if (warning == null || warning.isBlank()) {
      return;
    }

    this.menuWarning = warning;
  }
}
