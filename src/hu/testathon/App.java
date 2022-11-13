package hu.testathon;

import hu.testathon.controller.TestService;
import hu.testathon.model.service.*;

import java.util.Scanner;

public class App {

    private final TestService testService;
    private final Console console;
    private final FileWriter fileWriter;

    private App() {
        console = new Console(new Scanner(System.in));
        fileWriter = new FileWriter("pontok.txt");
        DataApi dataApi = new DataApi("valaszok.txt",
                new FileReader(), new DataParser());
        testService = new TestService(dataApi.getTestResults());
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("1. feladat: Az adatok beolvasása");
    }
}
