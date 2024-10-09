from functools import reduce

# constants used in the multGF2 function
mask1 = mask2 = polyred = None


def setGF2(degree, irPoly):
    """Define parameters of binary finite field GF(2^m)/g(x)
       - degree: extension degree of binary field
       - irPoly: coefficients of irreducible polynomial g(x)
    """

    def i2P(sInt):
        """Convert an integer into a polynomial"""
        return [(sInt >> i) & 1
                for i in reversed(range(sInt.bit_length()))]

    global mask1, mask2, polyred
    mask1 = mask2 = 1 << degree
    mask2 -= 1
    polyred = reduce(lambda x, y: (x << 1) + y, i2P(irPoly)[1:])


def multGF2(p1, p2):
    """Multiply two polynomials in GF(2^m)/g(x)"""
    p = 0
    while p2:
        if p2 & 1:
            p ^= p1
        p1 <<= 1
        if p1 & mask1:
            p1 ^= polyred
        p2 >>= 1
    return p & mask2



# We Know that for input (S0,j  S1,j  S2,j  S3,j) Calculate (S'0,j  S'1,j  S'2,j  S'3,j) In this way:
# S'0,j = (2.S0,j) + (3.S1,j) + (S2,j)   + (S3,j)
# S'1,j = (S0,j)   + (2.S1,j) + (3.S2,j) + (S3,j)
# S'2,j = (S0,j)   + (S1,j)   + (2.S2,j) + (3.S3,j)
# S'3,j = (3.S0,j) + (S1,j)   + (S2,j)   + (2.S3,j)

# So By using product function in GF(2^8) which we wrote in HW3 we calculate this array...

# Define binary field GF(2^8)/x^8 + x^4 + x^3 + x + 1
# (used in the Advanced Encryption Standard-AES)
setGF2(8, 0b100011011)

# the first input is (A1 B2 C3 D4):
arr0 = [multGF2(0x02, 0xA1), multGF2(0x03, 0xB2), multGF2(0x01, 0xC3), multGF2(0x01, 0xD4)]
arr1 = [multGF2(0x01, 0xA1), multGF2(0x02, 0xB2), multGF2(0x03, 0xC3), multGF2(0x01, 0xD4)]
arr2 = [multGF2(0x01, 0xA1), multGF2(0x01, 0xB2), multGF2(0x02, 0xC3), multGF2(0x03, 0xD4)]
arr3 = [multGF2(0x03, 0xA1), multGF2(0x01, 0xB2), multGF2(0x01, 0xC3), multGF2(0x02, 0xD4)]

Sp0 = multGF2(0x02, 0xA1)
Sp1 = multGF2(0x01, 0xA1)
Sp2 = multGF2(0x01, 0xA1)
Sp3 = multGF2(0x03, 0xA1)

for i in range(1, 4):
    Sp0 = Sp0 ^ arr0[i]
for i in range(1, 4):
    Sp1 = Sp1 ^ arr1[i]
for i in range(1, 4):
    Sp2 = Sp2 ^ arr2[i]
for i in range(1, 4):
    Sp3 = Sp3 ^ arr3[i]

print("The MixColumn is :\n")
print(hex(Sp0)[2:].upper())
print(hex(Sp1)[2:].upper())
print(hex(Sp2)[2:].upper())
print(hex(Sp3)[2:].upper())

# Checking the answer by calculating the inverse MixColumn :

test0 = [multGF2(0x0E, Sp0), multGF2(0x0B, Sp1), multGF2(0x0D, Sp2), multGF2(0x09, Sp3)]
test1 = [multGF2(0x09, Sp0), multGF2(0x0E, Sp1), multGF2(0x0B, Sp2), multGF2(0x0D, Sp3)]
test2 = [multGF2(0x0D, Sp0), multGF2(0x09, Sp1), multGF2(0x0E, Sp2), multGF2(0x0B, Sp3)]
test3 = [multGF2(0x0B, Sp0), multGF2(0x0D, Sp1), multGF2(0x09, Sp2), multGF2(0x0E, Sp3)]

S0 = multGF2(0x0E, Sp0)
S1 = multGF2(0x09, Sp0)
S2 = multGF2(0x0D, Sp0)
S3 = multGF2(0x0B, Sp0)

for i in range(1, 4):
    S0 = S0 ^ test0[i]
for i in range(1, 4):
    S1 = S1 ^ test1[i]
for i in range(1, 4):
    S2 = S2 ^ test2[i]
for i in range(1, 4):
    S3 = S3 ^ test3[i]

print("The Main Column was :\n")
print(hex(S0)[2:].upper())
print(hex(S1)[2:].upper())
print(hex(S2)[2:].upper())
print(hex(S3)[2:].upper())

# Now see the difference in the output by changing "A1" to
arr0 = [multGF2(0x02, 0xA3), multGF2(0x03, 0xB2), multGF2(0x01, 0xC3), multGF2(0x01, 0xD4)]
arr1 = [multGF2(0x01, 0xA3), multGF2(0x02, 0xB2), multGF2(0x03, 0xC3), multGF2(0x01, 0xD4)]
arr2 = [multGF2(0x01, 0xA3), multGF2(0x01, 0xB2), multGF2(0x02, 0xC3), multGF2(0x03, 0xD4)]
arr3 = [multGF2(0x03, 0xA3), multGF2(0x01, 0xB2), multGF2(0x01, 0xC3), multGF2(0x02, 0xD4)]

Sz0 = multGF2(0x02, 0xA3)
Sz1 = multGF2(0x01, 0xA3)
Sz2 = multGF2(0x01, 0xA3)
Sz3 = multGF2(0x03, 0xA3)

for i in range(1, 4):
    Sz0 = Sz0 ^ arr0[i]
for i in range(1, 4):
    Sz1 = Sz1 ^ arr1[i]
for i in range(1, 4):
    Sz2 = Sz2 ^ arr2[i]
for i in range(1, 4):
    Sz3 = Sz3 ^ arr3[i]

print("The Changed MixColumn is :\n")
print(hex(Sz0)[2:].upper())
print(hex(Sz1)[2:].upper())
print(hex(Sz2)[2:].upper())
print(hex(Sz3)[2:].upper())


