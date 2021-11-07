---
layout: page
title: Yangken's Project Portfolio Page
---

### Project: Track-O

Track-O is a desktop address book application used by private tutors to schedule lessons and track payments. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 17 kLoC.

Given below are my contributions to the project.

* **Tracking payments**: Added the ability to track payments for Tutees.
  * What it does: allows the user to store payment amounts due by their tutees and keep track of the due date.
  * Justification: This feature implements a core functionality for the application as tutors need not rely on manual bookkeeping, reducing mistakes in calculations and providing more convenience to the users.
  * Highlights: This enhancement affects existing commands and commands to be added in future, such as `PaymentAddCommand` and `PaymentSetAmountCommand`.
    It required an in-depth analysis of design alternatives, especially in regard to how the payment amount would be accumulated by the tutee and how the user would track it.
    <br><br>
    For example, initial plans involved having a fixed payment due date of 1 week since a payment was made, to be calculated automatically using `LocalDate`, but considering not all tutors collect their fees weekly, the final design let them set their own payment due dates.
  * The implementation was challenging to utilize `payment` as a parent command and parse it into 5 separate functionalities.
  * Credits: *-*

* **Get**: Added the ability to view more information.
  * What it does: allows the user to keep track of additional information without it clogging existing GUI tutee panel.
  * Justification: Information of tutee may span multiple lines, especially to track progress of the tutee. If it were displayed on the GUI tutee panel, this would reduce readability. Hence, the `get` command allows for information to be displayed without affected general overview of GUI.
  * Highlights: This enhancement allowed for more fields to be added such as lesson information and remarks which takes up more space, compared to shorter information like address.
  * Credits: *-*

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=ykwei7&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabAuthor=atyhamos&tabRepo=AY2122S1-CS2103T-F12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&tabType=zoom&zA=ykwei7&zR=AY2122S1-CS2103T-F12-3%2Ftp%5Bmaster%5D&zACS=238.5&zS=2021-09-17&zFS=ykwei7&zU=2021-11-05&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)

* **Project management**:
  * Assigned and tracked tasks and feature implementations

* **Enhancements to existing features**:
  * Fixed issue to synchronize error message output when index exceeds max integer range

* **Documentation**:
  * User Guide:
    * Added the `Setting up your first tutee` portion of the UG for users to get started
  * Developer Guide:
    * Added the use cases for `payment` feature for different scenarios

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#107](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/107),
  [\#159](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/159),
    [\#162](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/162)
