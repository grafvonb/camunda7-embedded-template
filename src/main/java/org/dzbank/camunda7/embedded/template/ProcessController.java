package org.dzbank.camunda7.embedded.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/process")
public class ProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    private final ProcessService processService;

    public ProcessController(ProcessService releaseProcessService) {
        this.processService = releaseProcessService;
    }

    @PostMapping("/start")
    public Mono<String> startProcessInstance(@RequestBody String processKey) {

        String processInstanceId = processService.startProcessInstance(processKey);

        logger.debug("Start of the process with the key {} completed", processKey);

        return Mono.just("Process started: " + processInstanceId);
    }

    @PostMapping("/complete-task")
    public Mono<String> releaseActionManually(@RequestBody String taskId) {

        processService.completeTask(taskId);

        logger.debug("Task with the id: {} completed", taskId);

        return Mono.just("Task completed: " + taskId);
    }
}
