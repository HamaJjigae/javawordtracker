package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import implementations.BSTree;
import implementations.BSTreeNode;
import utilities.Iterator;

public class WordTracker {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) 
    {
        if (args.length < 1) 
        {
            System.err.println("Usage: java -jar WordTracker.jar <input.txt> [-pf/-pl/-po] [-f <output.txt>]");
            return;
        }

        String inputFile = args[0];
        String outputFile = null;
        String option = null;
        BSTree<WordData> wordTree = null;

        for (int i = 1; i < args.length; i++) 
        {
            switch (args[i]) 
            {
                case "-pf":
                case "-pl":
                case "-po":
                case "-dl":
                    option = args[i].replace("-","");
                    break;
                case "-f":
                    if (i + 1 < args.length) 
                    {
                        outputFile = args[i + 1];
                        i++;
                    } else 
                    {
                        System.err.println("Error: Output file not specified after -f flag");
                        return;
                    }
                    break;
                default:
                    System.err.println("Unknown option: " + args[i]);
                    return;
            }
        }
        
        File repositoryFile = new File("repository.ser");
        if (repositoryFile.exists() && !option.equals("dl"))
        {
        	try (FileInputStream fileIn = new FileInputStream(repositoryFile);
        			ObjectInputStream in = new ObjectInputStream(fileIn))
        	{
        		wordTree = (BSTree<WordData>) in.readObject();
        	}
        	catch (IOException | ClassNotFoundException e)
        	{
        		e.printStackTrace();
        		wordTree = new BSTree<>();
        	}
        }
        else
        {
        	wordTree = new BSTree<>();
        }
        
        if ("dl".equals(option))
        {
        	if(repositoryFile.delete())
        	{
        		System.out.println("Deleted repository.ser");
        	}
        	else
        	{
        		System.err.println("Failed to delete repository.ser");
        	}
        	return;
        }
        
        
        inputFile = Paths.get("res", inputFile).toString();
        if (outputFile != null)
        {
        	outputFile = Paths.get("res", outputFile).toString();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) 
        {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null)
            {
            	lineNumber ++;
            	Pattern pattern = Pattern.compile("\\b(\\w+\\'\\w+|\\w+)\\b");
            	Matcher matcher = pattern.matcher(line);
            	while (matcher.find())
            	{
            		String word = matcher.group().replaceAll("\\p{Punct}", "").toLowerCase();
            		if (!word.isEmpty())
            		{
            			WordData wordData = new WordData(word);
            			BSTreeNode<WordData> searchNode = wordTree.search(wordData);
            			WordData existingWord = (searchNode != null) ? searchNode.getElement() : null;
            			
            			if (existingWord != null)
            			{
            				existingWord.addEntry(inputFile, lineNumber);
            			}
            			else
            			{
            				wordData.addEntry(inputFile, lineNumber);
            				wordTree.add(wordData);
            			}
            			
            		}
            	}
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        if (outputFile != null) 
        {
            try (FileWriter writer = new FileWriter(outputFile)) 
            {
                writer.write("Writing " + option + " format\n");
                utilities.Iterator<WordData> iterator = wordTree.inorderIterator();
                while (iterator.hasNext())
                {
                	WordData node = iterator.next();
                	writer.write(OutputFormatter.formatOutput(node, option));
                }
                System.out.println("Exporting file to : " + outputFile);
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        else
        {
        	System.out.println("Writing " + option + " format");
        	Iterator<WordData> iterator = wordTree.inorderIterator();
        	
        	while (iterator.hasNext())
        	{
        		WordData node = iterator.next();
        		System.out.println(OutputFormatter.formatOutput(node, option));
        	}
            System.out.println("\nNot exporting file");
        }
        
        try (FileOutputStream fileOut = new FileOutputStream("repository.ser");
        	ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {
        	out.writeObject(wordTree);
        }
        catch (IOException i)
        {
        	i.printStackTrace();
        }
    }
}
