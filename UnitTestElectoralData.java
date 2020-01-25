/*****************************************************************************
* File:      UnitTestElectoralData.java                                      *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Test the functionality of ElectoralData                         *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   28/10/2018 * Last Modified: 28/10/2018                          *
*****************************************************************************/
import java.util.*;

class UnitTestElectoralData
{
    public static void main( String [] args )
    {
        ElectoralData data = null;
        System.out.print("Testing Construction: ");
        try
        {
            data = new ElectoralData();
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }

        System.out.print("Testing addCandidates: ");
        try
        {
            DSAQueue<String[]> q = new DSAQueue<String[]>();
            String[] line;
            line = new String[] {"TES","1","Test1","TE","TestParty","12345",
            "TestSurname", "TestName", "Y","Y"};
            q.enqueue(line);
            q.enqueue(line);
            q.enqueue(line);
            line = new String[] {"TES","1","Test1","T2","TestPart2","23456",
            "Test2Sur","Test2Nam", "N", "N"};
            q.enqueue(line);
            data.addCandidates(q);
            System.out.println("Passed");
            data.printCandidates();
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }

        System.out.print("Testing addVotes: ");
        try
        {
            DSAQueue<String[]> q = new DSAQueue<String[]>();
            String[] line;
            line = new String[] {"TES","1","Test1","123","TestPoll","12345",
            "TestSurname", "TestName", "1", "Y", "Y", "TE", "TestParty",
            "2314", "3.4" };
            q.enqueue(line);
            q.enqueue(line);
            q.enqueue(line);
            line = new String[] {"TES","1","Test1","123","TestPoll","23456",
            "Test2Sur","Test2Nam", "2", "N", "N", "T2", "TestPart2",
            "2310", "45.4" };
            q.enqueue(line);
            data.addVotes(q);
            System.out.println("Passed");
            data.printCandidates();
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }

        System.out.println("Testing lookupParty");
        try
        {
            System.out.println(data.lookupParty( "TE" ).name());
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }

        System.out.println("Testing lookupDivision()");
        try
        {
            System.out.println(data.lookupDivision( "Test" ).name());
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }

        System.out.println("Testing lookupState");
        try
        {
            System.out.println(data.lookupState( "TES" ).name());
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }

        System.out.println("Testing getMarginal: ");
        try
        {
            Iterator<Division> iter = data.getMarginal(
                                data.lookupParty("TE"), 50.0 ).iterator();
            while (iter.hasNext())
            {
                System.out.println(iter.next().name());
            }
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }
}

