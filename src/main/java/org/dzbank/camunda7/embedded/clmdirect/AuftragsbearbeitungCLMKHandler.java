package org.dzbank.camunda7.embedded.clmdirect;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AuftragsbearbeitungCLMKHandler implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(AuftragsbearbeitungCLMKHandler.class);

    @Override
    public void execute(DelegateExecution execution) {
        logger.debug("Preparing additional tasks to run in parallel...");

        Collection<String> clmkAufgaben = new ArrayList<>();
        clmkAufgaben.add("Auftragsbearbeitung im Markt");
        clmkAufgaben.add("Auftragsbearbeitung von AbteilungX");

        execution.setVariable(HauptauftragConsts.VARIABLE_NAME_CLMK_AUFGABEN, clmkAufgaben);
    }
}
