package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_PAYMENT = new Prefix("pay/");
    public static final Prefix PREFIX_OVERDUE = new Prefix("overdue/");
    public static final Prefix PREFIX_SCHOOL = new Prefix("sch/");
    public static final Prefix PREFIX_LEVEL = new Prefix("l/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");

    public static final Prefix PREFIX_SUBJECT = new Prefix("subject/");
    public static final Prefix PREFIX_DAY_OF_WEEK = new Prefix("d/");
    public static final Prefix PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix PREFIX_HOURLY_RATE = new Prefix("rate/");

    // PREFIX_ADD_PAYMENT refers to number of lessons added
    public static final Prefix PREFIX_LESSON = new Prefix("lesson/");
    public static final Prefix PREFIX_PAYMENT_DATE = new Prefix("by/");
    public static final Prefix PREFIX_PAYMENT_AMOUNT = new Prefix("amount/");
    public static final Prefix PREFIX_PAYMENT_RECEIVED_DATE = new Prefix("receive/");
}
