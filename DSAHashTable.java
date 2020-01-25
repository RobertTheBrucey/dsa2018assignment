/*****************************************************************************
* File:      DSAHashTable.java                                               *
* Author:    Sean Ashton * Student ID:                               *
* Unit:      COMP1002 Data Structures and Algorithms                         *
* Purpose:   Template file for source code files                             *
* Reference: Sean Ashton DSA Prac 8                                          *
* Comments:  This comment block is the maximum width of 78 characters.       *
* Requires:  Nothing. List dependencies here                                 *
* Created:   18/10/2018 * Last Modified: 29/10/2018                          *
*****************************************************************************/
import java.util.*;

public class DSAHashTable
{
    private DSAHashEntry[] m_hashTable;
    private int m_count;
    private static double resizeUpThresh = 0.8;
    private static double resizeDownThresh = 0.3;
    //Set a lower bound on size to minimise resizing on small data sets
    private static int minSize = 97;

    //Constructor - default
    public DSAHashTable()
    {
        this( minSize );
    }

    //Constructor - Alternative
    public DSAHashTable( int maxSize )
    {
        if (maxSize < minSize)
        {
            maxSize = minSize;
        }
        m_hashTable = new DSAHashEntry[getNextPrime(maxSize)];
        m_count = 0;
    }

    /*************************************************************************
    *   Name: put
    *   Purpose: insert key,value pair into table
    *   Imports: String, Object; key and value to insert
    *   Exports: none
    *************************************************************************/
    public void put( String key, Object value )
    {
        if (containsKey(key))
        {
            throw new IllegalArgumentException("Key already in table");
        }
        else
        {
            put(new DSAHashEntry(key, value), false);
        }
    }

    /*************************************************************************
    *   Name: put
    *   Purpose: insert an entry into the table, allowing for resizing
    *   Imports: DSAHashEntry, boolean, the entry to insert and whether
    *   Exports: none
    *************************************************************************/
    private void put( DSAHashEntry entry, boolean resizing )
    {
        boolean run = true;
        int index = 0;

        if (!containsKey(entry.key))
        {
            index = find(entry.key,true);
            if (m_hashTable[index] != null)
            {
                if (m_hashTable[index].state == 1)
                {
                    throw new IllegalArgumentException("Table is full!");
                }
            }
            m_hashTable[find(entry.key,true)] = entry;
            m_count++;
        }
        else
        {
            throw new IllegalArgumentException("Duplicate Entry");
        }
        if (!resizing)
        {
            if ((double)m_count / (double)m_hashTable.length > resizeUpThresh)
            {
                reSize(m_hashTable.length * 2);
            }
        }
    }

    /*************************************************************************
    *   Name: get
    *   Purpose: get a value from the table by key
    *   Imports: String, key to search for
    *   Exports: Object, value associated with key
    *************************************************************************/
    public Object get( String key )
    {
        DSAHashEntry entry = null;
        if (containsKey(key))
        {
            entry = m_hashTable[find(key)];
        }
        else
        {
            throw new IllegalArgumentException("Key is not in table");
        }
        return entry.value;
    }

    /*************************************************************************
    *   Name: remove
    *   Purpose: remove an element from the table by key
    *   Imports: String, key to remove by
    *   Exports: Object, removed value
    *************************************************************************/
    public Object remove( String key )
    {
        DSAHashEntry entry = null;
        Object object = null;
        if (containsKey(key))
        {
            entry = m_hashTable[find(key)];
        }
        else
        {
            throw new IllegalArgumentException("Key is not in table");
        }
        object = entry.value;
        entry.state = -1;
        entry.value = null;
        m_count--;
        if ((double)m_count / (double)m_hashTable.length < resizeDownThresh)
        {
            reSize(m_hashTable.length / 2);
        }
        return object;
    }

    /*************************************************************************
    *   Name: containsKey
    *   Purpose: check if a key in present in the table
    *   Imports: String, key to check for
    *   Exports: boolean, whether key is present
    *************************************************************************/
    public boolean containsKey( String key )
    {
        boolean result = false;
        DSAHashEntry entry = m_hashTable[find(key)];
        if (entry != null && entry.state == 1 && entry.key.equals(key))
        {
            result = true;
        }
        return result;
    }

    /*************************************************************************
    *   Name: reSize
    *   Purpose: resize the table to the nearest prime
    *   Imports: int, starting point for prime number search
    *   Exports: none
    *************************************************************************/
    private void reSize(int size)
    {
        int newSize = getNextPrime(size);
        if (newSize > minSize)
        {
            DSAHashEntry[] oldTable = m_hashTable;
            m_hashTable = new DSAHashEntry[newSize];
            m_count = 0;
            for (int ii = 0; ii < oldTable.length; ii++)
            {
                if (oldTable[ii] != null)
                {
                    put(oldTable[ii], true);
                }
            }
        }
    }

    /*************************************************************************
    *   Name: getNextPrime
    *   Purpose: get the next prime at or above the import
    *   Imports: int, starting point for prime number search
    *   Exports: int, next prime number
    *************************************************************************/
    private int getNextPrime( int start )
    {
        boolean isPrime = false;
        int primeVal = start - 2;
        if (primeVal % 2 == 0)
        {
            primeVal++;
        }
        if (primeVal < 3)
        {
            primeVal = 3;
        }

        while (!isPrime)
        {
            primeVal += 2;
            int ii = 3;
            isPrime = true;
            while ((ii*ii <= primeVal) && isPrime)
            {
                if (primeVal % ii == 0)
                {
                    isPrime = false;
                }
                else
                {
                    ii += 2;
                }
            }
        }
        return primeVal;
    }

    /*************************************************************************
    *   Name: jumpHash
    *   Purpose: hash a key to a number to probe by
    *   Imports: String, key to hash
    *   Exports: int, probe jump size
    *************************************************************************/
    private int jumpHash( String key )//Pretty crap for main hash
    {
        int hashIdx = 0;

        for (int ii = 0; ii < key.length(); ii++)
        {
            hashIdx = (33 * hashIdx) + key.charAt(ii);
        }
        //Adding 1 do avoid having a jump size of 0, avoiding in infinite loop
        return 1+Math.abs(hashIdx % (int)Math.sqrt(m_hashTable.length-1));
    }

    /*************************************************************************
    *   Name: hash
    *   Purpose: hash a key to get an initial array index
    *   Imports: String, key to hash
    *   Exports: int, hash result
    *************************************************************************/
    private int hash( String key )//Better main hash
    {
        int hashIdx = 0;
        for (int ii = 0; ii < key.length(); ii++)
        {
            hashIdx = hashIdx ^ ((hashIdx << 5) + (hashIdx << 2) +
                                                        key.charAt(ii));
        }
        return Math.abs(hashIdx % m_hashTable.length);
    }

    /*************************************************************************
    *   Name: find
    *   Purpose: find the index of a given key
    *   Imports: String, key to find
    *   Exports: int, index of key
    *************************************************************************/
    private int find( String key )
    {
        return find(key, false);
    }

    /*************************************************************************
    *   Name: find
    *   Purpose: find the index of a key, optionally finding the first empty
    *   Imports: String, boolean; key to find and whether to find empty
    *   Exports: int, index of key location
    *************************************************************************/
    private int find( String key, boolean findEmpty )//Locate a key,
                                  // boolean to find next empty.
    {
        boolean run = true;
        int hash = hash(key);
        int jumpHash = jumpHash(key);
        int jumpCount = 0;
        DSAHashEntry entry = null;
        int index = hash;
        do
        {
            entry = m_hashTable[index];
            if (entry == null)
            {
                run = false;
            }
            else
            {
                if (entry.key.equals(key) || entry.state == 0)
                {
                    run = false;
                }
                else if (findEmpty && entry.state == -1)
                {
                    run = false;
                }
                else
                {
                    jumpCount++;
                    index = (hash + jumpHash * jumpCount) %
                                                        m_hashTable.length;
                    if (hash == index)
                    {
                        run = false;
                    }
                }
            }
        } while (run);
        return index;
    }

    /*************************************************************************
    *   Name: get
    *   Purpose: get a hash entry by index, for table output
    *   Imports: int, index to get
    *   Exports: DSAHashEntry, entry at index location
    *************************************************************************/
    private DSAHashEntry get( int index )
    {
        return m_hashTable[index];
    }

    /*************************************************************************
    *   Name: getSize
    *   Purpose: Accessor for m_hashTable.length
    *************************************************************************/
    private int getSize()
    {
        return m_hashTable.length;
    }

    /*************************************************************************
    *   Name: getHashTable
    *   Purpose: return the Hash Table as a DSAQueue
    *   Imports: none
    *   Exports: DSAQueue<Object[]>, export of hash table
    *************************************************************************/
    public DSAQueue<Object[]> getHashTable()
    {
        DSAQueue<Object[]> queue = new DSAQueue<Object[]>();
        for (int ii = 0; ii < m_hashTable.length; ii++)
        {
            DSAHashEntry entry = m_hashTable[ii];
            Object[] objArr = new Object[2];
            if (entry != null && entry.state == 1)
            {
                objArr[0] = entry.key;
                objArr[1] = entry.value;
                queue.enqueue(objArr);
            }
        }
        return queue;
    }

    //Private class for hash entries
    private class DSAHashEntry
    {
        public String key;
        public Object value;
        public int state;

        public DSAHashEntry( String inKey, Object inValue )
        {
            key = inKey;
            value = inValue;
            state = 1; //1 - used, 0 - free, -1 - previously used
        }
    }
}

