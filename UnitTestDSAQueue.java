/*****************************************************************************
* File:      UnitTestDSAQueue.java                                           *
* Author:    Sean Ashton * Student ID: 14866636                              *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Unit Test for DSAQueue Class                                    *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   15/08/2018 * Last Modified: 24/08/2018                          *
*****************************************************************************/
public class UnitTestDSAQueue
{
    public static void main ( String [] Args )
    {
        int iNumPassed = 0;
        int iNumTests = 0;
        DSAQueue<Object> queue;
        Integer[] testArr = new Integer[6];
        for (int i = 0; i < testArr.length; i++)
        {
            testArr[i] = i;
        }

        // Test with normal conditions (shouldn't throw exception)
        System.out.println( "\n" );
        System.out.println( "Testing Normal Conditions - Constructor" );
        System.out.println( "=======================================" );
        try
        {
            iNumTests++;
            System.out.print( "Testing creation of DSAQueue: " );

            queue = new DSAQueue<Object>();
            iNumPassed++;
            System.out.println( "Passed" );

            iNumTests++;
            System.out.print( "Testing isEmpty() while empty: " );

            if ( queue.isEmpty() )
            {
                iNumPassed++;
                System.out.println( "Passed" );
            }
            else
            {
                System.out.println( "FAILED" );
            }

            iNumTests++;
            System.out.print( "Testing adding size number of elements: " );
            try
            {
                for (int i = 0; i < testArr.length; i++)
                {
                    queue.enqueue(testArr[i]);
                }
                iNumPassed++;
                System.out.println( "Passed" );
            }
            catch (Exception e)
            {
                System.out.println( "FAILED" );
            }

            iNumTests++;
            System.out.print( "Testing isEmpty() while full: " );

            if ( !queue.isEmpty() )
            {
                iNumPassed++;
                System.out.println( "Passed" );
            }
            else
            {
                System.out.println( "FAILED" );
            }

            iNumTests++;
            System.out.print( "Testing peek(): " );

            if ( queue.peek() == testArr[0] )
            {
                iNumPassed++;
                System.out.println( "Passed" );
            }
            else
            {
                System.out.println( "FAILED" );
            }

            iNumTests++;
            System.out.print( "Testing dequeue(): " );

            try
            {
                int test;
                for (int ii = 0; ii < testArr.length; ii++)
                {
                    test = (int)queue.dequeue();
                    if (testArr[ii] != test)
                    {
                        throw new IllegalArgumentException( "FAILED" );
                    }
                }
                iNumPassed++;
                System.out.println( "Passed" );
            }
            catch (Exception e)
            {
                System.out.println( "FAILED: " + e.getMessage() );
            }

            iNumTests++;
            System.out.print( "Testing dequeue() while empty: " );

            try
            {
                queue.dequeue();
                System.out.println( "FAILED" );
            }
            catch (Exception e)
            {
                System.out.println( "Passed" );
            }

        }
        catch (Exception e)
        {
            System.out.println( "Uncaught FAILED" + e.getMessage() );
        }
    }
}
