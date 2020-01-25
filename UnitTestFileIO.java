/*****************************************************************************
* File:      UnitTestFileIO.java                                             *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   File IO test harness                                            *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   01/08/2018 * Last Modified: 04/10/2018                          *
*****************************************************************************/
import java.io.IOException;
class UnitTestFileIO
{
    public static void main( String [] args )
    {
        int numTests = 0;
        int numPassed = 0;
        DSAQueue queue = new DSAQueue<String[]>();
        DSAQueue queue2;
        String[] line;

        System.out.print("Testing writing out to FileIOTest.txt: ");
        for (int ii = 0; ii < 15; ii++)
        {
            line = new String[15];
            for (int jj = 0; jj < line.length; jj++)
            {
                line[jj] = jj + " ";
            }
            queue.enqueue(line);
        }

        try
        {
            FileIO.writeDSV( "FileIOTest.txt", queue, ',' );
            System.out.println("Passed");
        }
        catch (IOException e)
        {
            System.out.println("FAILED");
        }

        System.out.print("Testing reading from FileIOTest.txt: ");
        try
        {
            queue2 = FileIO.readDSV( "FileIOTest.txt", "," );
            System.out.println("Passed");
            while (!queue2.isEmpty())
            {
                line = (String[])queue2.dequeue();
                for (int ii = 0; ii < line.length; ii++)
                {
                    System.out.print(line[ii]+",");
                }
                System.out.println("");
            }
        }
        catch (IOException e)
        {
            System.out.println("FAILED");
        }
    }
}

