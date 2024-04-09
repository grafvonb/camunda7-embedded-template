package org.dzbank.camunda7.embedded.template;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    public ProcessService(final RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public String startProcessInstance(String processKey) {
        logger.debug("Starting process instance of {}", processKey);

        return runtimeService.startProcessInstanceByKey(processKey).getProcessInstanceId();
    }

    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }
}
