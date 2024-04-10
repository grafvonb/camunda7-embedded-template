package org.dzbank.camunda7.embedded.clmdirect;

import org.camunda.bpm.engine.RuntimeService;
import org.dzbank.camunda7.embedded.template.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clmdirect")
public class HauptauftragProzessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    private static final String HAUPTAUFTRAG_PROZESS_KEY = "CLMdirectHauptauftrag";

    private final RuntimeService runtimeService;

    public HauptauftragProzessController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping("/hauptauftrag/start")
    public Mono<String> startProcessInstance(@RequestBody HauptauftragProzessPayload payload) {

        String businessKey = UUID.randomUUID().toString();

        Map<String, Object> variables = new HashMap<>();
        variables.put(HauptauftragConsts.VARIABLE_NAME_PAYLOAD, payload);
        variables.put(HauptauftragConsts.VARIABLE_NAME_DEBUGSKIPCLMK, payload.getDebugSkipCLMK());

        String processInstanceId = runtimeService
                .startProcessInstanceByKey(HAUPTAUFTRAG_PROZESS_KEY, businessKey, variables)
                .getProcessInstanceId();

        logger.debug("Start of the process with the key {} completed, process id: {}", HAUPTAUFTRAG_PROZESS_KEY, processInstanceId);

        return Mono.just("Process started: " + processInstanceId + " with business key: " + businessKey);
    }

    @PostMapping("/hauptauftrag/kontoanlage/{businessKey}")
    public Mono<String> addKontoanlage(@PathVariable("businessKey") String businessKey, @RequestBody KontoanlageProzessPayload kontoPayload) {

        Map<String, Object> variables = new HashMap<>();
        variables.put(HauptauftragConsts.VARIABLE_NAME_KONTO_PAYLOAD, kontoPayload);
        variables.put(HauptauftragConsts.VARIABLE_NAME_KONTO_NAME, kontoPayload.getKontoName());
        variables.put(HauptauftragConsts.VARIABLE_NAME_DEBUGSKIPCLMK, true);

        runtimeService
                .createMessageCorrelation(HauptauftragConsts.MESSAGE_NAME_EXTRA_KONTO_ANGELEGEN)
                .processInstanceBusinessKey(businessKey)
                .setVariables(variables)
                .correlate();

        return Mono.just("Message sent for process with business key: " + businessKey);
    }
}
