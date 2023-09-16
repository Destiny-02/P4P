On-board diagnostics (OBD) is a term referring to a vehicle's self-diagnostic and reporting capability. OBD systems give the vehicle owner or repair technician access to the status of the various vehicle sub-systems. The amount of diagnostic information available via OBD has varied widely since its introduction in the early 1980s versions of on-board vehicle computers. Early versions of OBD would simply illuminate a malfunction indicator light (MIL) or "idiot light" if a problem was detected, but would not provide any information as to the nature of the problem. Modern OBD implementations use a standardized digital communications port to provide real-time data in addition to a standardized series of diagnostic trouble codes, or DTCs, which allow a person to rapidly identify and remedy malfunctions within the vehicle.

# Contents

1 History
2 Standard interfaces
2.1 ALDL
2.2 OBD-I
2.3 OBD-1.5
2.4 OBD-II
2.4.1 OBD-II diagnostic connector
2.5 EOBD
2.5.1 EOBD fault codes
2.6 EOBD2
2.7 JOBD
2.8 ADR 79/01 & 79/02 (Australian OBD standard)
3 OBD-II signal protocols
3.1 OBD-II diagnostic data available
3.2 Mode of operation/OBD services
4 Applications
4.1 Hand-held scan tools
4.2 Mobile device-based tools and analysis
4.3 OBD2 Software
4.4 PC-based scan tools and analysis platforms
4.5 Data loggers
4.6 Emission testing
4.7 Driver's supplementary vehicle instrumentation
4.8 Vehicle telematics
5 OBD-II diagnostic trouble codes
6 Standards documents
6.1 SAE standards documents on OBD-II
6.2 SAE standards documents on HD (Heavy Duty) OBD
6.3 ISO standards
7 Security issues
8 See also
9 References
10 External links

# History

This section is in list format but may read better as prose. You can help by converting this section, if appropriate. Editing help is available. (September 2021)
1968: Volkswagen introduces the first on-board computer system, in their fuel-injected Type 3 models. This system is entirely analog with no diagnostic capabilities.
1975: Bosch and Bendix EFI systems are adopted by major automotive manufacturers in an effort to improve tail pipe emissions. These systems are also analog in nature, though some provide rudimentary diagnostic capability through factory tools, such as the Kent Moore J-25400, compatible with the Datsun 280Z, and the Cadillac Seville.
1980: General Motors introduces the first data link on their 1980 Cadillac Eldorado and Seville models. Diagnostic Trouble Codes (DTC's) are displayed through the electronic climate control system's digital readout when in diagnostic mode.[1]
1981: General Motors introduces its "Computer Command Control" system on all US passenger vehicles for model year 1981. Included in this system is a proprietary 5-pin ALDL that interfaces with the Engine Control Module (ECM) to initiate a diagnostic request and provide a serial data stream. The protocol communicates at 160 baud with Pulse-width modulation (PWM) signaling and monitors all engine management functions. It reports real-time sensor data, component overrides, and Diagnostic Trouble Codes. The specification for this link is as defined by GM's Emissions Control System Project Center document XDE-5024B.[2][3]
1982: RCA defines an analog STE/ICE vehicle diagnostic standard used in the CUCV, M60 tank and other military vehicles of the era for the US Army.[4]
1986: General Motors introduces an upgraded version of the ALDL protocol, which communicates at 8192 baud with half-duplex UART signaling on some models.
1988: The California Air Resources Board (CARB) requires that all new vehicles sold in California from 1988 onward have some basic OBD capability.[5] These requirements are generally referred to as "OBD-I", though this name is not applied until the introduction of OBD-II. The data link connector and its position are not standardized, nor is the data protocol. The Society of Automotive Engineers (SAE) recommends a standardized diagnostic connector and set of diagnostic test signals.
~1994: Motivated by a desire for a state-wide emissions testing program, the CARB issues the OBD-II specification and mandates that it be adopted for all cars sold in California starting in model year 1996 (see CCR Title 13 Section 1968.1 and 40 CFR Part 86 Section 86.094). The DTCs and connector suggested by the SAE are incorporated into this specification.
1996: The OBD-II specification is made mandatory for all cars sold in the United States.
2001: The European Union makes EOBD mandatory for all gasoline (petrol) vehicles sold in the European Union, starting in MY2001 (see European emission standards Directive 98/69/EC[6]).
2004: The European Union makes EOBD mandatory for all diesel vehicles sold in the European Union
2006: All vehicles manufactured in Australia and New Zealand are required to be OBD-II compliant after January 1, 2006.[7]
2008: All cars sold in the United States are required to use the ISO 15765-4[8] signaling standard (a variant of the Controller Area Network (CAN) bus).[9]
2008: Certain light vehicles in China are required by the Environmental Protection Administration Office to implement OBD (standard GB18352[10]) by July 1, 2008.[citation needed] Some regional exemptions may apply.
2010: HDOBD (heavy duty) specification is made mandatory for selected commercial (non-passenger car) engines sold in the United States.
Standard interfaces
ALDL
Main article: ALDL
GM's ALDL (Assembly Line Diagnostic Link) is sometimes referred as a predecessor to, or a manufacturer's proprietary version of, an OBD-I diagnostic starting in 1981. This interface was made in different varieties and changed with power train control modules (aka PCM, ECM, ECU). Different versions had slight differences in pin-outs and baud rates. Earlier versions used a 160 baud rate, while later versions went up to 8192 baud and used bi-directional communications to the PCM.[11][12]

OBD-I
The regulatory intent of OBD-I was to encourage auto manufacturers to design reliable emission control systems that remain effective for the vehicle's "useful life".[13] The hope was that by forcing annual emissions testing for California starting in 1988, [14] and denying registration to vehicles that did not pass, drivers would tend to purchase vehicles that would more reliably pass the test. OBD-I was largely unsuccessful, as the means of reporting emissions-specific diagnostic information was not standardized. Technical difficulties with obtaining standardized and reliable emissions information from all vehicles led to an inability to implement the annual testing program effectively.[15]

The Diagnostic Trouble Codes (DTC's) of OBD-I vehicles can usually be found without an expensive scan tool. Each manufacturer used their own Diagnostic Link Connector (DLC), DLC location, DTC definitions, and procedure to read the DTC's from the vehicle. DTC's from OBD-I cars are often read through the blinking patterns of the 'Check Engine Light' (CEL) or 'Service Engine Soon' (SES) light. By connecting certain pins of the diagnostic connector, the 'Check Engine' light will blink out a two-digit number that corresponds to a specific error condition. The DTC's of some OBD-I cars are interpreted in different ways, however. Cadillac (gasoline) fuel-injected vehicles are equipped with actual on-board diagnostics, providing trouble codes, actuator tests and sensor data through the new digital Electronic Climate Control display.

Holding down 'Off' and 'Warmer' for several seconds activates the diagnostic mode without the need for an external scan tool. Some Honda engine computers are equipped with LEDs that light up in a specific pattern to indicate the DTC. General Motors, some 1989-1995 Ford vehicles (DCL), and some 1989-1995 Toyota/Lexus vehicles have a live sensor data stream available; however, many other OBD-I equipped vehicles do not. OBD-I vehicles have fewer DTC's available than for OBD-II equipped vehicles.

OBD-1.5
OBD 1.5 refers to a partial implementation of OBD-II which General Motors used on some vehicles in 1994, 1995, & 1996. (GM did not use the term OBD 1.5 in the documentation for these vehicles — they simply have an OBD and an OBD-II section in the service manual.)

For example, the 94–95 Corvettes have one post-catalyst oxygen sensor (although they have two catalytic converters), and have a subset of the OBD-II codes implemented.[16]

This hybrid system was present on GM B-body cars (the Chevrolet Caprice, Impala, and Buick Roadmaster) in 94–95, H-body cars in 94–95, W-body cars (Buick Regal, Chevrolet Lumina ('95 only), Chevrolet Monte Carlo ('95 only), Pontiac Grand Prix, Oldsmobile Cutlass Supreme) in 94–95, L-body (Chevrolet Beretta/Corsica) in 94–95, Y-body (Chevrolet Corvette) in 94–95, on the F-body (Chevrolet Camaro and Pontiac Firebird) in 95 and on the J-Body (Chevrolet Cavalier and Pontiac Sunfire) and N-Body (Buick Skylark, Oldsmobile Achieva, Pontiac Grand Am) in 95 and 96 and also on '94-'95 Saab vehicles with the naturally aspirated 2.3.

The pinout for the ALDL connection on these cars is as follows:

1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
For ALDL connections, pin 9 is the data stream, pins 4 and 5 are ground, and pin 16 is battery voltage.

An OBD 1.5 compatible scan tool is required to read codes generated by OBD 1.5.

Additional vehicle-specific diagnostic and control circuits are also available on this connector. For instance, on the Corvette there are interfaces for the Class 2 serial data stream from the PCM, the CCM diagnostic terminal, the radio data stream, the airbag system, the selective ride control system, the low tire pressure warning system, and the passive keyless entry system.[17]

An OBD 1.5 has also been used in the Ford Scorpio since 95.[18]

OBD-II
OBD-II is an improvement over OBD-I in both capability and standardization. The OBD-II standard specifies the type of diagnostic connector and its pinout, the electrical signalling protocols available, and the messaging format. It also provides a candidate list of vehicle parameters to monitor along with how to encode the data for each. There is a pin in the connector that provides power for the scan tool from the vehicle battery, which eliminates the need to connect a scan tool to a power source separately. However, some technicians might still connect the scan tool to an auxiliary power source to protect data in the unusual event that a vehicle experiences a loss of electrical power due to a malfunction. Finally, the OBD-II standard provides an extensible list of DTCs. As a result of this standardization, a single device can query the on-board computer(s) in any vehicle. This OBD-II came in two models OBD-IIA and OBD-IIB. OBD-II standardization was prompted by emissions requirements, and though only emission-related codes and data are required to be transmitted through it, most manufacturers have made the OBD-II Data Link Connector the only one in the vehicle through which all systems are diagnosed and programmed. OBD-II Diagnostic Trouble Codes are 4-digit, preceded by a letter: P for powertrain (engine and transmission), B for body, C for chassis, and U for network.

OBD-II diagnostic connector

Female OBD-II connector on a car

Female OBD-II type A connector pinout - front view

Female OBD-II type B connector pinout - front view
The OBD-II specification provides for a standardized hardware interface — the female 16-pin (2x8) J1962 connector, where type A is used for 12-volt vehicles and type B for 24-volt vehicles. Unlike the OBD-I connector, which was sometimes found under the hood of the vehicle, the OBD-II connector is required to be within 2 feet (0.61 m) of the steering wheel (unless an exemption is applied for by the manufacturer, in which case it is still somewhere within reach of the driver).

SAE J1962 defines the pinout of the connector as:

1 Manufacturer discretion
GM: J2411 GMLAN/SWC/Single-Wire CAN.
Audi: Switched +12 to tell a scan tool whether the ignition is on.
VW: Switched +12 to tell a scan tool whether the ignition is on.
Mercedes[19] (K-Line): Ignition control (EZS), air-conditioner (KLA), PTS, safety systems (Airbag, SRS, AB) and some other.

9 Manufacturer discretion
GM: 8192 baud ALDL where fitted.
BMW: RPM signal.
Toyota: RPM signal.
Mercedes (K-Line): ABS, ASR, ESP, ETS, BAS diagnostic.

2 Bus positive Line
SAE J1850 PWM and VPW

10 Bus negative Line
SAE J1850 PWM only (not SAE 1850 VPW)

3 Manufacturer discretion
Ethernet TX+ (Diagnostics over IP)
Ford DCL(+) Argentina, Brazil (pre OBD-II) 1997–2000, USA, Europe, etc.
Chrysler CCD Bus(+)
Mercedes (TNA): TD engine rotation speed.

11 Manufacturer discretion
Ethernet TX- (Diagnostics over IP)
Ford DCL(-) Argentina, Brazil (pre OBD-II) 1997–2000, USA, Europe, etc.
Chrysler CCD Bus(-)
Mercedes (K-Line): Gearbox and other transmission components (EGS, ETC, FTC).

4 Chassis ground 12 Manufacturer discretion
Ethernet RX+ (Diagnostics over IP)
Mercedes (K-Line): All activity module (AAM), Radio (RD), ICS (and more)

5 Signal ground 13 Manufacturer discretion
Ethernet RX- (Diagnostics over IP)
Ford: FEPS - Programming PCM voltage
Mercedes (K-Line): AB diagnostic - safety systems.

6 CAN high
(ISO 15765-4 and SAE J2284)

14 CAN low
(ISO 15765-4 and SAE J2284)

7 K-line
(ISO 9141-2 and ISO 14230-4)

15 L-line
(ISO 9141-2 and ISO 14230-4)

8 Manufacturer discretion
Activate Ethernet (Diagnostics over IP)
Many BMWs: A second K-line for non OBD-II (Body/Chassis/Infotainment) systems.
Mercedes: Ignition
16 Battery voltage
(+12 Volt for type A connector)
(+24 Volt for type B connector)

The assignment of unspecified pins is left to the vehicle manufacturer's discretion.[20]

EOBD
The European on-board diagnostics (EOBD) regulations are the European equivalent of OBD-II, and apply to all passenger cars of category M1 (with no more than 8 passenger seats and a Gross Vehicle Weight rating of 2500 kg or less) first registered within EU member states since January 1, 2001 for petrol (gasoline) engined cars and since January 1, 2004 for diesel engined cars.[21]

For newly introduced models, the regulation dates applied a year earlier - January 1, 2000 for petrol and January 1, 2003, for diesel.
For passenger cars with a Gross Vehicle Weight rating of greater than 2500 kg and for light commercial vehicles, the regulation dates applied from January 1, 2002, for petrol models, and January 1, 2007, for diesel models.

The technical implementation of EOBD is essentially the same as OBD-II, with the same SAE J1962 diagnostic link connector and signal protocols being used.

With Euro V and Euro VI emission standards, EOBD emission thresholds are lower than previous Euro III and IV.

EOBD fault codes
Each of the EOBD fault codes consists of five characters: a letter, followed by four numbers. The letter refers to the system being interrogated e.g. Pxxxx would refer to the powertrain system. The next character would be a 0 if complies to the EOBD standard. So it should look like P0xxx.

The next character would refer to the sub system.

P00xx - Fuel and Air Metering and Auxiliary Emission Controls.
P01xx - Fuel and Air Metering.
P02xx - Fuel and Air Metering (Injector Circuit).
P03xx - Ignition System or Misfire.
P04xx - Auxiliary Emissions Controls.
P05xx - Vehicle Speed Controls and Idle Control System.
P06xx - Computer Output Circuit.
P07xx - Transmission.
P08xx - Transmission.
The following two characters would refer to the individual fault within each subsystem.[22]

EOBD2
The term "EOBD2" is marketing speak used by some vehicle manufacturers to refer to manufacturer-specific features that are not actually part of the OBD or EOBD standard. In this case "E" stands for Enhanced.

JOBD
JOBD is a version of OBD-II for vehicles sold in Japan.

ADR 79/01 & 79/02 (Australian OBD standard)
The ADR 79/01 (Vehicle Standard (Australian Design Rule 79/01 – Emission Control for Light Vehicles) 2005) standard is the Australian equivalent of OBD-II.
It applies to all vehicles of category M1 and N1 with a Gross Vehicle Weight rating of 3500 kg or less, registered from new within Australia and produced since January 1, 2006 for petrol (gasoline) engined cars and since January 1, 2007 for diesel engined cars.[23]
For newly introduced models, the regulation dates applied a year earlier - January 1, 2005 for petrol and January 1, 2006, for diesel.
The ADR 79/01 standard was supplemented by the ADR 79/02 standard which imposed tighter emissions restrictions, applicable to all vehicles of class M1 and N1 with a Gross Vehicle Weight rating of 3500 kg or less, from July 1, 2008, for new models, July 1, 2010, for all models.[24]

The technical implementation of this standard is essentially the same as OBD-II, with the same SAE J1962 diagnostic link connector and signal protocols being used.

OBD-II signal protocols
There are five signaling protocols that are permitted with the OBD-II interface. Most vehicles implement only one of the protocols. It is often possible to deduce the protocol used based on which pins are present on the J1962 connector:[25]

SAE J1850 PWM (pulse-width modulation — 41.6 kB/sec, standard of the Ford Motor Company)
pin 2: Bus+
pin 10: Bus–
High voltage is +5 V
Message length is restricted to 12 bytes, including CRC
Employs a multi-master arbitration scheme called 'Carrier Sense Multiple Access with Non-Destructive Arbitration' (CSMA/NDA)
SAE J1850 VPW (variable pulse width — 10.4/41.6 kB/sec, standard of General Motors)
pin 2: Bus+
Bus idles low
High voltage is +7 V
Decision point is +3.5 V
Message length is restricted to 12 bytes, including CRC
Employs CSMA/NDA
ISO 9141-2.[26] This protocol has an asynchronous serial data rate of 10.4 kbit/s.[27] It is somewhat similar to RS-232; however, the signal levels are different, and communications happen on a single, bidirectional line without additional handshake signals. ISO 9141-2 is primarily used in Chrysler, European, and Asian vehicles.
pin 7: K-line
pin 15: L-line (optional)
UART signaling
K-line idles high, with a 510 ohm resistor to Vbatt
The active/dominant state is driven low with an open-collector driver.
Message length is Max 260Bytes. Data field MAX 255.
ISO 14230 KWP2000 (Keyword Protocol 2000)
pin 7: K-line
pin 15: L-line (optional)
Physical layer identical to ISO 9141-2
Data rate 1.2 to 10.4 kBaud
Message may contain up to 255 bytes in the data field
ISO 15765 CAN (250 kbit/s or 500 kbit/s). The CAN protocol was developed by Bosch for automotive and industrial control. Unlike other OBD protocols, variants are widely used outside of the automotive industry. While it did not meet the OBD-II requirements for U.S. vehicles prior to 2003, as of 2008 all vehicles sold in the US are required to implement CAN as one of their signaling protocols.
pin 6: CAN High
pin 14: CAN Low
All OBD-II pinouts use the same connector, but different pins are used with the exception of pin 4 (battery ground) and pin 16 (battery positive).

OBD-II diagnostic data available
OBD-II provides access to data from the engine control unit (ECU) and offers a valuable source of information when troubleshooting problems inside a vehicle. The SAE J1979 standard defines a method for requesting various diagnostic data and a list of standard parameters that might be available from the ECU. The various parameters that are available are addressed by "parameter identification numbers" or PIDs which are defined in J1979. For a list of basic PIDs, their definitions, and the formula to convert raw OBD-II output to meaningful diagnostic units, see OBD-II PIDs. Manufacturers are not required to implement all PIDs listed in J1979 and they are allowed to include proprietary PIDs that are not listed. The PID request and data retrieval system gives access to real time performance data as well as flagged DTCs. For a list of generic OBD-II DTCs suggested by the SAE, see Table of OBD-II Codes. Individual manufacturers often enhance the OBD-II code set with additional proprietary DTCs.

Mode of operation/OBD services
Here is a basic introduction to the OBD communication protocol according to ISO 15031. In SAE J1979 these "modes" were renamed to "services", starting in 2003.

Service / Mode $01 shows current sensor live data from PIDs ("Parameter IDs"). See OBD-II PIDs#Service_01 for an extensive list.
Service / Mode $02 makes Freeze Frame data accessible via the same PIDs.[28] See OBD-II PIDs#Service_02 for a list.
Service / Mode $03 lists the emission-related "confirmed" diagnostic trouble codes stored. It either displays numeric, 4 digit codes identifying the faults or maps them to a letter (P, B, U, C) plus 4 digits. See #OBD-II_diagnostic_trouble_codes.
Service / Mode $04 is used to clear emission-related diagnostic information. This includes clearing the stored pending/confirmed DTCs and Freeze Frame data.[29]
Service / Mode $05 displays the oxygen sensor monitor screen and the test results gathered about the oxygen sensor. There are ten numbers available for diagnostics:
$01 Rich-to-Lean O2 sensor threshold voltage
$02 Lean-to-Rich O2 sensor threshold voltage
$03 Low sensor voltage threshold for switch time measurement
$04 High sensor voltage threshold for switch time measurement
$05 Rich-to-Lean switch time in ms
$06 Lean-to Rich switch time in ms
$07 Minimum voltage for test
$08 Maximum voltage for test
$09 Time between voltage transitions in ms
See OBD-II PIDs#Service_05 for a list.
Service / Mode $06 is a Request for On-Board Monitoring Test Results for Continuously and Non-Continuously Monitored System. There are typically a minimum value, a maximum value, and a current value for each non-continuous monitor.
Service / Mode $07 is a Request for emission-related diagnostic trouble codes detected during current or last completed driving cycle. It enables the external test equipment to obtain "pending" diagnostic trouble codes detected during current or last completed driving cycle for emission-related components/systems. This is used by service technicians after a vehicle repair, and after clearing diagnostic information to see test results after a single driving cycle to determine if the repair has fixed the problem. See #OBD-II_diagnostic_trouble_codes.
Service / Mode $08 could enable the off-board test device to control the operation of an on-board system, test, or component.
Service / Mode $09 is used to retrieve vehicle information. Among others, the following information is available:
VIN (Vehicle Identification Number): Vehicle ID
CALID (Calibration Identification): ID for the software installed on the ECU
CVN (Calibration Verification Number): Number used to verify the integrity of the vehicle software. The manufacturer is responsible for determining the method of calculating CVN(s), e.g. using checksum.
In-use performance counters
Gasoline engine : Catalyst, Primary oxygen sensor, Evaporating system, EGR system, VVT system, Secondary air system, and Secondary oxygen sensor
Diesel engine : NMHC catalyst, NOx reduction catalyst, NOx absorber Particulate matter filter, Exhaust gas sensor, EGR system, VVT system, Boost pressure control, Fuel system.
See OBD-II PIDs#Service_09 for an extensive list.
Service / Mode $0A lists emission-related "permanent" diagnostic trouble codes stored. As per CARB, any diagnostic trouble codes that is commanding MIL on and stored into non-volatile memory shall be logged as a permanent fault code. See #OBD-II_diagnostic_trouble_codes.
Applications
Various tools are available that plug into the OBD connector to access OBD functions. These range from simple generic consumer level tools to highly sophisticated OEM dealership tools to vehicle telematic devices.

Hand-held scan tools

Multi-brand vehicle diagnostics system handheld Autoboss V-30 with adapters for connectors of several vehicle manufacturers.[30]
A range of rugged hand-held scan tools is available.

Simple fault code readers/reset tools are mostly aimed at the consumer level.
Professional hand-held scan tools may possess more advanced functions
Access more advanced diagnostics
Set manufacturer- or vehicle-specific ECU parameters
Access and control other control units, such as air bag or ABS
Real-time monitoring or graphing of engine parameters to facilitate diagnosis or tuning
Mobile device-based tools and analysis
Mobile device applications allow mobile devices such as cell phones and tablets to display and manipulate the OBD-II data accessed via USB adaptor cables or Bluetooth adapters plugged into the car's OBD II connector. Newer devices on the market are equipped with GPS sensors and the ability to transmit vehicle location and diagnostics data over a cellular network. Modern OBD-II devices can therefore nowadays be used to for example locate vehicles, monitor driving behavior in addition to reading Diagnostics Trouble Codes (DTC). Even more advanced devices allow users to reset engine DTC codes, effectively turning off engine lights in the dashboard; however, resetting the codes does not address the underlying issues and can in worst-case scenarios even lead to engine breakage where the source issue is serious and left unattended for long periods of time.[31][32]

OBD2 Software
An OBD2 software package when installed in a computer (Windows, Mac, or Linux) can help diagnose the onboard system, read and erase DTCs, turn off MIL, show real-time data, and measure vehicle fuel economy.[33]

To use OBD2 software, one needs to have an OBD2 adapter (commonly using Bluetooth, Wi-Fi or USB)[34] plugged in the OBD2 port to enable the vehicle to connect with the computer where the software is installed.[35]

PC-based scan tools and analysis platforms

Typical simple USB KKL Diagnostic Interface without protocol logic for signal level adjustment.
A PC-based OBD analysis tool that converts the OBD-II signals to serial data (USB or serial port) standard to PCs or Macs. The software then decodes the received data to a visual display. Many popular interfaces are based on the ELM327 or STN[36] OBD Interpreter ICs, both of which read all five generic OBD-II protocols. Some adapters now use the J2534 API allowing them to access OBD-II Protocols for both cars and trucks.

In addition to the functions of a hand-held scan tool, the PC-based tools generally offer:

Large storage capacity for data logging and other functions
Higher resolution screen than handheld tools
The ability to use multiple software programs adding flexibility
The identification and clearance of fault code
Data shown by intuitive graphs and charts
The extent that a PC tool may access manufacturer or vehicle-specific ECU diagnostics varies between software products[37] as it does between hand-held scanners.

Data loggers

TEXA OBD log. Small data logger with the possibility to read out the data later on PC via USB.
Data loggers are designed to capture vehicle data while the vehicle is in normal operation, for later analysis.

Data logging uses include:

Engine and vehicle monitoring under normal operation, for the purposes of diagnosis or tuning.
Some US auto insurance companies offer reduced premiums if OBD-II vehicle data loggers[38][39] or cameras[40] are installed - and if the driver's behaviour meets requirements. This is a form of auto insurance risk selection
Monitoring of driver behaviour by fleet vehicle operators.
Analysis of vehicle black box data may be performed on a periodic basis, automatically transmitted wirelessly to a third party or retrieved for forensic analysis after an event such as an accident, traffic infringement or mechanical fault.

Emission testing
In the United States, many states now use OBD-II testing instead of tailpipe testing in OBD-II compliant vehicles (1996 and newer). Since OBD-II stores trouble codes for emissions equipment, the testing computer can query the vehicle's onboard computer and verify there are no emission related trouble codes and that the vehicle is in compliance with emission standards for the model year it was manufactured.

In the Netherlands, 2006 and later vehicles get a yearly EOBD emission check.[41]

Driver's supplementary vehicle instrumentation
Driver's supplementary vehicle instrumentation is instrumentation installed in a vehicle in addition to that provided by the vehicle manufacturer and intended for display to the driver during normal operation. This is opposed to scanners used primarily for active fault diagnosis, tuning, or hidden data logging.

Auto enthusiasts have traditionally installed additional gauges such as manifold vacuum, battery current etc. The OBD standard interface has enabled a new generation of enthusiast instrumentation accessing the full range of vehicle data used for diagnostics, and derived data such as instantaneous fuel economy.

Instrumentation may take the form of dedicated trip computers,[42] carputer or interfaces to PDAs,[43] smartphones, or a Garmin navigation unit.

As a carputer is essentially a PC, the same software could be loaded as for PC-based scan tools and vice versa, so the distinction is only in the reason for use of the software.

These enthusiast systems may also include some functionality similar to the other scan tools.

Vehicle telematics
OBD II information is commonly used by vehicle telematics devices that perform fleet tracking, monitor fuel efficiency, prevent unsafe driving, as well as for remote diagnostics and by Pay-As-You-Drive insurance.

Although originally not intended for the above purposes, commonly supported OBD II data such as vehicle speed, RPM, and fuel level allow GPS-based fleet tracking devices to monitor vehicle idling times, speeding, and over-revving. By monitoring OBD II DTCs a company can know immediately if one of its vehicles has an engine problem and by interpreting the code the nature of the problem. It can be used to detect reckless driving in real time based on the sensor data provided through the OBD port.[44] This detection is done by adding a complex events processor (CEP) to the backend and on the client's interface. OBD II is also monitored to block mobile phones when driving and to record trip data for insurance purposes.[45]

OBD-II diagnostic trouble codes
OBD-II diagnostic trouble codes (DTCs)[46][47] are five characters long, with the first letter indicating a category, and the remaining four being a hexadecimal number.[48]

The first character, representing category can only be one of the following four letters, given here with their associated meanings. (This restriction in number is due to how only two bits of memory are used to indicate the category when DTCs are stored and transmitted).[48]

P – Powertrain (engine, transmission and ignition)
C – Chassis (includes ABS and brake fluid)
B – Body (includes air conditioning and airbag)
U – Network[a] (wiring bus)
Whilst this is commonly referred to as the network category, it may originally have been the 'undefined' category, hence the use of the letter 'U' rather than 'N'.
The second character is a number in the range of 0-3. (This restriction is again due to memory storage limitations).[48]

0 – Indicates a generic (SAE defined) code.
1 – Indicates a manufacturer-specific (OEM) code.
2 – Category dependent:
For the 'P' category this indicates a generic (SAE defined) code.
For other categories indicates a manufacturer-specific (OEM) code.
3 – Category dependent:
For the 'P' category this is indicates a code that has been 'jointly' defined.
For other categories this has been reserved for future use.
The third character may denote a particular vehicle system that the fault relates to.[46]

0 – Fuel and air metering and auxiliary emission controls
1 – Fuel and air metering
2 – Fuel and air metering (injector circuit)
3 – Ignition systems or misfires
4 – Auxiliary emission controls
5 – Vehicle speed control and idle control systems
6 – Computer and output circuit
7 – Transmission
8 – Transmission
A-F - Hybrid Trouble Codes
Finally the fourth and fifth characters define the exact problem detected.

Standards documents
SAE standards documents on OBD-II
J1962 – Defines the physical connector used for the OBD-II interface.
J1850 – Defines a serial data protocol. There are 2 variants: 10.4 kbit/s (single wire, VPW) and 41.6 kbit/s (2 wire, PWM). Mainly used by US manufacturers, also known as PCI (Chrysler, 10.4K), Class 2 (GM, 10.4K), and SCP (Ford, 41.6K)
J1978 – Defines minimal operating standards for OBD-II scan tools
J1979 – Defines standards for diagnostic test modes
J2012 – Defines standards trouble codes and definitions.
J2178-1 – Defines standards for network message header formats and physical address assignments
J2178-2 – Gives data parameter definitions
J2178-3 – Defines standards for network message frame IDs for single byte headers
J2178-4 – Defines standards for network messages with three byte headers\*
J2284-3 – Defines 500K CAN physical and data link layer
J2411 – Describes the GMLAN (Single-Wire CAN) protocol, used in newer GM vehicles. Often accessible on the OBD connector as PIN 1 on newer GM vehicles.
SAE standards documents on HD (Heavy Duty) OBD
J1939 – Defines a data protocol for heavy duty commercial vehicles
ISO standards
ISO 9141: Road vehicles – Diagnostic systems. International Organization for Standardization, 1989.
Part 1: Requirements for interchange of digital information
Part 2: CARB requirements for interchange of digital information
Part 3: Verification of the communication between vehicle and OBD II scan tool
ISO 11898: Road vehicles – Controller area network (CAN). International Organization for Standardization, 2003.
Part 1: Data link layer and physical signalling
Part 2: High-speed medium access unit
Part 3: Low-speed, fault-tolerant, medium-dependent interface
Part 4: Time-triggered communication
ISO 14230: Road vehicles – Diagnostic systems – Keyword Protocol 2000, International Organization for Standardization, 1999.
Part 1: Physical layer
Part 2: Data link layer
Part 3: Application layer
Part 4: Requirements for emission-related systems
ISO 15031: Communication between vehicle and external equipment for emissions-related diagnostics, International Organization for Standardization, 2010.
Part 1: General information and use case definition
Part 2: Guidance on terms, definitions, abbreviations and acronyms
Part 3: Diagnostic connector and related electrical circuits, specification and use
Part 4: External test equipment
Part 5: Emissions-related diagnostic services
Part 6: Diagnostic trouble code definitions
Part 7: Data link security
ISO 15765: Road vehicles – Diagnostics on Controller Area Networks (CAN). International Organization for Standardization, 2004.
Part 1: General information
Part 2: Network layer services ISO 15765-2
Part 3: Implementation of unified diagnostic services (UDS on CAN)
Part 4: Requirements for emissions-related systems
Security issues
Researchers at the University of Washington and University of California examined the security around OBD, and found that they were able to gain control over many vehicle components via the interface. Furthermore, they were able to upload new firmware into the engine control units. Their conclusion is that vehicle embedded systems are not designed with security in mind.[49][50][51]

There have been reports of thieves using specialist OBD reprogramming devices to enable them to steal cars without the use of a key.[52] The primary causes of this vulnerability lie in the tendency for vehicle manufacturers to extend the bus for purposes other than those for which it was designed, and the lack of authentication and authorization in the OBD specifications, which instead rely largely on security through obscurity.[53]

See also

Wikimedia Commons has media related to Obd2.
OBD-II PIDs ("Parameter IDs")
Unified Diagnostic Services
Engine control unit
Immobiliser
