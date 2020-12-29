package org.atcrowdfunding.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapUtil {

    public static class Entry<K,V> {
        private K key;
        private V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return this.key;
        }

        public V getVal() {
            return this.val;
        }
    }

    public static <K, V> Entry<K, V> kv(K key, V val) {
        return new Entry<K, V>(key,  val);
    }

    public static <K, V> Map<K, V> map(Entry<K, V>... data) {
        return concurrentHashMap(data);
    }

    //这个返回HashMap
    public static <K, V> HashMap<K, V> hashMap(Entry<K, V>... entries){

        HashMap<K, V> result = new HashMap<K, V>();
        putEntriesIntoMap(result, entries);
        return result;
    }

    //这个返回ConcurrentHashMap
    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap(Entry<K, V>... entries){
        ConcurrentHashMap<K, V> result = new ConcurrentHashMap<K, V>();
        putEntriesIntoMap(result, entries);
        return result;
    }

    private static <K, V> void putEntriesIntoMap(Map<K,V> map, Entry<K, V>[] entries) {
        for(Entry<K, V> item : entries){
            map.put(item.getKey(), item.getVal());
        }
    }

}