WordTracker is a Java application that processes a text file to track the occurrences and locations of words. It supports various formats and serializes the results for future use.

Different Output Formats:
pf: plain format, shows words and the files they are in.
pl: detailed format, shows words the files they are in and line number.
po: very detailed format; shows words, the occurrences, the files they are in, and line number.

Running the App:

in the terminal navigate to your containing directory and input the command:

java -jar WordTracker.jar <input.txt> [pf/pl/po/dl] [-f <output.txt]

<input.txt> = the name of your input file
pf/pl/po as listed above
dl = deletes the serialized repository
-f <output.txt> the desired output file (instead of console)