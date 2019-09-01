public class Node<T extends Comparable<T>> {
    node head = null;
    node sorted = null;
    class node {
        T value;
        node next;
        node(T value){
            this.value = value;
        }
    }

    node sortedMerge(node a, node b) {
        node result = null;
        /* Base cases */
        if (a == null)
            return b;
        if (b == null)
            return a;

        /* Pick either a or b, and recur */
        if (a.value.compareTo(b.value)<0) {
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }

    node mergeSort(node h) {
        // Base case : if head is null
        if (h == null || h.next == null) {
            return h;
        }

        // get the middle of the list
        node middle = getMiddle(h);
        node nextofmiddle = middle.next;

        // set the next of middle node to null
        middle.next = null;

        // Apply mergeSort on left list
        node left = mergeSort(h);

        // Apply mergeSort on right list
        node right = mergeSort(nextofmiddle);

        // Merge the left and right lists
        node sortedlist = sortedMerge(left, right);
        return sortedlist;
    }

    // Utility function to get the middle of the linked list
    node getMiddle(node h) {
        // Base case
        if (h == null)
            return h;
        node fastptr = h.next;
        node slowptr = h;

        // Move fastptr by two and slow ptr by one
        // Finally slowptr will point to middle node
        while (fastptr != null) {
            fastptr = fastptr.next;
            if (fastptr != null) {
                slowptr = slowptr.next;
                fastptr = fastptr.next;
            }
        }
        return slowptr;
    }

    void push(T new_data) {
        /* allocate node */
        node new_node = new node(new_data);

        /* link the old list off the new node */
        new_node.next = head;

        /* move the head to point to the new node */
        head = new_node;
    }

    void insertionSort(node headref)
    {
        // Initialize sorted linked list
        sorted = null;
        node current = headref;
        // Traverse the given linked list and insert every
        // node to sorted
        while (current != null)
        {
            // Store next for next iteration
            node next = current.next;
            // insert current in sorted linked list
            sortedInsert(current);
            // Update current
            current = next;
        }
        // Update head_ref to point to sorted linked list
        head = sorted;
    }

    /*
     * function to insert a new_node in a list. Note that
     * this function expects a pointer to head_ref as this
     * can modify the head of the input linked list
     * (similar to push())
     */
    void sortedInsert(node newnode)
    {
        /* Special case for the head end */
        if (sorted == null || sorted.value.compareTo( newnode.value)<=0 )
        {
            newnode.next = sorted;
            sorted = newnode;
        }
        else
        {
            node current = sorted;
            /* Locate the node before the point of insertion */
            while (current.next != null && current.next.value.compareTo(newnode.value)>0 )
            {
                current = current.next;
            }
            newnode.next = current.next;
            current.next = newnode;
        }
    }
    // Utility function to print the linked list
    void printList(node headref) {
        while (headref != null) {
            System.out.println(headref.value);
            headref = headref.next;
        }
    }
}
