package flyweight;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Point {
    private final int x;
    private final int y;
    private final PointIcon icon;

    void draw() {
        System.out.printf("%s at (%d, %d)", icon, x, y);
    }
}
