package flyweight;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PointIcon {
    final Type type;
    final byte[] icon;

    @Override
    public String toString() {
        return type.toString();
    }

    enum Type {
        HOSPITAL, CAFE, RESTAURANT
    }
}
