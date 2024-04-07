
# ROMAN NUMERAL CONVERSION
This repo contains the scala code to convert roman numerals to arabic numerals and viceversa. numbers-numerals.csv contains the conversion list for 1 to 3999 numbers. 
The list of conversions are taken from https://oeis.org/A006968/a006968.txt. 
The rules to implement the conversions are taken from https://www.cuemath.com/numbers/roman-numerals/

+ Rule 1: When certain numerals are repeated, the number represented by them is their sum. For example, II = 1 + 1 = 2, or XX = 10 + 10 = 20, or, XXX = 10 + 10 + 10 = 30.
+ Rule 2: It is to be noted that no Roman numerals can come together more than 3 times. For example, we cannot write 40 as XXXX
+ Rule 3: The letters V, L, and D are not repeated.
+ Rule 4: Only I, X, and C can be used as subtractive numerals. There can be 6 combinations when we subtract. These are IV = 5 - 1 = 4; IX = 10 - 1 = 9; XL = 50 - 10 = 40; XC = 100 - 10 = 90; CD = 500 - 100 = 400; and CM = 1000 - 100 = 900
+ Rule 5: When a Roman numeral is placed after another Roman numeral of greater value, the result is the sum of the numerals. For example, VIII = 5 + 1 + 1 + 1 = 8, or, XV = 10 + 5 = 15,
+ Rule 6: When a Roman numeral is placed before another Roman numeral of greater value, the result is the difference between the numerals. For example, IV = 5 - 1 = 4, or, XL = 50 - 10 = 40, or XC = 100 - 10 = 90
+ Rule 7: When a Roman numeral of a smaller value is placed between two numerals of greater value, it is subtracted from the numeral on its right. For example, XIV = 10 + (5 - 1) = 14, or, XIX = 10 + (10 - 1) = 19
+ Rule 8: To multiply a number by a factor of 1000 a bar is placed over it.
+ Rule 9: Roman numerals do not follow any place value system.
+ Rule 10: There is no Roman numeral for zero (0).

## IMPLEMENTATION LOGIC

### Roman numeral to Arabic numeral conversion

#### Create tests
1. Create tests that read each numeral from the file as the input
2. The expected value should be the corresponding number from the file
3. Create negative tests to check Rule 2 and Rule 3

#### Map values   
1. Create 2 maps - one that has the conversions for the base numerals - I,V,X,L,C,D,M and another that has the conversions for the subtractive pairs IV, IX, XL, XC, CD, CM
2. Create a regex that checks the Rule 2

#### Parse the inputs
1. Generate a list containing numeral pairs. This is to check if the numeral string has any of the subtractive pairs so they can be added separately.
2. Check if the list contains subtractive pair. For this, check each pair in the list if the first item of the pair has lower value than the second item. If true then this pair in the list is a subtractive pair
3. Add all the subtractive pairs in the list(take the appropriate values from the map)
4. Remove the subtractive pairs if any from the numeral string. To get this write a recursive function that checks if any of the subtractive pairs exists in the string. If it exists then   replace the subtractive pair in the string with empty string "".
5. Add the individual numerals according to the mapped values.
6. Add the added values from the subtractive and individual numerals.

### Arabic numeral to Roman numeral conversion

#### Create tests
1. Create tests that read each number from the file as the input
2. The expected value should be the corresponding numeral from the file
3. Create negative tests to check Rule 10 and negative numbers

#### Map values   
1. Create a map that has the conversions for the base numerals - I,V,X,L,C,D,M and the subtractive pairs IV, IX, XL, XC, CD, CM

#### Parse the inputs
1. Create a group of conditions to check if the input number is within a lower and upper limit.
2. If the number is more than the lower limit and less than the upper limit of a condition, then get the new number by subtracting the value of the lower limit number and add that number  to the list
   ```
   newNum = inputNum -lowerLimitNum, List(lowerLimitNum)
   ```
3. Pass the new number with the list of lower limit numbers to the function again
4. Do this recursively until the number is subtracted until zero
5. When the number reaches zero return the list of lower limit numbers
6. Convert the numbers in the list to the corresponding numerals as per the map
