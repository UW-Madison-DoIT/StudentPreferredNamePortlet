# Analysis: potential support for names with accented characters

So, what prevents this Portlet from supporting names with accented characters,
like García or André.

Or going further, what prevents support of a wide Unicode character set?

## Form validation

Status quo, the Portlet performs form validation using regular expressions that
do not support accented characters.

Even if the whole rest of the system would technically support
accented characters, this form validation prevents the user from submitting
the form with such characters.

This is server-side in the `PreferredNameValidator` class.

The given name and middle name validate with
(Java) regular expression `"^[A-Za-z .-]*$"`.
The surname validates with regular expression `"^[A-Za-z \\\\'-]*$"`

This is described to the user as

> Only A-Z, a-z, ' ' (space), and '-' (hyphen) are allowed.

We could change the regular expression or use a non-regular-expression approach
to validating the input that accepted accented characters.

## Interface to the database

The Java-facing interface of the data access layer classes
accept and return Java Strings. Java Strings can represent accented characters

> A String represents a string in the UTF-16 format

The database-facing interface is declaring parameters as type VARCHAR.
[VARCHAR in Oracle databases can support UTF-8](https://docs.oracle.com/cd/B28359_01/server.111/b28318/datatype.htm#CNCPT1822)
but it is not guaranteed to do so. (Type [NVARCHAR2 would be gauranteed to support UTF-8](https://docs.oracle.com/cd/B28359_01/server.111/b28318/datatype.htm#CNCPT1828)).

[Choice of character set is at the time of database creation](https://docs.oracle.com/cd/B28359_01/server.111/b28298/ch2charset.htm#i1006750).

## The stored procedures themselves

Open questions: what character set is the relevant database using?
Would it support UTF-8?
