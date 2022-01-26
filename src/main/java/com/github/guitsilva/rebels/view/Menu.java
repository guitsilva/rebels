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
    System.out.println("#  [g] Generate Report          #");
    System.out.println("#  [e] Exit                     #");
    System.out.println("#                               #");
    System.out.println("#################################");
    System.out.println();
  }

  public void print() {

    String option = "";

    do {

      Console.clear();

      this.printMenu();

      option = this.prompt.read();

      switch (option.toLowerCase()) {
        case "n":
          this.newRebel();
          break;

        case "g":
          this.generateReport();
          break;

        case "e":
          continue;

        default:
          System.out.println("Invalid option. Try again!");
      }

    } while (!option.equalsIgnoreCase("e"));
  }

  private void newRebel() {
    Rebel rebel = new NewRebel().get();

    boolean rebelAccepted = this.intelligence.evaluateRebelApplication(rebel);

    if (rebelAccepted) {
      this.emitWarning(String.format("%s is a rebel now!", rebel.getName()));
    } else {
      this.emitWarning(String.format("%s was refused.", rebel.getName()));
    }
  }

  private void generateReport() {
    String reportFileName = "rebels.csv";

    try {
      this.intelligence.generateReport(reportFileName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    this.emitWarning(String.format("%s generated!", reportFileName));
  }

  private void emitWarning(String warning) {
    if (warning == null || warning.isBlank()) {
      return;
    }

    this.menuWarning = warning;
  }
}
