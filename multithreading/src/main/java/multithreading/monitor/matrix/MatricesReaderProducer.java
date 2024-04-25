package multithreading.monitor.matrix;

import java.io.FileReader;
import java.util.Scanner;

class MatricesReaderProducer extends Thread {
    private final int N;
    private final Scanner scanner;
    private final FixedThreadSafeQueue queue;

    MatricesReaderProducer(int matrixSize, FileReader reader, FixedThreadSafeQueue queue) {
        this.N = matrixSize;
        this.scanner = new Scanner(reader);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            float[][] matrix1 = readMatrix();
            float[][] matrix2 = readMatrix();
            if (matrix1 == null || matrix2 == null) {
                queue.terminate();
                System.out.println("No more matrices to read. Producer Thread is terminating");
                return;
            }

            var matricesPair = new MatricesPair();
            matricesPair.matrix1 = matrix1;
            matricesPair.matrix2 = matrix2;

            queue.add(matricesPair);
        }
    }

    private float[][] readMatrix() {
        float[][] matrix = new float[N][N];
        for (int r = 0; r < N; r++) {
            if (!scanner.hasNext()) {
                return null;
            }
            String[] line = scanner.nextLine().split(",");
            for (int c = 0; c < N; c++) {
                matrix[r][c] = Float.parseFloat(line[c]);
            }
        }
        scanner.nextLine();
        return matrix;
    }

}
