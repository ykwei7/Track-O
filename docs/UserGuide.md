---
layout: page
title: User Guide
---

Track-O is a **desktop app for private tutors to manage their tutees**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). Track-O lets you track an assortment of data, from grades to home addresses to lesson timings. You can easily access the many functions of Track-O using simple commands, such as add and get. Hop over to our Quick Start section to get started.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `tracko.jar` from here.

3. Copy the file to the folder you want to use as the _home folder_ to run Track-O.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will prompt the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all tutees.

   * **`add`**`n/John Doe l/P5: Adds a contact named John Doe of level P5` to the Address Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the tutee list.

   * **`clear`** : Deletes all contacts.  [To be released]

   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.


### Viewing help : `help`

Shows a message explaining how to access the help page.


Format: `help`


### Adding a tutee: `add`

Adds a tutee to the existing list of tutees.

Format: `add n/NAME l/LEVEL​`

Examples:
* `add n/John Doe l/J2`
* `add n/Betsy Crowe l/P5`

### Listing all tutees : `list`

Shows the current list of tutees.

Format: `list`

### Deleting a tutee : `delete`

Deletes the specified tutee from our list of tutees.

Format: `delete INDEX`

* Deletes the tutee at the specified `INDEX`.
* The index refers to the index number shown in the displayed tutee list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd tutee in the address book.

### Obtaining details of a tutee : `get`

Gets details of the specified tutee from our list of tutees.

Format: `get INDEX`

* Gets the tutee at the specified `INDEX` in the format `Name: John Doe Level: P5`
* The index refers to the index number shown in the displayed tutee list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `get 2` shows the 2nd tutee in the address book.


### Exiting application : `exit`

Exits from the application.

### Editing a tutee :  `[coming in v2.0]`

### Locating tutees by name:  `[coming in v2.0]`

### Clearing all entries : `[coming in v2.0]`

### Saving the data `[coming in v2.0]`

### Editing the data file `[coming in v2.0]`

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME l/LEVEL` <br> e.g. `add n/James Ho l/S4`
**Delete** | `delete INDEX`<br> e.g. `delete 3`
**Get** | `get INDEX`<br> e.g. `get 2`
**List** | `list`
**Help** | `help`
**Exit** | `exit`
