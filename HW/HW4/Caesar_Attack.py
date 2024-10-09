# In The Name Of GOD
import string, sys
from Caesar_Decrypt import decrypt

ciphertext = input("Ciphertext to break: ")

# print("")
# check the frequency of each letter occurrence (A to Z)

for l in list(map(chr, range(ord('A'), ord('Z')))):
    # print("dividing",ciphertext.count(l),"by",float(len(ciphertext)))
    freq = ciphertext.count(l) / float(len(ciphertext))
    # if letter appears 10% or more it could be 'E'
    if freq >= .1:
        # decrypt all letters with freq >=10% using them as E
        print("Possible key: A=" + l + ". Attempting decryption...")
        # shift so decrypt key is A=
        key = chr(ord(l) - 4)
        print(decrypt(key, ciphertext) + "\n")
