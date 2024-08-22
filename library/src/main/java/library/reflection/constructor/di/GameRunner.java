package library.reflection.constructor.di;

import library.reflection.constructor.di.game.Game;
import library.reflection.constructor.di.game.internal.TicTacToeGame;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

class GameRunner {

    public static void main(String[] args) throws Exception {
        Game game = createObjectRecursively(TicTacToeGame.class);
        game.startGame();
    }

    private static <T> T createObjectRecursively(Class<T> clazz) throws Exception {
        Constructor<?> constructor = getFirstConstructor(clazz);
        List<Object> constructorArguments = new ArrayList<>();
        for (Class<?> argumentType : constructor.getParameterTypes()) {
            Object argumentValue = createObjectRecursively(argumentType);
            constructorArguments.add(argumentValue);
        }
        constructor.setAccessible(true);
        return (T) constructor.newInstance(constructorArguments.toArray());
    }

    private static Constructor<?> getFirstConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalStateException(String.format("No constructor has been found for class %s", clazz.getName()));
        }
        return constructors[0];
    }

}
