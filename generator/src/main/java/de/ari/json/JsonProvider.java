package de.ari.json;

import de.ari.data.AxonData;

public interface JsonProvider {

   String getJson(final AxonData axonData);
}
