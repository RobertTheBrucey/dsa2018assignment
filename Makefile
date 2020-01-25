CC = javac
CFLAGS = 
EXEC = ElectoralMain

$(EXEC).class : FileIO.class ElectoralData.class Menu.class $(EXEC).java
	javac $(EXEC).java

FileIO.class : DSAQueue.class FileIO.java
	javac DSAQueue.java

ElectoralData.class : DSAQueue.class Party.class Candidate.class \
Division.class State.class DSAHashTable.class DSALinkedList.class \
ElectoralData.java
	javac ElectoralData.java

Menu.class : Menu.java DSAQueue.class Filter.class Party.class \
ElectoralData.class
	javac Menu.java

DSAQueue.class : DSALinkedList.class DSAQueue.java
	javac DSAQueue.java

Party.class : DSALinkedList.class Candidate.java Party.java
	javac Party.java

Candidate.class : Party.java Candidate.java
	javac Candidate.java

Division.class : Division.java Candidate.java State.java DSALinkedList.class
	javac Division.java

State.class : State.java Division.java DSALinkedList.class
	javac State.java

#Not sure how to work with inner classes with a makefile
DSAHashTable.class : DSAHashTable.java
	javac DSAHashTable.java

DSALinkedList.class : DSALinkedList.java
	javac DSALinkedList.java

Filter.class : Filter.java MergeSortList.class Party.class Candidate.class \
Division.class State.class DSAQueue.class DSALinkedList.class \
ElectoralData.class
	javac Filter.java

MergeSortList.class : MergeSortList.java DSALinkedList.class
	javac MergeSortList.java

clean:
	rm *.class
