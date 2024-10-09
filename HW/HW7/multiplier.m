function mul = multiplier(x,y)
mul = int16(0);
m = int16(27);
b7 = int16(256);
for i = 0:7
    if(bitand(b7,x))
        x = x-256;
        temp = bitxor(x,m);
        x = temp;
    end
    if(bitand(bitsrl(y,i),1))
        mul = bitxor(mul,x);
    end
    x = bitsll(x,1);
end
end