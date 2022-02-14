package com.github.guitsilva.rebels.view;

import com.github.guitsilva.rebels.model.Race;
import com.github.guitsilva.rebels.model.Rebel;
import com.github.guitsilva.rebels.view.utils.Prompt;

public class NewRebel {

  private final Prompt prompt;

  public NewRebel() {
    this.prompt = new Prompt();
  }

  public Rebel get() {
    Rebel.RebelBuilder rebelBuilder = Rebel.builder();

    String name = this.promptName();
    rebelBuilder.name(name);

    short age = this.promptAge();
    rebelBuilder.age(age);

    Race race = this.promptRace();
    rebelBuilder.race(race);

    return rebelBuilder.build();
  }

  private String promptName() {

    String name;

    while (true) {
      System.out.println();
      name = this.prompt.read("Name: ");

      if (name.contains(",")) {
        System.out.printf("%nName must not contain commas. Try again!%n");
        continue;
      }

      break;
    }

    return name;
  }

  private short promptAge() {

    short age;

    while (true) {
      System.out.println();
      String ageInput = this.prompt.read("Age: ");

      try {
        age = Short.parseShort(ageInput);
      } catch (NumberFormatException e) {
        System.out.printf("%nAge must be an integer number. Try again!%n");
        continue;
      }

      if (age < 0) {
        System.out.printf("%nAge must be positive. Try again!%n");
        continue;
      }

      break;
    }

    return age;
  }

  private Race promptRace() {

    Race race;

    while (true) {

      System.out.println();
      for (Race r : Race.values()) {
        System.out.printf("%d - %s%n", r.ordinal(), r.name());
      }

      String raceIndexString = this.prompt.read("Race: ");

      int raceIndex;

      try {
        raceIndex = Integer.parseInt(raceIndexString);
      } catch (NumberFormatException e) {
        System.out.printf("%nRace index must be an integer number. Try again!%n");
        continue;
      }

      if (0 <= raceIndex && raceIndex < Race.values().length) {
        race = Race.values()[raceIndex];
        break;
      } else {
        System.out.printf("%nRace index out of bounds. Try again!%n");
      }
    }

    return race;
  }
}
