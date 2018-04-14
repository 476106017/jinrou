package zdm.jinrou.util;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class MyMaps {
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> Map<K, V> newHashMap(Object... args) {
        Map map = Maps.newHashMap();
        if(args.length%2==1)
            throw new IllegalArgumentException("参数必须成对");
        Stream.iterate(0, p->p+2).limit(args.length/2)
                .forEach(p->map.put(args[p],args[p+1]));
        return map;
    }
}
