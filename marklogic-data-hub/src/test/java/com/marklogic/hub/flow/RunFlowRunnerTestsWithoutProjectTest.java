package com.marklogic.hub.flow;

import com.marklogic.hub.flow.impl.FlowRunnerImpl;
import com.marklogic.hub.step.MarkLogicStepDefinitionProvider;
import com.marklogic.hub.step.StepDefinition;
import com.marklogic.hub.step.StepDefinitionProvider;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Extends FlowRunnerTest so that each test can be run with a non-Spring-managed instance of FlowRunnerImpl that doesn't
 * depend on an instance of HubProject.
 */
public class RunFlowRunnerTestsWithoutProjectTest extends FlowRunnerTest {

    private FlowRunnerImpl myFlowRunner;

    @Override
    protected RunFlowResponse runFlow(String flowName, String commaDelimitedSteps, String jobId, Map<String, Object> options, Map<String, Object> stepConfig) {
        FlowInputs inputs = new FlowInputs(flowName);
        if (commaDelimitedSteps != null) {
            inputs.setSteps(Arrays.asList(commaDelimitedSteps.split(",")));
        }
        inputs.setJobId(jobId);
        inputs.setOptions(options);
        inputs.setStepConfig(stepConfig);

        // Need to avoid instantiating this twice for the benefit of the MultipleJobs test in the parent class
        if (myFlowRunner == null) {
            myFlowRunner = new FlowRunnerImpl(host, flowRunnerUser, flowRunnerPassword);
        }
        flowRunner = myFlowRunner;

        makeInputFilePathsAbsoluteInFlow(flowName);
        verifyStepDefinitionsCanBeFound(flowRunner);

        return flowRunner.runFlowWithoutProject(inputs);
    }

    /**
     * Before running the flow, we do a little testing to verify that both default and custom step definitions can be
     * found by MarkLogicStepDefinitionProvider.
     *
     * @param flowRunner
     */
    private void verifyStepDefinitionsCanBeFound(FlowRunnerImpl flowRunner) {
        StepDefinitionProvider provider = new MarkLogicStepDefinitionProvider(flowRunner.getHubConfig().newStagingClient(null));

        StepDefinition stepDef = provider.getStepDefinition("default-mapping", StepDefinition.StepDefinitionType.MAPPING);
        assertEquals("default-mapping", stepDef.getName());

        stepDef = provider.getStepDefinition("json-mapping", StepDefinition.StepDefinitionType.MAPPING);
        assertEquals("json-mapping", stepDef.getName());
    }
}
