/*****************************************************************************
* File:      FileIO.java                                                     *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   General file io class                                           *
* Reference: None.                                                           *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   20/09/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/
import java.io.*;
import java.nio.file.*;
import java.nio.file.spi.*;

class FileIO
{
    /*************************************************************************
    *   Name: readDSV
    *   Purpose: read in a file with a specified delimiter
    *   Imports: String, String; filename and delimiters
    *   Exports: DSAQueue; result of reading in file
    *************************************************************************/
    public static DSAQueue<String[]> readDSV( String filename, String delims )
                                                            throws IOException
    {
        String line;
        RandomAccessFile fileRdr = null;
        DSAQueue<String[]> queue = new DSAQueue<String[]>();
        fileRdr = new RandomAccessFile( filename, "r" );
        line = fileRdr.readLine();
        while ( line != null )
        {
            queue.enqueue( line.split( delims ) );
            line = fileRdr.readLine();
        }
        return queue;
    }

    /*************************************************************************
    *   Name: writeDSV
    *   Purpose: write a queue out as a delimiter separated file
    *   Imports: String, DSAQueue, char; filename, queue to write, delimiter
    *   Exports: none
    *************************************************************************/
    public static void writeDSV( String filename, DSAQueue<String[]> queue,
                                            char delim ) throws IOException
    {
        PrintWriter writer;
        String[] lineArr;
        String line;
        writer = new PrintWriter( filename );
        while ( !queue.isEmpty() )
        {
            lineArr = queue.dequeue();
            line = lineArr[0];
            for ( int ii = 1; ii < lineArr.length; ii++ )
            {
                line += delim + lineArr[ii];
            }
            writer.println( line );
        }
        writer.close();
    }
}
