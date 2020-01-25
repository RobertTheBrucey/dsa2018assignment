/*****************************************************************************
* File:      DSALinkedList.java                                              *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Implement the ADT Double ended, doubly linked list              *
* Reference: Sean Ashton, DSA Prac 4                                         *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   23/08/2018 * Last Modified: 26/10/2018                          *
*****************************************************************************/
import java.util.*;

public class DSALinkedList<E> implements Iterable<E>
{
    //Class Fields
    private DSAListNode<E> head, tail;
    private int count;

    //Constructor
    public DSALinkedList()
    {
        head = tail = null;
        count = 0;
    }

    //Purpose: Copy constructor
    public DSALinkedList(DSALinkedList<E> inList)
    {
        this();
        Iterator<E> iter = inList.iterator();
        while (iter.hasNext())
        {
            insertLast(iter.next());
        }
    }

    //Accessors
    //Purpose: Allows for checking the number of elements without iterating
    //          through the whole list.
    public int getCount()
    {
        return count;
    }

    //Purpose: increment count from list iterator
    private void incCount()
    {
        count++;
    }

    //Purpose: decrement count from list iterator
    private void decCount()
    {
        count--;
    }

    //Purpose: Allows for checking if the list is empty
    public boolean isEmpty()
    {
        return (head == null);
    }

    //Purpose: return the value of the first element, abort if empty
    public E peekFirst()
    {
        if ( isEmpty() )
        {
            throw new NullPointerException( "List is empty" );
        }
        return head.getValue();
    }

    //Purpose: return the value of the last element, abort if empty
    public E peekLast()
    {
        if ( isEmpty() )
        {
            throw new NullPointerException( "List is empty" );
        }
        return tail.getValue();
    }

    //Mutators
    /* Purpose: insert a DSAListNode<E> at the head.
     * Considerations; Empty: update tail also, prev = null
     *                 Some values: update head, prev of old head
     */
    public void insertFirst( E inValue )
    {
        DSAListNode<E> newNode = new DSAListNode<E>( inValue );

        if ( !isEmpty() )
        {
            newNode.setNext( head );
            head.setPrev( newNode );
        }
        else
        {
            tail = newNode;
        }

        head = newNode;
        count++;
    }

    /* Purpose: insert a DSAListNode<E> at the head.
     * Considerations; Empty: update tail + head
     *                 Some values: update tail, next of tail
     */
    public void insertLast( E inValue )
    {
        DSAListNode<E> newNode = new DSAListNode<E>( inValue );
        if ( !isEmpty() )
        {
            newNode.setPrev( tail );
            tail.setNext( newNode );
        }
        else
        {
            head = newNode;
        }

        tail = newNode;
        count++;
    }

    /* Purpose: Remove and return the first element
     * Considerations; Empty: exception
     *                 1 value: remove head and tail
     *                 Some Values: move head to head.next,
     *                               set tail of head to null
     */
    public E removeFirst()
    {
        E value = peekFirst();

        if ( head.next() == null )
        {
            head = tail = null;
        }
        else
        {
            head = head.next();
            head.setPrev( null );
        }

        count--;
        return value;
    }

    /* Purpose: Remove and return the last element
     * Considerations; Empty: exception
     *                 1 value: update tail + head
     *                 Some values: update tail, next of tail
     */
    public E removeLast()
    {
        E value = peekLast();

        if ( tail.prev() == null )
        {
            head = tail = null;
        }
        else
        {
            tail = tail.prev();
            tail.setNext( null );
        }

        count--;
        return value;
    }

    /*************************************************************************
    *   Name: contains
    *   Purpose: Check to see if value is contained in list
    *   Imports: E, value to check for
    *   Exports: boolean, is value contained within
    *************************************************************************/
    public boolean contains( E value )
    {
        boolean found = false;
        Iterator<E> iter = this.iterator();
        while ( !found & iter.hasNext() )
        {
            if ( iter.next().equals( value ) )
            {
                found = true;
            }
        }
        return found;
    }

    //Utility methods
    DSAListNode<E> getHead()
    {
        return head;
    }

    void setHead( DSAListNode<E> node )
    {
        head = node;
    }

    DSAListNode<E> getTail()
    {
        return tail;
    }

    void setTail( DSAListNode<E> node )
    {
        tail = node;
    }

    public DSALinkedList<E> clone()
    {
        return new DSALinkedList<E>(this);
    }

    /*************************************************************************
    *   Name: equals
    *   Purpose: equals method
    *************************************************************************/
    public boolean equals( DSALinkedList<E> inList )
    {
        boolean equal = false;
        boolean run = true;
        if ( peekFirst().equals( inList.peekFirst() ) )
        {
            if ( peekLast().equals( inList.peekLast() ) )
            {
                Iterator<E> iter1 = this.iterator();
                Iterator<E> iter2 = inList.iterator();
                while ( iter1.hasNext() && iter2.hasNext() && run )
                {
                    if ( !iter1.next().equals( iter2.next() ) )
                    {
                        run = false;
                    }
                }
                if ( run && !iter1.hasNext() && !iter2.hasNext() )
                {
                    equal = true;
                }
            }
        }
        return equal;
    }

    //Iterator<E>
    public Iterator<E> iterator()
    {
        return new DSALinkedListIterator<E>( this );
    }

    public ListIterator<E> listIterator( int index )
    {
        return new ListItr<E>( this, index );
    }

    //Inner classes
    class DSAListNode<E>
    {
        //Class fields
        private E value;
        private DSAListNode<E> next, prev;

        public DSAListNode( E inValue )
        {
            value = inValue;
            next = prev = null;
        }

        //Accessors
        public E getValue()
        {
            return value;
        }

        public DSAListNode<E> next()
        {
            return next;
        }

        public DSAListNode<E> prev()
        {
            return prev;
        }

        //Mutators
        public void setValue( E inValue )
        {
            value = inValue;
        }

        public void setNext( DSAListNode<E> inNext )
        {
            next = inNext;
        }

        public void setPrev( DSAListNode<E> inPrev )
        {
            prev = inPrev;
        }
    }

    private class DSALinkedListIterator<E> implements Iterator<E>
    {
        private DSALinkedList<E>.DSAListNode<E> cursor;
        boolean canRemove;
        private DSALinkedList<E> list;

        public DSALinkedListIterator( DSALinkedList<E> theList )
        {
            if ( theList == null )
            {
                throw new NullPointerException();
            }
            list = theList;
            cursor = theList.head;
            canRemove = false;
        }

        //Purpose: check if this iterator has more elements to access
        public boolean hasNext()
        {
            boolean next = true;
            if (cursor == null)
            {
                next = false;
            }
            return (next);
        }

        //Purpose: return the next element in the list - should throw an
        //          exception if at the end, however this was just copied from
        //            a prac
        public E next()
        {
            E value;

            if ( cursor == null )
            {
                value = null;
            }
            else
            {
                value = cursor.getValue();
                cursor = cursor.next();
                canRemove = true;
            }

            return value;
        }

        //Purpose: remove the last element returned by next()
        public void remove()
        {
            if ( !canRemove )
            {   throw new IllegalStateException(
                    "next method not yet called or remove already used" );  }

            //In case of head
            if ( cursor == null )
            {
                list.removeLast();
            }
            // In case of tail
            else if ( cursor.prev().prev() == null )
            {
                list.removeFirst();
            }
            else //In case of neither head or tail.
            {
                cursor.prev().prev().setNext( cursor );
                cursor.setPrev( cursor.prev().prev() );
            }
            canRemove = false;
        }
    }

    /*************************************************************************
    *   Name: ListItr
    *   Purpose: Implement a list iterator to be able to sort on insert
    *   Reference: https://stackoverflow.com/questions/20444586/java-listiterator-implementation-specifics
    *************************************************************************/
    private class ListItr<E> implements ListIterator<E>
    {
        private DSALinkedList<E>.DSAListNode<E> lastReturned = null;
        private DSALinkedList<E>.DSAListNode<E> next, prev;
        private int nextIndex;
        private int prevIndex;
        private DSALinkedList<E> list;

        public ListItr( DSALinkedList<E> theList, int index )
        {
            list = theList;
            if ( index < 0 || index > list.getCount() )
            {
                throw new IndexOutOfBoundsException();
            }

            if ( index == getCount() )
            {
                next = null;
                nextIndex = list.getCount();
                prev = list.getTail();
                prevIndex = index;
            }
            else if ( index == 0 )
            {
                next = list.getHead();
                nextIndex = 0;
                prev = null;
                prevIndex = -1;
            }
            else
            {
                next = list.getHead();
                nextIndex = 0;
                prev = null;
                prevIndex = -1;
                for ( int ii = 1; ii < index; ii++ )
                {
                    next();
                }
            }
        }

        //Purpose: add a value between next and previous nodes
        public void add( E value )
        {
            if ( prev == null )
            {
                list.insertFirst( value );
            }
            else if ( next == null )
            {
                list.insertLast( value );
            }
            else
            {
                DSALinkedList<E>.DSAListNode<E> node =
                    new DSALinkedList<E>.DSAListNode<E>( value );
                node.setPrev( prev );
                node.setNext( next );
                prev.setNext( node );
                next.setPrev( node );
                prev = node;
                list.incCount();

                prevIndex++;
                nextIndex++;
            }
        }

        //Purpose: check if this iterator has a next node
        public boolean hasNext()
        {
            return ( next != null );
        }

        //Purpose: check if this iterator has a previous node
        public boolean hasPrevious()
        {
            return ( prev != null );
        }

        //Purpose: return the value of the next node
        public E next()
        {
            if ( hasNext() )
            {
                prev = next;
                next = next.next();
                prevIndex++;
                nextIndex++;
            }
            else
            {
                throw new NoSuchElementException();
            }
            lastReturned = prev;
            return prev.getValue();
        }

        //Purpose: get the index of the next node
        public int nextIndex()
        {
            return nextIndex;
        }

        //Purpose: get the value of the previous node
        public E previous()
        {
            if ( hasPrevious() )
            {
                next = prev;
                prev = prev.prev();
                prevIndex--;
                nextIndex--;
            }
            else
            {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            return next.getValue();
        }

        //Purpose: get the index of the previous node
        public int previousIndex()
        {
            return prevIndex;
        }

        //Purpose: remove the last returned node from the list
        public void remove()
        {
            if ( lastReturned == null )
            {
                throw new IllegalStateException();
            }

            if ( prev == null )
            {
                list.removeFirst();
            }
            else if ( next == null )
            {
                list.removeLast();
            }
            else
            {
                lastReturned.prev.setNext( lastReturned.next() );
                lastReturned.next.setPrev( lastReturned.prev() );
                if ( lastReturned == next )
                {
                    next = lastReturned.next();
                }
                else if ( lastReturned == prev )
                {
                    prev = lastReturned.prev();
                }
                list.decCount();
            }
            lastReturned = null;
        }

        //Purpose: Set the value of the last returned node
        public void set( E value )
        {
            if ( lastReturned == null )
            {
                throw new IllegalStateException();
            }

            lastReturned.setValue( value );
            lastReturned = null;
        }

    }

}
