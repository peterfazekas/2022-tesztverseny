package hu.testathon.model.service;

import hu.testathon.model.domain.TestResult;

import java.util.List;

public class DataApi {

    private final DataParser dataParser;
    private final List<String> lines;

    public DataApi(String filename, FileReader fileReader, DataParser dataParser) {
        lines = fileReader.read(filename);
        this.dataParser = dataParser;
    }

    public List<TestResult> getTestResults() {
        return dataParser.getResults(lines);
    }

    public String getAnswers() {
        return dataParser.getAnswers(lines);
    }
}
