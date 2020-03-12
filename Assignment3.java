import java.util.*;
import java.io.*;

public class Assignment3{
	
	private File dir;
	private double[][] docTerm = new double[100][10];
	private double[][] docTermTransposed = new double[10][100];
	private double[][] coOccurenceMatrix = new double[100][100];
	
	//Constructor
	public Assignment3() {
		
	}
	
	public void computeDocumentTermMatrix() {
		
		//Getting sorted list
		ArrayList<String> sortedList = putIntoSortedList();
		
		//Making a term document matrix hopefully
		try {
			
			for(int i = 0; i < sortedList.size(); i++) {
				
				File[] fileList = dir.listFiles();
			
				for(int j = 0; j < fileList.length; j++) {
				
					Scanner sc = new Scanner(fileList[j]);
					while(sc.hasNextLine()) {
						StringTokenizer st = new StringTokenizer(sc.nextLine());
		            
						String[] wordsInAFile = new String[st.countTokens()];
						
						int k = 0;
						//Creating an array full of words in a file
						while(st.hasMoreTokens()) {
							wordsInAFile[k] = st.nextToken();
							k++;
						}
						
						//Create a TF matrix
						docTerm[i][j] = computeTF(wordsInAFile, sortedList.get(i));
					}
				}
			}
			
		}catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
		
	}
	
	private double computeTF(String[] docs, String term) {
		double result = 0;
		for(String word : docs) {
			if(term.equals(word)) {
				result++;
			}
		}
		return result/docs.length;
	}
	
	private ArrayList<String> putIntoSortedList() {
		
		ArrayList<String> listOfWords = new ArrayList<>();
		
		try
	    {
	      System.out.print("Enter name of a directory> ");
	      Scanner scan = new Scanner(System.in);
	      this.dir = new File(scan.nextLine());
	      File[] fileList = dir.listFiles();
	      for(int i = 0; i < fileList.length; i++ ) {
	    	  Scanner sc = new Scanner(fileList[i]);
	   
	    	  while (sc.hasNextLine())
	          {
	            StringTokenizer st = new StringTokenizer(sc.nextLine());
	            
	            while (st.hasMoreTokens())
	            {
	              String word = st.nextToken();
	              // Process word
	              // Case Folding, punctuation, stemming, etc.
	              // Process word
	              //Case folding
//	              word = word.toLowerCase();
//	              
//	              //Punctuation not taken care of
//	              //Removes !"$#%&'()*+,-./:;<=>?@{}^_`{|}~“”
//	              word = word.replaceAll("([\\p{Punct}])", "");
//	              word = word.replace("“", "").replace("”", "");
//	              word = word.replace("[", "").replace("]", "");
//	              word = word.trim();
//	              
//	              //Stop words
//	              //: the, be, to, of, and, a, in, that, have, i
//	              word = removeStopWords(word);
//	              word = word.trim();
//	              
//	              //PorterStemming
//	              word = porterStemming(word);
	              
	              //Adding words to the list
	              listOfWords.add(word);
	              
	          }  
	        }
	      }
	      
	      //Sort arrayList using Collections
	      Collections.sort(listOfWords);
	      //Printing list of words
	      System.out.println(listOfWords);
	      
	    }
	    catch(Exception e)
	    {
	      System.out.println("Error:  " + e.toString());
	    }
		return listOfWords;
	}
	
	/**
	   * Function: Removes stop words from the dictionary
	   * Top 10 most common words in English lexicon are removed: the, be, to, of, and, a, in, that, have, i
	   * @param string is a stop word or not?
	   * @return blank string if it is stop word
	   * 		 else unchanged string
	   */
	  private String removeStopWords(String string) 
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
	
	public void printDocTerm() {
		for(int i = 0; i < docTerm.length; i++) {
			System.out.println(Arrays.toString(docTerm[i]));
		}
	}
	
	/**
	 * Part 2
	 * @param args
	 */
	public void computeTransposedMatrix() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 100; j++) {
				docTermTransposed[i][j] = docTerm[j][i];
			}
		}
		printTransposedDocTerm();
	}
	
	//Cause I was lazy to think
	public void printTransposedDocTerm() {
		for(int i = 0; i < docTermTransposed.length; i++) {
			System.out.println(Arrays.toString(docTermTransposed[i]));
		}
	}
	
	public static void main(String[]  args) {
		Assignment3 documentTermMatrix = new Assignment3();
		documentTermMatrix.computeDocumentTermMatrix();
		documentTermMatrix.printDocTerm();
		documentTermMatrix.computeTransposedMatrix();
	}
}