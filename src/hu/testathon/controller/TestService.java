package hu.testathon.controller;

import hu.testathon.model.domain.TestResult;

import java.util.List;

public class TestService {

    private final List<TestResult> testResults;

    public TestService(List<TestResult> testResults) {
        this.testResults = testResults;
    }

}
