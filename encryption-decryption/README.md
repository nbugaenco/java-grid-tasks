# Encryption-decryption

#### Is a program, that uses CLI arguments to encrypt or decrypt strings

- [x] Encryption and decryption using two methods: **shift** and **unicode**
- [x] Parameters using CLI arguments
- [x] Enum for parameters
- [x] Fool proof
- [x] _Factory_ design pattern

## Usage

List of available arguments:

- `-mode`: **enc**, **dec**
- `-key`: an **integer** number specifying the shift of characters in a string
- `-alg`: **shift** or **unicode** algorithm (**shift** encoding only english letters inside alphabet; **unicode**
  encoding all chars by **key**)
- `-in`, `-out`: _/path/to/file_, files to read from (`-in`) and write out (`-out`) respectively

### Tests example

1. Args: `-mode enc -data "Hello World!" -key 5 -alg unicode`

   Result: `Mjqqt%\twqi&`

2. Decrypt: `-mode dec -data "Mjqqt%\twqi&" -key 5 -alg unicode`

   Result: `Hello World!`

3. Args: `-data "Test without some args" -key 5` (it uses default values: `-mode enc`, `-alg shift`)

   Result: `Yjxy bnymtzy xtrj fwlx`

4. Args: `-mode enc -alg unicode -key 7 -in in.txt -out out.txt`

   <img width="255" alt="Screenshot 2023-03-07 at 11 26 26" src="https://user-images.githubusercontent.com/126724439/223379984-08e6ab30-aeff-4a25-a37f-0c8ab0932cdd.png">

Result:


   <img width="248" alt="Screenshot 2023-03-07 at 11 26 56" src="https://user-images.githubusercontent.com/126724439/223380111-9d6a8fdc-7155-4e61-ae1b-cdc98e03a146.png">

5. Args: `-mode enc -alg unicode -key 3 -in in.txt`

   <img width="418" alt="Screenshot 2023-03-07 at 11 31 30" src="https://user-images.githubusercontent.com/126724439/223381301-83825b36-a2d4-4a71-b868-0d9067799e05.png">

Result:
`Qrz#wu|#lw#xvlqj#ilohv#zlwkrxw#rxwsxw#lq#iloh`
