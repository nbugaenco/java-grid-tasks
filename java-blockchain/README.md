# Java Blockchain

Java Blockchain is a program that simulates a microcosm where virtual miners earn cryptocurrency and exchange
transactions using a blockchain.

## Project Goals

1. [x] Create a simple **Blockchain** system (without **Proof of Work** yet)
2. [x] Implement the **Proof of Work** concept
3. [x] Make the program **multithreaded**
4. [x] Implement **cryptocurrency**
5. [x] Implement **thread transactions** send system

## Features

- Simple and easy-to-understand implementation of a blockchain system
- Multithreaded mining process for efficient block creation
- Proof of Work implementation for mining new blocks
- Cryptocurrency reward system for miners
- Thread transaction system for sending cryptocurrency between miners

## Usage

To start using this program, simply clone the repository and run the `Main` class. By default, the program creates a *
*blockchain** with **7 blocks** in it, but you can modify the **blocks count** as well as the **start difficulty of the
blockchain**.

At the end of the program, you will see all blocks created with their information and the balances of each miner.

## Example Output

Below is an example output of the program after successfully mining 7 blocks:

```bash
Started mining...

Block:
Created by miner # 1
Miner 1 gets 100 VC
Id: 1
Timestamp: 1680013989
Magic number: 0
Hash of the previous block:
0
Hash of the block:
0cfde7bbce4d65d01236f09fb73121f1d8f25ff84b64a4c745c1cbd038d70c93
Block data:
no transactions
Block was generating for 0 seconds
N was increased to 1

Block:
Created by miner # 2
Miner 2 gets 100 VC
Id: 2
Timestamp: 1680013989
Magic number: 1346819726
Hash of the previous block:
0cfde7bbce4d65d01236f09fb73121f1d8f25ff84b64a4c745c1cbd038d70c93
Hash of the block:
0f365fe06abd53ad3919852224add92464ca34bbe05f380c6e3563b19727a614
Block data:
Miner 4 sent 25 VC to Miner 3
Miner 12 sent 54 VC to Miner 1
Miner 5 sent 53 VC to Miner 4
Miner 3 sent 38 VC to Miner 8
Miner 1 sent 183 VC to Miner 6
Block was generating for 0 seconds
N was increased to 2

Block:
Created by miner # 9
Miner 9 gets 100 VC
Id: 3
Timestamp: 1680013989
Magic number: 914641153
Hash of the previous block:
0f365fe06abd53ad3919852224add92464ca34bbe05f380c6e3563b19727a614
Hash of the block:
000e8f3ef8478e6c8088685db1d7dc05ebf45b5f63d197c63e8e87d98c24013f
Block data:
Miner 2 sent 121 VC to Miner 4
Miner 1 sent 45 VC to Miner 10
Miner 7 sent 3 VC to Miner 6
Block was generating for 0 seconds
N was increased to 3

Block:
Created by miner # 6
Miner 6 gets 100 VC
Id: 4
Timestamp: 1680013989
Magic number: 448359424
Hash of the previous block:
000e8f3ef8478e6c8088685db1d7dc05ebf45b5f63d197c63e8e87d98c24013f
Hash of the block:
000805c827dc9602a9397ce9441fc23fa88f41dddb0482cd674276e9f3da4b37
Block data:
Miner 9 sent 149 VC to Miner 11
Miner 12 sent 7 VC to Miner 5
Miner 6 sent 14 VC to Miner 7
Block was generating for 0 seconds
N was increased to 4

Block:
Created by miner # 2
Miner 2 gets 100 VC
Id: 5
Timestamp: 1680013989
Magic number: 1313131792
Hash of the previous block:
000805c827dc9602a9397ce9441fc23fa88f41dddb0482cd674276e9f3da4b37
Hash of the block:
0000968027b2d7aeb36f33285ce821d15e751392960716dc0f2828e034fb4df5
Block data:
Miner 2 sent 31 VC to Miner 7
Miner 1 sent 18 VC to Miner 12
Miner 2 sent 13 VC to Miner 6
Block was generating for 1 seconds
N was increased to 5

Block:
Created by miner # 5
Miner 5 gets 100 VC
Id: 6
Timestamp: 1680013990
Magic number: 1805567247
Hash of the previous block:
0000968027b2d7aeb36f33285ce821d15e751392960716dc0f2828e034fb4df5
Hash of the block:
00000da4b247b7e560490b052f5296b0231c3f899854a255e761f56d6a82fdb6
Block data:
Miner 1 sent 5 VC to Miner 5
Miner 2 sent 61 VC to Miner 6
Miner 1 sent 2 VC to Miner 12
Miner 5 sent 35 VC to Miner 2
Miner 8 sent 85 VC to Miner 1
Miner 3 sent 64 VC to Miner 7
Miner 9 sent 1 VC to Miner 10
Miner 11 sent 222 VC to Miner 10
Miner 8 sent 42 VC to Miner 4
Miner 4 sent 38 VC to Miner 12
Block was generating for 0 seconds
N was increased to 6

Block:
Created by miner # 12
Miner 12 gets 100 VC
Id: 7
Timestamp: 1680013990
Magic number: 917932224
Hash of the previous block:
00000da4b247b7e560490b052f5296b0231c3f899854a255e761f56d6a82fdb6
Hash of the block:
0000003357f7e0b547fdf9806e259e87b6df9f0d13ec242e4b57818c79e59d07
Block data:
Miner 2 sent 31 VC to Miner 1
Miner 3 sent 19 VC to Miner 7
Block was generating for 1 seconds
N was increased to 7

Miner 1 balance: 117 VC
Miner 2 balance: 78 VC
Miner 3 balance: 4 VC
Miner 4 balance: 253 VC
Miner 5 balance: 124 VC
Miner 6 balance: 446 VC
Miner 7 balance: 225 VC
Miner 8 balance: 11 VC
Miner 9 balance: 50 VC
Miner 10 balance: 368 VC
Miner 11 balance: 27 VC
Miner 12 balance: 197 VC
```

## Dependencies

- Java 8 or later