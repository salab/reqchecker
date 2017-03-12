package jp.ac.titech.cs.se.reqchecker.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class AllPairs<T> implements Iterable<Pair<T, T>>, Iterator<Pair<T, T>> {

    private final List<T> list;

    private int i = 0;

    private int j = 1;

    private AllPairs(final List<T> list) {
        this.list = list;
    }

    public static <T> Iterable<Pair<T, T>> of(final List<T> list) {
        return new AllPairs<T>(list);
    }

    @Override
    public Iterator<Pair<T, T>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return i < list.size() && j < list.size();
    }

    @Override
    public Pair<T, T> next() {
        final Pair<T, T> result = ImmutablePair.of(list.get(i), list.get(j));

        j++;
        if (j == list.size()) {
            i++;
            j = i + 1;
        }

        return result;
    }
}
