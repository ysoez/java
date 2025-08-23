package dsa.stack;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.EmptyStackException;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class ArrayMinMaxStack<E extends Comparable<E>> implements MinMaxStack<E> {

    private final Stack<E> stack;
    private final Stack<E> minStack;
    private final Stack<E> maxStack;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    ArrayMinMaxStack(int capacity) {
        stack = new ArrayStack<>(capacity);
        minStack = new ArrayStack<>(capacity);
        maxStack = new ArrayStack<>(capacity);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E e) {
        stack.push(e);
        if (minStack.isEmpty() || e.compareTo(minStack.peek()) <= 0)
            minStack.push(e);
        if (maxStack.isEmpty() || e.compareTo(maxStack.peek()) >= 0)
            maxStack.push(e);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E pop() {
        if (stack.isEmpty())
            throw new EmptyStackException();
        var top = stack.pop();
        if (top.compareTo(minStack.peek()) == 0)
            minStack.pop();
        if (top.compareTo(maxStack.peek()) == 0)
            maxStack.pop();
        return top;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (stack.isEmpty())
            throw new EmptyStackException();
        return stack.peek();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E min() {
        return minStack.peek();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E max() {
        return maxStack.peek();
    }

}
