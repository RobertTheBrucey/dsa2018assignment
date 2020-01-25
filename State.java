/*****************************************************************************
* File:      State.java                                                      *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Container class for storing State information                   *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  DSALinkedList                                                   *
* Created:   26/10/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/
import java.util.ListIterator;
import java.util.Iterator;

class State implements Comparable<State>
{
    //Class fields
    private DSALinkedList<Division> divisions;
    private String name;

    /*Constructor: All values except divisions must be initialised
        Condensing if error checks for readability  */
    public State( String inName )
    {
        if ( inName == null )
        {   throw new IllegalArgumentException( "Name is null" );    }
        name = inName;

        divisions = new DSALinkedList<Division>();
    }

    //Accessors
    public String name()
    {
        return name;
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<Division> divisions()
    {
        return divisions.clone();
    }

    //Mutators
    public void addDivision( Division division )
    {
        if ( division == null )
        {   throw new IllegalArgumentException( "Division is null" );   }
        /* This line needs to change to insert the division at the correct
            location and to check for duplicates */
        insertDivision( division );
    }

    //Utility functions
    /*************************************************************************
    *   Name: insertDivision
    *   Purpose: Insert a division sorted by ID
    *   Imports: Division
    *   Comments: Need to implement using List Iterator methods
    *************************************************************************/
    private void insertDivision( Division division )
    {
        ListIterator<Division> iter = divisions.listIterator( 0 );
        Division tempC = null;
        boolean done = false;
        while ( !done && iter.hasNext() )
        {
            tempC = iter.next();
            if ( tempC.id() >= division.id() )
            {
                done = true;
            }
        }
        if ( tempC == null )
        {
            iter.add( division );
        }
        else if ( tempC.id() > division.id() )//Don't insert a duplicate id
        {
            iter.previous();
            iter.add( division );
        }
    }

    /*************************************************************************
    * Name: contains
    * Purpose: Check if this state contains a particular division
    * Imports: division, division to check for
    * Exports: found, boolean if division is found within this state
    * Comments: Could be beneficial to use a tree for increased search speed
    *************************************************************************/
    public boolean contains( Division division )
    {
        boolean found = false;
        Iterator<Division> iter = divisions.iterator();
        while ( !found && iter.hasNext() )
        {
            found = (iter.next() == division);
        }
        return found;
    }

    /*************************************************************************
    * Name: filterIn
    * Purpose: remove any divisions from a linked list that are not within
    *            this state
    * Imports: list, list to iterate through and remove entries from
    * Exports: list, same as above
    * Comments: Not used
    *************************************************************************/
    public DSALinkedList<Division> filterIn( DSALinkedList<Division> list )
    {
        Iterator<Division> listIter = list.iterator();
        Iterator<Division> thisIter = divisions.iterator();
        Division listDiv = null;
        Division thisDiv = null;

        if ( thisIter.hasNext() )
        {   thisDiv = thisIter.next();   }

        while ( listIter.hasNext() )
        {
            listDiv = listIter.next();
            while ( thisIter.hasNext() && thisDiv.id() < listDiv.id() )
            {
                thisDiv = thisIter.next();
            }
            if ( thisDiv != listDiv )
            {
                listIter.remove();
            }
        }
        return list;
    }

    /*************************************************************************
    * Name: filterOut
    * Purpose: remove any division from a linked list that are within
    *            this state
    * Imports: list, list to iterate through and remove entries from
    * Exports: list, same as above
    * Comments: Not used
    *************************************************************************/
    public DSALinkedList<Division> filterOut( DSALinkedList<Division> list )
    {
        Iterator<Division> listIter = list.iterator();
        Iterator<Division> thisIter = divisions.iterator();
        Division listDiv = null;
        Division thisDiv = null;

        if ( thisIter.hasNext() )
        {   thisDiv = thisIter.next();   }

        while ( listIter.hasNext() )
        {
            listDiv = listIter.next();
            while ( thisIter.hasNext() && thisDiv.id() < listDiv.id() )
            {
                thisDiv = thisIter.next();
            }
            if ( thisDiv == listDiv )
            {
                listIter.remove();
            }
        }
        return list;
    }

    /*************************************************************************
    *   Name: equals
    *   Purpose: equals method
    *************************************************************************/
    public boolean equals( State state )
    {
        boolean equal = false;
        if ( name.equals( state.name() ) )
        {
            if ( divisions.equals( state.divisions() ) )
            {
                equal = true;
            }
        }
        return equal;
    }

    /*************************************************************************
    *   Name: compareTo
    *   Purpose: Implements comparable
    *************************************************************************/
    public int compareTo( State state )
    {
        return ( name.compareTo( state.name() ) );
    }
}

