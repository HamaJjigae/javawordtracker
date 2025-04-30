package appDomain;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.TreeSet;

public class WordData implements Comparable<WordData>, Serializable
{
	private static final long serialVersionUID = 1L;
	private String word;
	private int entries;
	private Map<String, Set<Integer>> fileLineMap;
	
	public WordData(String word)
	{
		this.word = word;
		this.entries = 0;
		this.fileLineMap = new HashMap<>();
	}
	
	public String getWord()
	{
		return word;
	}
	public int getEntries()
	{
		return entries;
	}
	
	public Map<String, Set<Integer>> getFileLineMap()
	{
		return fileLineMap;
	}
	
	public void addEntry(String fileName, int lineNumber)
	{
		fileLineMap.putIfAbsent(fileName, new TreeSet<>());
		fileLineMap.get(fileName).add(lineNumber);
		entries++;
	}
	
	@Override
	public int compareTo(WordData other)
	{
		return this.word.compareTo(other.word);
	}
	
	@Override
	public String toString()
	{
		return word + ": " + fileLineMap.toString();
	}
}
