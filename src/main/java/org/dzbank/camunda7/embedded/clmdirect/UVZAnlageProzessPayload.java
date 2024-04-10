package org.dzbank.camunda7.embedded.clmdirect;

import java.io.Serializable;

public class UVZAnlageProzessPayload implements Serializable {

    private String uvzId;

    private String kontoName;

    private String uvzName;

    public String getUvzId() {
        return uvzId;
    }

    public void setUvzId(String uvzId) {
        this.uvzId = uvzId;
    }

    public String getKontoName() {
        return kontoName;
    }

    public void setKontoName(String kontoName) {
        this.kontoName = kontoName;
    }

    public String getUvzName() {
        return uvzName;
    }

    public void setUvzName(String uvzName) {
        this.uvzName = uvzName;
    }
}
