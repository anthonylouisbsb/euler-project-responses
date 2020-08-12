import static java.lang.Math.toIntExact;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FibonacciWords {

  private static final String EOF_PATTERN = "(\r\n|[\n\r\u2028\u2029\u0085])?";

  private static long firstWordLength = 0L;
  private static long secondWordLength = 0L;

  private static String FIRST_STRING_ID = "A";
  private static String SECOND_STRING_ID = "B";

  private static Map<String, Long> stringToSize = new HashMap<>(2);

  private static Map<String, String> stringIdToPlain = new HashMap<>(2);

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

      stringIdToPlain.put(FIRST_STRING_ID, firstString);
      stringIdToPlain.put(SECOND_STRING_ID, secondString);

      final char nthDigit = getTheLineWithDigits(firstString, secondString,
          quantityOfDigits);

      System.out.println(nthDigit);
    }
  }

  private static char getTheLineWithDigits(String firstString, String secondString,
      long requiredQuantityDigits) {

    firstWordLength = firstString.length();

    if (firstWordLength >= requiredQuantityDigits) {
      final int positionInsideFirstString = toIntExact(requiredQuantityDigits - 1);

      return firstString.charAt(positionInsideFirstString);
    }

    secondWordLength = secondString.length();

    if (firstWordLength + secondWordLength >= requiredQuantityDigits) {
      int posInSecondString = toIntExact(requiredQuantityDigits - (firstWordLength + 1));

      return firstString.charAt(posInSecondString);
    }

    stringToSize.put(FIRST_STRING_ID, firstWordLength);
    stringToSize.put(SECOND_STRING_ID, secondWordLength);

    return doFibUntilReachRequiredQuantity(FIRST_STRING_ID, SECOND_STRING_ID, firstWordLength,
        secondWordLength, requiredQuantityDigits);
  }

  private static char doFibUntilReachRequiredQuantity(String firstStringId, String secondStringId,
      long firstWordLength, long secondWordLength, long requiredQuantityDigits) {

    final String actualString = firstStringId + secondStringId;
    final long actualLength = firstWordLength + secondWordLength;

    if (actualLength >= requiredQuantityDigits) {
      return getCharFromConcatenatedDigit(actualString, actualLength, requiredQuantityDigits);
    }

    return doFibUntilReachRequiredQuantity(secondStringId, actualString, secondWordLength,
        actualLength, requiredQuantityDigits);
  }

  private static char getCharFromConcatenatedDigit(String actualString, final long actualLength,
      final long requiredQuantityOfDigits) {
    long actualSize = 0L;

    for (int i = 0; i < actualLength; i++) {
      final String charAtPoint = String.valueOf(actualString.charAt(i));
      final long stringSize = stringToSize.get(charAtPoint);

      if (actualSize + stringSize >= requiredQuantityOfDigits) {
        final int sizeToSeekInString = toIntExact(requiredQuantityOfDigits - (actualSize + 1));

        final String plainString = stringIdToPlain.get(charAtPoint);

        return plainString.charAt(sizeToSeekInString);
      }

      actualSize += stringSize;
    }

    throw new RuntimeException("Error while trying to retrieve char from concatened string");
  }

}