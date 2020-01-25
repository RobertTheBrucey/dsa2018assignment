/*****************************************************************************
* File:      ElectoralData.java                                              *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Class for storing and manipulating electral data                *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  DSALinkedList, DSAQueue, DSAHashTable                           *
* Created:   26/10/2018 * Last Modified: 26/10/2018                          *
*****************************************************************************/
import java.util.ListIterator;
import java.util.Iterator;
import java.io.IOException;

class ElectoralData
{
    //Class fields
    private DSALinkedList<Candidate> candidates;
    private DSAHashTable candidateHT;
    private DSALinkedList<State> states;
    private DSALinkedList<Division> divisions;
    private DSALinkedList<Party> parties;
    private DSALinkedList<VoteThread> threads;

    // Construct empty then read in with addCandidates() and addVotes()
    public ElectoralData()
    {
        candidates = new DSALinkedList<Candidate>();
        candidateHT = new DSAHashTable();
        states = new DSALinkedList<State>();
        divisions = new DSALinkedList<Division>();
        parties = new DSALinkedList<Party>();
        threads = new DSALinkedList<VoteThread>();
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<Candidate> candidates()
    {
        return candidates.clone();
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<State> states()
    {
        return states.clone();
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<Division> divisions()
    {
        return divisions.clone();
    }

    //Return a copy to ensure that this list is not modified unexpectedly
    public DSALinkedList<Party> parties()
    {
        return parties.clone();
    }

    // Data import methods
    /*************************************************************************
    *   Name: addCandidates
    *   Purpose: iterate through a queue to add candidates to object
    *   Imports: DSAQueue<String[]>, queue of separated lines.
    *   Exports: none
    *************************************************************************/
    public void addCandidates( DSAQueue<String[]> queue )
    {
        String[] line;
        Division div;
        Party party;
        Candidate candidate = null;
        //Remove header lines
        queue.dequeue();
        queue.dequeue();
        while ( !queue.isEmpty() )
        {
            line = queue.dequeue();
            //Get or create division (division must be added to state)
            div = getDiv( line[0], line[1], line[2] );
            party = getParty( line[3], line[4] );
            candidate = new Candidate( div, party,
                            Integer.parseInt( line[5] ), line[6], line[7] );
            //Add candidate to party and division and candidates
            //Need to use listIterator here for order!
            insertCandidate( candidate );
            div.addCandidate( candidate );
            party.addCandidate( candidate );
        }
        new MergeSortList<State>(states).sort();
        new MergeSortList<Party>(parties).sort();
        //System.out.println("Last candidate read: " + candidate.id());
    }

    /*************************************************************************
    *   Name: insertCandidate
    *   Purpose: insert the candidate at the required id
    *   Imports: Candidate
    *   Comments: Should change to use listIterator methods
    *************************************************************************/
    private void insertCandidate( Candidate candidate )
    {
        try
        {
            candidateHT.put( ""+candidate.id(), candidate );
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
            if ( tempC == null )//Case empty list - working
            {
                iter.add( candidate );
            }
            else if ( tempC.id() > candidate.id() )//Case of id smaller
                //Don't insert a duplicate id
            {
                iter.previous();
                iter.add( candidate );
            }
            else if ( tempC.id() < candidate.id() )//Case of id larger
            {
                iter.add( candidate );
            }
        }
        catch ( IllegalArgumentException e )
        {
            System.out.println( "Duplicate candidate, skipping" );
        }
    }

    /*************************************************************************
    *   Name: getDiv
    *   Purpose: Get the division if exists or create it
    *   Imports: Strings: State, DivisionID, Division Name
    *   Exports: Division object
    *   Comments: This could be sped up by using a hashtable for search
    *************************************************************************/
    private Division getDiv( String state, String sid, String name )
    {
        int id = Integer.parseInt( sid );
        boolean found = false;
        Division div = null;
        Iterator<Division> iter = divisions.iterator();
        while ( !found && iter.hasNext() )
        {
            div = iter.next();
            found = ( div.id() == id );
        }
        if ( !found )
        {
            div = new Division( getState( state ), id, name );
            insertDivision( div );
            getState( state ).addDivision( div );
        }

        return div;
    }
    /*************************************************************************
    *   Name: insertDivision
    *   Purpose: Insert a division into a sorted position
    *   Imports: Division to add
    *   Exports: none
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
        else if ( tempC.id() < division.id() )//Case of id larger
        {
            iter.add( division );
        }
    }
    /*************************************************************************
    *   Name: getState
    *   Purpose: Get the state if it exists, or create it
    *   Imports: String abbreviation
    *   Exports: State
    *************************************************************************/
    private State getState( String state )
    {
        boolean found = false;
        State st = null;
        Iterator<State> iter = states.iterator();
        while ( !found && iter.hasNext() )
        {
            st = iter.next();
            found = ( st.name().equals(state) );
        }
        if ( !found )
        {
            st = new State( state );
            states.insertLast( st );
        }
        return st;
    }

    /*************************************************************************
    *   Name: getParty
    *   Purpose: Return the party if it exists, or create it
    *   Imports: Strings; Abbreviation, Name
    *   Exports: State
    *************************************************************************/
    private Party getParty( String abbr, String name )
    {
        boolean found = false;
        Party party = null;
        Iterator<Party> iter = parties.iterator();
        while ( !found && iter.hasNext() )
        {
            party = iter.next();
            found = ( party.abbr().equals(abbr) );
        }
        if ( !found )
        {
            party = new Party( abbr, name );
            parties.insertLast( party );
        }
        return party;
    }

    /*************************************************************************
    *   Name: addVotes
    *   Purpose: iterate through a queue to add votes to candidates
    *   Imports: DSAQueue<String[]>, queue of separated lines.
    *   Exports: none
    *************************************************************************/
    public void addVotes( DSAQueue<String[]> queue )
    {
        String[] line;
        Candidate cand;
        int candID, votes;
        //Discard header lines
        queue.dequeue();
        queue.dequeue();

        while ( !queue.isEmpty() )
        {
            line = queue.dequeue();
            //Check for informal votes line
            candID = Integer.parseInt( line[5] );
            votes = Integer.parseInt( line[13] );
            if ( candID != 999 )
            {
                cand = (Candidate)(candidateHT.get( line[5] ) );
                cand.addVotes( votes );
            }
            else
            {
                getDiv( line[0], line[1], line[2] ).addVotes( votes );
            }
        }
    }

    /*************************************************************************
    *   Name: getMarginal
    *   Purpose: Calculate all marginal seats for a party
    *   Imports: Party, margin
    *   Exports: Linked list of marginal divisions
    *   Comments: margin is set on each division as it is calculated
    *************************************************************************/
    public DSALinkedList<Division> getMarginal( Party party, double margin )
    {
        ready();
        Iterator<Division> iter = divisions.iterator();
        Division div;
        Candidate cand;
        double votesAgainst;
        double votesFor;
        DSALinkedList<Division> outList = new DSALinkedList<Division>();
        ListIterator<Division> lIter = outList.listIterator( 0 );
        double oldMargin = -100.0;

        while ( iter.hasNext() )
        {
            div = iter.next();
            votesFor = votesAgainst = 0.0;
            Iterator<Candidate> iterCand = div.candidates().iterator();
            while ( iterCand.hasNext() )
            {
                cand = iterCand.next();
                if ( cand.party().equals( party ) )
                {
                    votesFor += (double)cand.votes();
                }
                else
                {
                    votesAgainst += (double)cand.votes();
                }
            }
            div.margin = Math.abs( votesFor / ( votesFor + votesAgainst ) *
                                                            100.0 - 50.0 );
            div.margin = (double)((int)( div.margin * 100.0 + 0.5 ) / 100.0);
            if ( div.margin < margin )
            {//Insert in correct location
                outList.insertLast( div );
            }
        }
        new MergeSortList<Division>( outList ).sort();
        return outList;
    }

    /*************************************************************************
    *   Name: printCandidates
    *   Purpose: print all candidates to screen
    *   Imports: none   |   Exports: none
    *************************************************************************/
    public void printCandidates()
    {
        Iterator<Candidate> iter = candidates.iterator();
        Candidate cand = null;
        while ( iter.hasNext() )
        {
            cand = iter.next();
            System.out.println( cand.division().state().name() + ", " +
                cand.surname() + " " + cand.givenName() +
                ", " + cand.id() + ", " + cand.party().abbr() + ", " +
                cand.division().name() + ". " + cand.votes() + " votes." );
        }
    }

    /*************************************************************************
    *   Name: lookupState
    *   Purpose: return a state from search
    *   Imports: String, state to search for
    *   Exports: State, state matching string
    *************************************************************************/
    public State lookupState( String search )
    {
        Iterator<State> iter = states.iterator();
        boolean found = false;
        State state = null;
        State closest = null;
        while ( iter.hasNext() && !found )
        {
            state = iter.next();
            if ( state.name().contains( search.toUpperCase() ) )
            {
                closest = state;
                if ( closest.name().equals( search ) )
                {
                    found = true;
                }
            }
        }
        return closest;
    }

    /*************************************************************************
    *   Name: lookupParty
    *   Purpose: return a party from search
    *   Imports: String, party to search for
    *   Exports: Party, party matching string
    *************************************************************************/
    public Party lookupParty( String search )
    {
        Iterator<Party> iter = parties.iterator();
        boolean found = false;
        Party party = null;
        Party closest = null;
        while ( iter.hasNext() && !found )
        {
            party = iter.next();
            if ( party.name().toUpperCase().contains( search.toUpperCase() ) )
            {
                closest = party;
                if ( closest.name().equals( search ) )
                {
                    found = true;
                }
            }
            else if ( party.abbr().contains( search.toUpperCase() ) )
            {
                closest = party;
                if ( closest.abbr().equals( search ) )
                {
                    found = true;
                }
            }
        }
        return closest;
    }

    /*************************************************************************
    *   Name: lookupDivision
    *   Purpose: return a division from search
    *   Imports: String, division to search for
    *   Exports: Division, division matching string
    *************************************************************************/
    public Division lookupDivision( String search )
    {
        Iterator<Division> iter = divisions.iterator();
        boolean found = false;
        Division division = null;
        Division closest = null;
        while ( iter.hasNext() && !found )
        {
            division = iter.next();
            if ( division.name().toUpperCase().contains(
                                                    search.toUpperCase() ) )
            {
                closest = division;
                if ( closest.name().equals( search ) )
                {
                    found = true;
                }
            }
            else
            {
                try
                {
                    if ( division.id() == Integer.parseInt( search ) )
                    {
                        closest = division;
                        found = true;
                    }
                }
                catch ( NumberFormatException e ) {}
            }
        }
        return closest;
    }

    /*************************************************************************
    *   Name: voteThread
    *   Purpose: Spawn a thread in the background to add votes
    *   Imports: String filename, String regex to split by
    *   Exports: none
    *************************************************************************/
    public void voteThread( String fileName, String regex )
    {
        VoteThread t = this.new VoteThread( this, fileName, regex );
        threads.insertLast( t );
        t.start();
    }

    /*************************************************************************
    *   Name: ready
    *   Purpose: wait for threads to complete before using data that relies on
    *               the threads to complete
    *   Imports: none
    *   Exports: none
    *************************************************************************/
    public void ready()
    {
        Iterator<VoteThread> iter = threads.iterator();
        VoteThread t;
        while ( iter.hasNext() )
        {
            try
            {
                t = iter.next();
                if ( t.isAlive() )
                {
                    t.join();
                }
            }
            catch ( InterruptedException e ) {}
            iter.remove();
        }
    }

    /*************************************************************************
    *   Name: VoteThread
    *   Purpose: Multithread the vote reading and addition
    *************************************************************************/
    class VoteThread extends Thread
    {
        ElectoralData data;
        String fileN, regex;

        //Constructor
        VoteThread( ElectoralData inData, String inFN, String inRegex )
        {
            data = inData;
            fileN = inFN;
            regex = inRegex;
        }

        //Purpose: run the thread
        public void run()
        {
            try
            {
                data.addVotes( FileIO.readDSV( fileN, regex ) );
                //System.out.println( "File successfully read in");
            }
            catch ( IOException e )
            {
                System.out.println( "Problem reading in files: " +
                                                        e.getMessage() );
            }
        }
    }

}
