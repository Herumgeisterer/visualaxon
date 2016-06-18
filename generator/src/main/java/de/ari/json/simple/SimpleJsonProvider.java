package de.ari.json.simple;

import de.ari.data.AxonData;
import de.ari.json.JsonProvider;

import com.google.gson.Gson;

public class SimpleJsonProvider implements JsonProvider {
   @Override
   public String getJson(final AxonData axonData) {
      return new Gson().toJson(axonData);
   }
}
