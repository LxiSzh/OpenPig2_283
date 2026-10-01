package kakiku.pig2mod.map;

import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.LongFunction;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.util.SortedArraySet;
import net.minecraft.world.level.entity.EntitySection;

public class MyLong2ObjectOpenHashMap<V> extends Long2ObjectOpenHashMap<V> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_EntitySectionStorage = "EntitySectionStorage";
    private final String OWNER_PersistentEntitySectionManager = "PersistentEntitySectionManager";
    private final String OWNER_DistanceManager = "DistanceManager";
    private final String OWNER_TickingTracker = "TickingTracker";
    private boolean mDerivedConstructed = false;

    public MyLong2ObjectOpenHashMap(Long2ObjectMap<V> map, Class<?> ownerClass) {
        this.mOwnerClass = ownerClass;
        this.defRetValue = map.defaultReturnValue();
        super.putAll(map);
    }

    protected void setDerivedConstructed() {
        this.mDerivedConstructed = true;
        this.mOwnerClass = this.getClass();
    }

    protected boolean isDerivedConstructed() {
        return this.mDerivedConstructed;
    }

    public MyLong2ObjectOpenHashMap() {
    }

    public MyLong2ObjectOpenHashMap(int expected, float f) {
        super(expected, f);
    }

    public MyLong2ObjectOpenHashMap(int expected) {
        super(expected);
    }

    public MyLong2ObjectOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
        super(m, f);
    }

    public MyLong2ObjectOpenHashMap(Map<? extends Long, ? extends V> m) {
        super(m);
    }

    public MyLong2ObjectOpenHashMap(Long2ObjectMap<V> m, float f) {
        super(m, f);
    }

    public MyLong2ObjectOpenHashMap(Long2ObjectMap<V> m) {
        super(m);
    }

    public MyLong2ObjectOpenHashMap(long[] k, V[] v, float f) {
        super(k, v, f);
    }

    public MyLong2ObjectOpenHashMap(long[] k, V[] v) {
        super(k, v);
    }

    private V protect(V v) {
        if (this.mOwnerClass.getSimpleName().equals("DistanceManager") || this.mOwnerClass.getSimpleName().equals("TickingTracker")) {
            if (v instanceof MySortedArraySet) {
                return v;
            }

            if (v instanceof SortedArraySet<?> sortedArraySet) {
                return (V)(new MySortedArraySet(sortedArraySet, MyLong2ObjectOpenHashMap.class));
            }
        }

        return v;
    }

    public void putAll(Map<? extends Long, ? extends V> m) {
        if (this.isDerivedConstructed()) {
            super.putAll(m);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            super.putAll(m);
        }
    }

    public V put(long k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.put(k, this.protect(v)) : this.defRetValue);
        }
    }

    public V myPut(long k, V v) {
        return (V)super.put(k, this.protect(v));
    }

    public V put(Long k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.put(k, this.protect(v)) : this.defRetValue);
        }
    }

    public V putIfAbsent(long key, V value) {
        if (this.isDerivedConstructed()) {
            return (V)super.putIfAbsent(key, value);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.putIfAbsent(key, this.protect(value)) : this.defRetValue);
        }
    }

    public V remove(long k) {
        if (this.isDerivedConstructed()) {
            return (V)super.remove(k);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass || MyLib2.getCaller2().getDeclaringClass() == this.mOwnerClass) {
            return (V)super.remove(k);
        } else {
            if (this.get(k) instanceof EntitySection<?> entitySection && entitySection.isEmpty()) {
                return (V)super.remove(k);
            }

            return (V)this.defRetValue;
        }
    }

    public boolean remove(long k, Object v) {
        return this.isDerivedConstructed() ? super.remove(k, v) : false;
    }

    public V remove(Object key) {
        return (V)(this.isDerivedConstructed() ? super.remove(key) : this.defRetValue);
    }

    public void clear() {
        if (this.isDerivedConstructed()) {
            super.clear();
        }
    }

    public V compute(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.compute(k, remappingFunction) : super.get(k));
    }

    public V computeIfPresent(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.computeIfPresent(k, remappingFunction) : super.get(k));
    }

    public V computeIfAbsent(long k, LongFunction<? extends V> mappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.computeIfAbsent(k, mappingFunction) : super.get(k));
    }

    public V computeIfAbsent(long key, Long2ObjectFunction<? extends V> mappingFunction) {
        if (this.isDerivedConstructed()) {
            return (V)super.computeIfAbsent(key, mappingFunction);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? this.myComputeIfAbsent(key, mappingFunction) : super.get(key));
        }
    }

    public V myComputeIfAbsent(long key, Long2ObjectFunction<? extends V> mappingFunction) {
        V v = (V)super.computeIfAbsent(key, mappingFunction);
        if (v instanceof MySortedArraySet) {
            return v;
        } else if (v instanceof SortedArraySet) {
            V v2 = this.protect(v);
            super.put(key, v2);
            return v2;
        } else {
            return v;
        }
    }

    public V merge(long k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.merge(k, v, remappingFunction) : super.get(k));
    }

    public boolean replace(long k, V oldValue, V v) {
        return this.isDerivedConstructed() ? super.replace(k, oldValue, v) : false;
    }

    public V replace(long k, V v) {
        return (V)(this.isDerivedConstructed() ? super.replace(k, v) : super.get(k));
    }

    public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> function) {
        if (this.isDerivedConstructed()) {
            super.replaceAll(function);
        }
    }

    public ObjectCollection<V> values() {
        return (ObjectCollection<V>)(this.isDerivedConstructed() ? super.values() : new ObjectArrayList(super.values()));
    }

    public FastEntrySet<V> long2ObjectEntrySet() {
        if (this.isDerivedConstructed()) {
            return super.long2ObjectEntrySet();
        } else {
            Class<?> caller1Class = MyLib2.getCallerClass1();
            return (FastEntrySet<V>)(caller1Class != this.mOwnerClass && caller1Class != MyLong2ObjectOpenHashMap.class
                ? new MyLong2ObjectOpenHashMap.MySnapshotEntrySet(super.long2ObjectEntrySet())
                : super.long2ObjectEntrySet());
        }
    }

    public ObjectSet<java.util.Map.Entry<Long, V>> entrySet() {
        return (ObjectSet<java.util.Map.Entry<Long, V>>)(this.isDerivedConstructed() ? super.entrySet() : this.long2ObjectEntrySet());
    }

    public LongSet keySet() {
        return (LongSet)(this.isDerivedConstructed() ? super.keySet() : new MyLong2ObjectOpenHashMap.MySnapshotKeySet(super.keySet()));
    }

    private final class MySnapshotEntrySet extends AbstractObjectSet<Entry<V>> implements FastEntrySet<V> {
        private final ObjectArrayList<Entry<V>> snapshot;

        MySnapshotEntrySet(FastEntrySet<V> src) {
            this.snapshot = new ObjectArrayList(src.size());
            ObjectIterator var3 = src.iterator();

            while (var3.hasNext()) {
                Entry<V> e = (Entry<V>)var3.next();
                this.snapshot.add(new BasicEntry<V>(e.getLongKey(), e.getValue()) {
                    public V setValue(V v) {
                        return (V)this.getValue();
                    }
                });
            }
        }

        public ObjectIterator<Entry<V>> iterator() {
            return this.snapshot.iterator();
        }

        public ObjectIterator<Entry<V>> fastIterator() {
            return this.snapshot.iterator();
        }

        public int size() {
            return this.snapshot.size();
        }
    }

    private final class MySnapshotKeySet extends AbstractLongSet {
        private final LongArrayList snapshot;

        MySnapshotKeySet(LongSet src) {
            this.snapshot = new LongArrayList(src.size());
            LongIterator it = src.iterator();

            while (it.hasNext()) {
                this.snapshot.add(it.nextLong());
            }
        }

        public LongIterator iterator() {
            return this.snapshot.iterator();
        }

        public int size() {
            return this.snapshot.size();
        }

        public void clear() {
            this.snapshot.clear();
        }

        public boolean remove(long k) {
            return this.snapshot.rem(k);
        }

        public boolean contains(long k) {
            return this.snapshot.contains(k);
        }
    }
}
