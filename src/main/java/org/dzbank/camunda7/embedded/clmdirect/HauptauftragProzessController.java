package org.dzbank.camunda7.embedded.clmdirect;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.dzbank.camunda7.embedded.template.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clmdirect")
public class HauptauftragProzessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    private static final String HAUPTAUFTRAG_PROZESS_KEY = "CLMdirectHauptauftrag";
    private static final String HAUPTAUFTRAG_PROZESS_KEY_V2 = "CLMdirectHauptauftragV2";
    private static final String NEUKUNDENANLAGE_PROZESS_KEY = "CLMdirectNeukundenanlageProzess";

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

    @PostMapping("/hauptauftrag/startV2")
    public Mono<String> startProcessInstanceV2() {

        String businessKey = UUID.randomUUID().toString();

        Map<String, Object> variables = new HashMap<>();

        String processInstanceId = runtimeService
                .startProcessInstanceByKey(HAUPTAUFTRAG_PROZESS_KEY_V2, businessKey, variables)
                .getProcessInstanceId();

        logger.debug("Start of the process with the key {} completed, process id: {}", HAUPTAUFTRAG_PROZESS_KEY_V2, processInstanceId);

        return Mono.just("Process started: " + processInstanceId + " with business key: " + businessKey);
    }

    @PostMapping("/hauptauftrag/stopV2/{businessKey}")
    public Mono<String> stopProcessInstanceV2(@PathVariable("businessKey") String businessKey) {

        Map<String, Object> variables = new HashMap<>();

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(HAUPTAUFTRAG_PROZESS_KEY_V2)
                .processInstanceBusinessKey(businessKey)
                .active()
                .singleResult();

        if (processInstance != null) {
            List<?> subprocesses = runtimeService.createProcessInstanceQuery()
                    .superProcessInstanceId(processInstance.getId())
                    .active()
                    .list();

            if (subprocesses.isEmpty()) {
                sendMessage(HauptauftragConsts.MESSAGE_NAME_HAUPTAUFTRAG_AUSGEFUERT, businessKey, variables);
                return Mono.just("Message sent for process with business key: " + businessKey);
            }

            return Mono.just("Cannot stop main process instance. There are " + subprocesses.size() + " subprocesses running!");
        }

        return Mono.just("Process instance for businessKey " + businessKey + "not found!");
    }

    @PostMapping("/hauptauftrag/kundenanlage/{businessKey}")
    public Mono<String> addKundenanlage(@PathVariable("businessKey") String businessKey, @RequestBody NeukundenanlageProzessPayload kundenPayload) {

        var subprocessId = UUID.randomUUID().toString();

        Map<String, Object> variables = new HashMap<>();
        variables.put(HauptauftragConsts.VARIABLE_SUBPROCESS_ID, subprocessId);
        variables.put(HauptauftragConsts.VARIABLE_NAME_KUNDEN_PAYLOAD, kundenPayload);
        variables.put(HauptauftragConsts.VARIABLE_NAME_KUNDEN_NAME, kundenPayload.getKundenName());

        sendMessage(HauptauftragConsts.MESSAGE_NAME_KUNDEN_ANGELEGEN, businessKey, variables);

        // this assumes that the engine works synchronously, which will not function in Camunda 8
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(NEUKUNDENANLAGE_PROZESS_KEY)
                .processInstanceBusinessKey(businessKey)
                .variableValueEquals(HauptauftragConsts.VARIABLE_SUBPROCESS_ID, subprocessId)
                .active()
                .singleResult();

        logger.debug("Message sent for process with business key: %s, instance id: %s".formatted(businessKey, processInstance.getProcessInstanceId()));
        return Mono.just("Message sent for process with business key: %s, instance id: %s".formatted(businessKey, processInstance.getProcessInstanceId()));
    }

    @PostMapping("/hauptauftrag/kontoanlage/{businessKey}")
    public Mono<String> addKontoanlage(@PathVariable("businessKey") String businessKey, @RequestBody KontoanlageProzessPayload kontoPayload) {

        Map<String, Object> variables = new HashMap<>();
        variables.put(HauptauftragConsts.VARIABLE_NAME_KONTO_PAYLOAD, kontoPayload);
        variables.put(HauptauftragConsts.VARIABLE_NAME_KONTO_NAME, kontoPayload.getKontoName());
        variables.put(HauptauftragConsts.VARIABLE_NAME_DEBUGSKIPCLMK, true);

        sendMessage(HauptauftragConsts.MESSAGE_NAME_KONTO_ANGELEGEN, businessKey, variables);

        return Mono.just("Message sent for process with business key: " + businessKey);
    }

    @PostMapping("/hauptauftrag/uvzanlage/{businessKey}")
    public Mono<String> addKontoanlage(@PathVariable("businessKey") String businessKey, @RequestBody UVZAnlageProzessPayload uvzPayload) {

        Map<String, Object> variables = new HashMap<>();
        variables.put(HauptauftragConsts.VARIABLE_NAME_UVZ_PAYLOAD, uvzPayload);
        variables.put(HauptauftragConsts.VARIABLE_NAME_UVZ_NAME, uvzPayload.getUvzName());

        sendMessage(HauptauftragConsts.MESSAGE_NAME_UVZ_ANGELEGEN, businessKey, variables);

        return Mono.just("Message sent for process with business key: " + businessKey);
    }


    private void sendMessage(String messageName, String businessKey, Map<String, Object> variables) {
        logger.debug("Sending message {} with the business key: {}", messageName, businessKey);

        runtimeService
                .createMessageCorrelation(messageName)
                .processInstanceBusinessKey(businessKey)
                .setVariables(variables)
                .correlate();
    }
}
