package library.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;
import java.util.stream.IntStream;

class StreamGathererFinisher {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10)
                .boxed()
                .gather(batch(5))
                .forEach(System.out::println);
    }

    private static <T> Gatherer<T, ?, List<T>> batch(int size) {
        class BatchBuffer<E> {
            private final List<E> batch = new ArrayList<>();

            boolean integrate(E element, Gatherer.Downstream<? super List<E>> downstream) {
                batch.add(element);
                if (batch.size() < size)
                    return true;
                var batchCp = List.copyOf(batch);
                batch.clear();
                return downstream.push(batchCp);
            }

            void finish(Gatherer.Downstream<? super List<E>> downstream) {
                if (!downstream.isRejecting() && !batch.isEmpty())
                    downstream.push(List.copyOf(batch));
            }
        }
        return Gatherer.ofSequential(
                () -> new BatchBuffer<T>(),
                Integrator.ofGreedy(BatchBuffer::integrate),
                BatchBuffer::finish);
    }

}
