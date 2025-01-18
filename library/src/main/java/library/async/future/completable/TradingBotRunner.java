package library.async.future.completable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class TradingBotRunner {

    private static final String BROKER_API_KEY = "demo-api-key";

    public static void main(String[] args) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        try (ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(cores)) {
            var tradingBot = new TradingBot(BROKER_API_KEY, 10_000d);
            CompletableFuture<TradingBot.State> completableFuture = tradingBot.start(scheduledThreadPool);
            Thread.sleep(100000000);
        }
    }

    private static class TradingBot {

        private final BrokerAPI brokerAPI;
        private Double workingAmount;

        TradingBot(String apiKey, Double initialAmount) {
            brokerAPI = BrokerAPI.getInstance(apiKey);
            workingAmount = initialAmount;
        }

        public CompletableFuture<State> start(ScheduledExecutorService executor) {
            var future = new CompletableFuture<State>();
            //
            // ~ split the amount evenly on all the symbols
            //
            brokerAPI.getPrices().thenAcceptAsync(prices -> {
                        Double amountPerSymbol = workingAmount / prices.size();
                        prices.forEach(price -> {
                            int sharesCount = (int) (amountPerSymbol / price.price());
                            var order = new Order(Order.Type.BUY, sharesCount, price);
                            brokerAPI.submitOrder(order);
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        workingAmount = 0d; // decrement on each iteartion?
                    })
                    .thenRunAsync(() -> executor.scheduleAtFixedRate(new ExecutionStep(), 3, 4, TimeUnit.SECONDS))
                    .thenRunAsync(() -> future.complete(State.STARTED));
            return future;
        }

        public void stop() {
            // One final check for the profit to see if we need to sell the shares or keep them
        }

        class ExecutionStep implements Runnable {

            @Override
            public void run() {
                System.out.println("Current working amount: " + workingAmount);
                //
                // ~ compare prices & trade if needed
                //
                brokerAPI.getPrices().thenCombineAsync(brokerAPI.getSubmittedOrders(), (prices, orders) -> {
                    prices.forEach(price -> {
                        Order lastOrder = orders.stream()
                                .filter(order -> order.getSymbol() == price.symbol())
                                .findFirst()
                                .get();
                        Double oldPrice = lastOrder.getPrice();
                        Double newPrice = price.price();
                        if (lastOrder.getOrderType() == Order.Type.BUY && newPrice > oldPrice * 1.2) {
                            //
                            // ~ sell & capture 20% profit
                            //
                            Order order = new Order(Order.Type.SELL, lastOrder.getSharesCount(), price);
                            brokerAPI.submitOrder(order);
                            workingAmount += lastOrder.getSharesCount() * newPrice;
                        } else if (lastOrder.getOrderType() == Order.Type.SELL && newPrice < oldPrice * 0.9) {
                            //
                            // ~ buy 10 % DECREASE PRICE SHARE
                            //

                            int numberOfShares = (int) (workingAmount / newPrice);
                            if (numberOfShares < 1) {
                                System.out.println("Not enough money to buy shares, skipping iteration");
                            } else {
                                var order = new Order(Order.Type.BUY, numberOfShares, price);
                                brokerAPI.submitOrder(order);
                                workingAmount -= numberOfShares * newPrice;
                            }
                        } else {
                            System.out.println("Do nothing for: " + price);
                        }
                    });

                    return null;
                });
            }
        }

        enum State {
            STARTED,
            STOPPED
        }

    }

    private static class BrokerAPI {

        private static BrokerAPI instance;
        private final Set<Order> orders = new HashSet<>();
        /**
         * Map from symbol to (currentPrice, price direction)
         * Price direction is a boolean that tells whether the price should be increased or decreased on the next iteration
         */
        private final Map<Symbol, Map.Entry<Double, Boolean>> currentPrices = new HashMap<>();

        /**
         * Map from symbol to (minPriceLimit, maxPriceLimit)
         * <p>
         * This is the interval in which the price of that symbol will play
         */
        private final Map<Symbol, Map.Entry<Double, Double>> priceLimits = new HashMap<>();

        private BrokerAPI(String apiKey) {
            if (!apiKey.equals("demo-api-key")) {
                throw new IllegalStateException("Wrong API key");
            }
            initPrices();
        }

        private void initPrices() {
            priceLimits.put(Symbol.AMZN, new AbstractMap.SimpleEntry<>(10d, 400d));
            priceLimits.put(Symbol.TSLA, new AbstractMap.SimpleEntry<>(100d, 400d));
            priceLimits.put(Symbol.KO, new AbstractMap.SimpleEntry<>(200d, 400d));
        }

        public static BrokerAPI getInstance(String apiKey) {
            if (instance == null) {
                instance = new BrokerAPI(apiKey);
            }
            return instance;
        }

        public CompletableFuture<OrderResponse> submitOrder(Order order) {
            System.out.println("Order submitted: " + order);
            orders.removeIf(e -> e.getSymbol() == order.getSymbol());
            orders.add(order);
            String orderId = UUID.randomUUID().toString();
            return CompletableFuture.completedFuture(new OrderResponse(orderId));
        }

        public CompletableFuture<Set<Order>> getSubmittedOrders() {
            return CompletableFuture.completedFuture(orders);
        }

        public CompletableFuture<Set<Price>> getPrices() {
            for (Symbol symbol : Symbol.values()) {

                Map.Entry<Double, Boolean> priceDirection = currentPrices.get(symbol);

                // If no entry, I add the min price and price change direction will be UP (so it will raise)
                if (priceDirection == null) {
                    currentPrices.put(symbol, new AbstractMap.SimpleEntry<>(priceLimits.get(symbol).getKey(), true));
                } else {
                    // If price direction is true, then we need to increase the price (until it reaches the max limit)
                    if (priceDirection.getValue()) {
                        Double newPrice = priceDirection.getKey() + priceDirection.getKey() * 0.05;

                        // If the new price is greater than the max limit
                        if (newPrice > priceLimits.get(symbol).getValue()) {
                            // Switch the direction and keep the max price
                            currentPrices.put(symbol, new AbstractMap.SimpleEntry<>(priceLimits.get(symbol).getValue(), false));
                        } else {
                            // We haven't reach the limit, so we go up
                            currentPrices.put(symbol, new AbstractMap.SimpleEntry<>(newPrice, true));
                        }
                    } else {
                        Double newPrice = priceDirection.getKey() - priceDirection.getKey() * 0.05;

                        // If the new price is smaller than the min limit
                        if (newPrice < priceLimits.get(symbol).getKey()) {
                            // Switch the direction and keep the min price
                            currentPrices.put(symbol, new AbstractMap.SimpleEntry<>(priceLimits.get(symbol).getKey(), true));
                        } else {
                            // We haven't reach the limit, so we go down
                            currentPrices.put(symbol, new AbstractMap.SimpleEntry<>(newPrice, false));
                        }
                    }
                }
            }

            return CompletableFuture.completedFuture(currentPrices.entrySet().stream()
                    .map(entry -> new Price(entry.getKey(), entry.getValue().getKey()))
                    .collect(Collectors.toSet()));
        }
    }

    @Getter
    @ToString
    static class Order {
        private final Type orderType;
        private final Symbol symbol;
        private final Integer sharesCount;
        private final Double price;

        Order(Type type, int sharesCount, Price price) {
            this.orderType = type;
            this.sharesCount = sharesCount;
            this.symbol = price.symbol();
            this.price = price.price();
        }


        public enum Type {
            BUY,
            SELL
        }
    }

    @RequiredArgsConstructor
    static class OrderResponse {
        private final String orderId;
    }


    private record Price(Symbol symbol, Double price) {

    }

    public enum Symbol {
        TSLA,
        AMZN,
        KO
    }

}
