package observer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Chart implements Observer {

    private final DataSource dataSource;

    @Override
    public void update() {
        System.out.println("Chart notified: " + dataSource.getValue());
    }

}
