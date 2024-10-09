%% In The Name Of GOD
%% This code has written to pretends hand calculating for question 3.22
m1 = 'I thought to see the fairies in the fields, but I saw only the evil elephants with their black'; 
m2 = 'backs. Woe! how that sight awed me! The elves danced all around and about while I heard';
m3 = 'voices calling clearly. Ah! how I tried to see—throw off the ugly cloud—but no blind eye';
m4 = 'of a mortal was permitted to spy them. So then came minstrels, having gold trumpets, harps';
m5 = 'and drums. These played very loudly beside me, breaking that spell. So the dream vanished,';
m6 = 'whereat I thanked Heaven. I shed many tears before the thin moon rose up, frail and faint as';
m7 = 'a sickle of straw. Now though the Enchanter gnash his teeth vainly, yet shall he return as the';
m8 = 'Spring returns. Oh, wretched man! Hell gapes, Erebus now lies open. The mouths of Death';
m9 = 'wait on thy end.';

text = [m1, m2, m3, m4, m5, m6, m7, m8, m9];
key = '7876565434321123434565678788787656543432112343456567878878765654433211234';

Captal_alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
alphabet = 'abcdefghijklmnopqrstuvwxyz';
% making all the text lowercase
for i=1:26
    text = strrep(text,Captal_alphabet(i),alphabet(i));
end
% Deleting spaces and interrogation points
X = 1;
while X <= length(text)
    if ( double(text(X))<97 || double(text(X))>122 )
        text(X) = '';
    else
        X = X + 1;
    end
    
end
X = 0;
i = 1;
dec_m = '';
while i <= length(key)
    dec_m(i) = text(X+str2double(key(i)));
    X = X + 8;
    i = i + 1;
end
disp('The deciphered text is : ');
disp(['    ',dec_m]);
