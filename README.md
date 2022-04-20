# MC-PacketRouter
Simple Fabric Mod that Catches *valid* Handshake packets and sends back a response

## Idea
Misuse Minecraft Handshake packets to send data from a Python Client to A Minecraft Server

## Valid packets?
As far as I know, you can't inject code into io.netty, the network backend of minecraft. So all the parsing to packets is already done when Minecraft handles the packets. This means that the packets that we send to Minecraft need to be parsable to a Packet Object.
I will try to do it differentyl at one point.