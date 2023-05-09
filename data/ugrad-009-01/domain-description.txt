Ugrad009-01 Assignment
The goal of this assignment is to implement the extremely simplified Payroll system.
Requirements
The system has to process payment records from an input file that contains the details of employees, what they earn, how much time they should
be paid for, and so on. The input file is specified from the keyboard (stdin) as an argument to your program, and the output is to be ASCII to the
console (stdout). There is a second argument to your program that specifies exactly what processing is to take place on the employee records.
More details are below. Usually a set of requirements will take pages, not a few paragraphs, so it is quite possible you will find something that
has not been completely specified, or seems inconsistent. In this case please ask!
Your system has to support two kinds of employees — Salaried and Hourly. Salaried employees get an annual salary (assuming 52 weeks at 40
hours per week) and they get paid the same amount no matter how many hours they actually work, that is, for 40 hours a week. Hourly
employees get paid at an hourly rate. A typical working week is 40 hours, but if they work more than that they get overtime payments. The
overtime for Hourly employees is "time and a half" for the first 3 hours (if they work more than 40 hours but less than 43 hours then the time they
work over 40 hours is multiplied by 1.5). For time over 43 hours, they get "double time" (hours over 43 are multiplied by 2) in addition to the
time and a half for the 3 hours over 40 hours.
The input file should be a text file (format described below). It can have any name (including any suffix). If there are any errors in the input file
any output you produce will be ignored (although see Errors in Data below for options).
To simplify things, some unrealistic assumptions have be made.
A year is exactly 52 weeks.
The pay period is always 7 days.
The pay period is the same for every employee record in an input file.
Time period is to the nearest quarter-hour.
Round money to the nearest cent after each operation.
Everyone has a given name and a family name.
Arguments
As mentioned above there are two arguments to the program.
Employee Records
The employee records are specified in a text file, one employee record per line. Each line has several fields of data, as follows:
1. Tax ID number, an integer, unique for all employees (in the input file).
2. Name, formatted as "Family Name, Given Names" (not including the quotes, but including the comma)
3. Either "Salaried" or "Hourly" for the two kinds of employees
4. Rate. For Salaried employees this will be the annual salary. For Hourly employees this will be the amount per hour.
5. YTD (Year to date). The income earned by the employee to the current date.
6. Start. The first day of the pay period. A string formatted as an ISO 8601 date (yyyy-mm-dd, with leading zeros as necessary).
7. End. The last day of the pay period. (Also an ISO 8601 date). The end date should be 7 days after the start date. (This makes the end date
unnecessary but it is provided for future development.)
8. Hours. The number of hours worked in the pay period. Hours are computed to the nearest 15 minutes, so this value is a floating point
number, but the 'fractional' part should only be ".0", ".25", ".50", or ".75".
9. Deductions. The total amount that should be deducted after the income tax is deducted. This covers things such as Union membership and
Superannuation.
The values appear in the order given above, and a separated by tab ("\t") characters. An example is as below. Note that lines that begin with "#"
are to be ignored (they are comments), as are blank lines.
#---------------- FILE START --------------------------
#TID Name Employment Rate YTD Start End Hours Deduction
216 Deacon, John Salaried $89000 $750 2016-02-15 2016-02-21 45.0 $200
11 May, Brian Salaried $52000 $0 2016-02-15 2016-02-21 40.0 $100
31 Taylor, Roger Hourly $20.00 $400 2016-02-15 2016-02-21 42.5 $50.50
#---------------- FILE END --------------------------
Processing
The second argument specfies the kind of processing to be done. This must be 1 of the following values (case sensitive):
Payslips
Produce payslips in TID order.
Employees
List employees in alphabetical order of family name.
Burden
Report how much is needed to meet this payroll (total gross amount that will be paid).
PAYE
Report how much tax (Pay As You Earn) the company need to give to the government.
Output
The output depends on the processing to be performed.
Payslips processing
For this kind of processing the output is, the first line contains the date of the report (in ISO-8601 format), followed by a line for each Employee
that needs to be paid, a "Payslip". Like the input, this will be one line of output per employee. The order of the payslips is in TID order. Each line
should contain:
The Income Tax ID
The employee's name, as Given Names Family Name.
The period the payslip is for.
The gross amount earned by the employee (that is, for the hours worked, how much they would earn before any deductions)
The Income Tax (PAYE) they pay for the pay period based on the New Zealand income tax rates (see below)
The other deductions made.
The amount the employee will be paid (Nett) after all deductions have been made.
The YTD amount after this pay period.
Each line should be formatted as in the example below, which is what would be expected for the input example above. Note use of punctuation
and spacing. There are no tab or other non-space whitespace characters. There is only a single space between parts.
2016-03-21
11. Brian May, Period: 2016-02-15 to 2016-02-21. Gross: $1000.00, PAYE: $165.77, Deductions: $100.00 Nett: $734.23 YTD: $1000.00
31. Roger Taylor, Period: 2016-02-15 to 2016-02-21. Gross: $875.00, PAYE: $134.28, Deductions: $50.50 Nett: $690.22 YTD: $1275.00
216. John Deacon, Period: 2016-02-15 to 2016-02-21. Gross: $1711.54, PAYE: $390.19, Deductions: $200.00 Nett: $1121.35 YTD: $2461.54
Employees processing
For this kind of processing, the output is a list of the employees, one per line, in alphabetical order of Family name, formatted as in the example
below.
2016-03-21
Deacon, John (216) Salaried, $89000.00 YTD:$2461.54
May, Brian (11) Salaried, $52000.00 YTD:$1000.00
Taylor, Roger (31) Hourly, $20.00 YTD:$1275.00
Burden processing
For this kind of processing the output is the total of the gross amounts for the employees, formatted as in the example below.
2016-03-21
2016-02-15 to 2016-02-21
Total Burden: $3586.54
PAYE processing
For this kind of processing the output is the total of the paye amounts for the employees, formatted as in the example below.
2016-03-21
2016-02-15 to 2016-02-21
Total PAYE: $690.24
Tax Computation
For the assignment, we will assume all pay periods are for one week. For salaried employees, the tax computed is the same every pay period, but
for hourly it depends on what the total for the year will be. Overtime may take an employee into a new tax bracket, but we won't know that until
the end of the year. Because of the simplifications already made, we do not have enough information to do this properly. So, compute tax as
follows:
Multiply the gross for the pay period by 52 to get the annual gross. The annual tax is computed for the annual gross, and then divided by 52 to get
the tax for the pay period.
The tax rate to use is:
Taxable income Tax rate
Up to $14,000 10.5%
Over $14,000 and up to $48,000 17.5%
Over $48,000 and up to $70,000 30%
Remaining income over $70,000 33%
Errors in Data
There are several ways in which the input file could contain errors. A partial list is:
Missing data Data in wrong format (e.g. money amounts that do not begin with '$', Dates in wrong format, Name in wrong format)
Tax IDs are not unique
Nonsense values (e.g. negative numbers, deductions that are greater than what the employee earns, end date before start date)
You can get full marks without dealing with errors in the data. If you do try to do so (and are reasonably successful), you could an Immunity Idol
of up to 10%. This will provide a top-up of your mark up to 100%, and so give you some protection against lost marks in other parts of the
assignment. You do not need to deal with all possible errors to get the full 10%.
Non-functional requirements
You may not use Java 8-specific features.
Your class with the main method must be named payroll.Payroll.