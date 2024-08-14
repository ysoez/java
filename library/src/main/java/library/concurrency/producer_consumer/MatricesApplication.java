package library.concurrency.producer_consumer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class MatricesApplication {
    static final String BASE_PATH = "multithreading/src/main/java/multithreading/monitor/matrix";
    private static final String INPUT_FILE = BASE_PATH + "/matrices";
    private static final String OUTPUT_FILE = BASE_PATH + "/matrices_results.txt";

    public static void main(String[] args) throws IOException {
        var threadSafeQueue = new FixedThreadSafeQueue();
        var inputFile = new File(INPUT_FILE);
        var outputFile = new File(OUTPUT_FILE);
        var n = 10;

        var matricesReader = new MatricesReaderProducer(n, new FileReader(inputFile), threadSafeQueue);
        var matricesConsumer = new MatricesMultiplierConsumer(n, new FileWriter(outputFile), threadSafeQueue);

        matricesConsumer.start();
        matricesReader.start();
    }

}
