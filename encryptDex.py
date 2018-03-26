#--coding:utf-8--

from Crypto.Cipher import ARC4
import hashlib
import struct

def md5(s):
    m = hashlib.md5()
    m.update(s)
    return m.hexdigest()

filename = md5('check.dex')

xorKey = 70624300
xorByte = [44,-92,53,4]
key = ''
for i in range(len(filename)):
    print (ord(filename[i])^xorByte[i%4]),
    key += chr((ord(filename[i])^xorByte[i%4])&0xff)

print
print key

obj = ARC4.new(key)

fp = open('check.dex', 'rb')
out = open(filename, 'wb')
data = fp.read()

encrypt = obj.encrypt(data)
out.write(encrypt)


