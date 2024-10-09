import random
import math


def Random_Generator1():
    Range = pow(10, 6)
    return random.randint(1, Range)


def Random_Generator2(a, m, c, X0):
    return ((a * X0) + c) % m


"""
for i in range(0, 10):
    print(Random_Generator())
"""


def gcd_Caculator(a, b):
    # First Make sure that a is bigger number :
    if a < b:
        temp = a
        a = b
        b = temp
    if a % b == 0:
        return b
    a = a % b
    gcd = gcd_Caculator(a, b)
    return gcd


"""
print(gcd_Caculator(96,60))
"""


def Square_Roots(a):
    return math.sqrt(a)


"""
print(Square_Roots(2346.3432))
"""


def RandomTest():
    N = 1000
    m = 997
    a = 3
    X0_a = random.randint(1, m)
    c_a = random.randint(1, m)
    X0_b = random.randint(1, m)
    c_b = random.randint(1, m)
    # first number of "a" stream and "b" stream
    a2 = Random_Generator2(a, m, c_a, X0_a)
    b2 = Random_Generator2(a, m, c_b, X0_b)
    count = 0
    for i in range(0, N):
        a1 = Random_Generator1()
        b1 = Random_Generator1()
        ########
        a2 = Random_Generator2(a, m, c_a, a2)
        b2 = Random_Generator2(a, m, c_b, b2)
        if a2 == 0:
            a2 = 1
        if b2 == 0:
            b2 = 1
        ########
        # k = gcd_Caculator(a1, b1)
        k = gcd_Caculator(a2, b2)
        if k == 1:
            count = count + 1
    P = count / N
    Estimated_pi = Square_Roots(6 / P)
    return Estimated_pi


# for i in range(0, 10):
#   print("The Estimated Value of pi from the 'system random generator' is : " + str(RandomTest()))

for i in range(0, 10):
    print("The Estimated Value of pi from the 'Linear Congruential Generator' is : " + str(RandomTest()))
