package wordgolf;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class WordGolf extends JFrame{
    
    public static void main(String[] args)throws FileNotFoundException, IOException{
        
    	//WordGolfTest gui = new WordGolfTest();
    	
        //put dictionary into ArrayList
        BufferedReader in = new BufferedReader(new FileReader(
                "wordgolfdict.txt"));
        String str;
        List<String> list = new ArrayList<String>();
        while ((str = in.readLine()) != null)
            if (str.length() < 8)
                list.add(str);
        
        Scanner input =  new Scanner(System.in);
        String firstWord = "";
        boolean legalMove = false;
        
        //checking if first word is legal
        do{
            System.out.print("Enter first word: ");
            firstWord = input.next();
            firstWord = firstWord.toLowerCase();
            if (!wordExists(firstWord, list)){
                System.out.println("That's not a word.");
                continue;
            }
            
            legalMove = true;
            
        }while (!legalMove);
        
        String endWord = "";
        int score = 0;
        legalMove = false;
        
        //Checking if end word is legal
        do{
            System.out.print("Enter final word: ");
            endWord = input.next();
            endWord = endWord.toLowerCase();
            
            //check if first word was entered again
            if (firstWord.equals(endWord)){
                System.out.println("You entered the first word. Try again.");
                continue;
            }
            
            //check if word is in dictionary using wordExists method
            if (!wordExists(endWord, list)){
                System.out.println("That's not a word. Try again.");
                continue;
            }
            
            //check word length
            if (firstWord.length() != endWord.length()){
                System.out.println("Choose words of the same length.");
                continue;
            }
            
            legalMove = true;
            
        }while (!legalMove);
        
        String mid = ""; //displays all words entered so far
        String currentWord = "", priorWord = firstWord;
        
        long startTime = System.currentTimeMillis();
        
        //main gameplay
        while (!(currentWord.equals(endWord))){
            System.out.println("\n" + firstWord + "\n" + 
                    mid + "?\n" + endWord + "\n");
            
            legalMove = false;
            
            do{
                System.out.print("Enter word: ");
                currentWord = input.next();
                currentWord = currentWord.toLowerCase();
                
                //check if prior word was entered again
                if (currentWord.equals(priorWord)){
                    System.out.println("You entered the previous word. Try again.");
                    continue;
                }
                
                //check if word is in dictionary using wordExists method
                if (!wordExists(currentWord, list)){
                    System.out.println("That's not a word. Try again.");
                    continue;
                }
                
                //check word length
                if (currentWord.length() != priorWord.length()){
                    System.out.println("Choose words of the same length.");
                    continue;
                }
                
                //count different letters between current and prior words
                int diffLetters = 0;
                for (int i = 0; i < currentWord.length(); i++)
                    if (currentWord.charAt(i) != priorWord.charAt(i))
                        diffLetters++;
                
                if (diffLetters == 1) break;
                
                //sort the chars in each word alphabetically to check if scramble
                char[] currentArray = currentWord.toCharArray();
                char[] priorArray = priorWord.toCharArray();
                Arrays.sort(currentArray); Arrays.sort(priorArray);
                
                if (!(Arrays.toString(currentArray).equals(
                        Arrays.toString(priorArray)))){
                    System.out.println("Follow instructions. Try again.");
                    continue;
                }
                
                legalMove = true;
                
            }while (!legalMove);
            
            mid += currentWord + "\n";
            priorWord = currentWord;
            score += 100;
        }
        
        long endTime = System.currentTimeMillis();
        int timeScore = (int)(endTime - startTime) / 100;
        System.out.println("You win! Score: " + (score + timeScore));
    }
    
    public static boolean wordExists(String word, List list) throws IOException{
        return wordExists(word, list, 0, list.size() - 1);
    }
    
    //recursive binary search of dictionary
    public static boolean wordExists(String word, List list,
            int low, int high) throws IOException{
        
        if (low > high) return false;
        int mid = (low + high) / 2;
        if (word.compareTo(list.get(mid).toString()) == 0)
            return true;
        if (word.compareTo(list.get(mid).toString()) < 0)
            return wordExists(word, list, low, mid - 1);
        if (word.compareTo(list.get(mid).toString()) > 0)
            return wordExists(word, list, mid + 1, high);
        return false;
        
    }   
}
