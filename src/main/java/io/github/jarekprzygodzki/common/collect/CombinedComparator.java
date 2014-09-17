package io.github.jarekprzygodzki.common.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class CombinedComparator<T> implements Comparator<T> {

	private final List<Comparator<T>> comparators;

	@java.lang.SafeVarargs
	public CombinedComparator(Comparator<T>... comparators) {
		if (comparators == null || comparators.length < 1) {
			throw new IllegalArgumentException();
		}
		this.comparators = Arrays.asList(comparators);
	}

	public CombinedComparator(List<Comparator<T>> comparators) {
		if (comparators == null || comparators.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.comparators = comparators;
	}

	@Override
	public int compare(T o1, T o2) {
		int result = 0;
		for (Comparator<T> comparator : comparators) {
			result = comparator.compare(o1, o2);
			if (result != 0) {
				break;
			}
		}
		return result;
	}

	static <T, R extends Comparable<? super R>> Comparator<T> build(
			Function<T, R> propertyAccessor) {
		return new CombinedComparator<T>((new ByPropertyComparator<T, R>(propertyAccessor)));
	}

	@java.lang.SafeVarargs
	static <T, R extends Comparable> Comparator<T> build(
			Function<T, R>... propertyAccessors) {
		List<Comparator<T>> comparators = new ArrayList<Comparator<T>>(
				propertyAccessors.length);
		for (Function<T, R> propertyAccessor : propertyAccessors) {
			comparators.add(new ByPropertyComparator<T, R>(propertyAccessor));
		}
		return new CombinedComparator<T>(comparators);
	}

	private static final class ByPropertyComparator<T, R extends Comparable>
			implements Comparator<T> {

		private final boolean nullsFirst;

		private final Function<T, R> propertyAccessor;

		private ByPropertyComparator(Function<T, R> propertyAccessor) {
			this(propertyAccessor, false);
		}

		private ByPropertyComparator(Function<T, R> propertyAccessor, boolean nullsFirst) {
			this.propertyAccessor = propertyAccessor;
			this.nullsFirst = nullsFirst;
		}

		@Override
		public int compare(T o1, T o2) {
			R p1 = propertyAccessor.apply(o1);
			R p2 = propertyAccessor.apply(o2);
			if (p1 == null) {
				if (p2 == null) {
					return 0;
				}
				return nullsFirst ? -1 : 1;
			}
			return p1.compareTo(p2);
		}

	}
}
