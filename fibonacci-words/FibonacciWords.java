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

      final char nthDigit = getTheLineWithDigits(firstString, secondString, quantityOfDigits);

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

    final long quantityIterations = doFibUntilReachRequiredQuantityDigits(firstWordLength,
        secondWordLength,
        requiredQuantityDigits);

    return getNthDigit(firstString, secondString, firstWordLength, secondWordLength,
        quantityIterations, requiredQuantityDigits);
  }

  private static long doFibUntilReachRequiredQuantityDigits(long firstWordLength,
      long secondWordLength,
      long requiredQuantityDigits) {
    long actualSize = firstWordLength + secondWordLength;
    long actualIteration = 2;

    while (actualSize < requiredQuantityDigits) {
      actualIteration++;

      long temporaryVariable = actualSize;
      actualSize += secondWordLength;
      secondWordLength = temporaryVariable;
    }

    return actualIteration;
  }

  private static char getNthDigit(String firstString, String secondString, long firstWordLength,
      long secondWordLength, long quantityIterations, long requiredQuantityDigits) {
    long actualQuantityOFDigits = requiredQuantityDigits;
    long actualIteration = quantityIterations;

    while (true) {
      long minusOneIteration = actualIteration - 2;
      long minusTwoIteration = actualIteration - 1;

      final long minusOneLength = doFib(minusOneIteration, firstWordLength, secondWordLength);

      if (minusOneLength >= actualQuantityOFDigits && minusOneIteration < 2) {
        return getFromBasicIteration(firstString, secondString, minusOneIteration,
            actualQuantityOFDigits);
      } else if (minusOneLength >= actualQuantityOFDigits) {
        actualIteration = minusOneIteration;
        continue;
      }

      actualQuantityOFDigits -= minusOneLength;

      final long minusTwoLength = doFib(minusTwoIteration, firstWordLength, secondWordLength);

      if (minusTwoLength >= actualQuantityOFDigits && minusTwoIteration < 2) {
        return getFromBasicIteration(firstString, secondString, minusTwoIteration,
            actualQuantityOFDigits);
      }

      actualIteration = minusTwoIteration;
    }
  }

  private static char getFromBasicIteration(String firstString, String secondString,
      long actualIteration, long requiredQuantityDigits) {
    final int positionInsideString = toIntExact(requiredQuantityDigits - 1);

    if (actualIteration == 0L) {
      return firstString.charAt(positionInsideString);
    }

    return secondString.charAt(positionInsideString);
  }

  private static long doFib(long quantityIterations, long firstWordLength,
      long secondWordLength) {

    if (quantityIterations == 0) {
      return firstWordLength;
    } else if (quantityIterations == 1) {
      return secondWordLength;
    }

    long actualIteration = 1;
    long response = secondWordLength;

    while (actualIteration < quantityIterations) {
      long temporaryVariable = response;
      response += firstWordLength;
      firstWordLength = temporaryVariable;

      actualIteration++;
    }

    return response;
  }
}