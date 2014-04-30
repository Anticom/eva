package eu.anticom.eva.util;

/*
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of HumanNumber.

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: HumanNumber.java,v 1.3 2004/05/15 12:12:00 pjm2 Exp $

*/

/**
 * <p>
 * The HumanNumber class provides a few static methods that turn numerical
 * numbers into English-spoken strings.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <pre>    // Format some ints or longs.
 *    String result = HumanNumber.format(0);
 *    result = HumanNumber.format(1);
 *    result = HumanNumber.format(12);
 *    result = HumanNumber.format(123);
 *    result = HumanNumber.format(1234);
 * <p/>
 *    // Format a String (allows longer numbers to be formatted)
 *    result = HumanNumber.format("645635467684453453463455435");</pre>
 * <p>
 * The above examples print the following:
 * </p>
 * <ul>
 * <li>zero</li>
 * <li>one</li>
 * <li>twelve</li>
 * <li>one hundred and twenty three</li>
 * <li>one thousand, two hundred and thirty four</li>
 * <li>six hundred and forty five septillion, six hundred and thirty five sextillion,
 * four hundred and sixty seven quintillion, six hundred and eighty four quadrillion,
 * four hundred and fifty three trillion, four hundred and fifty three billion,
 * four hundred and sixty three million, four hundred and fifty five thousand,
 * four hundred and thirty five</li>
 * </ul>
 * <p>
 * This class supports positive numbers up to approximately 10<sup>65</sup>.
 * </p>
 *
 * @author Paul Mutton, <a href="http://www.jibble.org/">http://www.jibble.org/</a>
 * @version 1.0
 */
public class HumanNumber {

    private static final String[] units = {"zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

    private static final String[] tens = {"zero", "ten", "twenty", "thirty", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"};

    private static final String[] orders = {"thousand", "million", "billion", "trillion",
            "quadrillion", "quintillion", "sextillion", "septillion",
            "octillion", "nonillion", "decillion", "undecillion",
            "duodecillion", "tredecillion", "quattuordecillion",
            "quindecillion", "sexdecillion", "septendecillion",
            "octodecillion", "novemdecillion", "vigintillion"};

    private HumanNumber() {

    }

    public static String format(int input) {
        return format(String.valueOf(input));
    }

    public static String format(long input) {
        return format(String.valueOf(input));
    }

    public static String format(String input) {
        if ((input.length() + 2) / 3 - 1 > orders.length) {
            throw new IllegalArgumentException("Number too big.");
        }
        StringBuffer result = new StringBuffer();
        int i = input.length();
        int order = -1;
        while (i >= 3) {
            int a = charToInt(input.charAt(i - 3));
            int b = charToInt(input.charAt(i - 2));
            int c = charToInt(input.charAt(i - 1));
            String number = format(a, b, c);
            if (order >= 0 && !number.equals("")) {
                result.insert(0, " " + orders[order]);
            }
            result.insert(0, number);
            if (order == -1 && i > 3 && a == 0 && (b != 0 || c != 0)) {
                result.insert(0, " and ");
            } else if (i > 3 && (a != 0 || b != 0 || c != 0)) {
                result.insert(0, ", ");
            }
            order++;
            i = i - 3;
        }
        if (i > 0) {
            if (order >= 0) {
                result.insert(0, " " + orders[order]);
            }
            if (i == 2) {
                result.insert(0, format(0, charToInt(input.charAt(0)), charToInt(input.charAt(1))));
            } else if (i == 1) {
                result.insert(0, format(0, 0, charToInt(input.charAt(0))));
            }
        }
        return result.toString();
    }

    private static String format(int a, int b, int c) {
        String result = "";
        if (b == 1) {
            result = units[10 + c];
        } else {
            if (c != 0) {
                result = units[c];
            }
            if (b >= 2) {
                if (c != 0) {
                    result = " " + result;
                }
                result = tens[b] + result;
            }
        }
        if (a != 0) {
            if (b != 0 || c != 0) {
                result = " and " + result;
            }
            result = units[a] + " hundred" + result;
        }
        return result;
    }

    private static int charToInt(char ch) {
        return ch - '0';
    }

}
