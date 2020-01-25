/*****************************************************************************
* File:      Menu.java                                                       *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Display the menu and call appropriate code                      *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  ElectoralData                                                   *
* Created:   19/10/2018 * Last Modified: 26/10/2018                          *
*****************************************************************************/
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;

class Menu
{
    ElectoralData data;
    public static String[] options = { "Primary", "Secondary", "Tertiary" };
    public static String[] orderLabels = { "Division - Reversed",
        "Party - Reversed", "State - Reversed", "Surname - Reversed", "",
            "Surname", "State", "Party", "Division" };
    private Scanner sc;

    //Constructor
    public Menu()
    {
        sc = new Scanner( System.in );
    }
    /*************************************************************************
    * Name: menu
    * Purpose: Display the main menu and get user selection
    * Imports: ElectoralData
    * Exports: none
    *************************************************************************/
    public void menu( ElectoralData inData )
    {
        data = inData;
        int choice = -1;
        do
        {
            choice = -1;
            System.out.print("\n(1)\t- List Nominees\n" +
            "(2)\t- Nominee Search\n(3)\t- List by Margin\n" +
            "(4)\t- Itinerary by Margin\n(0)\t- Quit\nChoice:>");

            choice = readInt();

            switch (choice)
            {
                case 1:
                listNominees();
                break;

                case 2:
                nomineeSearch();
                break;

                case 3:
                listByMargin();
                break;

                case 4:
                itineraryByMargin();
                break;
            }
        } while (choice != 0);
    }

    /*************************************************************************
    * Name: listNominees
    * Purpose: Second level menu to configure Nominee listing
    * Imports: none
    * Exports: none
    *************************************************************************/
    private void listNominees()
    {
        Filter filters = new Filter( data );
        //order by priority: Surname, State, Party, Division.
        //lower values mean higher priority (sort by 1 first, 2 second...)
        int[] order = { 1, 2, 3, 4 };
        int choice = -1;
        do
        {
            choice = -1;
            System.out.print("\n(1)\t- Set Filters\n(2)\t- Set Order\n" +
                "(3)\t- List Nominees\n(0)\t- Return to main menu\nChoice:>");

            choice = readInt();

            switch (choice)
            {
                case 1:
                filters = setFilters(filters);
                break;

                case 2:
                order = setOrder(order);
                break;

                case 3:
                displayNominees(filters, order);
                break;
            }
        } while (choice != 0);
    }

    /*************************************************************************
    * Name: setFilters
    * Purpose: Set the filters object
    * Imports: filters, object that represents filters
    * Exports: filters
    *************************************************************************/
    private Filter setFilters( Filter filters )
    {
        int choice = -1;
        do
        {
            choice = -1;
            System.out.print(
            "\n(1)\t- Show filters\n(2)\t- Clear filters\n" +
                "(3)\t- Add State\n(4)\t- Remove State\n" +
                    "(5)\t- Add Party\n(6)\t- Remove Party\n" +
            "(7)\t- Add Division\n(8)\t- Remove Division\n(0)\t- Back\n" +
                "Choice:>");

            choice = readInt();

            switch (choice)
            {
                case 1:
                filters.print();
                break;

                case 2:
                filters.clear();
                System.out.println("Filters Cleared");
                break;

                case 3:
                System.out.print("State:> ");
                System.out.println(filters.addState(data.lookupState(
                                                                sc.next())));
                break;

                case 4:
                System.out.print("State:> ");
                System.out.println(filters.delState(data.lookupState(
                                                                sc.next())));
                break;

                case 5:
                System.out.print("Party:> ");
                System.out.println(filters.addParty(data.lookupParty(
                                                                sc.next())));
                break;

                case 6:
                System.out.print("Party:> ");
                System.out.println(filters.delParty(data.lookupParty(
                                                                sc.next())));
                break;

                case 7:
                System.out.print("Division:> ");
                System.out.println(filters.addDivision(data.lookupDivision(
                                                                sc.next())));
                break;

                case 8:
                System.out.print("Division:> ");
                System.out.println(filters.delDivision(data.lookupDivision(
                                                                sc.next())));
                break;
            }
        } while (choice != 0);
        return filters;
    }

    /*************************************************************************
    * Name: setOrder
    * Purpose: set the order for printing the Nominee list
    * Imports: order, an integer array representing the order to sort by
    * Exports: order, (1:Surname, 2:State, 3:Party, 4:Division)
    *************************************************************************/
    private int[] setOrder( int[] order )
    {
        int choice = -1;
        do
        {
            choice = -1;
            System.out.print("\n(1)\t- Show Order\n(2)\t- Reset Order\n" +
            "(3)\t- Set Primary Order\n(4)\t- Set Secondary Order\n" +
            "(5)\t- Set Tertiary Order\n(0)\t- Back\nChoice:>");

            choice = readInt();

            switch (choice)
            {
                case 1:
                System.out.println("Current order:\n[1] - " +
                    orderLabels[4+order[0]] + "\n[2] - " +
                    orderLabels[4+order[1]] + "\n[3] - " +
                    orderLabels[4+order[2]] + "\n[4] - " +
                    orderLabels[4+order[3]] );
                break;

                case 2:
                order[0] = 1;
                order[1] = 2;
                order[2] = 3;
                order[3] = 4;
                break;

                case 3:
                order = getOrderFromUser( 0, order );
                break;

                case 4:
                order = getOrderFromUser( 1, order );
                break;

                case 5:
                order = getOrderFromUser( 2, order);
                break;
            }
        } while ( choice != 0 );
        return order;
    }

    /*************************************************************************
    *   Name:   getOrderFromUser
    *   Purpose: get an ordering datum from the user
    *   Imports: String, order description to ask about; order
    *   Export: order
    *************************************************************************/
    private int[] getOrderFromUser( int place, int[] order )
    {
        int choice = -1;
        int swap = 0;
        String desc = options[place];
        do
        {
            System.out.print( desc + " order to sort by:\n" +
            "(1)\t- Surname (A-Z)\n(2)\t- State (A-Z)\n" +
            "(3)\t- Party (A-Z)\n(4)\t- Division (Ascending)\n" +
            "(5)\t- Surname (Z-A)\n(6)\t- State (Z-A)\n(7)\t- Party (Z-A)\n" +
            "(8)\t- Division (Descending)\n(0)\t- Back\nChoice:>" );

            choice = readInt();

            if ( choice > 0 && choice <= 8 )
            {
                //New value to put in order[place]
                swap = ( ( choice - 1 ) % 4 ) + 1;
                if ( choice >= 5 )
                {
                    swap *= -1;
                }
                //swap order places
                int oldVal = order[place];
                if ( Math.abs( oldVal ) != Math.abs( swap ) )
                {
                    //Find |swap| in order and put oldval there
                    for ( int ii = 0; ii < order.length; ii++ )
                    {
                        if ( Math.abs( order[ii] ) == Math.abs( swap ) )
                        {
                            order[ii] = oldVal;
                            order[place] = swap;
                        }
                    }
                }
                else
                {
                    order[place] = swap;
                }
                choice = 0;
            }
        } while ( choice != 0 );
        return order;
    }

    /*************************************************************************
    *   Name: displayNominees
    *   Purpose: Display nominees with set filter object
    *   Imports: Filter, filter object to use; order, order to use
    *   Exports: none
    *************************************************************************/
    private void displayNominees( Filter filters, int[] order )
    {
        filters.setOrder( order );
        filters.process();
        DSAQueue<String[]> queue = filters.candidateQueue();
        while ( !queue.isEmpty() )
        {
            System.out.println( formatCandidate( queue.dequeue() ) );
        }
        saveQuery( filters.candidateQueue(), "List of nominees" );
    }

    /*************************************************************************
    *   Name: saveQuery
    *   Purpose: Ask the user if they would like to save the last report
    *   Imports: DSAQueue<String[]>, last printed queue
    *   Exports: to file
    *   Reference: https://stackoverflow.com/questions/833768/java-code-for-getting-current-time
    *************************************************************************/
    private void saveQuery( DSAQueue<String[]> list, String type )
    {
        int choice = -1;
        list.unqueue( new String[] {type} );
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
        String fileName = type + sdf.format(
                                Calendar.getInstance().getTime() ) + ".txt";
        do
        {
            choice = -1;
            System.out.println("Would you like to save this report?");
            System.out.print("(1) Yes - Default name (" + fileName +")\n" +
                        "(2) Yes - Custom name" + "\n(3) No\nChoice:>");
            choice = readInt();
            switch (choice)
            {
                case 2:
                System.out.print( "File name for export: " );
                fileName = sc.nextLine();

                case 1:
                System.out.println( "Saving query as " + fileName );
                try
                {
                    FileIO.writeDSV( fileName, list, ',' );
                    choice = 3;
                }
                catch ( IOException e )
                {
                    System.out.println( "Error with saving query: " +
                                                            e.getMessage() );
                }
                break;
           }
        }
        while ( choice != 3 );//Prompt again on error.
    }

    /*************************************************************************
    * Name: nomineeSearch
    * Purpose: Search for nominees by substring, with filters.
    * Imports: none
    * Exports: none
    *************************************************************************/
    private void nomineeSearch()
    {
        String search = "";
        int choice = -1;
        Filter filters = new Filter( data );
        do
        {
            choice = -1;
            System.out.print("(1)\t- Set Filters\n" +
            "(2)\t- Search\n(0)\t- Back\nChoice:>");

            choice = readInt();

            switch (choice)
            {
                case 1:
                filters = setFilters(filters);
                break;

                case 2:
                System.out.println("Enter search string:");
                search = sc.next();
                doSearch( search, filters );
                break;
            }
        } while (choice != 0);
    }

    /*************************************************************************
    *   Name: doSearch
    *   Purpose: Execute the search of a nominee
    *   Imports: String to search, Filter to filter by
    *   Exports: none
    *************************************************************************/
    private void doSearch( String search, Filter filter )
    {
        filter.setOrder( new int[] {1,2,3,4} );
        filter.process();
        DSAQueue<String[]> cands = filter.candidateQueue();
        Iterator<String[]> iter = cands.iterator();
        String[] cand;
        while ( iter.hasNext() )
        {
            cand = iter.next();
            if ( !cand[0].matches( ".*" + search + ".*" ) )
            {
                iter.remove();
            }
            else
            {
                System.out.println( formatCandidate( cand ) );
            }
        }
        saveQuery( cands, "Nominee Search" );
    }

    /*************************************************************************
    *   Name: formatCandidate
    *   Purpose: format an array of candidate information in a nice way
    *   Imports: String[]
    *   Exports: String
    *************************************************************************/
    private String formatCandidate( String[] line )
    {
        return ( "Name: " + line[0] + ",\t" + line[1] + ",\tParty: " +
                    line[2] + ",\t State: " + line[6] + ",\tDivision: " +
                    line[3] + ",\tID: " + line[4] + ",\tVotes for: " +
                    line[5] );
    }

    /*************************************************************************
    * Name: listByMargin
    * Purpose: Select a party and list marginal seats within a threshold
    * Imports: boolean to indicate this call is for itinerayByMargin
    * Exports: none
    *************************************************************************/
    private void listByMargin()
    { listByMargin( false ); }

    private DSALinkedList<Division> listByMargin( boolean itinerary )
    {
        DSALinkedList<Division> divs = null;
        Party party = null;
        int choice = -1;
        do
        {
            System.out.println("Enter the party to get marginal seats for:");
            party = data.lookupParty(sc.nextLine());
            if (party == null)
            {
                System.out.println("Unable to find party");
            }
        } while (party == null);

        do
        {
            double margin = 6;
            System.out.println("(1)\t- View with default margin (%6)\n" +
            "(2)\t- View with custom margin\n(0)\t-Back");
            choice = readInt();
            switch (choice)
            {
                case 2:
                do
                {
                    System.out.print("Margin:>");
                    try
                    {
                        margin = sc.nextDouble();
                    }
                    catch (InputMismatchException e)
                    {
                        margin = -1.0;
                    }
                } while (margin < 0.0);

                case 1:
                if ( !itinerary )
                {
                    printByMargin(party, margin);
                }
                else
                {
                    divs = data.getMarginal( party, margin );
                }
                break;
            }
            choice = 0;
        } while (choice != 0);
        return divs;
    }

    /*************************************************************************
    * Name: printByMargin
    * Purpose: Display the marginal seats of a party by margin
    * Imports: party, margin
    * Exports: none
    *************************************************************************/
    private void printByMargin( Party party, double margin )
    {
        DSALinkedList<Division> divs = data.getMarginal( party, margin );
        Iterator<Division> iter = divs.iterator();
        Division div;
        System.out.println( "Marginal Seats for " + party.abbr() );
        while ( iter.hasNext() )
        {
            div = iter.next();
            System.out.println( "Division: " + div.name() + ",\tID: " +
                    div.id() + ",\tState: " + div.state().name() +
                                        ",\tMargin: " + div.margin );
        }
        //Do more stuff here
        saveQuery( divListToQueue( divs ), "Margin seats for " +
                                                            party.abbr() );
    }

    /*************************************************************************
    *   Name: divListToQueue
    *   Purpose: convert a list of division to a String[] queue for saving
    *   Imports: DSALinkedList<Division>
    *   Exports: DSAQueue<String[]>
    *************************************************************************/
    private DSAQueue<String[]> divListToQueue( DSALinkedList<Division> list )
    {
        DSAQueue<String[]> output = new DSAQueue<String[]>();
        Iterator<Division> iter = list.iterator();
        Division div;
        while ( iter.hasNext() )
        {
            String[] line = new String[4];
            div = iter.next();
            line[0] = ""+div.id();
            line[1] = div.name();
            line[2] = div.state().name();
            line[3] = ""+div.margin;
            output.enqueue( line );
        }
        return output;
    }


    /*************************************************************************
    *   Name: itineraryByMargin()
    *   Purpose: Display the menu for getting an itinerary by margin
    *   Imports: none
    *   Exports: none
    *************************************************************************/
    private void itineraryByMargin()
    {
        DSALinkedList<Division> divs = listByMargin( true );
        DSALinkedList<Division> output = new DSALinkedList<Division>();
        Iterator<State> sIter = data.states().iterator();
        Iterator<Division> cIter;
        Division div;

        while ( sIter.hasNext() )
        {
            cIter = divs.iterator();
            State state = sIter.next();
            while ( cIter.hasNext() )
            {
                div = cIter.next();
                if ( div.state().equals( state ) )
                {
                    output.insertFirst( div );
                    System.out.println( "Division: " + div.name() +
                        ",\tID: " + div.id() + ",\tState: " + 
                        div.state().name() + ",\tMargin: " + div.margin );
                }
            }
        }
        saveQuery( divListToQueue( output ), "Itinerary" );
    }

    /*************************************************************************
    * Name: readInt()
    * Purpose: read an integer or return -1
    * Imports: none
    * Exports: integer read
    *************************************************************************/
    private int readInt()
    {
        int choice = -1;
        try
        {
            choice = sc.nextInt();
            sc.nextLine(); //Clear line for next use
        }
        catch (InputMismatchException e)
        {
            sc.nextLine();
            choice = -1;
        }
        return choice;
    }
}
