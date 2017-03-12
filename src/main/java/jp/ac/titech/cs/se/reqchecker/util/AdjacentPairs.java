package jp.ac.titech.cs.se.reqchecker.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class AdjacentPairs<T> implements Iterable<Pair<T, T>>, Iterator<Pair<T, T>> {

    private final List<T> list;

    private int index = 1;

    private AdjacentPairs(final List<T> list) {
        this.list = list;
    }

    public static <T> Iterable<Pair<T, T>> of(final List<T> list) {
        return new AdjacentPairs<T>(list);
    }

    @Override
    public Iterator<Pair<T, T>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public Pair<T, T> next() {
        final Pair<T, T> result = ImmutablePair.of(list.get(index - 1), list.get(index));
        index++;
        return result;
    }
}
