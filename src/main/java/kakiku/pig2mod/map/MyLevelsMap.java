package kakiku.pig2mod.map;

import java.lang.StackWalker.StackFrame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class MyLevelsMap<K, V> extends LinkedHashMap<K, V> {
    private Class<?> mOwnerClass = null;

    public MyLevelsMap(Map<K, V> serverLevelsMap) {
        super(serverLevelsMap);
    }

    @Override
    public V put(K key, V value) {
        StackFrame caller = MyLib2.getCaller1();
        return caller.getDeclaringClass() == MinecraftServer.class && caller.getMethodName().equals("m_129815_") && value.getClass() == ServerLevel.class
            ? super.put(key, value)
            : null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(K key, V value) {
        return null;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return super.get(key);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return super.get(key);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return super.get(key);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return super.get(key);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>(super.entrySet());
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(super.keySet());
    }

    @Override
    public Collection<V> values() {
        return new ArrayList<>(super.values());
    }
}
