package kakiku.pig2mod.map;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.client.player.LocalPlayer;

public class MyHashMap<K, V> extends HashMap<K, V> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_ClassInstanceMultiMap = "ClassInstanceMultiMap";
    private final String OWNER_EntityLookup = "EntityLookup";
    private boolean mDerivedConstructed = false;

    public MyHashMap(Map<K, V> map, Class<?> ownerClass) {
        this.mOwnerClass = ownerClass;
        this.putAllSub(map);
    }

    protected void setDerivedConstructed() {
        this.mDerivedConstructed = true;
        this.mOwnerClass = this.getClass();
    }

    protected boolean isDerivedConstructed() {
        return this.mDerivedConstructed;
    }

    public MyHashMap() {
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MyHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MyHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    private V protect(V v) {
        if (this.mOwnerClass.getSimpleName().equals("ClassInstanceMultiMap")) {
            if (v instanceof MyArrayList) {
                return v;
            }

            if (v instanceof List) {
                return (V)(new MyArrayList((List)v, MyHashMap.class));
            }
        }

        return v;
    }

    @Override
    public V remove(Object key) {
        if (this.isDerivedConstructed()) {
            return super.remove(key);
        } else {
            return MyLib2.getCallerClass1() != this.mOwnerClass && !(this.get(key) instanceof LocalPlayer) ? null : super.remove(key);
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.isDerivedConstructed() ? super.remove(key, value) : false;
    }

    @Override
    public void clear() {
        if (this.isDerivedConstructed()) {
            super.clear();
        }
    }

    @Override
    public V put(K key, V value) {
        if (this.isDerivedConstructed()) {
            return super.put(key, value);
        } else {
            return this.get(key) != null && MyLib2.getCallerClass1() != this.mOwnerClass ? null : this.putSub(key, value);
        }
    }

    private V putSub(K key, V value) {
        return super.put(key, this.protect(value));
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (this.isDerivedConstructed()) {
            return super.putIfAbsent(key, value);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass ? super.putIfAbsent(key, this.protect(value)) : null;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (this.isDerivedConstructed()) {
            super.putAll(map);
        } else if (map.isEmpty() || MyLib2.getCallerClass1() == this.mOwnerClass) {
            this.putAllSub(map);
        }
    }

    private void putAllSub(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> e : map.entrySet()) {
            this.putSub((K)e.getKey(), (V)e.getValue());
        }
    }

    @Override
    public V replace(K key, V value) {
        if (this.isDerivedConstructed()) {
            return super.replace(key, value);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass ? super.replace(key, this.protect(value)) : null;
        }
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (this.isDerivedConstructed()) {
            return super.replace(key, oldValue, newValue);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass ? super.replace(key, oldValue, this.protect(newValue)) : false;
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (this.isDerivedConstructed()) {
            super.replaceAll(function);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            super.replaceAll((k, v) -> this.protect((V)function.apply(k, v)));
        }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (this.isDerivedConstructed()) {
            return super.compute(key, remappingFunction);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass ? super.compute(key, (k, v) -> this.protect((V)remappingFunction.apply(k, v))) : super.get(key);
        }
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (this.isDerivedConstructed()) {
            return super.computeIfPresent(key, remappingFunction);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass
                ? super.computeIfPresent(key, (k, v) -> this.protect((V)remappingFunction.apply(k, v)))
                : super.get(key);
        }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        if (this.isDerivedConstructed()) {
            return super.computeIfAbsent(key, mappingFunction);
        } else {
            return this.mOwnerClass.getSimpleName().equals("ClassInstanceMultiMap")
                ? super.computeIfAbsent(key, k -> this.protect((V)mappingFunction.apply(k)))
                : super.computeIfAbsent(key, mappingFunction);
        }
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (this.isDerivedConstructed()) {
            return super.merge(key, value, remappingFunction);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass
                ? super.merge(key, this.protect(value), (oldV, newV) -> this.protect((V)remappingFunction.apply(oldV, newV)))
                : super.get(key);
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (this.isDerivedConstructed()) {
            return super.entrySet();
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            final Set<Entry<K, V>> es = super.entrySet();
            return new AbstractSet<Entry<K, V>>() {
                @Override
                public Iterator<Entry<K, V>> iterator() {
                    final Iterator<Entry<K, V>> it = es.iterator();
                    return new Iterator<Entry<K, V>>() {
                        @Override
                        public boolean hasNext() {
                            return it.hasNext();
                        }

                        public Entry<K, V> next() {
                            final Entry<K, V> e = it.next();
                            return new Entry<K, V>() {
                                @Override
                                public K getKey() {
                                    return e.getKey();
                                }

                                @Override
                                public V getValue() {
                                    return e.getValue();
                                }

                                @Override
                                public V setValue(V value) {
                                    return e.setValue((V)MyHashMap.this.protect(value));
                                }
                            };
                        }

                        @Override
                        public void remove() {
                        }
                    };
                }

                @Override
                public int size() {
                    return es.size();
                }
            };
        } else {
            return new HashSet<>(super.entrySet());
        }
    }

    @Override
    public Set<K> keySet() {
        return (Set<K>)(this.isDerivedConstructed() ? super.keySet() : new HashSet<>(super.keySet()));
    }

    @Override
    public Collection<V> values() {
        return (Collection<V>)(this.isDerivedConstructed() ? super.values() : new ArrayList<>(super.values()));
    }
}
