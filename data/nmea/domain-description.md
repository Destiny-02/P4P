# NMEA 2000

```{=mediawiki}
{{Short description|Marine plug-and-play communications standard}}
```

**NMEA 2000**, abbreviated to **NMEA2k** or **N2K** and standardized as
**IEC 61162-3**, is a plug-and-play communications standard used for
connecting marine sensors and display units within ships and boats.
Communication runs at 250 kilobits-per-second and allows any sensor to
talk to any display unit or other device compatible with NMEA 2000
protocols.

## Details

Electrically, NMEA 2000 is compatible with the [Controller Area
Network](Controller_Area_Network "wikilink") (\"CAN Bus\") used on road
vehicles and fuel engines. The higher-level protocol format is based on
[SAE J1939](SAE_J1939 "wikilink"), with specific messages for the marine
environment. [Raymarine](Raymarine "wikilink") [SeaTalk
2](Seatalk#Seatalk2 "wikilink"), [Raymarine](Raymarine "wikilink")
[SeaTalk^NG^](Seatalk#SeatalkNG "wikilink"), Simrad Simnet, and Furuno
CAN are rebranded implementations of NMEA 2000, though may use physical
connectors different from the standardised
[DeviceNet](DeviceNet "wikilink") 5-pin A-coded [M12 screw
connector](M12_connector "wikilink"), all of which are electrically
compatible and can be directly connected.

The protocol is used to create a network of electronic devices---chiefly
marine instruments---on a boat. Various instruments that meet the NMEA
2000 standard are connected to one central cable, known as a backbone.
The backbone powers each instrument and relays data among all of the
instruments on the network. This allows one display unit to show many
different types of information. It also allows the instruments to work
together, since they share data. NMEA 2000 is meant to be \"plug and
play\" to allow devices made by different manufacturers to communicate
with each other.

Examples of [marine electronics](marine_electronics "wikilink") devices
to include in a network are [GPS](GPS "wikilink") receivers, [auto
pilots](auto_pilots "wikilink"), wind instruments, depth sounders,
[navigation](navigation "wikilink") instruments,
[engine](engine "wikilink") instruments, and [nautical
chart](nautical_chart "wikilink") plotters. The interconnectivity among
instruments in the network allows, for example, the
[GPS](GPS "wikilink") receiver to correct the course that the autopilot
is steering.

## History

The NMEA 2000 standard was defined by, and is controlled by, the
US-based [National Marine Electronics
Association](National_Marine_Electronics_Association "wikilink") (NMEA).
Although the NMEA divulges some information regarding the standard, it
claims copyright over the standard and thus its full contents are not
publicly available. For example, the NMEA publicizes which messages
exist and which fields they contain, but they do not disclose how to
interpret the values contained in those fields. However, enthusiasts are
slowly making progress in discovering these PGN definitions.[^1]

## Functionality

NMEA 2000 connects devices using [Controller Area
Network](Controller_Area_Network "wikilink") (CAN) technology originally
developed for the auto industry. NMEA 2000 is based on the [SAE
J1939](SAE_J1939 "wikilink") high-level protocol, but defines its own
messages.[^2][^3] NMEA 2000 devices and J1939 devices can be made to
co-exist on the same physical network.[^4]

NMEA 2000 ([IEC](International_Electrotechnical_Commission "wikilink")
61162-3) can be considered a successor to the [NMEA
0183](NMEA_0183 "wikilink") (IEC 61162-1) serial data bus standard.[^5]
It has a significantly higher data rate (250k bits/second vs. 4800
bits/second for NMEA 0183). It uses a compact binary message format as
opposed to the ASCII [serial
communications](serial_communication "wikilink") protocol used by NMEA 0183. Another improvement is that NMEA 2000 supports a disciplined
multiple-talker, multiple-listener data network whereas NMEA 0183
requires a single-talker, multiple-listener
([simplex](simplex "wikilink")) serial [communications
protocol](communications_protocol "wikilink").

## Network construction {#network_construction}

The NMEA 2000 network, like the SAE J1939 network on which it is based,
is organized around a bus topology, and requires a single 120Ω
termination resistor at each end of the bus. (The resistors are in
parallel, so a properly terminated bus should have a total resistance of
60Ω). The maximum distance for any device from the bus is six metres.
The maximum backbone cable length is 250 meters (820 feet) with Mini
cable backbone or 100 meters (328 feet) with Micro cable backbone [^6]
![Typical NMEA 2000 Network
Installation](NMEA2000_Modified_motor_yacht.jpg "Typical NMEA 2000 Network Installation")

## Cabling and interconnect {#cabling_and_interconnect}

The only cabling standard approved by the NMEA for use with NMEA 2000
networks is the [DeviceNet](DeviceNet "wikilink") cabling standard,
which is controlled by the [Open DeviceNet Vendors
Association](Open_DeviceNet_Vendors_Association "wikilink"). Such
cabling systems are permitted to be labeled \"NMEA 2000 Approved\". The
DeviceNet standard defines levels of shielding, conductor size, weather
resistance, and flexibility which are not necessarily met by other
cabling solutions marketed as \"NMEA 2000\" compatible.[^7][^8]

There are two sizes of cabling defined by the DeviceNet/NMEA 2000
standard. The larger of the two sizes is denoted as \"Mini\" (or
alternatively, \"Thick\") cable, and is rated to carry up to 8 Amperes
of power supply current. The smaller of the two sizes is denoted as
\"Micro\" (or alternatively, \"Thin\") cable using the M12 5-pin barrel
connector specified in IEC 61076-2-101, and is rated to carry up to 3
Amperes of power supply current.

Mini cable is primarily used as a \"backbone\" (or \"trunk\") for
networks on larger vessels (typically with lengths of 20 m and above),
with Micro cable used for connections between the network backbone and
the individual components. Networks on smaller vessels often are
constructed entirely of Micro cable and connectors.

An NMEA 2000 network is not electrically compatible with an NMEA 0183
network, and so an interface device is required to send messages between
devices on the different types of network. An adapter is also required
if NMEA 2000 messages are to be received by or transmitted from a PC.
![NMEA-2000 cabling components incl.
power-T](N2K-CABLING.jpg "NMEA-2000 cabling components incl. power-T")

## Message format and parameter group numbers (PGNs)`{{anchor|PGN}}`{=mediawiki} {#message_format_and_parameter_group_numbers_pgns}

In accordance with the SAE J1939 protocol, NMEA 2000 messages are sent
as packets that consist of a header followed by (typically) 8 bytes of
data. The header for a message specifies the transmitting device, the
device to which the message was sent (which may be all devices), the
message priority, and the PGN (Parameter Group Number). The PGN
indicates which message is being sent, and thus how the data bytes
should be interpreted to determine the values of the data fields that
the message contains.[^9]

## Device certification {#device_certification}

Devices go through a certification process overseen by the NMEA, and are
permitted to display the \"NMEA 2000 Certified\" logo once they have
completed the certification process. The certification process does not
guarantee data content, that is the responsibility of the manufacturers.
However, the certification process does assure that products from
different manufacturers exchange data in a compatible way and that they
can coexist on a network.[^10]

## NMEA 2000 and proprietary networks {#nmea_2000_and_proprietary_networks}

Several manufacturers, including [Simrad](Simrad_Yachting "wikilink"),
[Raymarine](Raymarine "wikilink"), [Stowe](Stowe_Marine "wikilink"), and
[BRP](BRP_Marine "wikilink"), have their own proprietary networks that
are compatible with or akin to NMEA 2000. Simrad\'s is called _SimNet_,
Raymarine\'s is called _SeaTalk NG_, Stowe\'s is called _Dataline 2000_,
and BRP\'s is called _CZone_. Some of these, such as SimNet and Seatalk
NG, are a standard NMEA 2000 network but use non-standard connectors and
cabling; adapters are available to convert to standard NMEA 2000
connectors, or the user can simply remove the connector and make a
direct connection.[^11]

## Trademarks

The term \"NMEA 2000\" is a registered trademark of the National Marine
Electronics Association. Devices which are not \"NMEA 2000 Certified\"
may not legally use the NMEA 2000 trademark in their advertising.

## Manufacturers

The following are some of the companies that have registered with the
NMEA for the purpose of producing NMEA 2000 certified products:[^12]
`{{div col|colwidth=20em}}`{=mediawiki}

- [KUS Americas Inc](KUS_Americas_Inc "wikilink")
- [MarineCraft](MarineCraft "wikilink")
- [SAMYUNG ENC](SAMYUNG_ENC "wikilink")
- Carling Technologies
- [Amphenol LTW](Amphenol_LTW "wikilink")
- [Actisense](Actisense "wikilink")[^13]
- [Airmar](Airmar "wikilink")
- [Empirbus](Empirbus "wikilink")
- [Furuno](Furuno "wikilink")
- [Garmin](Garmin "wikilink")
- GME Standard Communications
- [Honda](Honda "wikilink")
- Humminbird
- Quark-elec(UK)
- [Icom Incorporated](Icom_Incorporated "wikilink")
- [Lowrance Electronics](Lowrance_Electronics "wikilink")
- [Molex](Molex "wikilink")[^14]
- Maretron
- [Navico](Navico "wikilink")
- [Raymarine](Raymarine "wikilink")
- [Simrad Yachting](Simrad_Yachting "wikilink")
- SeaStar Solutions (formerly Teleflex Marine)
- [Tohatsu](Tohatsu "wikilink")
- [VeeThree](VeeThree "wikilink")
- Yacht Devices
- [Yamaha Marine](Yamaha_Motor_Company "wikilink")
- [Hemisphere GNSS](Hemisphere_GNSS "wikilink")[^15]
- [Warwick Control
  Technologies](Warwick_Control_Technologies "wikilink")[^16]

```{=mediawiki}
{{div col end}}
```

## See also {#see_also}

- [Marine electronics](Marine_electronics "wikilink")
- [GPS Exchange Format](GPS_Exchange_Format "wikilink")

Related standards

- [NMEA 0183](NMEA_0183 "wikilink")
- [NMEA OneNet](NMEA_OneNet "wikilink"), a future standard based on
  Ethernet

Safety Standards using NMEA 2000

- [Automatic Identification
  System](Automatic_Identification_System "wikilink")

Misspellings

- [NEMA 2000](NEMA_2000 "wikilink")

[^17]

## References

```{=mediawiki}
{{Reflist}}
```

## External links {#external_links}

- [Official NMEA 2000 Web Page](http://www.nmea.org/)
- [List of NMEA 2000 Certified
  Products](https://www.nmea.org/content/STANDARDS/nmea_2000_certified_products)
  `{{Webarchive|url=https://web.archive.org/web/20190904023107/https://www.nmea.org/content/STANDARDS/nmea_2000_certified_products |date=2019-09-04 }}`{=mediawiki}
- [NMEA 2000 Parameter Group Numbers and Brief
  Description](http://www.nmea.org/content/nmea_standards/messages_pgns.asp)
  `{{Webarchive|url=https://web.archive.org/web/20090526013250/http://www.nmea.org/content/nmea_standards/messages_pgns.asp |date=2009-05-26 }}`{=mediawiki}
- [NMEA 2000 Parameter Group Descriptions (Messages) with (Longer)
  Field
  Description](http://www.nmea.org/Assets/july%202010%20nmea2000_v1-301_app_b_pgn_field_list.pdf)
  `{{Webarchive|url=https://web.archive.org/web/20160304040258/http://www.nmea.org/Assets/july%202010%20nmea2000_v1-301_app_b_pgn_field_list.pdf |date=2016-03-04 }}`{=mediawiki}
- [ODVA Planning and Installation Manual: DeviceNet Cable
  System](https://web.archive.org/web/20070927205841/http://www.odva.org/portals/0/library/Publications_Numbered/PUB00027R1_Cable_Guide_Print_Copy.pdf) -
  network wiring for DeviceNet networks, much of which applies to NMEA
  2000 networks.
- [Luft LA, Anderson L, Cassidy F. \"NMEA 2000: A Digital Interface
  for the 21st
  Century\"](http://www.nmea.org/Assets/nmea-2000-digital-interface-white-paper.pdf)
  `{{Webarchive|url=https://web.archive.org/web/20101208030340/http://www.nmea.org/Assets/nmea-2000-digital-interface-white-paper.pdf |date=2010-12-08 }}`{=mediawiki}
  2002-01-30

[Category:Global Positioning
System](Category:Global_Positioning_System "wikilink")
[Category:Computer buses](Category:Computer_buses "wikilink")
[Category:Marine electronics](Category:Marine_electronics "wikilink")

# NMEA 0183

```{=mediawiki}
{{short description|Communication standard for marine electronics}}
```

**NMEA 0183** is a combined electrical and data specification for
communication between [marine
electronics](marine_electronics "wikilink") such as [echo
sounder](echo_sounder "wikilink"), [sonars](sonar "wikilink"),
[anemometer](anemometer "wikilink"),
[gyrocompass](gyrocompass "wikilink"),
[autopilot](Self-steering_gear "wikilink"), [GPS
receivers](GPS_receiver "wikilink") and many other types of instruments.
It has been defined and is controlled by the [National Marine
Electronics
Association](National_Marine_Electronics_Association "wikilink") (NMEA).
It replaces the earlier **NMEA 0180** and **NMEA 0182** standards.[^1]
In leisure marine applications it is slowly being phased out in favor of
the newer [NMEA 2000](NMEA_2000 "wikilink") standard,[^2][^3] though
NMEA 0183 remains the norm in commercial shipping.

## Details

The electrical standard that is used is [EIA-422](EIA-422 "wikilink"),
also known as RS-422, although most hardware with NMEA-0183 outputs are
also able to drive a single [EIA-232](EIA-232 "wikilink") port. Although
the standard calls for isolated inputs and outputs, there are various
series of hardware that do not adhere to this requirement.

The NMEA 0183 standard uses a simple [ASCII](ASCII "wikilink"), [serial
communications](serial_communications "wikilink") protocol that defines
how data are transmitted in a \"sentence\" from one \"talker\" to
multiple \"listeners\" at a time. Through the use of intermediate
expanders, a talker can have a unidirectional conversation with a nearly
unlimited number of listeners, and using
[multiplexers](multiplexers "wikilink"), multiple sensors can talk to a
single computer port.

At the application layer, the standard also defines the contents of each
sentence (message) type, so that all listeners can parse messages
accurately.

While NMEA 0183 only defines an RS-422 transport, there also exists a de
facto standard in which the sentences from NMEA0183 are placed in
[UDP](User_Datagram_Protocol "wikilink") datagrams (one sentence per
packet) and sent over an IP network.

The NMEA standard is proprietary and sells for at least US\$2000 (except
for members of the NMEA) as of September 2020.[^4][^5] However, much of
it has been reverse-engineered from public sources.[^6][^7]

## Serial configuration (data link layer) {#serial_configuration_data_link_layer}

---

Typical [Baud rate](Baud_rate "wikilink") 4800
Data bits 8
Parity None
Stop bits 1
Handshake None

---

There is a variation of the standard called NMEA-0183HS that specifies a
baud rate of 38,400. This is in general use by
[AIS](Automatic_Identification_System "wikilink") devices.

## Message structure {#message_structure}

- All transmitted data are printable [ASCII](ASCII "wikilink")
  characters between 0x20 (space) to 0x7e (\~)
- Data characters are all the above characters except the reserved
  characters (See next line)
- Reserved characters are used by NMEA0183 for the following uses:

ASCII Hex Dec Use

---

`<CR>`{=html} 0x0d 13 Carriage return
`<LF>`{=html} 0x0a 10 Line feed, end delimiter
! 0x21 33 Start of encapsulation sentence delimiter
\$ 0x24 36 Start delimiter \* 0x2a 42 Checksum delimiter
, 0x2c 44 Field delimiter
\\ 0x5c 92 TAG block delimiter
\^ 0x5e 94 Code delimiter for HEX representation of ISO/IEC 8859-1 (ASCII) characters
\~ 0x7e 126 Reserved

- Messages have a maximum length of 82 characters, including the \$ or
  ! starting character and the ending `<LF>`{=html}
- The start character for each message can be either a \$ (For
  conventional field delimited messages) or ! (for messages that have
  special encapsulation in them)
- The next five characters identify the talker (two characters) and
  the type of message (three characters).
- All data fields that follow are comma-delimited.
- Where data is unavailable, the corresponding field remains blank (it
  contains no character before the next delimiter -- see [Sample
  file](#Sample_file "wikilink") section below).
- The first character that immediately follows the last data field
  character is an asterisk, but it is only included if a checksum is
  supplied.
- The asterisk is immediately followed by a
  [checksum](checksum "wikilink") represented as a two-digit
  [hexadecimal](hexadecimal "wikilink") number. The checksum is the
  [bitwise exclusive OR](Bitwise_XOR "wikilink") of
  [ASCII](ASCII "wikilink") codes of all characters between the _\$_
  and \*\*\*, not inclusive. According to the official specification,
  the checksum is optional for most data sentences, but is compulsory
  for RMA, RMB, and RMC (among others).
- [`{{code|<CR><LF>}}`{=mediawiki}](Newline "wikilink") ends the
  message.

As an example, a waypoint arrival alarm has the form:

: _\$GPAAM,A,A,0.10,N,WPTNME\*32_

Another example for AIS messages is:

: !AIVDM,1,1,,A,14eG;o@034o8sd\<L9i:a;WF\>062D,0\*7D

## NMEA sentence format {#nmea_sentence_format}

The main talker ID
includes:[1](https://gpsd.gitlab.io/gpsd/NMEA.html#TALKERS)[2](https://www.nmea.org/Assets/NMEA%200183%20Talker%20Identifier%20Mnemonics.pdf)
`{{Webarchive|url=https://web.archive.org/web/20220902160821/https://www.nmea.org/Assets/NMEA%200183%20Talker%20Identifier%20Mnemonics.pdf |date=2022-09-02 }}`{=mediawiki}

- BD or GB - [Beidou](BeiDou "wikilink")
- GA - [Galileo](<Galileo_(satellite_navigation)> "wikilink")
- GP - [GPS](Global_Positioning_System "wikilink")
- GL - [GLONASS](GLONASS "wikilink").

NMEA message mainly include the following \"sentences\" in the NMEA
<message:%5Bhttps://fdocuments.in/document/introduction-to-gps-data-nmea-rtcm-donald-choi-alsg2.html>\]

Sentence Description

---

\$Talker ID+GGA Global Positioning System Fixed Data
\$Talker ID+GLL Geographic Position\-- Latitude and Longitude
\$Talker ID+GSA GNSS DOP and active satellites
\$Talker ID+GSV GNSS satellites in view
\$Talker ID+RMC Recommended minimum specific GPS data
\$Talker ID+VTG Course over ground and ground speed

One example, the sentence for Global Positioning System Fixed Data for
GPS should be \"\$GPGGA\".

## Vendor extensions {#vendor_extensions}

Most GPS manufacturers include special messages in addition to the
standard NMEA set in their products for maintenance and diagnostics
purposes. Extended messages begin with \"\$P\". These extended messages
are not standardized.

## Software compatibility {#software_compatibility}

NMEA 0183 is supported by various navigation and mapping software.
Notable applications include:

- [DeLorme](DeLorme "wikilink") Street Atlas

- [ESRI](Environmental_Systems_Research_Institute "wikilink")

- [Google Earth](Google_Earth "wikilink")

- [Google Maps](Google_Maps "wikilink") Mobile Edition[^8]

- [gpsd](gpsd "wikilink") - [Unix](Unix "wikilink") GPS Daemon

- [JOSM](JOSM "wikilink") - OpenStreetMap Map Editor

- [MapKing](MapKing "wikilink")

- [Microsoft MapPoint](Microsoft_MapPoint "wikilink")

- [Microsoft Streets & Trips](Microsoft_Streets_&_Trips "wikilink")

- [NetStumbler](NetStumbler "wikilink")

- [OpenCPN](OpenCPN "wikilink") - Open source navigation software

- [OpenBSD](OpenBSD "wikilink")\'s [hw.sensors](hw.sensors "wikilink")
  framework with the `nmea(4)`
  [pseudo-device](pseudo-device "wikilink")
  driver`<ref name=openbsd§nmea>`{=html}`{{cite web |author= Marc Balmer |date= 2006–2008 |url= http://bxr.su/o/sys/kern/tty_nmea.c |title= /sys/kern/tty_nmea.c |website= BSD Cross Reference |publisher= [[OpenBSD]]}}`{=mediawiki}

- ```{=mediawiki}
  {{cite book |section=nmea — NMEA 0183 timedelta sensor |title=OpenBSD manual page server |url=http://mdoc.su/o/nmea.4}}
  ```

  ```{=html}
  </ref>
  ```

- [OpenNTPD](OpenNTPD "wikilink") through sysctl
  `{{Section link|hw.sensors#timedelta}}`{=mediawiki} API

- [Rand McNally](Rand_McNally "wikilink") StreetFinder

- ObserVIEW [^9]

## Sample file {#sample_file}

A sample file produced by a Tripmate 850 GPS logger. This file was
produced in [Leixlip](Leixlip "wikilink"), [County
Kildare](County_Kildare "wikilink"),
[Ireland](Republic_of_Ireland "wikilink"). The record lasts two seconds.

    $GPGGA,092750.000,5321.6802,N,00630.3372,W,1,8,1.03,61.7,M,55.2,M,,*76
    $GPGSA,A,3,10,07,05,02,29,04,08,13,,,,,1.72,1.03,1.38*0A
    $GPGSV,3,1,11,10,63,137,17,07,61,098,15,05,59,290,20,08,54,157,30*70
    $GPGSV,3,2,11,02,39,223,19,13,28,070,17,26,23,252,,04,14,186,14*79
    $GPGSV,3,3,11,29,09,301,24,16,09,020,,36,,,*76
    $GPRMC,092750.000,A,5321.6802,N,00630.3372,W,0.02,31.66,280511,,,A*43
    $GPGGA,092751.000,5321.6802,N,00630.3371,W,1,8,1.03,61.7,M,55.3,M,,*75
    $GPGSA,A,3,10,07,05,02,29,04,08,13,,,,,1.72,1.03,1.38*0A
    $GPGSV,3,1,11,10,63,137,17,07,61,098,15,05,59,290,20,08,54,157,30*70
    $GPGSV,3,2,11,02,39,223,16,13,28,070,17,26,23,252,,04,14,186,15*77
    $GPGSV,3,3,11,29,09,301,24,16,09,020,,36,,,*76
    $GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45

Note some blank fields, for example:

- **GSV** records, which describe satellites \'visible\', lack the SNR
  (signal--to--noise ratio) field for satellite 16 and all data for
  satellite 36.
- **GSA** record, which lists satellites used for determining a [fix
  (position)](<fix_(position)> "wikilink") and gives a
  [DOP](<Dilution_of_precision_(GPS)> "wikilink") of the fix, contains
  12 fields for satellites\' numbers, but only 8 satellites were taken
  into account---so 4 fields remain blank.

## Status

NMEA 0183 continued to be maintained separately: V4.10 was published in
early May 2012, and an erratum noted on 12 May 2012.[^10] On November
27, 2018, it was issued an update to version 4.11, which supports
[Global Navigation Satellite
Systems](Global_Navigation_Satellite_System "wikilink") other than
[GPS](GPS "wikilink").[^11]

## See also {#see_also}

- [GPS Exchange Format](GPS_Exchange_Format "wikilink")
- [TransducerML](TransducerML "wikilink")
- [IEEE 1451](IEEE_1451 "wikilink")
- [IEC 61162](IEC_61162 "wikilink")
- [NMEA 2000](NMEA_2000 "wikilink")
- [NMEA OneNet](NMEA_OneNet "wikilink")
- [RTCM SC-104](RTCM_SC-104 "wikilink")
- [RINEX](RINEX "wikilink")

## References

```{=mediawiki}
{{Reflist}}
```

## External links {#external_links}

- [National Marine Electronics Association](http://www.nmea.org/)
- [NMEA\'s website about NMEA
  0183](https://web.archive.org/web/20131021183159/http://www.nmea.org/content/nmea_standards/nmea_0183_v_410.asp)
- [NMEA Specifications at APRS Info](http://aprs.gids.nl/nmea/)

[Category:Global Positioning
System](Category:Global_Positioning_System "wikilink") [Category:Network
protocols](Category:Network_protocols "wikilink") [Category:Computer
buses](Category:Computer_buses "wikilink") [Category:Marine
electronics](Category:Marine_electronics "wikilink") [Category:Satellite
navigation](Category:Satellite_navigation "wikilink")
