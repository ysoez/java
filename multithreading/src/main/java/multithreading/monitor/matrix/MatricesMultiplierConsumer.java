package multithreading.monitor.matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

class MatricesMultiplierConsumer extends Thread {

    private final int n;
    private final FixedThreadSafeQueue queue;
    private final FileWriter fileWriter;

    MatricesMultiplierConsumer(int n, FileWriter fileWriter, FixedThreadSafeQueue queue) {
        this.n = n;
        this.fileWriter = fileWriter;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            MatricesPair matricesPair = queue.remove();
            if (matricesPair == null) {
                System.out.println("No more matrices to read from the queue, consumer is terminating");
                break;
            }

            float[][] result = multiplyMatrices(matricesPair.matrix1, matricesPair.matrix2);

            try {
                saveMatrixToFile(fileWriter, result);
            } catch (IOException e) {
            }
        }

        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float[][] multiplyMatrices(float[][] m1, float[][] m2) {
        float[][] result = new float[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                for (int k = 0; k < n; k++) {
                    result[r][c] += m1[r][k] * m2[k][c];
                }
            }
        }
        return result;
    }

    private void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
        for (int r = 0; r < n; r++) {
            var stringJoiner = new StringJoiner(", ");
            for (int c = 0; c < n; c++) {
                stringJoiner.add(String.format("%.2f", matrix[r][c]));
            }
            fileWriter.write(stringJoiner.toString());
            fileWriter.write('\n');
        }
        fileWriter.write('\n');
    }

}
