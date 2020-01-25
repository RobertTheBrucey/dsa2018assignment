Sean Ashton DSA Assignment Readme

Date created: 27/10/2018

Date last modified: 28/10/2018

Purpose: Read in data from files (in ElectionData folder) compute useful information regarding election data.

Files in project:
readme.txt -> This file

Candidate.java -> Container class for holding all information about a candidate, includes some useful methods relating to candidate information as well.

DSAHashTable.java -> Implementation of the hash table ADT, adapted from prac 8 it is used for quick access to candidates when reading in votes.

DSALinkedList.java -> Implentation of a linked list, used to store most if not all of the data used in this program

DSAQueue.java -> Implementation of a Queue ADT, built on top of the linked list

Division.java -> Container class for holding all the data relating to a division, also includes methods relating to division data and manipulation

ElectoralData.java -> Class to hold all Container classes and manipulate the data

ElectoralMain.java -> Main program, checks for input files, reads them in to a ElectoralData object, then calls the Menu object.

FileIO.java -> Utility class for reading in delimiter separated values, being able to specify the delimiter proved very useful

Fitler.java -> Object to hold settings to filter and order by, and process input data into a Queue to be used to display sorted data or write it to file

Menu.java -> Main menu, has minimal processing code, interacts with the user

MergeSortList.java -> Implementation of merge sort for linked list, used for ordering candidates by surname

Party.java -> Container class for holding all information about a party

State.java -> Container class for holding all information about a state

Test Files:

Functionality:
Check ./ElectionData/ folder for Candidate and vote files;
Read in files;
Present a menu tree for the user to interact with;
Search for nominees, List nominees with filters and ordering,
    List Divisions by Margin, Compute Itinerary for marginal seats

To do
Unit tests, Documentation
Implement Reading in itinerary data and plotting with generated list
Implement graphs to optimise route

Known bugs

Additional functionality:
Ordering can be done in reverse.
