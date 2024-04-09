package org.dzbank.camunda7.embedded.clmdirect;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PayloadAktualisierenHandler implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(PayloadAktualisierenHandler.class);

    @Override
    public void execute(DelegateExecution execution) {
        HauptauftragProzessPayload payload = (HauptauftragProzessPayload) execution.getVariable(HauptauftragConsts.VARIABLE_NAME_PAYLOAD);
        KontoanlageProzessPayload kontoPayload = (KontoanlageProzessPayload) execution.getVariable(HauptauftragConsts.VARIABLE_NAME_KONTO_PAYLOAD);
        payload.getKontoPayloads().add(kontoPayload);
        execution.setVariable(HauptauftragConsts.VARIABLE_NAME_PAYLOAD, payload);

        logger.debug("Buum: " + payload.getKontoPayloads().size());
    }
}
