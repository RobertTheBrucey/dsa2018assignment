/*****************************************************************************
* File:      UnitTestDSAHashTable.java                                       *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Template file for source code files                             *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   21/10/2018 * Last Modified: 21/10/2018                          *
*****************************************************************************/
import java.io.*;
import java.util.*;
class UnitTestDSAHashTable
{
    public static void main( String [] args )
    {
        int count = 3000;
        int cap = 1000;
        DSAQueue<String[]> inList = new DSAQueue<String[]>();
        if (args.length == 1)
        {
            try
            {
                inList = FileIO.readDSV(args[0], ",");
            }
            catch (IOException e)
            {
                System.out.println("Unable to read from file");
            }
        }
        else if (args.length == 2)
        {
            cap = Integer.parseInt(args[1]);
            count = Integer.parseInt(args[0]);
        }
        System.out.print("Testing DSAHeap creation: ");
        DSAHashTable table = null;
        System.out.print("Testing creation of DSAHashTable: ");

        try
        {
            table = new DSAHashTable(cap);
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED; " + e.getMessage());
        }

        System.out.print("Testing add of " + count +" elements: ");
        int jj = 0;
        try
        {
            for (jj = 0; jj < count; jj++)
            {
                table.put("" + jj, (Integer)jj);
            }
            System.out.println("Passed");
        }
        catch (Exception e)
        {
            System.out.println("FAILED at " + jj + "; " + e.getMessage());
        }

        System.out.print("Testing getting " + count + " elements: ");
        try
        {
            boolean success = true;
            for (jj = 0; jj < count; jj++)
            {
                if (!table.get("" + jj).equals((Integer)jj))
                {
                    success = false;
                }
            }
            if (success)
            {
                System.out.println("Passed");
            }
            else
            {
                System.out.println("FAILED - Logic");
            }
        }
        catch (Exception e)
        {
            System.out.println("FAILED at " + jj + "; " + e.getMessage());
        }

        System.out.print("Testing remove all (" + count + ") elements: ");
        try
        {
            for (jj = 0; jj < count; jj++)
            {
                table.remove("" + jj);
            }
            System.out.println("Passed, checking with containsKey()");
        }
        catch (Exception e)
        {
            System.out.println("FAILED at " + jj + "; " + e.getMessage());
        }

        System.out.print("Testing containsKey: ");
        try
        {
            boolean success = true;
            table.put("Test", (Integer)100);
            if (!table.containsKey("Test"))
            {
                success = false;
            }
            else
            {
                for (int ii = 0; ii < count; ii++)
                {
                    if (table.containsKey("" + ii))
                    {
                        success = false;
                    }
                }
            }
            if (success)
            {
                System.out.println("Passed");
            }
            else
            {
                System.out.println("FAILED - Logic");
            }
        }
        catch (Exception e)
        {
            System.out.println("FAILED; " + e.getMessage());
        }

        if (args.length == 1)
        {
            System.out.println("Testing file input");
            String[] entry = null;
            Iterator<String[]> iter = inList.iterator();
            while (iter.hasNext())
            {
                try
                {
                    entry = iter.next();
                    table.put(entry[0], entry[1]);
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Duplicate entry, skipping");
                }
            }

            System.out.println("File read, checking for all keys");
            while (!inList.isEmpty())
            {
                entry = inList.dequeue();
                if (!table.get(entry[0]).equals(entry[1]))
                {
                    System.out.println("Missing: " + entry[1] +
                                ", Collided with: " + table.get(entry[0]));
                }
            }
        }

        System.out.println("Writing out names to output.csv");
        DSAQueue<Object[]> inQueue = table.getHashTable();
        DSAQueue<String[]> outQueue = new DSAQueue<String[]>();
        while (!inQueue.isEmpty())
        {
            String[] output = new String[2];
            Object[] input = new Object[2];
            input = inQueue.dequeue();
            output[0] = (input[0]).toString();
            output[1] = input[1].toString();
            outQueue.enqueue(output);
        }
        try
        {
            FileIO.writeDSV("output.csv", outQueue, ',');
        }
        catch (IOException e)
        {
            System.out.println("Problem writing file");
        }
    }
}
