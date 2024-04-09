package org.dzbank.camunda7.embedded.clmdirect;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KundeAngelegtHandler implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(KundeAngelegtHandler.class);

    @Override
    public void execute(DelegateExecution execution) {

        HauptauftragProzessPayload payload = (HauptauftragProzessPayload) execution.getVariable(HauptauftragConsts.VARIABLE_NAME_PAYLOAD);
        String kundenId = (String) execution.getVariable(HauptauftragConsts.VARIABLE_NAME_KUNDEN_ID);
        payload.getKundenPayload().setKundenId(kundenId);

        logger.debug("Sending {} messages to the process instances", payload.getKontoPayloads().size());
        for (KontoanlageProzessPayload kontoPayload : payload.getKontoPayloads()) {
            execution.getProcessEngineServices().getRuntimeService()
                    .createMessageCorrelation(HauptauftragConsts.MESSAGE_NAME_KUNDE_ANGELEGT)
                    .processInstanceBusinessKey(execution.getProcessBusinessKey())
                    .processInstanceVariableEquals(HauptauftragConsts.VARIABLE_NAME_KONTO_NAME, kontoPayload.getKontoName())
                    .setVariable(HauptauftragConsts.VARIABLE_NAME_PAYLOAD, payload)
                    .correlate();
        }
    }
}
