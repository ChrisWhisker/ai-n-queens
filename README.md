# N-Queens Problem Solver
This Java program generates up to n*2 solutions to the n-queens problem using backtracking search with either forward checking or maintaining arc consistency (MAC). A solution to the n-queens problem is any configuration of n queens on an n x n chess board where no two queens are threatening each other.

## Running the program
1. Compile the program.

Open a command-line window in the src directory which contains NQueens.java and run this command:
```
javac NQueens.java
```

2. Run the program.

Run this command, replacing the arguments with the suggestions listed below.
```
java NQueens <ALG> <N> <ALG>_<N>
```

- `<ALG>` the algorithm you wish to use, either `FOR` (forward checking) or `MAC` (maintaining arc consistency)
- `N` integer size of the chessboard. must be 1 or greater.
- `<ALG>_<N>` optional argument defining the name of the output file. If this argument isn't specified, the results will be written to `<FOR/MAC>_<N>.txt` in the src folder.

For example, `java NQueens FOR 10 FORWARD_CHECKING_10.txt` or `java NQueens MAC 25`.

3. You're done!

When the program is finished running, it will print a success message to the command line. Check the newly created file to see the number of solutions found, execution time, number of backtracks, and a visual representation of each solution.

---

### Alternative method

For ease of use, the program can also be ran without the command-line arguments in step 2, like so:
```
java NQueens
```
In this case, the program will ask the user for the size of the chessboard and which algorithm to use.

---

## Example output
This is an example of what can be expected in the output file for an input of `java NQueens FOR 10`:

```
FORWARD CHECKING
Solutions : 20 (max required for N of 10)
Time taken : 1 milliseconds
Backtracks : 1119

#1
1 0 0 0 0 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 

#2
1 0 0 0 0 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 1 0 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 

#3
1 0 0 0 0 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 1 0 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 1 0 0 0 0 0 

#4
1 0 0 0 0 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 1 0 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 1 0 0 0 0 

#5
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 0 1 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 

#6
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 1 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 1 0 0 0 0 

#7
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 1 0 0 0 0 

#8
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 

#9
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 0 1 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 

#10
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 0 1 0 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 

#11
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 1 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 

#12
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 

#13
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 0 1 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 1 0 0 0 0 0 

#14
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 

#15
1 0 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 1 0 0 0 0 

#16
1 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 0 1 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 

#17
1 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 1 0 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 

#18
1 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 

#19
1 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 1 0 0 0 0 0 0 0 
0 0 0 0 0 0 1 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 0 0 0 0 1 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 

#20
1 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 0 0 0 0 0 
0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 1 0 0 0 
0 0 0 1 0 0 0 0 0 0 
0 1 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 1 0 
0 0 0 0 0 1 0 0 0 0 
0 0 1 0 0 0 0 0 0 0 
```