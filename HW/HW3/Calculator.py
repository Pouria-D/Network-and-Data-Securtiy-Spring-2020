
def byte(x, n=8):
    return format(x, f"0{n}b")

def gf_2_8_product(a, b):
    tmp = 0
    b_byte = bin(b)[2:]
    for i in range(len(b_byte)):
        tmp = tmp ^ (int(b_byte[-(i+1)]) * (a << i))

    mod = int("100011011", 2)
    exp = len(bin(tmp)[2:])
    diff = exp - len(bin(mod)[2:]) + 1

    for i in range(diff):
        if byte(tmp, exp)[i] == 1:
            tmp = tmp ^ (mod << diff - i - 1)
    return tmp

def gf_2_8_divide(a,b):
    c = int("00", 16)
    while c < 0xff:
        d = gf_2_8_product(c, b)
        if d == a:
            return c
        c = c + 0x01
    return 0

def gf_2_8_calculator():
    print("\nThis Calculator works in GF(2^8) field\nand has four operation + , -, *, /\nThe irreducible polynomial is m(x) = x^8 + x^4 + x^3 + x + 1\n")
    print("Write 'help' to get help")
    command = input()
    stack = []
    while True:
            if command == "q" or "quit":
                break
            elif command == "help":
                print("This is a Postfix caculator")
                print("Write number in GF(2^8) in the hexadecimal form. Ex: 2A")
                print("Write '+' to sum the previous two numbers")
                print("Write '-' to minus the previous two numbers")
                print("Write '*' to multiply the previous two numbers")
                print("Write '/' to divide the previous two numbers")
                print("Write 'print' or 'p' to print the stack")
                print("Write 'clear' to clear the stack")
                print("Write 'q' or 'quit'to exit the calculator")
            elif command == "p" or command == "print":
                print(stack)
            elif command == "clear":
                stack = []
            elif command == "+" or command == "-":
                if len(stack) < 2:
                    print("Enter a number first!")
                else:
                    tmp = stack.pop() ^ stack.pop()
                    print("0x" + hex(tmp)[2:].upper())
                    stack.append(tmp)
            elif command == "*":
                if len(stack) < 2:
                    print("Enter a number first!")
                else:
                    tmp = gf_2_8_product(stack.pop(), stack.pop())
                    print("0x" + hex(tmp)[2:].upper())
                    stack.append(tmp)
            elif command == "/":
                if len(stack) < 2:
                    print("Enter a number first!")
                else:
                    tmp = gf_2_8_divide(stack.pop(), stack.pop())
                    print("0x" + hex(tmp)[2:].upper())
                    stack.append(tmp)
            else:
                stack.append(int(command, 16))
            command = input()

gf_2_8_calculator()
