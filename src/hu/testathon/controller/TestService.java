package hu.testathon.controller;

import hu.testathon.model.domain.FinalResult;
import hu.testathon.model.domain.TestResult;
import hu.testathon.model.domain.TestValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 6. feladat:
     */
    public List<String> getScores() {
        return createFinalResults().stream()
                .map(FinalResult::toString)
                .collect(Collectors.toList());
    }

    private List<FinalResult> createFinalResults() {
        return testResults.stream()
                .map(this::createFinalResult)
                .collect(Collectors.toList());
    }

    private FinalResult createFinalResult(TestResult testResult) {
        int score = testValidator.calculateScore(testResult.getAnswers());
        return new FinalResult(testResult.getId(), score);
    }

    /**
     * 7. feladat
     */
    public String getOrderedResult() {
        return createOrderedFinalResults().stream()
                .filter(i -> i.getOrder() <= 3)
                .map(FinalResult::printOrder)
                .collect(Collectors.joining("\n"));
    }

    private List<FinalResult> createOrderedFinalResults() {
        List<FinalResult> sortedFinalResults = createSortedFinalResults();
        List<FinalResult> orderedFinalResults = new ArrayList<>();
        int prevOrder = 0, prevScore = 0;
        for (FinalResult finalResult: sortedFinalResults) {
            int order = finalResult.getScore() == prevScore
                ? prevOrder
                : prevOrder + 1;
            orderedFinalResults.add(new FinalResult(finalResult, order));
            prevOrder = order;
            prevScore = finalResult.getScore();
        }
        return orderedFinalResults;
    }

    private List<FinalResult> createSortedFinalResults() {
        return createFinalResults().stream()
                .sorted((i, j) -> j.getScore().compareTo(i.getScore()))
                .collect(Collectors.toList());
    }
}
