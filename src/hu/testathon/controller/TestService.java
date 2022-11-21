package hu.testathon.controller;

import hu.testathon.model.domain.TestResult;
import hu.testathon.model.domain.TestValidator;

import java.util.List;

public class TestService {

    private final List<TestResult> testResults;
    private final TestValidator testValidator;

    public TestService(List<TestResult> testResults, TestValidator testValidator) {
        this.testResults = testResults;
        this.testValidator = testValidator;
    }

    /**
     * 2. feladat
     */
    public int getCompetitorsCount() {
        return testResults.size();
    }

    /**
     * 3. feladat
     */
    public String getAnswersById(String id) {
        return getTestResultById(id).getAnswers();
    }

    private TestResult getTestResultById(String id) {
        return testResults.stream()
                .filter(i -> i.isCompetitor(id))
                .findAny()
                .get();
    }

    /**
     * 4. feladat
     */
    public String getCheckedResult(String id) {
        return String.format("%s\t(a helyes megoldás)\n" +
                "%s\t(a versenyző helyes válaszai))",
                testValidator.getAnswer(),
                testValidator.checkResults(getAnswersById(id)));
    }

    /**
     * 5. feladat
     */
    public String getCorrectAnswerStatistic(int taskNumber) {
        long count = countCorrectAnswers(taskNumber - 1);
        double percent = count * 100.0 / getCompetitorsCount();
        return String.format("A feladatra %d fő, a versenyzők %.2f%%-a adott helyes választ.",
                count, percent);
    }

    private long countCorrectAnswers(int taskNumber) {
        return testResults.stream()
                .map(TestResult::getAnswers)
                .filter(i -> testValidator.isCorrect(i, taskNumber))
                .count();
    }

}
