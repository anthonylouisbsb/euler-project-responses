import static java.lang.Math.toIntExact;

import java.util.Scanner;

public class FibonacciWords {

  private static final String EOF_PATTERN = "(\r\n|[\n\r\u2028\u2029\u0085])?";

  public static void main(String args[]) {
    final Scanner scanner = new Scanner(System.in);

    final int quantityOfTriplets = scanner.nextInt();
    scanner.skip(EOF_PATTERN);

    for (int i = 0; i < quantityOfTriplets; i++) {
      final String firstString = scanner.next();
      scanner.skip(EOF_PATTERN);

      final String secondString = scanner.next();
      scanner.skip(EOF_PATTERN);

      final long quantityOfDigits = scanner.nextLong();
      scanner.skip(EOF_PATTERN);

      final char nthDigit = getTheLineWithDigits(firstString, secondString,
          quantityOfDigits);

      System.out.println(nthDigit);
    }
  }

  private static char getTheLineWithDigits(String firstString, String secondString,
      long requiredQuantityDigits) {

    final int quantityOfDigitsFirst = firstString.length();

    if (quantityOfDigitsFirst >= requiredQuantityDigits) {
      final int positionInsideSecondString = toIntExact(requiredQuantityDigits - 1);

      return firstString.charAt(positionInsideSecondString);
    }

    final int quantityOfDigitsSecond = secondString.length();

    if (quantityOfDigitsFirst + quantityOfDigitsSecond >= requiredQuantityDigits) {
      int posInSecondString = toIntExact(requiredQuantityDigits - (firstString.length() + 1));

      return firstString.charAt(posInSecondString);
    }

    return doFibUntilReachRequiredQuantity(firstString, secondString, 3,
        requiredQuantityDigits);
  }

  private static char doFibUntilReachRequiredQuantity(String firstString,
      String secondString, int actualLine, long requiredQuantityDigits) {

    final int firstStrLength = firstString.length();
    final int secondStrLength = secondString.length();

    if (firstStrLength + secondStrLength >= requiredQuantityDigits) {
      final int posInsideString = toIntExact(requiredQuantityDigits - (firstStrLength + 1));

      return secondString.charAt(posInsideString);
    }

    final String concatenatedString = firstString + secondString;

    return doFibUntilReachRequiredQuantity(secondString, concatenatedString,
        ++actualLine, requiredQuantityDigits);
  }
}