import socket
import math
from textwrap import indent
import time

class ByteBuffer:
    def __init__(self, data : bytes) -> None:
        self.data = data
        self.index = 0

    def readByte(self) -> int:
        self.index += 1
        return self.data[self.index - 1]

    def readVarInt(self) -> int:
        i = 0
        j = 0

        while 1:
            b = self.readByte()
            i |= (b & 127) << j * 7
            j += 1
            
            if not b & 128:
                break

        return i

    def readString(self) -> str:
        i = self.readVarInt()
        str_data = self.data[self.index : self.index + i]
        self.index += i
        return str_data.decode()
    

def prependSize(data : bytes) -> bytes:
    size = len(data)
    len_size = math.ceil(math.log(size, 256))
    size_b = (size).to_bytes(len_size, 'big')
    
    return size_b + data

def send(data : bytes) -> str:
    s = socket.socket(socket.AF_INET)
    s.connect(('localhost',25565))
    
    # 1 Byte: length of packet
    # 1 Byte: Packet Type (0x00: Handshake)
    # 1 Byte: Protocol verion
    # 1 Byte: Stringlength, max 255
    # n Bytes: String <- Used to transmit data
    # 2 Bytes: Port
    # 1 Byte: Next State
    data = prependSize(b'\x00/' + prependSize(data) + b'\x00\x00\x00')
    s.send(data)
    response = s.recv(1024)

    if response:
        data = ByteBuffer(response)
        
        size = data.readVarInt()
        packet = data.readVarInt()
        modid = data.readString()
        msg = data.readString()
        return msg

    s.close()
    return ''

print(send(b"Hello World!"))
#print(send(b"Hello World!"))
