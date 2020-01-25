/*****************************************************************************
* File:      ElectoralMain.java                                              *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Template file for source code files                             *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  ElectoralData, DSAQueue, DSALinkedList, Menu,                   *
* Created:   23/10/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/
import java.io.File;
import java.io.IOException;

class ElectoralMain
{
    public static int fileCount = 9;
    public static String regex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public static void main( String [] args )
    {
        ElectoralData data = new ElectoralData();
        String dataFolder = "./ElectionData";
        System.out.println( "Welcome to Electoral Calculator 2018" );
        System.out.println(
                    "Attempting to read in data from " + dataFolder + ":" );
        //https://stackoverflow.com/questions/15482423/how-to-list-the-files-in-current-directory
        File curDir = new File( dataFolder );
        File[] filesList = curDir.listFiles();
        DSALinkedList<File> csvs = new DSALinkedList<File>();
        boolean[] filePresent = new boolean[fileCount];
        String[] lookup = { "Candidates", "ACT", "NSW", "NT", "QLD", "SA",
                                                        "TAS", "VIC", "WA"};
        /*  0: HouseCandidatesDownload-20499
            1: HouseState......ACT
            2: HouseState......NSW
            3: HouseState......NT
            4: HouseState......QLD
            5: HouseState......SA
            6: HouseState......TAS
            7: HouseState......VIC
            8: HouseState......WA
        */
        if ( filesList != null )
        {
            for ( File f : filesList )
            {
                if ( f.isFile() )
                {
                    if ( f.getName().contains( ".csv" ) );
                    {
                        for ( int ii = 0; ii < lookup.length; ii++ )
                        {
                            if ( f.getName().contains( lookup[ii] ) )
                            {
                                csvs.insertLast( f );
                                filePresent[ii] = true;
                                System.out.println( "File for " + lookup[ii] +
                                                                   " found." );
                                //Change lookup to full file name.
                                lookup[ii] = dataFolder + "/" + f.getName();
                            }
                        }
                    }
                }
            }
        }
        else
        {
            filePresent[0] = false;
        }

        if ( !filePresent[0] )
        {
            System.out.println(
            "Can't find candidates file, make sure it is Candidates.csv" );
        }
        else
        {
            //Send queue of lines to appropriate methods in ElectoralData
            //https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split
            //Reference for special regex to split sections enclosed in ""
            System.out.println( "Reading in files, please wait" );
            try
            {
                data.addCandidates( FileIO.readDSV( lookup[0], regex ) );
                System.out.println( "Candidates read in, reading votes" );
                for ( int ii = 1; ii < fileCount; ii++ )
                {
                    if ( filePresent[ii] )
                    {
                    //data.addVotes( FileIO.readDSV( lookup[ii], regex ) );
                        data.voteThread( lookup[ii], regex );
                    }
                }
            }
            catch ( IOException e )
            {
                System.out.println( "Problem reading in files: " +
                                                            e.getMessage() );
            }
        }

        if ( !data.candidates().isEmpty() )
        {
            //data.printCandidates();
            (new Menu()).menu( data );
        }
        else
        {
            System.out.println( "Make sure all data is in a folder called " +
                                "ElectionData under the current path" );
            System.out.println( "Insufficient data, exitting" );
        }
    }
}
