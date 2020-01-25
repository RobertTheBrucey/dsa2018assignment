/*****************************************************************************
* File:      Filter.java                                                     *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Hold all settings for and execute a filter and sort             *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   27/10/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/
import java.util.*;

class Filter
{
    //Output list of candidates after process() is called
    private DSALinkedList<Candidate> candidates;
    //These fields represent filters
    private DSALinkedList<State> states;
    private DSALinkedList<Division> divisions;
    private DSALinkedList<Party> parties;
    //data is used to reset candidates and other filters if no filters set
    private ElectoralData data;
    private int[] order;

    public Filter( ElectoralData inData )
    {
        data = inData;
        candidates = inData.candidates();
        states = new DSALinkedList<State>();
        divisions = new DSALinkedList<Division>();
        parties = new DSALinkedList<Party>();
        order = new int[] { 1, 2, 3, 4 };
    }

    /*************************************************************************
    *   Name: print
    *   Purpose: Print current filter settings
    *   Imports: none
    *   Exports: none (to screen)
    *************************************************************************/
    public void print()
    {
        if ( states.isEmpty() )
        {
            System.out.println( "All States included" );
        }
        else
        {
            System.out.println( "States included:");
            Iterator<State> iter = states.iterator();
            while ( iter.hasNext() )
            {
                System.out.println( iter.next().name() );
            }
        }

        if ( divisions.isEmpty() )
        {
            System.out.println( "All Divisions included" );
        }
        else
        {
            System.out.println( "Divisions included:" );
            Iterator<Division> iter = divisions.iterator();
            while ( iter.hasNext() )
            {
                System.out.println( iter.next().name() );
            }
        }

        if ( parties.isEmpty() )
        {
            System.out.println( "All Parties included" );
        }
        else
        {
            System.out.println( "Parties included:" );
            Iterator<Party> iter = parties.iterator();
            Party party;
            while ( iter.hasNext() )
            {
                party = iter.next();
                System.out.println( party.abbr() + ", " + party.name() );
            }
        }
    }

    /*************************************************************************
    *   Name: clear
    *   Purpose: clear current filters
    *   Imports: none
    *   Exports: none
    *************************************************************************/
    public void clear()
    {
        states = new DSALinkedList<State>();
        divisions = new DSALinkedList<Division>();
        parties = new DSALinkedList<Party>();
    }

    /*************************************************************************
    *   Name: setOrder
    *   Purpose: set the order to sort by when printing
    *   Imports: order, int[] representing the order
    *   Exports: none
    *************************************************************************/
    public void setOrder( int[] inOrder )
    {
        if ( inOrder != null )
        {
            order = inOrder;
        }
    }

    /*************************************************************************
    *   Name: addState
    *   Purpose: add a state to the filters
    *   Imports: State to add (null for can't find)
    *   Exports: String, confirming addition
    *************************************************************************/
    public String addState( State state )
    {
        String string = "";
        if ( state != null )
        {
            if ( !states.contains( state ) )
            {
                //Need to change to insert in alphabetical order
                states.insertLast( state );
                string = "State " + state.name() + " added to filters.";
            }
            else
            {
                string = "State " + state.name() +  " already in filters.";
            }
        }
        else
        {
            string = "State could not be found.";
        }
        return string;
    }

    /*************************************************************************
    *   Name: delState
    *   Purpose: remove state from filters
    *   Imports: State to remove
    *   Exports: String, confirming removal
    *************************************************************************/
    public String delState( State state )
    {
        String string = "";
        if ( state != null )
        {
            Iterator<State> iter = states.iterator();
            boolean found = false;
            while ( !found && iter.hasNext() )
            {
                if ( iter.next().equals( state ) )
                {
                    iter.remove();
                    found = true;
                }
            }
            if ( found )
            {
                string = "State " + state.name() + " removed from fitlers.";
            }
            else
            {
                string = "State " + state.name() + " not found in filters.";
            }
        }
        else
        {
            string = "State not found.";
        }
        return string;
    }

    /*************************************************************************
    *   Name: addParty
    *   Purpose: add a party to the filters
    *   Imports: Party to add (null for can't find)
    *   Exports: String, confirming addition
    *************************************************************************/
    public String addParty( Party party )
    {
        String string = "";
        if ( party != null )
        {
            if ( !parties.contains( party ) )
            {
                //Need to change to insert in alphabetical order
                parties.insertLast( party );
                string = "Party " + party.name() + " added to filters.";
            }
            else
            {
                string = "Party " + party.name() +  " already in filters.";
            }
        }
        else
        {
            string = "Party could not be found.";
        }
        return string;
    }

    /*************************************************************************
    *   Name: delParty
    *   Purpose: remove party from filters
    *   Imports: Party to remove
    *   Exports: String, confirming removal
    *************************************************************************/
    public String delParty( Party party )
    {
        String string = "";
        if ( party != null )
        {
            Iterator<Party> iter = parties.iterator();
            boolean found = false;
            while ( !found && iter.hasNext() )
            {
                if ( iter.next().equals( party ) )
                {
                    iter.remove();
                    found = true;
                }
            }
            if ( found )
            {
                string = "Party " + party.name() + " removed from fitlers.";
            }
            else
            {
                string = "Party " + party.name() + " not found in filters.";
            }
        }
        else
        {
            string = "Party not found.";
        }
        return string;
    }

    /*************************************************************************
    *   Name: addDivision
    *   Purpose: add a division to the filters
    *   Imports: Division to add (null for can't find)
    *   Exports: String, confirming addition
    *************************************************************************/
    public String addDivision( Division division )
    {
        String string = "";
        if ( division != null )
        {
            if ( !divisions.contains( division ) )
            {
                //Need to change to insert in numerical order
                divisions.insertLast( division );
                string = "Division " + division.name() + " added to filters.";
            }
            else
            {
                string = "Division " + division.name() +
                                                    " already in filters.";
            }
        }
        else
        {
            string = "Division could not be found.";
        }
        return string;
    }

    /*************************************************************************
    *   Name: delDivision
    *   Purpose: remove division from filters
    *   Imports: Division to remove
    *   Exports: String, confirming removal
    *************************************************************************/
    public String delDivision( Division division )
    {
        String string = "";
        if ( division != null )
        {
            Iterator<Division> iter = divisions.iterator();
            boolean found = false;
            while ( !found && iter.hasNext() )
            {
                if ( iter.next().equals( division ) )
                {
                    iter.remove();
                    found = true;
                }
            }
            if ( found )
            {
                string = "Division " + division.name() +
                                                    " removed from fitlers.";
            }
            else
            {
                string = "Division " + division.name() +
                                                    " not found in filters.";
            }
        }
        else
        {
            string = "Division not found.";
        }
        return string;
    }

    /*************************************************************************
    *   Name: process
    *   Purpose: execute the filter and sort on data
    *   Imports: ElectoralData, data to filter and sort
    *   Exports: none
    *************************************************************************/
    public void process()
    {
        data.ready();
        if ( states.isEmpty() ) { states = data.states(); }
        if ( divisions.isEmpty() ) { divisions = data.divisions(); }
        if ( parties.isEmpty() ) { parties = data.parties(); }

        candidates = data.candidates();
        //Order: 1: Surname, 2: State, 3: Party, 4: Division
        boolean reverse = false;
        for ( int ii = order.length-1; ii >= 0; ii-- )
        {
            reverse = ( order[ii] < 0 );
            switch ( Math.abs(order[ii]) )
            {
                case 1://Sort by surname - mergesort
                if ( reverse )
                {
                    new MergeSortList<Candidate>(candidates).reverseSort();
                }
                else
                {
                    new MergeSortList<Candidate>(candidates).sort();
                }
                break;

                case 2://Sort by State
                stateSort( reverse );
                break;

                case 3://Sort by Party
                partySort( reverse );
                break;

                case 4://Division Sort
                divisionSort( reverse );
                break;
            }
        }

    }

    /*************************************************************************
    *   Name: stateSort
    *   Purpose: Sort candidates by state
    *   Imports: none (works on class field)
    *   Exports: none (see above)
    *************************************************************************/
    private void stateSort( boolean reverse )
    {
        DSALinkedList<Candidate> output = new DSALinkedList<Candidate>();
        Iterator<State> sIter = states.iterator();
        Iterator<Candidate> cIter;
        Candidate cand;

        while ( sIter.hasNext() )
        {
            cIter = candidates.iterator();
            State state = sIter.next();
            while ( cIter.hasNext() )
            {
                cand = cIter.next();
                if ( cand.division().state().equals( state ) )
                {
                    if ( reverse )
                    {
                        output.insertLast( cand );
                    }
                    else
                    {
                        output.insertFirst( cand );
                    }
                }
            }
        }
        if ( !states.isEmpty() )
        {
            candidates = output;
        }
    }

    /*************************************************************************
    *   Name: partySort
    *   Purpose: Sort candidates by party
    *   Imports: none (works on class field)
    *   Exports: none (see above)
    *************************************************************************/
    private void partySort( boolean reverse )
    {
        DSALinkedList<Candidate> output = new DSALinkedList<Candidate>();
        Iterator<Party> sIter = parties.iterator();
        Iterator<Candidate> cIter;
        Candidate cand;

        while ( sIter.hasNext() )
        {
            cIter = candidates.iterator();
            Party party = sIter.next();
            while ( cIter.hasNext() )
            {
                cand = cIter.next();
                if ( cand.party().equals( party ) )
                {
                    if ( reverse )
                    {
                        output.insertLast( cand );
                    }
                    else
                    {
                        output.insertFirst( cand );
                    }
                }
            }
        }
        if ( !parties.isEmpty() )
        {
            candidates = output;
        }

    }

    /*************************************************************************
    *   Name: divisionSort
    *   Purpose: Sort candidates by division
    *   Imports: none (works on class field)
    *   Exports: none (see above)
    *************************************************************************/
    private void divisionSort( boolean reverse )
    {
        DSALinkedList<Candidate> output = new DSALinkedList<Candidate>();
        Iterator<Division> sIter = divisions.iterator();
        Iterator<Candidate> cIter;
        Candidate cand;

        while ( sIter.hasNext() )
        {
            cIter = candidates.iterator();
            Division division = sIter.next();
            while ( cIter.hasNext() )
            {
                cand = cIter.next();
                if ( cand.division().equals( division ) )
                {
                    if ( !reverse )
                    {
                        output.insertLast( cand );
                    }
                    else
                    {
                        output.insertFirst( cand );
                    }
                }
            }
        }
        if ( !divisions.isEmpty() )
        {
            candidates = output;
        }

    }

    /*************************************************************************
    *   Name: printCandidates
    *   Purpose: Display all candidates remaining after filtering and sort
    *   Imports: none
    *   Exports: none
    *************************************************************************/
    public void printCandidates()
    {
        DSAQueue<String[]> stringOutput = candidateQueue();
        while ( !stringOutput.isEmpty() )
        {
            String[] line = stringOutput.dequeue();
            System.out.println(
                "Name: " + line[0] + ", " + line[1] + ", Party: " + line[2] +
                ", Division: " + line[3] + ", ID: " + line[4] +
                ", Votes for: " + line[5] );
        }
    }

    /*************************************************************************
    *   Name: candidateList
    *   Purpose: Return the sorted linked list of candidates other use
    *   Imports: none
    *   Exports: DSALinkedList of candidates
    *************************************************************************/
    public DSAQueue<String[]> candidateQueue()
    {
        Iterator<Candidate> iter = candidates.iterator();
        DSAQueue<String[]> stringOutput = new DSAQueue<String[]>();
        Candidate cand = null;
        while ( iter.hasNext() )
        {
            String[] line = new String[9];
            cand = iter.next();
            line[0] = cand.surname();
            line[1] = cand.givenName();
            line[2] = cand.party().abbr();
            line[3] = cand.division().name();
            line[4] = ""+cand.id();
            line[5] = ""+cand.votes();
            line[6] = cand.division().state().name();
            line[7] = cand.party().name();
            line[8] = ""+cand.division().id();
            stringOutput.enqueue( line );
        }
        return stringOutput;
    }
}
