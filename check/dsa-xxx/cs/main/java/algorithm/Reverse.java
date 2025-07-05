package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Target.IN_PLACE;
import static util.Algorithm.Target.OUT_OF_PLACE;

public class Reverse {

    @Algorithm(
        complexity = @Complexity(runtime = LINEAR, space = LINEAR),
        target = OUT_OF_PLACE
    )
    static String string(String str) {
        var stack = new ArrayDeque<Character>();
        for (char ch : str.toCharArray())
            stack.push(ch);
        var stringBuilder = new StringBuilder();
        while (!stack.isEmpty())
            stringBuilder.append(stack.pop());
        return stringBuilder.toString();
    }

    @Algorithm(
        complexity = @Complexity(runtime = LINEAR, space = LINEAR),
        target = IN_PLACE
    )
    static <E> Queue<E> queue(Queue<E> queue) {
        var stack = new ArrayDeque<E>();
        while (!queue.isEmpty())
            stack.push(queue.remove());
        while (!stack.isEmpty())
            queue.add(stack.pop());
        return queue;
    }

    @Algorithm(
        complexity = @Complexity(runtime = LINEAR, space = LINEAR),
        target = OUT_OF_PLACE
    )
    static int[] intArray(int[] arr) {
        if (arr == null)
            throw new IllegalArgumentException("Array is null");
        var reversedArr = new int[arr.length];
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            reversedArr[j] = arr[i];
        }
        return reversedArr;
    }

}
