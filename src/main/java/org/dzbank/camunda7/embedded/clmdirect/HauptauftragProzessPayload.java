package org.dzbank.camunda7.embedded.clmdirect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class HauptauftragProzessPayload implements Serializable {

    private NeukundenanlageProzessPayload kundenPayload;

    private Collection<KontoanlageProzessPayload> kontoPayloads;

    public HauptauftragProzessPayload() {
        kontoPayloads = new ArrayList<>();
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
}
