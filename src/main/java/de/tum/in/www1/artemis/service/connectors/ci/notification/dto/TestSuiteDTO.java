package de.tum.in.www1.artemis.service.connectors.ci.notification.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import de.tum.in.www1.artemis.service.dto.BuildJobDTOInterface;
import de.tum.in.www1.artemis.service.dto.TestCaseDTOInterface;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record TestSuiteDTO(String name, double time, int errors, int skipped, int failures, int tests,
        @JsonProperty("testCases") @JsonSetter(nulls = Nulls.AS_EMPTY) List<TestCaseDTO> testCases) implements BuildJobDTOInterface {

    @Override
    @JsonIgnore
    public List<? extends TestCaseDTOInterface> getFailedTests() {
        return testCases.stream().filter(testCase -> !testCase.isSuccessful()).toList();
    }

    @Override
    @JsonIgnore
    public List<? extends TestCaseDTOInterface> getSuccessfulTests() {
        return testCases.stream().filter(TestCaseDTO::isSuccessful).toList();
    }
}
