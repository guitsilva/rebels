package com.github.guitsilva.rebels.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.guitsilva.rebels.model.Rebel;

import lombok.Cleanup;

public class CentralIntelligence {

  private final List<Rebel> rebels;

  public CentralIntelligence() {
    this.rebels = new LinkedList<Rebel>();
  }

  public int getNumberOfRebels() {
    return this.rebels.size();
  }

  public boolean evaluateRebelApplication(Rebel rebel) {

    int randomNumber = new Random().nextInt(10);

    if (randomNumber >= 7) {
      return false;
    }

    return this.addRebel(rebel);
  }

  public void generateReport(String fileName)
      throws FileNotFoundException, UnsupportedEncodingException {

    @Cleanup
    PrintWriter writer = new PrintWriter(fileName, "UTF-8");

    writer.println("name, age, race");

    System.out.println();
    for (Rebel rebel : this.rebels) {
      writer.printf("%s, %s, %s%n",
          rebel.getName(),
          rebel.getAge(),
          rebel.getRace().toString().toLowerCase());
    }
  }

  private boolean addRebel(Rebel rebel) {

    if (rebel == null) {
      return false;
    }

    return this.rebels.add(rebel);
  }
}
