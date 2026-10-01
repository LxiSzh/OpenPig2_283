package kakiku.pig2mod.map;

import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ObjectSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.LongFunction;
import kakiku.pig2mod.xform.MyLib2;
import org.jetbrains.annotations.Nullable;

public class MyLong2ObjectLinkedOpenHashMap<V> extends Long2ObjectLinkedOpenHashMap<V> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_ChunkMap = "ChunkMap";
    private boolean mDerivedConstructed = false;

    public MyLong2ObjectLinkedOpenHashMap(Long2ObjectMap<V> map, Class<?> ownerClass) {
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

    public MyLong2ObjectLinkedOpenHashMap() {
    }

    public MyLong2ObjectLinkedOpenHashMap(int expected, float f) {
        super(expected, f);
    }

    public MyLong2ObjectLinkedOpenHashMap(int expected) {
        super(expected);
    }

    public MyLong2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
        super(m, f);
    }

    public MyLong2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m) {
        super(m);
    }

    public MyLong2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m, float f) {
        super(m, f);
    }

    public MyLong2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m) {
        super(m);
    }

    public MyLong2ObjectLinkedOpenHashMap(long[] k, V[] v, float f) {
        super(k, v, f);
    }

    public MyLong2ObjectLinkedOpenHashMap(long[] k, V[] v) {
        super(k, v);
    }

    public V put(long k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.put(k, v) : this.defRetValue);
        }
    }

    public V put(Long k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.put(k, v) : this.defRetValue);
        }
    }

    public void putAll(Map<? extends Long, ? extends V> m) {
        if (this.isDerivedConstructed()) {
            super.putAll(m);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            super.putAll(m);
        }
    }

    public V putIfAbsent(long key, V value) {
        if (this.isDerivedConstructed()) {
            return (V)super.putIfAbsent(key, value);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.putIfAbsent(key, value) : this.defRetValue);
        }
    }

    public V remove(long k) {
        if (this.isDerivedConstructed()) {
            return (V)super.remove(k);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.remove(k) : this.defRetValue);
        }
    }

    public boolean remove(long k, Object v) {
        if (this.isDerivedConstructed()) {
            return super.remove(k, v);
        } else {
            return MyLib2.getCallerClass1() == this.mOwnerClass ? super.remove(k, v) : false;
        }
    }

    public V remove(Object key) {
        return (V)(this.isDerivedConstructed() ? super.remove(key) : this.defRetValue);
    }

    public void clear() {
        if (this.isDerivedConstructed()) {
            super.clear();
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
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
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.computeIfAbsent(key, mappingFunction) : super.get(key));
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
        if (this.isDerivedConstructed()) {
            return super.values();
        } else {
            return (ObjectCollection<V>)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.values() : new ObjectArrayList(super.values()));
        }
    }

    public FastSortedEntrySet<V> long2ObjectEntrySet() {
        return (FastSortedEntrySet<V>)(this.isDerivedConstructed()
            ? super.long2ObjectEntrySet()
            : new MyLong2ObjectLinkedOpenHashMap.MySnapshotEntrySet(super.long2ObjectEntrySet()));
    }

    public ObjectSortedSet<java.util.Map.Entry<Long, V>> entrySet() {
        return (ObjectSortedSet<java.util.Map.Entry<Long, V>>)(this.isDerivedConstructed() ? super.entrySet() : this.long2ObjectEntrySet());
    }

    public LongSortedSet keySet() {
        return (LongSortedSet)(this.isDerivedConstructed() ? super.keySet() : new MyLong2ObjectLinkedOpenHashMap.MySnapshotKeySet(super.keySet()));
    }

    private final class MySnapshotEntrySet extends AbstractObjectSortedSet<Entry<V>> implements FastSortedEntrySet<V> {
        private final ObjectArrayList<Entry<V>> snapshot;

        MySnapshotEntrySet(FastSortedEntrySet<V> src) {
            this.snapshot = new ObjectArrayList(src.size());
            ObjectBidirectionalIterator var3 = src.iterator();

            while (var3.hasNext()) {
                Entry<V> e = (Entry<V>)var3.next();
                this.snapshot.add(new BasicEntry<V>(e.getLongKey(), e.getValue()) {
                    public V setValue(V v) {
                        return (V)this.getValue();
                    }
                });
            }
        }

        public int size() {
            return this.snapshot.size();
        }

        public ObjectBidirectionalIterator<Entry<V>> iterator() {
            return this.snapshot.iterator();
        }

        public ObjectBidirectionalIterator<Entry<V>> iterator(Entry<V> fromElement) {
            return this.snapshot.iterator();
        }

        public ObjectBidirectionalIterator<Entry<V>> fastIterator() {
            return this.snapshot.iterator();
        }

        public ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> from) {
            return this.snapshot.iterator();
        }

        public Entry<V> first() {
            return this.snapshot.isEmpty() ? null : (Entry)this.snapshot.get(0);
        }

        public Entry<V> last() {
            return this.snapshot.isEmpty() ? null : (Entry)this.snapshot.get(this.snapshot.size() - 1);
        }

        @Nullable
        public Comparator<? super Entry<V>> comparator() {
            return null;
        }

        public ObjectSortedSet<Entry<V>> subSet(Entry<V> fromElement, Entry<V> toElement) {
            return this;
        }

        public ObjectSortedSet<Entry<V>> headSet(Entry<V> toElement) {
            return this;
        }

        public ObjectSortedSet<Entry<V>> tailSet(Entry<V> fromElement) {
            return this;
        }
    }

    private final class MySnapshotKeySet extends AbstractLongSortedSet {
        private final LongArrayList snapshot;

        MySnapshotKeySet(LongSet src) {
            this.snapshot = new LongArrayList(src.size());
            LongIterator it = src.iterator();

            while (it.hasNext()) {
                this.snapshot.add(it.nextLong());
            }
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

        public LongBidirectionalIterator iterator() {
            return this.snapshot.iterator();
        }

        public LongBidirectionalIterator iterator(long fromElement) {
            return this.snapshot.iterator();
        }

        public LongSortedSet subSet(long fromElement, long toElement) {
            return this;
        }

        public LongSortedSet headSet(long toElement) {
            return this;
        }

        public LongSortedSet tailSet(long fromElement) {
            return this;
        }

        public LongComparator comparator() {
            return null;
        }

        public long firstLong() {
            return 0L;
        }

        public long lastLong() {
            return 0L;
        }
    }
}
