package observer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SpreadSheet implements Observer {

    private final DataSource dataSource;

    @Override
    public void update() {
        System.out.println("SpreadSheet notified: " + dataSource.getValue());
    }

}
