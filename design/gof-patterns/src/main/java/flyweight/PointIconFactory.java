package flyweight;

import java.util.EnumMap;
import java.util.Map;

class PointIconFactory {

    private Map<PointIcon.Type, PointIcon> cache = new EnumMap<>(PointIcon.Type.class);

    PointIcon getPointIcon(PointIcon.Type type) {
        if (!cache.containsKey(type)) {
            cache.put(type, new PointIcon(type, new byte[10]));
        }
        return cache.get(type);
    }

}
