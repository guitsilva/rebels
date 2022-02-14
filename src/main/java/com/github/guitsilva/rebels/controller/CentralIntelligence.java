package com.github.guitsilva.rebels.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.guitsilva.rebels.model.Rebel;

import lombok.Cleanup;

public class CentralIntelligence {

  private List<Rebel> rebels;

  public CentralIntelligence() {
    this.rebels = new LinkedList<Rebel>();
  }

  public int getNumberOfRebels() {
    return this.rebels.size();
  }

  public boolean evaluateRebel(Rebel rebel) {

    if (this.rebels.contains(rebel)) {
      return false;
    }

    int randomNumber = new Random().nextInt(10);

    if (randomNumber >= 7) {
      return false;
    }

    return this.addRebel(rebel);
  }

  public void persistRebelsReport(String fileName)
      throws FileNotFoundException, UnsupportedEncodingException {

    @Cleanup
    PrintWriter writer = new PrintWriter(fileName, "UTF-8");

    writer.print(this.getRebelsReport());
  }

  public void sortRebels(Comparator<Rebel> comparator) {
    this.rebels.sort(comparator);
  }

  public String getRebelsReport() {

    StringBuilder report = new StringBuilder();

    report.append(String.format("name, age, race%n"));

    for (Rebel rebel : this.rebels) {
      report.append(String.format("%s, %s, %s%n",
          rebel.getName(),
          rebel.getAge(),
          rebel.getRace().toString().toLowerCase()));
    }

    return report.toString();
  }

  private boolean addRebel(Rebel rebel) {

    if (rebel == null) {
      return false;
    }

    return this.rebels.add(rebel);
  }
}
