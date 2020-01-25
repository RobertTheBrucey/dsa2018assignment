/*****************************************************************************
* File:      Party.java                                                      *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Container class for storing Party information                   *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  DSALinkedList                                                   *
* Created:   26/10/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/
import java.util.ListIterator;
import java.util.Iterator;

class Party implements Comparable<Party>
{
    //Class fields
    private DSALinkedList<Candidate> candidates;
    private String name;
    private String abbr;

    /*Constructor: All values except candidates must be initialised
        Condensing if error checks for readability  */
    public Party( String inAbbr, String inName )
    {
        if ( inAbbr == null )
        {   throw new IllegalArgumentException( "Abbreviation is null" );   }
        abbr = inAbbr;

        if ( inName == null )
        {   throw new IllegalArgumentException( "Name is null" );    }
        name = inName;

        candidates = new DSALinkedList<Candidate>();
    }

    //Accessors
    public String abbr()
    {
        return abbr;
    }

    public String name()
    {
        return name;
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<Candidate> candidates()
    {
        return candidates.clone();
    }

    //Mutators
    public void addCandidate( Candidate candidate )
    {
        if ( candidate == null )
        {   throw new IllegalArgumentException( "Candidate is null" );   }
        /* This line needs to change to insert the candidate at the correct
            location and to check for duplicates */
        insertCandidate( candidate );
    }

    //Utility functions
    /*************************************************************************
    *   Name: insertCandidate
    *   Purpose: Insert a candidate sorted by ID
    *   Imports: Candidate
    *   Comments: Need to implement using List Iterator methods
    *************************************************************************/
    private void insertCandidate( Candidate candidate )
    {
        ListIterator<Candidate> iter = candidates.listIterator( 0 );
        Candidate tempC = null;
        boolean done = false;
        while ( !done && iter.hasNext() )
        {
            tempC = iter.next();
            if ( tempC.id() >= candidate.id() )
            {
                done = true;
            }
        }
        if ( tempC == null )
        {
            iter.add( candidate );
        }
        else if ( tempC.id() > candidate.id() )//Don't insert a duplicate id
        {
            iter.previous();
            iter.add( candidate );
        }
    }

    /*************************************************************************
    * Name: contains
    * Purpose: Check if this party contains a particular candidate
    * Imports: candidate, candidate to check for
    * Exports: found, boolean if candidate is found within this party
    * Comments: Could be beneficial to use a tree for increased search speed
    *************************************************************************/
    public boolean contains( Candidate candidate )
    {
        boolean found = false;
        Iterator<Candidate> iter = candidates.iterator();
        while ( !found && iter.hasNext() )
        {
            found = (iter.next() == candidate);
        }
        return found;
    }

    /*************************************************************************
    * Name: filterIn
    * Purpose: remove any candidates from a linked list that are not within
    *            this party
    * Imports: list, list to iterate through and remove entries from
    * Exports: list, same as above
    * Comments: Not used
    *************************************************************************/
    public DSALinkedList<Candidate> filterIn( DSALinkedList<Candidate> list )
    {
        Iterator<Candidate> listIter = list.iterator();
        Iterator<Candidate> thisIter = candidates.iterator();
        Candidate listCand = null;
        Candidate thisCand = null;

        if ( thisIter.hasNext() )
        {   thisCand = thisIter.next();   }

        while ( listIter.hasNext() )
        {
            listCand = listIter.next();
            while ( thisIter.hasNext() && thisCand.id() < listCand.id() )
            {
                thisCand = thisIter.next();
            }
            if ( thisCand != listCand )
            {
                listIter.remove();
            }
        }
        return list;
    }

    /*************************************************************************
    * Name: filterOut
    * Purpose: remove any candidate from a linked list that are within
    *            this party
    * Imports: list, list to iterate through and remove entries from
    * Exports: list, same as above
    * Comments: Not used
    *************************************************************************/
    public DSALinkedList<Candidate> filterOut( DSALinkedList<Candidate> list )
    {
        Iterator<Candidate> listIter = list.iterator();
        Iterator<Candidate> thisIter = candidates.iterator();
        Candidate listCand = null;
        Candidate thisCand = null;

        if ( thisIter.hasNext() )
        {   thisCand = thisIter.next();   }

        while ( listIter.hasNext() )
        {
            listCand = listIter.next();
            while ( thisIter.hasNext() && thisCand.id() < listCand.id() )
            {
                thisCand = thisIter.next();
            }
            if ( thisCand == listCand )
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
    public boolean equals( Party party )
    {
        boolean equal = false;
        if ( name.equals( party.name() ) )
        {
            if ( abbr.equals( party.abbr() ) )
            {
                if ( candidates.equals( party.candidates() ) )
                {
                    equal = true;
                }
            }
        }
        return equal;
    }

    /*************************************************************************
    *   Name: compareTo
    *   Purpose: Implements Comparable
    *************************************************************************/
    public int compareTo( Party party )
    {
        return ( name.compareTo( party.name() ) );
    }
}

