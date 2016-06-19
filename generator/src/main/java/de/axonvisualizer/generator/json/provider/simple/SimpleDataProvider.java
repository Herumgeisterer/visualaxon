package de.axonvisualizer.generator.json.provider.simple;

import de.axonvisualizer.generator.data.AxonData;
import de.axonvisualizer.generator.json.provider.DataProvider;

public class SimpleDataProvider implements DataProvider {

   @Override
   public Object getData(final AxonData axonData) {
      return axonData;
   }
}
