/*****************************************************************************
* File:      Candidate.java                                                  *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Container class for Candidate information                       *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   26/10/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/

class Candidate implements Comparable<Candidate>
{
    //Class fields
    private String surname;
    private String givenName;
    private Party party;
    private Division division;
    private int id;
    private int votes;

    /*Constructor, all values are not to be changed after construction, except
        for votes, so all other data much be entered. Empty candidate is not
            permitted.
        Compacting if else statements for readability, deviating from standard
        */
    public Candidate( Division inDiv, Party inParty, int inID, String inSurN,
                        String inGivenN )
    {
        if ( inDiv == null )
        {   throw new IllegalArgumentException( "Division is null" );     }
        division = inDiv;

        if ( inParty == null )
        {   throw new IllegalArgumentException( "Party is null" );    }
        party = inParty;

        if ( inID < 0 )
        {   throw new IllegalArgumentException( "ID must be positive" );  }
        id = inID;

        if ( inSurN == null )
        {   throw new IllegalArgumentException( "Surname is null" );  }
        surname = inSurN;

        if ( inGivenN == null )
        {
            throw new IllegalArgumentException(
                    "Given name is null. Use an empty String for no name" );
        }
        givenName = inGivenN;

        votes = 0;
    }

    //Accessors
    public Division division()
    {
        return division;
    }

    public Party party()
    {
        return party;
    }

    public int id()
    {
        return id;
    }

    public String surname()
    {
        return surname;
    }

    public String givenName()
    {
        return givenName;
    }

    public int votes()
    {
        return votes;
    }

    //Only mutator, add votes to this candidate
    /*************************************************************************
    *   Name: addVotes
    *   Purpose: add votes to this candidate
    *   Imports: int, votes to add
    *   Exports: none
    *************************************************************************/
    public void addVotes( int inVotes )
    {
        if ( inVotes < 0 )
        {   throw new IllegalArgumentException( "Votes must be positive" );  }

        votes += inVotes;
    }

    /*************************************************************************
    *   Name: equals
    *   Purpose: equals method
    *   Imports: Candidate; candidate to compare with
    *   Exports: boolean, whether this import is equal to this
    *************************************************************************/
    public boolean equals( Candidate cand )
    {
        boolean equal = false;
        if ( surname.equals( cand.surname() ) )
        {
            if ( givenName.equals( cand.givenName() ) )
            {
                if ( party.equals( cand.party() ) )
                {
                    if ( division.equals( cand.division() ) )
                    {
                        if ( id == cand.id() )
                        {
                            if ( votes == cand.votes() )
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
    *   Purpose: implementation of comparable
    *   Imports: Candidate, object to compare with
    *   Exports: int, comparison value
    *   Comments: This method is only used for surname ordering, this should
    *               be implemented with Comparators instead
    *************************************************************************/
    public int compareTo( Candidate cand )
    {
        return surname.compareTo( cand.surname() );
    }
}


