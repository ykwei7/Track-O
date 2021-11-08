---
layout: page
title: Ryan's Project Portfolio Page
---

### Project: Track-O

Track-O is a desktop address book application used by private tutors to schedule lessons and track payments. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 17 kLoC.

Given below are my contributions to the project.

* **Add and clear remarks**: Added the ability to add and clear remarks for Tutees.
  * What it does: allows the user to add informal remarks and clear them as necessary.
  * Justification: Allows the user to add information that may not be reflected in the fields given, such as work progress, homework assigned or questions asked. Information also need not be limited to a single line or paragraph and multiple sets of information can be added using remarks.
  * Highlights: Adding remarks appends new remarks to existing remarks instead of replacing them. Using the `clearremark` Command clears all remarks.
  * Credits: Initial implementation from [AB3 tutorial](https://nus-cs2103-ay2122s1.github.io/tp/tutorials/AddRemark.html) and Yangken
<br><br>
* **`School` field**: Added a `school` field to `Tutee`.
  * What it does: Tutors can now save infomration about a tutee's school when adding them to Track-O.
  * Justification: Having information about what school a tutee is from will help tutors plan their lessons more efficiently, such as by planning their schedules based on the timetables of different schools or by catering to different school's students differently based on how their syllabus is covered in school.
  * Highlights: This enhancement affects existing commands such as `add` and `edit` as a new field is added, leading to an increase in the number of parameters these commands take in. 
  * Credits: *-*
<br><br>
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=nhjryan&tabRepo=AY2122S1-CS2103T-F12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)
<br><br>
* **Project management**:
  * Ensure the quality and format of the deliverables.
<br><br>
* **Enhancements to existing features**:
  * Refactor AB3 to change all instances of `Person` to `Tutee` and `AddressBook` to `TrackO` [\#43](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/44)
  * Update `help` command to make it more reader-friendly [\#84](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/84)
<br><br>
* **Documentation**:
  * User Guide:
    * Added documentation for `remark` and `clearremark` commands [\#107](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/107)
  * Developer Guide:
    * Add in target user profile, value proposition and user stories [\#13](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/13)
    * Amend UML diagrams to reflect `Tutee` and `TrackO` instead of `Person` and `AddressBook` respectively [\#64](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/64)
<br><br>
* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#37](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/37), [\#99](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/99), [\#162](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/162), [\#172](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/172)
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2122S1-CS2103T-T09-1/tp/issues/193), [2](https://github.com/AY2122S1-CS2103T-T09-1/tp/issues/176), [3](https://github.com/AY2122S1-CS2103T-T09-1/tp/issues/182))
