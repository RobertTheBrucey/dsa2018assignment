/*****************************************************************************
* File:      DSAQueue.java                                                   *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Implement the queue concept - Shuffling queue                   *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   23/08/2018 * Last Modified: 23/08/2018                          *
*****************************************************************************/
import java.util.*;

class DSAQueue<E> implements Iterable<E>
{
    private DSALinkedList<E> list;

    public DSAQueue ()
    {
        list = new DSALinkedList<E>();
    }

    public DSAQueue ( DSAQueue<E> toCopy )//Broken
    {
        this();
        Iterator<E> iter = toCopy.iterator();
        while ( iter.hasNext() )
        {
            enqueue( iter.next() );
        }
    }

    public boolean isEmpty ()
    {
        return (list.isEmpty());
    }

    //Purpose: Add an object to the stack
    public void enqueue ( E inObj )
    {
        list.insertLast( inObj );
    }

    //Purpose: put something as the front of the queue
    public void unqueue ( E inObj )
    {
        list.insertFirst( inObj );
    }

    //Purpose: Return the next object in the queue - without removing it
    public E peek ()
    {
        if ( isEmpty() )
        {
            throw new IndexOutOfBoundsException( "Queue is empty!" );
        }
        return list.peekFirst();
    }

    //Purpose: Return the next object in the queue and remove from queue
    public E dequeue ()
    {
        E outObj = peek();
        list.removeFirst();
        return outObj;
    }

    //Purpose: Expose list's iterator
    public Iterator<E> iterator()
    {
        return list.iterator();
    }

    public DSAQueue<E> clone()
    {
        return new DSAQueue<E>(this);
    }
}
