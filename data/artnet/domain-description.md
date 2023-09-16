This article is about a lighting industry control system. For the art-dealing website, see Artnet.
Art-Net is a royalty-free communications protocol for transmitting the DMX512-A lighting control protocol and Remote Device management (RDM) protocol over the User Datagram Protocol (UDP) of the Internet protocol suite.[1] It is used to communicate between "nodes" (e.g., intelligent lighting instruments) and a "server" (a lighting desk or general purpose computer running lighting control software).

# Contents

1 Facilities
2 Versions
3 Addressing
4 Packet format
5 See also
6 References
7 External links
Facilities
Art-Net is a simple implementation of DMX512-A protocol over UDP in which lighting control information is conveyed in IP packets, typically on a private local area network such as Ethernet. Supported functions include transmitting and receiving lighting data (e.g., fader levels for individual lights, positions of movable lights); management functions such as detecting nodes, updating node control parameters, and transmitting timecodes; and functions that allow nodes to "subscribe" to "publisher" nodes so that, for example, nodes A and B can subscribe to node C (C will unicast information to A and B).

# Versions

Art-Net has gone through four versions which are claimed to be interoperable. Art-Net I used broadcasts extensively, giving a universe limit of approximately 40. Art-Net II mostly uses unicast packets, and addresses 256 universes. Art-Net III, released in 2011, addresses issues in managing larger numbers of universes, up to 32,768. Artnet IV, released in 2016, allows over 1000 ports per ip address.[2]

Internally to the protocol, it is referred to as version 14.

# Addressing

In its simplest implementation, nodes all broadcast, originally on the 2.0.0.0/8 networks.

Addressing is typically fixed per node, often locked to the MAC Address and an "OEM" code allocated to the manufacturer, and jumper settings. Networks can use DHCP or statically configured IP addresses, and use unicast packets for greater network efficiency. The protocol can address 32768 DMX "universes", each of 512 channels, limited by bandwidth.

The fixed addressing can be problematic in networks with other addressing requirements.[3] Revision Q of the protocol addressed this problem by adding 10.0.0.0/8 as an addressing scheme. For node discovery, broadcast packets are used.

# Packet format

The following table shows a typical packet, ArtDMX, for transmitting lighting values. It is sent to the fixed UDP port 0x1936 (6454 decimal).

The pink portion is the same on all Art-Net packets; the green portion is variable. The opcode (given in little endian) tells the recipient this is a packet containing DMX data in the data portion, intended to be output of the specified universe. Sequence is a sequential number between 1 and 255 allowing the recipient to reorder packets to address out-of-order delivery (this value is set to 0 to disable this feature); physical is an information packet showing the original physical universe of this data, if required. Then follows up to 512 lighting values in the range 0 to 255. Conceptually, this packet is broadcast to all nodes; but is ignored by all nodes except the one which is configured to listen for this universe. In practice the packet is typically unicast to the correct node.

offset (bytes) 0 1 2 3
0 'A' 'r' 't' '-'
4 'N' 'e' 't' 0
8 Opcode ArtDMX (0x5000) little endian Protocol Version Hi (0) Protocol Version Lo (14)
12 Sequence Physical Universe little endian
16 Length Hi Length Lo (2 to 512, even) Data Data
20
Data ...

# See also

Architecture for Control Networks (ANSI E1.31/sACN/Streaming ACN), a network protocol for theatrical control over UDP/IP

# Terminology:

- Node: A device that translates DMX512 to or from Art-Net is referred to as a Node.
- Port-Address: one of the 32,768 possible addresses to which a DMX frame can be directed. The Port-Address is a 15-bit number composed of Net+Sub-Net+Universe.
- Net: A group of 16 consecutive Sub-Nets or 256 consecutive Universesisreferred to as a net. There are 128 Nets in total.
- Sub-Net: A group of 16 consecutive universes is referred to as a sub-net. (Not to be confused with the subnet mask).
- Universe: A single DMX512 frame of 512 channels is referred to as a Universe.
- Kiloverse: A group of 1024 Universes.
- Controller: A central controller or monitoring device (lighting console) is referred to as a Controller.
- IP: The IP is the Internet protocol address. It is expressed in either a long word format (0x12345678) or dot format (2.255.255.255). Convention is that the former is hexadecimal and the latter is decimal. The IP uniquely identifies any Nodes or Controllers on a network.
- Subnet Mask: Defines which part of the IP represents the Network address and which part represents the Node address. Example: A Sub-Net mask of 255.0.0.0means that the first byte of the IP is the network address and the remaining three bytes are the Node address.
- Port: Actual data transmission on Art-Net uses the UDP protocol that operates ‘on top of’ the TCP/IP protocol. UDP data transfer operates by transferring data from a specific - IP: Port on a Node or Controller to a second specific IP:Port on a second Node or Controller. Art-Net uses only one port of 0x1936.
- Directed Broadcast: When a network first connects, the Controller does not know the number of Nodes on the network, nor does it know their IP addresses. The Directed broadcast address allows the Controller to send an ArtPoll to all Nodes on the network.
- Limited Broadcast: Art-Net packets should not be broadcast to the Limited Broadcast address of 255.255.255.255.
- Controller: A generic term describing an Art-Net device with the primary task of generating control data. For example, a lighting console.
- Media Server: A generic term describing an Art-Net device capable of generating control data based on the ‘mx’ Media Extensions to Art-Net.

# thernet Implementation:

General Notes:
All communication is UDP. Each packet format defined in this document forms the Data
field of an enclosing UDP packet.
Packet formats are specified in a manner similar to C-language structures, in which all
data items are considered to be unsigned integers of type INT8, INT16 or INT32
according to the number of bits. There are no hidden padding bytes, except at the very
end of a packet, which may be rounded up to a multiple of 2 or 4 bytes. Extra bytes at
the end of a valid received packet are ignored.
The protocols are generalised for handling future versions with increased numbers of
ports.
Many bit data fields contain unused positions. These may be used in future versions of
the protocol. They should be transmitted as zero and not tested by receivers.
All packet definitions are designed such that their length can be increased in future
revisions, whilstretaining co
