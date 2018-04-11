/*--------------------------------------------------------------------------------------*/
/*  BinarySearch.java  -                                                                */
/*           Use Binary Search and Linear Search to look for a phone number as input    */
/*           by user through a file and return if the phone # was found and at what     */
/*           index it was found. Also report if the # was not found. The program should */
/*           report # of comparisons it took to find in each way.                       */
/*--------------------------------------------------------------------------------------*/
/*  Author:  Kshitij Shah                                                               */
/*  Date:    March 10 - 21 2017                                                         */
/*--------------------------------------------------------------------------------------*/
/*  Input:   which phone # to find from index                                           */
/*  Output:  If searched # exists, if it does at which index, and how many steps it took*/
/*           (copmparisons) for Binary and Linear Search to find the search term        */
/*--------------------------------------------------------------------------------------*/

import java.io.*;
import java.util.*;
import java.text.*;

public class BinarySearch {
    //these global variables will be used to keep track of the comparisons done by each search system
    static int linCalc, binCalc;
    //this returns a notFound value of negative 1 incase binary search doesn't find what its looking for
    static int notFound = -1;

    //This method called by main method counts the number of lines in the txt file, sends number back to main method
    static int fileAnalyzer(String fileName) throws IOException {
	//make new reader to read file with, read file with name as sent from main method
	BufferedReader reader = new BufferedReader (new FileReader (fileName));
	//make int variable to keep track of number of lines
	int numberCount = 0;
	//run while loop to read until reader encounters a blank lines (null)
	while(reader.readLine() != null) {
	    //every non blank line increases number count
	    numberCount++;
	}
	//close reader
	reader.close();
	//return the number of lines to main method
	return numberCount;
    }

    //also called by main method, this method reads phone numbers from file and saves it
    static String fileReader(String fileName, int lineNumber) throws IOException {
	// new String to read phone numbers from each line
	String phoneNumber = null;
	//New reader to read phone numbers based on line numbre
	BufferedReader reader = new BufferedReader(new FileReader(fileName));
	// read phone number at line requested by main method
	for (int i = 0; i <= lineNumber; i++) {
	    phoneNumber = reader.readLine();
	}
	//pass back to main method the string for the line #
	return phoneNumber;
    }

    //takes user input for what number to search for, then passes back the search string
    static String searchType (int numberCount, String[] phoneNumber) throws IOException {
	BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));
	//new search string for storing the number that will be searched for
	String searchString = null;
	//new booleans to validate custom search term matches input
	boolean validInput = true, formatMatch = false;
	int searchType = 0;
	//tell user how many files in the txt file
	System.out.println ("\nIn the file, there are " + numberCount + " lines, each containing a phone number.");
	//new do while loop to run while invalid input for switch is entered
	do {
	    //present list of options for user to choose from
	    System.out.println("What do you want to do?\n  1 - custom search String\n  2 - best case for linear\n  3 - worst case for linear\n  4 - average case for linear\n  5 - best case for binary \n  6 - worst case for binary \n  7 - average case for binary\n  8 - not found case (for both)\n");
	    searchType = Integer.parseInt(stdin.readLine ());
	    //new switch statement to take user input and collect the relevant search string
	    switch (searchType) {
		//first case, custom string
		case 1:
		    //keep asking for string until proper format of string is inputted
		    do {
			//ask for and get string input of phone number
			System.out.println("What phone number do you want to search for? (###-###-####)");
			searchString = stdin.readLine();
			//check if format matches
			if (searchString.matches("\\d{3}-\\d{3}-\\d{4}")) {
			   formatMatch = true;
			}
			//if format doesn't match, say it doesn't remind of what the right format is
			else {
			    System.out.println("You entered something invalid, remember, the number must be in the this format: \n###-###-#### \n");
			}
			//run this loop until proper format is matched
		    }
		    while (!formatMatch);
		    break;
		//in this case, set search string to default best case for linear (first one in list)
		case 2:
		    searchString = phoneNumber[0];
		    break;
		//in this case, set search string to default worst case for linear (one that is not found)
		case 3:
		    searchString = "999-555-0101"; // I googled it and anything with a 555 - 1100 - 1200 is saved for fake numbers
		    break;
		//in this case, set search string to default average case for linear (middle one in list)
		case 4:
		    int x = (numberCount) / 2; //middle number of "numbercount" numbers
		    searchString = phoneNumber[x - 1];
		    break;
		//in this case, set search string to default best case for binary (middle one in list)
		case 5:
		    x = (numberCount) / 2; //doing it this way so it can work with a list of any length
		    searchString = phoneNumber[x - 1];
		    break;
		//this case (6) sets search string to default worst case for binary (this one is searching for something that doesn't exist because it will have to go every possibility)
		case 6:
		    searchString = "999-555-0101";
		    break;
		// case 7 sets search string to an average search case for binary search
		case 7:
		    //log2(n) comparisons
		    searchString = phoneNumber[numberCount - 1];
		    break;
		//case 8 searches for a phone number that doesn't exist
		case 8:
		    searchString = "999-555-0101";
		    break;
		//if user somehow cannot enter the right number, then tell them they're dumb
		default:
		    System.out.println("Try again\n");
		    //set valid input to false so it will ask for input again
		    validInput = false;
		    break;
	    }
	}
	while(!validInput);
	//if search string wasn't custom, tell user what string will be searched for
	if (searchType != 1) {
	    System.out.println ("\nThe phone number that will be searched for: " + searchString);
	}
	//send back the searchstring so it can be used to search and stuff
	return searchString;
    }

    //void method to run a linear search on data
    static void linearSearch(String searchString, String[] phoneNumber, int numberCount, String fileName) throws IOException {
	//new boolean for if string is found
	boolean found = false;
	//look through the phoneNumbers array to see if any number matches search string
	for (int i = 0; i < (numberCount); i++) {
	    //everytime it runs through loop, increment the number of comparisons it ran
	    linCalc++;
	    //if it finds the number in phone number array do the following
	    if (searchString.equals(phoneNumber [i])) {
		//increment counter because it had to do this comparison
		linCalc++;
		//report that number was found in which file, at the index, and the number of comparisons linear search had to run to find it
		System.out.println("\nLinear Search found the number " + (phoneNumber [i]) + " at line " + (i + 1) + " in '" + fileName + "'. \nIt took " + linCalc + " comparisons (not including ones required for input)");
		//set found variable to true for later
		found = true;
		//exit for loop, save the memory
		i = numberCount;
	    }
	}
	//if found variable was not set to true, then it wasn't found, so alert user that it wasn't found in the file and how many calculations that took
	if (!found) {
	    linCalc++;
	    System.out.println("\nLinear Search was unable to find '" + searchString + "' in '" + fileName + "'. :(\nIt took " + linCalc + " comparisons (not including ones required for input)");
	}
    }


    //this is for the coolios binary search, it returns the the index of a number if its found, or a random number so the system know it wasn't found, called from main method, returns index to main method
    static int binarySearch(String searchString, String[] phoneNumber, int start, int end) throws IOException {
	//find the middle of the search area
	int mid = start + ((end - start) / 2);
	//base case, if you run out of things to search, return a not found value (-1)
	if (start > end) {
	    binCalc++;
	    return notFound;
	}
	//if you find the string, then return the index in the phone number array where it was found, another base case
	if (searchString.equals(phoneNumber [mid])) {
	    binCalc++;
	    return mid;
	}
	//if you went to far, run binary search again on the first half
	else if (searchString.compareTo(phoneNumber [mid]) < 0) {
	    //increase calculation counter every time you have to do this kind of thing because you needed to make a comparison
	    binCalc++;
	    //call recursively but now search bottom half by making the same start value, but making the end half (-1 because you already checked the exact middle)
	    int index = binarySearch(searchString, phoneNumber, start, (mid - 1));
	    //when the recursive loop ends, return the value it came up with back to main method
	    return index;
	}
	//if not in the middle, or bottom half, still room to search and hasn't been found yet, look in the top half
	else {//(searchString.compareTo (phoneNumber [mid]) > 0)is implied but not necessary
	    //calculations happened
	    binCalc++;
	    //recursively call the method it self to search through top half, by making the search area halfway to end, minus the halfway one itself since you already seached it
	    int index = binarySearch(searchString, phoneNumber, (mid + 1), end);
	    //when loop ends, and you have a value, either not found or index of found. return that value back to main method
	    return index;
	}
    }

    //main method aka start point
    public static void main (String args[]) throws IOException {
	BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));
	DecimalFormat df = new DecimalFormat ("#");
       
	//This String is the file that will be read, here its presset but can be asked as input from user
	String fileName = "phone.txt";
	//get number of lines in file by calling file analyzer method
	int numberCount = fileAnalyzer (fileName);
	// make a new string array to store all phone numbers
	String[] phoneNumber = new String [numberCount];
	//fill up that array by using a for loop to fill the array by reading numbers from each line and passing them back by calling the filereader method
	for (int i = 0; i < numberCount; i++) {
	    phoneNumber[i] = fileReader(fileName, i);
	}
	//new string for checking if user wants to run program again, deafult to no
	String loop = "N";
	//run program until user wants to stop
	do {
	    //everytime it runs, reset comparison counter
	    linCalc = 0;
	    binCalc = 0;
	    //call search string method to find out what string they're searching for
	    String searchString = searchType(numberCount, phoneNumber);
	    // run linear search on the search string
	    linearSearch(searchString, phoneNumber, numberCount, fileName);
	    // find index of number in array using binary search method
	    int binIndex = binarySearch(searchString, phoneNumber, 0, (numberCount - 1));
	    //if the binary search returns a not found int for index, report this to user, that the number they searched for wasn't found in the file and how many comparisons it took to determine this
	    if (binIndex == -1) {
		//checking the index number took a comparison
		binCalc++;
		System.out.println("\nBinary Search was unable to find '" + searchString + "' in '" + fileName + "'. :(\nIt took " + binCalc + " comparisons (not including ones required for input)");
	    }
	    //if it wasn't not found, it was found so the returned value was index value of array where it was found
	    else {
		//print out that the number at that index was found, which line in file, which file and the number of comparisons it took to find
		System.out.println("\nBinary Search found the number '" + (phoneNumber [binIndex]) + "' at line " + (binIndex + 1) + " in '" + fileName + "'. \nIt took " + binCalc + " comparisons (not including ones required for input)");
	    }
	    //ask if the want to run the program again, defaults to n unless y or Y
	    System.out.println("\nDo you want run this program again?(Y/N)");
	    loop = stdin.readLine();
	    System.out.println("\n-------------------------------------------------------------------------------\n");
	}
	//if answer is yes, loop, otherwise end
	while (loop.equalsIgnoreCase ("Y"));
	//if they said they don't want to run again, close the window because they're probably lazy and would appreciate it
	System.exit (0);
    }
}


