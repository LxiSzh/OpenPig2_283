package kakiku.pig2mod.map;

import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.mixin.MxAccessorTrackedEntity;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;

public class MyInt2ObjectOpenHashMap<V> extends Int2ObjectOpenHashMap<V> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_ChunkMap = "ChunkMap";
    private boolean mDerivedConstructed = false;

    public MyInt2ObjectOpenHashMap(Int2ObjectMap<V> map, Class<?> ownerClass) {
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

    public MyInt2ObjectOpenHashMap() {
    }

    public MyInt2ObjectOpenHashMap(int expected, float f) {
        super(expected, f);
    }

    public MyInt2ObjectOpenHashMap(int expected) {
        super(expected);
    }

    public MyInt2ObjectOpenHashMap(Map<? extends Integer, ? extends V> m, float f) {
        super(m, f);
    }

    public MyInt2ObjectOpenHashMap(Map<? extends Integer, ? extends V> m) {
        super(m);
    }

    public MyInt2ObjectOpenHashMap(Int2ObjectMap<V> m, float f) {
        super(m, f);
    }

    public MyInt2ObjectOpenHashMap(Int2ObjectMap<V> m) {
        super(m);
    }

    public MyInt2ObjectOpenHashMap(int[] k, V[] v, float f) {
        super(k, v, f);
    }

    public MyInt2ObjectOpenHashMap(int[] k, V[] v) {
        super(k, v);
    }

    private V protect(V v) {
        if (this.mOwnerClass.getSimpleName().equals("ChunkMap")) {
            return v;
        } else {
            if (v instanceof MxAccessorTrackedEntity aTrackedEntity) {
                Set<ServerPlayerConnection> seenBy1 = aTrackedEntity.getSeenBy();
                if (!(seenBy1 instanceof MySet)) {
                    aTrackedEntity.setSeenBy(new MySet<>(seenBy1, MyInt2ObjectOpenHashMap.class));
                }
            }

            return v;
        }
    }

    public V put(int k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.put(k, this.protect(v)) : this.defRetValue);
        }
    }

    public V put(Integer k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.put(k, this.protect(v)) : this.defRetValue);
        }
    }

    public void putAll(Map<? extends Integer, ? extends V> m) {
        if (this.isDerivedConstructed()) {
            super.putAll(m);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            super.putAll(m);
        }
    }

    public V putIfAbsent(int key, V value) {
        if (this.isDerivedConstructed()) {
            return (V)super.putIfAbsent(key, value);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.putIfAbsent(key, value) : this.defRetValue);
        }
    }

    public V remove(int k) {
        if (this.isDerivedConstructed()) {
            return (V)super.remove(k);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            return (V)super.remove(k);
        } else {
            if (this.mOwnerClass.getSimpleName().equals("ChunkMap") && this.get(k) instanceof MxAccessorTrackedEntity aTrackedEntity) {
                Entity entity = aTrackedEntity.getEntity();
                if (!(entity instanceof Pig2)) {
                    return (V)super.remove(k);
                }
            }

            return (V)this.defRetValue;
        }
    }

    public boolean remove(int k, Object v) {
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

    public V compute(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.compute(k, remappingFunction) : super.get(k));
    }

    public V computeIfPresent(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.computeIfPresent(k, remappingFunction) : super.get(k));
    }

    public V computeIfAbsent(int k, IntFunction<? extends V> mappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.computeIfAbsent(k, mappingFunction) : super.get(k));
    }

    public V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
        if (this.isDerivedConstructed()) {
            return (V)super.computeIfAbsent(key, mappingFunction);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.computeIfAbsent(key, mappingFunction) : super.get(key));
        }
    }

    public V merge(int k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.merge(k, v, remappingFunction) : super.get(k));
    }

    public boolean replace(int k, V oldValue, V v) {
        return this.isDerivedConstructed() ? super.replace(k, oldValue, v) : false;
    }

    public V replace(int k, V v) {
        return (V)(this.isDerivedConstructed() ? super.replace(k, v) : super.get(k));
    }

    public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
        if (this.isDerivedConstructed()) {
            super.replaceAll(function);
        }
    }

    public ObjectCollection<V> values() {
        return (ObjectCollection<V>)(this.isDerivedConstructed() ? super.values() : new ObjectArrayList(super.values()));
    }

    public FastEntrySet<V> int2ObjectEntrySet() {
        return (FastEntrySet<V>)(this.isDerivedConstructed()
            ? super.int2ObjectEntrySet()
            : new MyInt2ObjectOpenHashMap.MySnapshotEntrySet(super.int2ObjectEntrySet()));
    }

    public ObjectSet<java.util.Map.Entry<Integer, V>> entrySet() {
        return (ObjectSet<java.util.Map.Entry<Integer, V>>)(this.isDerivedConstructed() ? super.entrySet() : this.int2ObjectEntrySet());
    }

    public IntSet keySet() {
        return (IntSet)(this.isDerivedConstructed() ? super.keySet() : new MyInt2ObjectOpenHashMap.MySnapshotKeySet(super.keySet()));
    }

    private final class MySnapshotEntrySet extends AbstractObjectSet<Entry<V>> implements FastEntrySet<V> {
        private final ObjectArrayList<Entry<V>> snapshot;

        MySnapshotEntrySet(FastEntrySet<V> src) {
            this.snapshot = new ObjectArrayList(src.size());
            ObjectIterator var3 = src.iterator();

            while (var3.hasNext()) {
                Entry<V> e = (Entry<V>)var3.next();
                this.snapshot.add(new BasicEntry<V>(e.getIntKey(), e.getValue()) {
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

    private final class MySnapshotKeySet extends AbstractIntSet {
        private final IntArrayList snapshot;

        MySnapshotKeySet(IntSet src) {
            this.snapshot = new IntArrayList(src.size());
            IntIterator it = src.iterator();

            while (it.hasNext()) {
                this.snapshot.add(it.nextInt());
            }
        }

        public IntIterator iterator() {
            return this.snapshot.iterator();
        }

        public int size() {
            return this.snapshot.size();
        }

        public void clear() {
            this.snapshot.clear();
        }

        public boolean remove(int k) {
            return this.snapshot.rem(k);
        }

        public boolean contains(int k) {
            return this.snapshot.contains(k);
        }
    }
}
