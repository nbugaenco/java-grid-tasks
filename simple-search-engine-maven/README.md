# Simple Search Engine
This SSE uses **Inverted Index** to find the data by search query. It has three strategies: `ALL`, `ANY` and `NONE`.

## Project goals
1. [x] Write **Inverted Index** Search Engine
2. [x] Make data **input from file**
3. [x] Using of **patterns**
4. [x] Make simple **user menu**
5. [x] Make several **search strategies**
6. [x] Make **Maven Project** with standard **project structure**
7. [x] Write **Unit tests**

### Usage
To start using this program, you need to provide a **file** with **some data**.

You can provide a file using CLI arguments:
`--data /path/to/file.txt`

In the program you will see this menu:

```
=== Menu ===
1. Find a person
2. Print all people
0. Exit
```

1. Find a person – user choosing one of search strategies (`ALL`, `ANY`, `NONE`) and write a search query;
2. Print all people – program printing all lines from input data;
3. Exit (0) – exit the program;
4. By other values nothing happens. You'll see a message and menu will be printed again.

### Test example
For example, we have this dataset:

```
=== List of people ===
Dwight Joseph djo@gmail.com
Rene Webb webb@gmail.com
Katie Jacobs
Erick Harrington harrington@gmail.com
Myrtle Medina
Erick Burgess

=== Menu ===
1. Find a person
2. Print all people
0. Exit
```
#### Let's try all strategies:
- **`ALL`** – program print lines containing all the words from the query

```
Enter your choice: 1

Select a matching strategy (ALL, ANY, NONE): ALL

Enter a name or email to search all suitable people:
Harrington Erick

=== Found people ===
Erick Harrington harrington@gmail.com
```

- **`ANY`** – program print lines containing at least one word from the query

```
Enter your choice: 1

Select a matching strategy (ALL, ANY, NONE): ANY

Enter a name or email to search all suitable people:
Erick Dwight webb@gmail.com

=== Found people ===
Dwight Joseph djo@gmail.com
Rene Webb webb@gmail.com
Erick Harrington harrington@gmail.com
Erick Burgess
```

- **`NONE`** – program print lines that do not contain words from the query at all

```
Enter your choice: 1

Select a matching strategy (ALL, ANY, NONE): NONE

Enter a name or email to search all suitable people:
djo@gmail.com ERICK

=== Found people ===
Rene Webb webb@gmail.com
Katie Jacobs
Myrtle Medina
```