package org.dzbank.camunda7.embedded.template;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogMessageHandler implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(LogMessageHandler.class);

    @Override
    public void execute(DelegateExecution execution) {

        Object message = execution.getVariable("message");

        if (message instanceof String) {
            logger.debug("Logging for process instance {} with message: {}", execution.getProcessInstanceId(), message);
        } else {
            logger.debug("Logging for process instance {} with no message", execution.getProcessInstanceId());
        }
    }
}
