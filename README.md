**Entrance Test Assignment**

Example of implementation for two tasks:
1. Caesar Cipher encryption/decryption
2. Arithmetic expression evaluator

Part 1: Caesar Cipher Implementation
Functionality:
- Accept text input in Russian and English languages from console
- Accept text input in Russian and English languages from a file 
- Accept a shift value (integer)
- Encrypt plaintext using Caesar cipher algorithm
- Decrypt ciphertext back to plaintext, with and without receiving a shift value
Encryption Requirements:
- Support both Cyrillic (Russian) and Latin (English) alphabets
- Preserve case (uppercase/lowercase)
- Non-alphabetic characters (spaces, punctuation, numbers) should remain unchanged
- Handle wrap-around (e.g., with shift 3: 'z' becomes 'c', 'я' becomes 'в')
- Support both positive and negative shift values

Part 2: Arithmetic Expression Evaluator
Functionality:
- Parse and evaluate arithmetic expressions
- Support basic operations: addition (+), subtraction (-), multiplication (*), division (/)
- Support parentheses for operation precedence
- Return the calculated result
Requirements:
- Follow standard mathematical order of operations (PEMDAS/BODMAS)
- Handle nested parentheses
- Support decimal numbers
- Handle division by zero appropriately
- Support negative numbers

