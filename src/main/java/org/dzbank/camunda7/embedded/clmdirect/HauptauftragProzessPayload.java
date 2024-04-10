package org.dzbank.camunda7.embedded.clmdirect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class HauptauftragProzessPayload implements Serializable {

    private NeukundenanlageProzessPayload kundenPayload;

    private Collection<KontoanlageProzessPayload> kontoPayloads;
    private Collection<UVZAnlageProzessPayload> uvzPayloads;

    private boolean debugSkipCLMK = true;

    public HauptauftragProzessPayload() {
        kontoPayloads = new ArrayList<>();
        uvzPayloads = new ArrayList<>();
    }

    public NeukundenanlageProzessPayload getKundenPayload() {
        return kundenPayload;
    }

    public void setKundenPayload(NeukundenanlageProzessPayload kundenPayload) {
        this.kundenPayload = kundenPayload;
    }

    public Collection<KontoanlageProzessPayload> getKontoPayloads() {
        return kontoPayloads;
    }

    public void setKontoPayloads(ArrayList<KontoanlageProzessPayload> kontoPayloads) {
        this.kontoPayloads = kontoPayloads;
    }

    public Collection<UVZAnlageProzessPayload> getUvzPayloads() {
        return uvzPayloads;
    }

    public void setUvzPayloads(Collection<UVZAnlageProzessPayload> uvzPayloads) {
        this.uvzPayloads = uvzPayloads;
    }

    public boolean getDebugSkipCLMK() {
        return debugSkipCLMK;
    }

    public void setDebugSkipCLMK(boolean debugSkipCLMK) {
        this.debugSkipCLMK = debugSkipCLMK;
    }
}
