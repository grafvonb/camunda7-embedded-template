package org.dzbank.camunda7.embedded.clmdirect;

public interface HauptauftragConsts {

    String VARIABLE_NAME_PAYLOAD = "payload";
    String VARIABLE_NAME_KUNDEN_ID = "kundenId";
    String VARIABLE_NAME_KONTO_NAME = "kontoName";
    String VARIABLE_NAME_KONTO_PAYLOAD = "kontoPayload";
    String VARIABLE_NAME_CLMK_AUFGABEN = "clmkAufgaben";
    String VARIABLE_NAME_DEBUGSKIPCLMK = "debugSkipCLMK";

    String VARIABLE_NAME_BUSINESS_KEY = "businessKey";
    String VARIABLE_NAME_KUNDEN_NAME = "kundenName";
    String VARIABLE_NAME_KUNDEN_PAYLOAD = "kundenPayload";
    String VARIABLE_NAME_UVZ_NAME = "uvzName";
    String VARIABLE_NAME_UVZ_PAYLOAD = "uvzPayload";
    String VARIABLE_SUBPROCESS_ID = "subprocessId";

    String MESSAGE_NAME_KUNDE_ANGELEGT = "KundeAngelegtMessage";
    String MESSAGE_NAME_KUNDEN_ANGELEGEN = "KundenAnlegenMessage";
    String MESSAGE_NAME_KONTO_ANGELEGEN = "KontoAnlegenMessage";
    String MESSAGE_NAME_UVZ_ANGELEGEN = "UVZAnlegenMessage";
    String MESSAGE_NAME_EXTRA_KONTO_ANGELEGEN = "ExtraKontoAnlagenMessage";
    String MESSAGE_NAME_HAUPTAUFTRAG_AUSGEFUERT = "HauptauftragAusgefuertMessage";
}
