package forex.conversion.appln.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E extends Object> extends AbstractSet<E> {
  private ConcurrentHashMap<E, E> map;

  public ConcurrentHashSet() {
    map = new ConcurrentHashMap<>();
  }

  public ConcurrentHashSet(int capacity) {
    map = new ConcurrentHashMap<>(capacity);
  }

  public ConcurrentHashSet(Set<E> set) {
    this();
    for (E element: set) {
      map.put(element, element);
    }
  }

  @Override
  public boolean add(E element) {
    E fromCollection = map.putIfAbsent(element, element);
    return fromCollection == null;
  }

  @Override
  public boolean remove(Object o) {
    return map.remove(o) != null;
  }

  @Override
  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return map.containsKey(o);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ConcurrentHashSet) {
      ConcurrentHashSet c1 = (ConcurrentHashSet)obj;
      if (c1.map.entrySet().equals(map.entrySet())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    Set<Entry<E,E>> entrySet = map.entrySet();
    int sum = 0;
    for (Entry e: entrySet) {
      sum += e.hashCode();
    }
    return sum;
  }
}
