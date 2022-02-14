package com.github.guitsilva.rebels.view.utils;

import java.util.Scanner;

public class Prompt {

  private final Scanner scanner;

  public Prompt() {
    this.scanner = new Scanner(System.in);
  }

  public String read() {
    return this.read("#: ", true);
  }

  public String read(String prompString) {
    return this.read(prompString, true);
  }

  public String read(String promptString, boolean validateInput) {
    String input;

    while (true) {
      System.out.print(promptString);
      input = this.scanner.nextLine();

      if (!validateInput) {
        break;
      }

      if (this.isValidInput(input)) {
        break;
      }
    }

    return input;
  }

  private boolean isValidInput(String input) {

    if (input == null || input.isBlank()) {
      return false;
    }

    return true;
  }
}
