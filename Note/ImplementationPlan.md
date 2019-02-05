### Implementation Plan ####

#### Tools
- JavaParser
- IntelliJ IDEA
- Java
- Gradle
- JUnit

##### Architecture #####

- CLI/IDE Adapter
	- Find file path and send it to parser
	- Find config file and **create config instance**
	- Response to file-save; trigger driver
- Parser
	- Generate AST to output
- Driver
	- Input: AST
	- Output: lines, col, msg
	- Compiler check
- ConfigFile (`FlintConfig.Java`)
- Rule
	- `runChecks()`; need filePath and AST