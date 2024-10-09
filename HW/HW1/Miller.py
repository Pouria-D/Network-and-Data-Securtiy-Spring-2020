import math
import random
def Miller(n):
    k = 0
    q = n - 1
    while q % 2 == 0:
        q = q/2
        k = k + 1
    q = int(q)

    # find a < n wich (a,n) = 1
    a = random.randint(2, n-2)
    flag = 0
    if pow(a, q, n) == 1:
        return 0
    else:
        for j in range(0,k):
            if pow(a, q*pow(2, j), n) == n-1:
                return 0
        return 1


a = 0
n = int(input())
for i in range(0, 10):
    a += Miller(n)
if a > 0:
    print("composit")
else:
    print("inconclusive")
import math

''' for i in range(0, 8):
     c = c + pow(10, i)*((a % 10) + (b % 10) - (a % 10)*(b % 10))
     a = int(a / 10)
     b = int(b / 10)

def Calculator(a,b,o):

    if o == '+' or o == '-':
        c = a ^ b
        return c
    elif o == '*':
        return 0

print("\nThis Calculator works in GF(2^8) field\nand has four operation + , -, *, /\nThe irreducible polynomial is m(x) = x^8 + x^4 + x^3 + x + 1\n")
print("Enter the first binary number(8 digits):")
a = hex(input())
print("Enter the operation:")
o = input()
print("Enter The second binary number(8 digits):")
b = hex(input())
tmp = a ^ b
print("0x" +  hex(tmp)[2:].upper())
#c = Calculator(bin_a, bin_b, o)
#print(c)
'''
