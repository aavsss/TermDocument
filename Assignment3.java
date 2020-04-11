/**
 * @author Aavash Sthapit
 * Class: COSC 4315.001
 * Submitted to: Dr. Leonard Brown
 * Date: 4/03/2020
 * Purpose 1:  To display a term-document matrix for a set of input documents. 
 * Feature 1:  Program prompts the user for a folder/directory and read all the
 * 			   files in that folder. 
 * 			   All terms(words) are changed into lower-case equivalent, and porter stemmed
 * 			   Punctuations have also been removed. 
 * 			   Top 10 most common words in English lexicon are removed: the, be, to, of, and, a, in, that, have, i
 * 			   Additionally, Punctuations are removed. 
 * 			   Normalizes term frequency for all the terms in each document.
 * 			   Prints TF till 4 decimal places.
 * 			   Can hold for maximum of 100 terms and 10 documents. 
 * 			   Prints a term-document matrix with terms sorted in ascending order.
 * 			   Array of 100 * 10 is declared to hold the terms.
 * 
 *  Purpose 2: Extending the previous purpose to compute and print a co-occurence matrix.
 *  Feature 2: Transpose of the term-document matrix is computed.
 *  		   The two matrices are multiplied to compute a co-occurence matrix.
 *  		   Prints a co-occurence matrix
 *  		   Array of 100*100 is declared to hold the matrx. 
 *  		   Thus, the row filled with 0 indicates that all the terms have been computed.
 */
import java.util.*;
import java.io.*;

public class Assignment3{
	
	private File dir;
	private float[][] docTerm = new float[100][10];
	private float[][] docTermTransposed = new float[10][100];
	private float[][] coOccurenceMatrix = new float[100][100];
	private ArrayList<String> listOfWords;
	
	//Constructor
	public Assignment3() {
		
	}
	
	/**
	 * Function: Makes a sorted list of all the words in the directory
	 * @return sorted list in the form of ArrayList
	 */
	private ArrayList<String> putIntoSortedList() {
		
		listOfWords = new ArrayList<>();
		
		try
	    {
		  //Prompting the user to enter the name of a directory
	      System.out.print("Enter name of a directory> ");
	      Scanner scan = new Scanner(System.in);
	      this.dir = new File(scan.nextLine());
	      File[] fileList = dir.listFiles();
	      
	      //Printing the term-document matrix
	      System.out.println("\nPart 1 -- Printing the term-document matrix with \n"
	    		  + "terms being on the y-axis and documents on the x-axis.\n");
	      
    	  for(int a = 0; a < fileList.length; a++) {
    		  System.out.print("\t\t"+fileList[a].getName());
    	  }
    	  
    	  System.out.println();
	      
	      for(int i = 0; i < fileList.length; i++ ) {
	    	  Scanner sc = new Scanner(fileList[i]);
	   
	    	  while (sc.hasNextLine())
	          {
	            StringTokenizer st = new StringTokenizer(sc.nextLine());
	            
	            while (st.hasMoreTokens())
	            {
	              String word = st.nextToken();
	              
	              //Processing word
	              word = processWord(word);
	              
	              //Adding words to the list
	              //If the word is an empty literal then we discard it
	              if(!word.equals("") && !listOfWords.contains(word)) {
            		  listOfWords.add(word);
	              }    
	          }  
	        }
	      }
	      
	      //Sort arrayList using Collections
	      Collections.sort(listOfWords);
	      System.out.println();
	      
	    }
	    catch(Exception e)
	    {
	      System.out.println("Error:  " + e.toString());
	    }
		//Returning sorted list of arrays
		return listOfWords;
	}
	
	private String processWord(String word) {
        // Process word
        // Case Folding, punctuation, stemming, etc.
        // Process word
        //Case folding
        word = word.toLowerCase();
        
        //Punctuation not taken care of
        //Removes !"$#%&'()*+,-./:;<=>?@{}^_`{|}~ï¿½ï¿½
        word = word.replaceAll("([\\p{Punct}])", "");
        word = word.replace("“", "").replace("”", "");
        word = word.replace("[", "").replace("]", "");
        word = word.trim();
        
        //Stop words
        //: the, be, to, of, and, a, in, that, have, i
        word = removeStopWords(word);
        word = word.trim();
        
        //PorterStemming
        word = porterStemming(word);
        
        return word;
        
	}
	
	  
		  /**
		   * Function: Stems the word using Porter Stemming Algorithm
		   * @param word to be stemmed
		   * @return stemmed word
		   */
		  private String porterStemming(String word) {
			  
			  	//Stemming using porter stemmer algorithm provided by Tartarus
		  	//Instantiating a charSequence with the length of the input
		  	char[] stemmedWord = new char[word.length()];
			
		  	//Adding each character from the input into an array 
			  	for(int i = 0; i < word.length(); i++) {
			  		stemmedWord[i] = word.charAt(i);
			  	}
		     	Stemmer stemmer = new Stemmer();
		     	stemmer.add(stemmedWord, stemmedWord.length);
		     	stemmer.stem();
		     	word = stemmer.toString();
		     	return word;
		  }
		  
		  /**
		   * Function: Removes stop words from the dictionary
		   * Top 10 most common words in English lexicon are removed: the, be, to, of, and, a, in, that, have, i
		   * @param string is a stop word or not?
		   * @return blank string if it is stop word
		   * 		 else unchanged string
		   */
		  public static String removeStopWords(String string) 
		  { 

			  String[] stopWords = {"the","be","to","of","and","a","in","that","have","i"};
			  
			  for(int i = 0; i < stopWords.length; i++) {
				  
				  // Check if the word is a stop word. 
				  //if it is a stop word, assign it to an empty literal value
			      if (string.equals(stopWords[i])) { 
			    	  string = "";
			      } 
			  }
		      
		      // Return blank string if it is stop word 
			  // else unchanged word
		      return string; 
		  } 
		  

	
	/**
	 * Function: Computes term-document matrix by searching and computing 
	 * 			 a word from the sorted list to every word in the directory
	 */
	public void computeDocumentTermMatrix() {
		
		//TODO: Re-do reading from the file
		
		//Getting sorted list
		//Function defined below
		ArrayList<String> sortedList = putIntoSortedList();
		
		
		//Making a term document matrix 
		try {
			//Making an array of files in a directory
			File[] fileList = dir.listFiles();
			
			//To traverse through every term in the sorted list
			for(int i = 0; i < sortedList.size(); i++) {
				
				
				//To traverse through every file.
				for(int j = 0; j < fileList.length; j++) {
				
					Scanner fscan = new Scanner(fileList[j]);
					
					int count = 0;
					//Into the file
					while(fscan.hasNext()) {
						count++;
						String empty = fscan.next();
					}
					
					Scanner sc = new Scanner(fileList[j]);
					
					String[] wordsInAFile = new String[count];
					
					for(int k = 0; k < count; k++) {
						String word = sc.next();
						word = processWord(word);
						//If word is an empty literal then we discard it.
						if(!word.equals("")) {
							wordsInAFile[k] = word;
						}
					}
					
					
						//Computing term frequency value and storing it in 
						//an appropriate place in the matrix
						docTerm[i][j] = computeTF(wordsInAFile, sortedList.get(i));
					
				}
			}
			
		}catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
		printLabeledTable(docTerm);
		
	}
	
	/**
	 * Function: Printing the matrices
	 * @param matrix to be printed.
	 */
	private void printLabeledTable(float[][] matrix) {
		for(int i = 0; i < listOfWords.size(); i++) {
			System.out.printf("%-10s\t", listOfWords.get(i));
			for(int j = 0; j < matrix[0].length;j++) {
				System.out.printf("%.4f \t\t",matrix[i][j]);
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Function: Computes term frequency of a term.
	 * @param words in a file
	 * @param term to be tested.
	 * @return term frequency value
	 */
	private float computeTF(String[] words, String term) {
		float result = 0;
		for(String word : words) {
			if(term.equals(word)) {
				result++;
			}
		}
		return result/words.length;
	}
	
		
	/**
	 *Mark:  Part 2: Extending to compute and print a co-occurence matrix
	 */
	  
  /**
   * Function: Computes co-occurence matrix by calling appropriate functions.
   */
	public void computeCooccurenceMatrx(){
		computeTransposedMatrix();
		multiplyMatrices();
	}

	/**
	 * Function: Computes transpose of the term-document matrix
	 *           Interchanges values of rows and columns.
	 */
	private void computeTransposedMatrix() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 100; j++) {
				docTermTransposed[i][j] = docTerm[j][i];
			}
		}
	}
	
	/**
	 * Function: Multiplying term-document matrix and its transpose.
	 *           And prints it.
	 */
	private void multiplyMatrices(){
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 100; j++){
				for(int k = 0; k < 10; k++){
					coOccurenceMatrix[i][j] += docTerm[i][k] * docTermTransposed[k][j];
				}
			}
		}
		
		printLabeledCoMatrix(coOccurenceMatrix);

	}
	
	/**
	 * Function: Printing the Co-occurence matrix with appropriate formatting.
	 * @param matrix to be printed.
	 */
	private void printLabeledCoMatrix(float[][] matrix) {
		
		//Printing the co-occurence matrix
		System.out.println("\nPart 2 -- Printing the co-occurence matrix\n"
				+ "Computed by multiplying the term-document matrix with its transpose.\n ");
		
		System.out.printf("%-14s", "");
		for(int i = 0; i < listOfWords.size(); i++) {
			System.out.printf("%-10s", listOfWords.get(i));
		}
		
		System.out.println("\n");
		
		for(int i = 0; i < listOfWords.size(); i++) {
			System.out.printf("%-10s", listOfWords.get(i));
			for(int j = 0; j < listOfWords.size(); j++) {
				System.out.printf("%10.4f", matrix[i][j]);
			}
			System.out.println();
		}
			
	}

	
	public static void main(String[]  args) {
		Assignment3 documentTermMatrix = new Assignment3();
		documentTermMatrix.computeDocumentTermMatrix();
		documentTermMatrix.computeCooccurenceMatrx();
	}
}