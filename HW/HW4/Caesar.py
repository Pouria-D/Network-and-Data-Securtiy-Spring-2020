import string

#
# Relative letter frequencies for the french and english languages.
# First item is letter 'A' frequency and so on.
#
ENGLISH = (0.0749, 0.0129, 0.0354, 0.0362, 0.1400, 0.0218, 0.0174, 0.0422, 0.0665, 0.0027, 0.0047, 0.0357,
           0.0339, 0.0674, 0.0737, 0.0243, 0.0026, 0.0614, 0.0695, 0.0985, 0.0300, 0.0116, 0.0169, 0.0028,
           0.0164, 0.0004)


# This program can be used to decipher Caesar encoding as produced by the
# function bellow
def cipher(s, key):
    r = ''
    ASC_A = ord('a')
    for c in s.lower():
        if 'a' <= c <= 'z':
            r += chr(ASC_A + (ord(c) - ASC_A + key) % 26)
        else:
            r += c
    return r


# compute letter frequencies delta
def delta(source, dest):
    N = 0.0
    for f1, f2 in zip(source, dest):
        N += abs(f1 - f2)
    return N


# compute letter frequencies from a text
def frequency(s):
    D = dict([(c, 0) for c in string.lowercase])
    N = 0.0
    for c in s:
        if 'a' <= c <= 'z':
            N += 1
            D[c] += 1
    L = D.items()
    L.sort()
    return [f / N for (l, f) in L]


# deciphering caesar code by letter frequencies analysis
def decipher(s):
    deltamin = 1000
    bestrot = 0
    freq = frequency(s)
    for key in range(26):
        d = min([delta(freq[key:] + freq[:key], ENGLISH)])
        if d < deltamin:
            deltamin = d
            bestrot = key
    return cipher(s, -bestrot)


#
# Some tests
#

T1 = """
Python is an easy to learn, powerful programming language. It has 
efficient high-level data structures and a simple but effective approach 
to object-oriented programming. Python's elegant syntax and dynamic
typing, together with its interpreted nature, make it an ideal language 
for scripting and rapid application development in many areas on most platforms. 

Python tutorial
"""


import random
command = input("Welcome to My Ceasar cipher analysis\n If you have a main text and you want to Encrypt it type 'E' and If you want to Decrypt an Encrypted text type 'D'")
while True:
    if command == "E":
        text = input("Enter the main message:")
        key = random.randrange(26)
        Encrypted_text = cipher(text, key)
        print(Encrypted_text)
    elif command == "D":
        text = input("Enter the Encrypted text:")
        print(decipher(text))
    elif command == "q":
        break
    else:
        print("Command not found")
    command = input("next operation: ( if you want to exit type 'q'")