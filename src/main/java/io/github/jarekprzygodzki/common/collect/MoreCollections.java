package io.github.jarekprzygodzki.common.collect;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MoreCollections {

	public static <T, R extends Comparable<? super R>> void sort(List<T> list,
			Function<T, R> propertyAccessor) {
		Comparator<T> comparator = CombinedComparator.build(propertyAccessor);
		Collections.sort(list, comparator);
	}

	public static <T, R extends Comparable> void sort(List<T> list,
			Function<T, R>... propertyAccessors) {
		Comparator<T> comparator = CombinedComparator.build(propertyAccessors);
		Collections.sort(list, comparator);
	}
}
