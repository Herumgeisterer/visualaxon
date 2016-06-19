package de.axonvisualizer.generator.json.provider;

import de.axonvisualizer.generator.data.AxonData;

public interface DataProvider {
   Object getData(final AxonData axonData);
}
