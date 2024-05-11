import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary method {@code sort} for
 * {@code Queue<String>}.
 */
public final class Queue1LSort1 extends Queue1L<String> {

    /**
     * No-argument constructor.
     */
    public Queue1LSort1() {
        super();
    }

    /**
     * Removes and returns the minimum value from {@code q} according to the
     * ordering provided by the {@code compare} method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to compare entries
     * @return the minimum value from {@code q}
     * @updates q
     * @requires <pre>
     * q /= empty_string  and
     *  [the relation computed by order.compare is a total preorder]
     * </pre>
     * @ensures <pre>
     * perms(q * <removeMin>, #q)  and
     *  for all x: string of character
     *      where (x is in entries (q))
     *    ([relation computed by order.compare method](removeMin, x))
     * </pre>
     */
    private static String removeMin(Queue<String> q, Comparator<String> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";

        Queue<String> temp = new Queue1L<>();
        temp.transferFrom(q);

        String minItem = temp.dequeue();
        q.enqueue(minItem);

        /*
         * Locate minItem
         */
        while (temp.length() > 0) {
            String item = temp.dequeue();
            q.enqueue(item);
            if (order.compare(item, minItem) <= 0) {
                minItem = item;
            }
        }

        /*
         * Remove minItem
         */

        temp.transferFrom(q);
        boolean wasRemoved = false;

        while (temp.length() > 0) {
            String item = temp.dequeue();
            if (!item.equals(minItem) || (item.equals(minItem) && wasRemoved)) {
                q.enqueue(item);
            } else {
                wasRemoved = true;
            }
        }

        return minItem;
    }

    @Override
    public void sort(Comparator<String> order) {
        assert order != null : "Violation of: order is not null";

        Queue<String> temp = new Queue1L<>();
        temp.transferFrom(this);

        while (temp.length() > 0) {
            this.enqueue(removeMin(temp, order));
        }
    }

}
