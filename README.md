# bootcamp-casestudy-1

## The case study is about integrating data generated by different static code analyzers for java
- The project StaticCodeAnalysisSummarizer runs different static code analyzers on java code and combines the report in a 
single developer friendly document.

# Directory Structure
- StaticCodeAnalysisSummarizer - main java project
- reports - The reports generated by different tools are stored here
- static-code-analyzers - the binaries of different code analyzers are stored here

# Usage
- To use the summarizer the project to be analyzed should be specified 
- Different options for output format and running the commands can be also be specified in map 

# Addition of new static code analyzers
- The binaries for the code analyzers are stored in static-code-analyzers and the path to those should be added while using them
- New tools can be easily added to the project by extending from StaticAnalysisSummarizer abstract class



