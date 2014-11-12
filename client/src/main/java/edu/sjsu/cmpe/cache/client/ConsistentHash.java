package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.hash.HashFunction;

public class ConsistentHash<CacheServiceInterface> {

  private final HashFunction hashFunction;
  private final SortedMap<Integer, CacheServiceInterface> circle =
    new TreeMap<Integer, CacheServiceInterface>();
  private static int i = 0;

  public ConsistentHash(HashFunction hashFunction,ArrayList<CacheServiceInterface> nodes)
   {

    this.hashFunction = hashFunction;

    for (CacheServiceInterface node : nodes) {
      add(node);
    }
  }

  public void add(CacheServiceInterface node) {
      int key = hashFunction.hashInt(i).hashCode();
      circle.put(key, node);
      i++;
  }

  public void remove(CacheServiceInterface node) {
 {
      circle.remove(hashFunction.hashInt(i));  
      i--;}

  }

  public CacheServiceInterface get(int key) {
    if (circle.isEmpty()) {
      return null;
    }
    int temp = key % i;
    int hash = hashFunction.hashInt(temp).hashCode();
    if (!circle.containsKey(hash)) {
      SortedMap<Integer, CacheServiceInterface> tailMap =
        circle.tailMap(hash);
      hash = tailMap.isEmpty() ?
             circle.firstKey() : tailMap.firstKey();
    }
    return circle.get(hash);
  } 

}