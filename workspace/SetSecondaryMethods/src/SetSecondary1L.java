import components.set.Set;
import components.set.Set1L;

/**
 * Layered implementations of secondary methods {@code add} and {@code remove}
 * for {@code Set}.
 *
 * @param <T>
 *            type of {@code Set} elements
 */
public final class SetSecondary1L<T> extends Set1L<T> {

    /**
     * No-argument constructor.
     */
    public SetSecondary1L() {
        super();
    }

    @Override
    public Set<T> remove(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> remSet = new SetSecondary1L<T>();
        Set<T> temp = new SetSecondary1L<T>();
        temp.transferFrom(this);

        while (temp.size() > 0) {
            T item = temp.removeAny();
            if (s.contains(item)) {
                remSet.add(item);
            } else {
                this.add(item);
            }
        }

        return remSet;
    }

    @Override
    public void add(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> temp = new SetSecondary1L<T>();
        temp.transferFrom(s);

        while (temp.size() > 0) {
            T item = temp.removeAny();
            if (!this.contains(item)) {
                this.add(item);
            } else {
                s.add(item);
            }
        }
    }

    /**
     * Reports whether {@code this} is a subset of {@code s}. (A is a subset of
     * B exactly when every element of A is also an element of B.)
     *
     * @param s
     *            the second set
     * @return whether {@code this} is a subset of {@code s}
     * @ensures isSubset = (this is subset of s)
     */
    @Override
    public boolean isSubset(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        boolean isSubset = true;
        Set<T> temp = new SetSecondary1L<T>();
        temp.transferFrom(this);

        while (temp.size() > 0) {
            T item = temp.removeAny();
            if (!s.contains(item)) {
                isSubset = false;
            }
            this.add(item);
        }

        return isSubset;
    }
}
