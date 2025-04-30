package appDomain;

import java.util.Map;
import java.util.Set;
import java.nio.file.Paths;
import java.util.StringJoiner;

public class OutputFormatter 
{
	public static String formatOutput(WordData node, String option)
	{
		StringBuilder output = new StringBuilder();
		switch (option)
		{
		case "pf":
			output.append("\nKey : ===").append(node.getWord()).append("===");
			for (String fileName : node.getFileLineMap().keySet())
				{
					String newFileName = Paths.get(fileName).getFileName().toString();
					output.append(" found in file: ").append(newFileName).append("");
				}
			break;
		case "pl":
			output.append("\nKey : ===").append(node.getWord()).append("===");
			for (Map.Entry<String, Set<Integer>> entry : node.getFileLineMap().entrySet())
				{
					String fileName = Paths.get(entry.getKey()).getFileName().toString();
					StringJoiner lineNumbers = new StringJoiner(", ");
					for (Integer lineNumber : entry.getValue())
					{
						lineNumbers.add(lineNumber.toString());
					}
					output.append(" found in file: ").append(fileName)
					.append(" on lines: ").append(lineNumbers).append("");
				}
			break;
		case "po":
			output.append("\nKey : ===").append(node.getWord()).append("===")
			.append(" number of entries: ").append(node.getEntries());			
			for (Map.Entry<String, Set<Integer>> entry : node.getFileLineMap().entrySet())
			{
				String fileName = Paths.get(entry.getKey()).getFileName().toString();
				StringJoiner lineNumbers = new StringJoiner(", ");
				for (Integer lineNumber : entry.getValue())
				{
					lineNumbers.add(lineNumber.toString());
				}
				output.append(" found in file: ").append(fileName)
				.append(" on lines: ").append(lineNumbers);
			}
			break;
		case "dl":
			break;
		}
		return output.toString();
	}
}
