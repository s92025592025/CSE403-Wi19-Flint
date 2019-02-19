# Flint --- A  Programmable Style Linter for Java

Older Project CI

[![Build Status](https://travis-ci.com/s92025592025/CSE403-Wi19-Flint.svg?branch=master)](https://travis-ci.com/s92025592025/CSE403-Wi19-Flint)

Remade Project CI

[![Build Status](https://travis-ci.com/s92025592025/checkstyle-idea.svg?branch=master)](https://travis-ci.com/s92025592025/checkstyle-idea)

## Table Of Contents

* [Quick Start](#quick-start)
* [Introduction](#introduction)
* [Installing Fint](#installing-flint)
  * [CLI Adapter](#cli-adapter)
  * [IntelliJ IDEA](#intellij-idea-plugin)
* [Running Fint](#running-flint)
  * [CLI Adapter](#cli-adapter)
  * [IntelliJ IDEA](#intellij-idea-plugin)
* [Configuring FlintConfig403](#configuring-flintconfig403)
* [Creating New Rules](#creating-new-rules)

### Quick Start

First, download and unzip our distribution repository: [`Flint-CLI-Distribution`](https://github.com/elliottdebruin/Flint-CLI-Distribution/archive/master.zip). This folder contains all the files you need to get Flint running.

To run Flint with skeleton configuration file, run the following commands:
```bash
$(FLINT_PATH) -config-path $(CONFIG_PATH) -config-class FlintConfig403 -file-path $(FILE_PATH)
```
Where:
* `$(FLINT_PATH)` is the path of `flint.sh` file for Mac/Linux or the `flint.bat` file for Windows
* `$(CONFIG_PATH)` is the path to the directory containing `FlintConfig403.class`  
* `$(FILE_PATH)` is the path to the file you want to run Flint on

### Introduction

Flint is a programmable style and documentation linter for Java. Flint can be used as a CLI tool as well as a plugin for IntelliJ IDEA. Using Flint as a CLI tool you can configure Flint to run style checking rules on a provided Java file and it will output error messages for any rule failures in your Java file. Using the IDE plugin version of Flint you are able to have Flint run on a Java file every time the file is saved. Flint comes with a set of premade rules and also allows you to create and customize your own rules. All rules you want to be run and when you want them to be run can be configured using a Flint configuration file.
### Installing Flint

#### CLI Adapter

Download and unzip our distribution repository:  [`Flint-CLI-Distribution`](https://github.com/elliottdebruin/Flint-CLI-Distribution/archive/master.zip). This folder contains all the files you need to get Flint running.

*OPTIONAL:*
If you would like, you can alias the `flint.sh` file on Unix or add the directory containing `flint.bat` to your `PATH` for Windows.

#### IntelliJ IDEA Plugin
1. In IntelliJ IDEA go to `Settings/Preferences` > `Plugins`
2. Click the `Install JetBrains plugin`
3. In the dialog that opens, search for `Flint-403`
4. Press `Install`
5. Click `OK` in the Settings dialog and restart IntelliJ IDEA

After these five steps you will have Flint installed and running on your IntelliJ IDEA application, and you will have a Flint icon in your IntelliJ IDEA toolbar.

6. Download the skeleton configuration class, `FlintConfig403.java`
7. Put `FlintConfig403.java` in the root directory of your project

#### Requirements
* [Java 8 or lower](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) (No Java 11)

### Running Flint

#### CLI Adapter

We have provided a shell script named `flint.sh` for users using Unix terminals and named `flint.bat` for users using Windows. In order for the script to find how to run flint, ensure that `flint-0.1.3.jar` is in the same directory as `flint.sh`/`flint.bat`. If `flint-0.1.3.jar` is not in the same directory, the following output will show
```bash
flint-0.1.3.jar should be in the same directory as flint.sh
```
on Unix or
```
Please make sure the flint-0.1.3.jar file is in the same directory as the flint.bat script!
```
on Windows.
If `flint-0.1.3.jar` is under the right place, you may run the Flint shell script as follows:
```bash
javac $(CONFIG_JAVA_PATH)
$(FLINT_PATH) -config-path $(CONFIG_PATH) -config-class FlintConfig403 -file-path $(FILE_PATH)
```
Where:
* `$(CONFIG_JAVA_PATH)` is the path to `FlintConfig403.java`
* `$(FLINT_PATH)` is the path of `flint.sh` file for Mac/Linux or the `flint.bat` file for Windows
* `$(CONFIG_PATH)` is the path to the directory containing `FlintConfig403.class`
* `$(CLASS_NAME)` is name of the configuration class
* `$(FILE_PATH)` is the path to the file you want to run Flint on

#### IntelliJ IDEA Plugin

To run Flint with Intellij, once you have it installed click the Flint icon in the Intellij IDEA toolbar and choose either “Run Flint” or “Run Flint on file save”.

Clicking “Run Flint” will run Flint once using the configuration file in the root directory of your project on the currently open file.

Clicking “Run Flint on file save” will run Flint using the configuration file in the root directory of your project on the currently open file each time it is saved.


### Configuring FlintConfig403
When using Flint, you have full control over what checks to run on your Java files and how to run them. This behavior is defined in the configuration file. Once you have the `FlintConfig403.java` file downloaded you will see that it comes with a set of premade rules that are already enabled and ready to run on your Java files:

```java
Collection<LintFailure> result = new HashSet<>();
result.addAll(CharacterLimitRule.run(inputFile, 80));
result.addAll(ConsecutiveEmptyLineLimitRule.run(inputFile, 1));
result.addAll(NoTrailingWhitespaceRule.run(inputFile));
```

If you do not want to include one or more of the premade rules, simply remove the line of code corresponding to that rule.

To add rules add an import to the given rule class at the top of FlintConfig403.java and add the line of code below under the rest of the check calls:
```java
result.addAll(RuleClassName.run(inputFile, Parameters));
```

To create new rules to add to your configuration, see the next section, [Creating New Rules](#creating-new-rules)

If you are using the CLI version of Flint, once you are done editing use the following command to compile your new configuration file:
```bash
javac $(CONFIG_JAVA_PATH)
```

### Creating New Rules

When writing new rules we recommend creating a new rule class and then importing and adding it to your `FlintConfig403.java` file. You have the option to create new rules directly in your `FlintConfig403.java` file, however creating new rules in separate files will help you easily manage your configuration file and keep it clean and readable.

To create a new rule from scratch start by making a new java class: `RuleClassName.java`.

The configuration class will be given access to a `CompilationUnit` (the root of an Abstract Syntax Tree representation of the Java file) and a `RandomAccessFile` (a file reader/writer with read-access to the file).

Your rules may need one of these (or both) to process the file. You should pass these and any other parameters to your rules. In `ConsecutiveEmptyLineLimitRule` below you can see that the `run` method takes the input file and a line limit as parameters, where the line limit is the number of consecutive empty lines allowed.

```java
package com.github.s92025592025.flint.rules;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashSet;

import com.github.s92025592025.flint.LintFailure;

public class ConsecutiveEmptyLineLimitRule {
  public static Collection<LintFailure> run(RandomAccessFile inputFile, int lineLimit) throws IOException {
    Collection<LintFailure> result = new HashSet<>();
    int lineNum = 1;
    int prevLineLength = 0;
    int consecutive = 0;
    String line;
    while ((line = inputFile.readLine()) != null) {
      if (line.trim().length() == 0) {
        consecutive++;
      } else {
        if (consecutive > lineLimit) {
          result.add(new LintFailure(lineNum - consecutive, lineNum - 1, 1, prevLineLength + 1,
              "There are over " + lineLimit + " consecutive empty lines."));
        }
        consecutive = 0;
      }
      lineNum++;
      prevLineLength = line.length();
    }

    inputFile.seek(0);
    return result;
  }
}
```

For each new class you make, the `run` method should return a `Collection<LintFailure>`, which is a collection of failure reports found in your Java file for the given rule. To create a new `LintFailure` object use the following code and fill in the parameters:

```java
LintFailure error = new LintFailure(int  errorLineStart, int  errorLineEnd, int  errorColStart, int  errorColEnd, String errorMessage);
```

At the end of each `run` method you write, before returning the `Collection<LintFailure>` insert the line:

```java
inputFile.seek(0);
```

This sets the pointer of the `RandomAccessFile` back to the start of the file for other rule classes to use.
<!--stackedit_data:
eyJoaXN0b3J5IjpbODk2MDcwNjI0LC0yMDQxNzM3MzQ1LDEzMD
MwNzkxMzIsLTE4MTgzNDM4NTMsMTE1NzkwNzE5NCwtMTY0NjIw
NDM0NCwtMTYxMDY2MzI4MSwtOTQwNDI5NzAsLTE2NTg0NTk3NT
YsMTY0MDQxNjg3MiwtMTc2ODA4NzIwMSwtNjYxNDU4OTIsLTE5
MjI0MjY2MjksMjc3MTM3NzkwLDE0Mzg0MDc5NDEsLTk2NDk5MD
Y3NCwtMTU0Mzc3MTc2NiwtMTQwNTE0MTQ2LC0xNzgyOTQ2ODQw
LC05MDg4NDc4NDldfQ==
-->
