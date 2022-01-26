package com.github.guitsilva.rebels.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Rebel {
  private final String name;
  private final short age;
  private final Race race;
}
