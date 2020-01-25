/*****************************************************************************
* File:      MergeSort.java                                                  *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Perform a merge sort on generic input with function             *
* Reference: Sean Ashton 14866636 Submitted for Prac 6                       *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   01/08/2018 * Last Modified: 04/10/2018                          *
*****************************************************************************/
import java.util.Iterator;

public class MergeSortList<E extends Comparable<E>>
{
    DSALinkedList<E> list;
    public MergeSortList( DSALinkedList<E> inList )
    {
        list = inList;
    }

    //Purpose: Sort the linked list
    public DSALinkedList<E> sort()
    {
        list.setHead(mergeSort(list.getHead(), false));
        return list;
    }

    //Purpose: sort the list in reverse order
    public DSALinkedList<E> reverseSort()
    {
        list.setHead(mergeSort(list.getHead(), true));
        return list;
    }

    //Purpose: Recursively split the list for merge sorting
    private DSALinkedList<E>.DSAListNode<E> mergeSort(
                    DSALinkedList<E>.DSAListNode<E> node, boolean reverse )
    {
        DSALinkedList<E>.DSAListNode<E> retNode, middle, middleNext, left,
                                                                        right;
        //Base case if head is null or last node
        if ((node == null) || (node.next() == null))
        {
            retNode = node;
        }
        else
        {
            middle = getMiddle( node );
            middleNext = middle.next();

            middle.setNext( null );

            left = mergeSort( node, reverse );
            right = mergeSort( middleNext, reverse );

            retNode = merge( left, right, reverse );
        }
        return retNode;
    }

    //Purpose: Assist in finding the middle of the list
    //https://www.geeksforgeeks.org/merge-sort-for-linked-list/
    private DSALinkedList<E>.DSAListNode<E> getMiddle(
                                        DSALinkedList<E>.DSAListNode<E> node )
    {
        DSALinkedList<E>.DSAListNode<E> retNode = null;
        DSALinkedList<E>.DSAListNode<E> fast, slow;
        if ( node != null )
        {
            fast = node.next();
            slow = node;
            while (fast != null)
            {
                fast = fast.next();
                if ( fast != null )
                {
                    slow = slow.next();
                    fast = fast.next();
                }
            }
            retNode = slow;
        }
        return retNode;
    }

    /*************************************************************************
    *   Name: merge
    *   Purpose: merge split lists per merge sort
    *   Imports: DSAListNode, DSAListNode, boolean; left list, right list,
    *                whether to reverse sort
    *   Exports: DSAListNode; head of merged list
    *************************************************************************/
    private DSALinkedList<E>.DSAListNode<E> merge(
                    DSALinkedList<E>.DSAListNode<E> left,
                    DSALinkedList<E>.DSAListNode<E> right, boolean reverse )
    {
        DSALinkedList<E>.DSAListNode<E> result = null;
        if ( left == null )
        {
            result = right;
        }
        else if ( right == null )
        {
            result = left;
        }
        else
        {
            if ( !reverse )
            {
                if ( left.getValue().compareTo(right.getValue()) <= 0 )
                {
                    result = left;
                    result.setNext(merge(left.next(), right, reverse));
                }
                else
                {
                    result = right;
                    result.setNext(merge(left, right.next(), reverse));
                }
            }
            else
            {
                if ( left.getValue().compareTo(right.getValue()) > 0 )
                {
                    result = left;
                    result.setNext(merge(left.next(), right, reverse));
                }
                else
                {
                    result = right;
                    result.setNext(merge(left, right.next(), reverse));
                }
            }
        }
        return result;
    }
}
