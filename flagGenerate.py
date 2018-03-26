#--coding:utf-8--

fake_flag = 'green{Do_you_really_think_this_is_flag?}'
real_flag = 'green{Wow!Hooking_mechanism_is_amazing!}'

fake_byte = []
for i in range(len(fake_flag)):
    num = ((0x99 ^ ord(fake_flag[i])) + i) & 0xff
    fake_byte.append(num)

print fake_byte
# fake_byte = [254, 236, 254, 255, 251, 231, 227, 253, 206, 233, 0, 247, 210, 248, 10, 7, 5, 6, 242, 217, 1, 6, 6, 14, 10, 223, 7, 12, 12, 7, 228, 15, 10, 231, 33, 24, 28, 35, 204, 11]

real_byte = []
for i in range(len(real_flag)):
    num = ord(real_flag[i])
    if i==0:
        real_byte.append(num^0x28)       
    else:
        num ^= real_byte[i-1]
        real_byte.append(num)

print real_byte
        
# real_byte = [79, 61, 88, 61, 83, 40, 127, 16, 103, 70, 14, 97, 14, 101, 12, 98, 5, 90, 55, 82, 49, 89, 56, 86, 63, 76, 33, 126, 23, 100, 59, 90, 55, 86, 44, 69, 43, 76, 109, 16]

for i in range(len(real_byte))[::-1]:
    if i == 0:
        real_byte[i] ^= 0x28
    else:
        real_byte[i] = real_byte[i-1]^real_byte[i]

print ''.join(map(chr,real_byte))
