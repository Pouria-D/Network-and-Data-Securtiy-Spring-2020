str1 = ["a1","b2","c3","d4"];
State = int16(hex2dec(str1));
str2 = ["02","03","01","01";
    "01","02","03","01";
    "01","01","02","03";
    "03","01","01","02";
   ];
Mat = int16(hex2dec(str2));
x = int16([0,0,0,0]);
for j = 1:4
    for i = 1:length(x)
        x(j) = bitxor(x(j), multiplier(State(i),Mat(j,i)));
    end
end

Ans = dec2hex(x)

str3 = ["0e","0b","0d","09";
    "09","0e","0b","0d";
    "0d","09","0e","0b";
    "0b","0d","09","0e";
   ];
 Inv_Mat = int16(hex2dec(str3));  
y = int16([0,0,0,0]);
for j = 1:4
    for i = 1:length(x)
        y(j) = bitxor(y(j), multiplier(x(i),Inv_Mat(j,i)));
    end
end
Inv_Ans = dec2hex(y)