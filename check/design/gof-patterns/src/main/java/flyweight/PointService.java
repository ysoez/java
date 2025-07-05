package flyweight;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class PointService {

    private final PointIconFactory factory;

    public List<Point> getPoints() {
        return List.of(
                new Point(1, 2, factory.getPointIcon(PointIcon.Type.CAFE)),
                new Point(10, 20, factory.getPointIcon(PointIcon.Type.RESTAURANT))
        );
    }

}
