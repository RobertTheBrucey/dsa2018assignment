/*****************************************************************************
* File:      Division.java                                                   *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Container class for storing Division information                *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  DSALinkedList                                                   *
* Created:   26/10/2018 * Last Modified: 26/10/2018                          *
*****************************************************************************/
import java.util.ListIterator;
import java.util.Iterator;

class Division implements Comparable<Division>
{
    //Class fields
    private DSALinkedList<Candidate> candidates;
    private State state;
    private int informalVotes;
    private int id;
    private String name;
    public double margin;

    /*Constructor: All values except for informal votes and candidates must
                    be initialised on construction
        Condensing if error checks for readability  */
    public Division( State inState, int inID, String inName )
    {
        if ( inState == null )
        {   throw new IllegalArgumentException( "State is null" );    }
        state = inState;

        if ( inID < 0 )
        {   throw new IllegalArgumentException( "ID is negative" );    }
        id = inID;

        if ( inName == null )
        {   throw new IllegalArgumentException( "Name is null" );    }
        name = inName;

        informalVotes = 0;
        candidates = new DSALinkedList<Candidate>();
    }

    //Accessors
    public State state()
    {
        return state;
    }

    public int id()
    {
        return id;
    }

    public String name()
    {
        return name;
    }

    public int informalVotes()
    {
        return informalVotes;
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<Candidate> candidates()
    {
        return candidates.clone();
    }

    //Mutators
    /*************************************************************************
    *   Name: addVotes
    *   Purpose: add informal votes to division
    *   Imports: int, votes to add
    *   Exports: none
    *   Comments: Informal votes don't count as votes against for marginal
    *                seats as they are not for any candidate.
    *************************************************************************/
    public void addVotes( int inVotes )
    {
        if ( inVotes < 0 )
        {   throw new IllegalArgumentException( "Votes must be positive" );  }
        informalVotes += inVotes;
    }

    /*************************************************************************
    *   Name: addCandidate
    *   Purpose: add a candidate to the division
    *   Imports: Candidate; candidate to add
    *   Exports: none
    *************************************************************************/
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
    *   Purpose: insert the candidate at the required id
    *   Imports: Candidate
    *   Comments: Should change to use listIterator methods
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
    * Purpose: Check if this division contains a particular candidate
    * Imports: candidate, candidate to check for
    * Exports: found, boolean if candidate is found within this division
    * Comments: Could be beneficial to use a tree for increased search speed
    *************************************************************************/
    public boolean contains( Candidate candidate )
    {
        boolean found = false;
        Iterator<Candidate> iter = candidates.iterator();
        while ( !found && iter.hasNext() )
        {
            found = ( iter.next() == candidate );
        }
        return found;
    }

    /*************************************************************************
    * Name: filterIn
    * Purpose: remove any candidate from a linked list that are not within
    *            this division
    * Imports: list, list to iterate through and remove entries from
    * Exports: list, same as above
    * Comments: Assuming sorted data to increase speed.
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
    *            this division
    * Imports: list, list to iterate through and remove entries from
    * Exports: list, same as above
    * Comments: Assuming sorted data to increase speed.
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
    public boolean equals( Division div )
    {
        boolean equal = false;
        if ( state.equals( div.state() ) )
        {
            if ( informalVotes == div.informalVotes() )
            {
                if ( id == div.id() )
                {
                    if ( name.equals( div.name() ) )
                    {
                        if ( candidates.equals( div.candidates() ) )
                        {
                            equal = true;
                        }
                    }
                }
            }
        }
        return equal;
    }

    /*************************************************************************
    *   Name: compareTo
    *   Purpose: Implement comparable for a dirty solution to a problem
    *************************************************************************/
    public int compareTo( Division div )
    {
        return (int)( 100 * ( margin - div.margin ) );
    }
}

