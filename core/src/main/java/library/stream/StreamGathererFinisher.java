package library.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;
import java.util.stream.IntStream;

class StreamGathererFinisher {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10)
                .boxed()
                .gather(batch(5))
                .forEach(System.out::println);
        System.out.println();
        IntStream.rangeClosed(1, 10)
                .boxed()
                .gather(batchUntil(i -> i % 3 == 0))
                .forEach(System.out::println);
    }

    private static <T> Gatherer<T, ?, List<T>> batch(int size) {
        return batch(() -> new BatchBuffer<>(size));
    }

    private static <T> Gatherer<T, ?, List<T>> batchUntil(Predicate<T> condition) {
        return batch(() -> new BatchBuffer<>(condition));
    }

    private static <T> Gatherer<T, ?, List<T>> batch(Supplier<BatchBuffer<T>> init) {
        return Gatherer.ofSequential(
                init,
                Integrator.ofGreedy(BatchBuffer::integrate),
                BatchBuffer::finish);
    }

    private static class BatchBuffer<E> {
        private final List<E> batch = new ArrayList<>();
        private Predicate<E> condition;
        private Integer size;

        BatchBuffer(Integer size) {
            this.size = size;
        }

        BatchBuffer(Predicate<E> condition) {
            this.condition = condition;
        }

        boolean integrate(E element, Gatherer.Downstream<? super List<E>> downstream) {
            batch.add(element);
            if (consumeMore(element))
                return true;
            var batchCp = List.copyOf(batch);
            batch.clear();
            return downstream.push(batchCp);
        }

        void finish(Gatherer.Downstream<? super List<E>> downstream) {
            if (!downstream.isRejecting() && !batch.isEmpty())
                downstream.push(List.copyOf(batch));
        }

        private boolean consumeMore(E element) {
            if (Objects.nonNull(size) && batch.size() < size)
                return true;
            return Objects.nonNull(condition) && condition.negate().test(element);
        }
    }

}
