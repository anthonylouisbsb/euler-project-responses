import static java.lang.String.valueOf;

import java.math.BigInteger;
import java.util.Scanner;

public class FibonacciWords {

  private static final String EOF_PATTERN = "(\r\n|[\n\r\u2028\u2029\u0085])?";

  private static final BigInteger ZERO = new BigInteger("0");
  private static final BigInteger ONE = new BigInteger("1");
  private static final BigInteger TWO = new BigInteger("2");

  public static void main(String[] args) {
    try (final Scanner scanner = new Scanner(System.in)) {
      final int quantityOfTriplets = scanner.nextInt();
      scanner.skip(EOF_PATTERN);

      for (int i = 0; i < quantityOfTriplets; i++) {
        final String firstString = scanner.next();
        scanner.skip(EOF_PATTERN);

        final String secondString = scanner.next();
        scanner.skip(EOF_PATTERN);

        final BigInteger quantityOfDigits = scanner.nextBigInteger();
        scanner.skip(EOF_PATTERN);

        final char nthDigit = getTheLineWithDigits(firstString, secondString, quantityOfDigits);

        System.out.println(nthDigit);
      }
    }
  }

  private static char getTheLineWithDigits(String firstString, String secondString,
      BigInteger requiredQuantityDigits) {

    BigInteger firstWordLength = new BigInteger(valueOf(firstString.length()));

    if (firstWordLength.compareTo(requiredQuantityDigits) >= 0) {
      final int positionInsideFirstString = requiredQuantityDigits.intValueExact() - 1;

      return firstString.charAt(positionInsideFirstString);
    }

    BigInteger secondWordLength = new BigInteger(valueOf(secondString.length()));

    if (secondWordLength.compareTo(requiredQuantityDigits) >= 0) {
      final int positionInsideFirstString = requiredQuantityDigits.intValueExact() - 1;

      return secondString.charAt(positionInsideFirstString);
    }

    if (firstWordLength.add(secondWordLength).compareTo(requiredQuantityDigits) >= 0) {
      int posInSecondString = requiredQuantityDigits.subtract(firstWordLength).intValueExact() - 1;

      return secondString.charAt(posInSecondString);
    }

    final BigInteger quantityIterations = doFibUntilReachRequiredQuantityDigits(firstWordLength,
        secondWordLength,
        requiredQuantityDigits);

    return getNthDigit(firstString, secondString, firstWordLength, secondWordLength,
        quantityIterations, requiredQuantityDigits);
  }

  private static BigInteger doFibUntilReachRequiredQuantityDigits(BigInteger firstWordLength,
      BigInteger secondWordLength,
      BigInteger requiredQuantityDigits) {
    BigInteger actualSize = firstWordLength.add(secondWordLength);
    BigInteger actualIteration = new BigInteger("2");

    while (actualSize.compareTo(requiredQuantityDigits) < 0) {
      actualIteration = actualIteration.add(ONE);

      final BigInteger temporaryVariable = actualSize;
      actualSize = actualSize.add(secondWordLength);
      secondWordLength = temporaryVariable;
    }

    return actualIteration;
  }

  private static char getNthDigit(String firstString, String secondString,
      BigInteger firstWordLength,
      BigInteger secondWordLength, BigInteger quantityIterations,
      BigInteger requiredQuantityDigits) {
    BigInteger actualQuantityOFDigits = requiredQuantityDigits;
    BigInteger actualIteration = quantityIterations;

    while (true) {
      BigInteger minusOneIteration = actualIteration.subtract(TWO);
      BigInteger minusTwoIteration = actualIteration.subtract(ONE);

      final BigInteger minusOneLength = doFib(minusOneIteration, firstWordLength, secondWordLength);

      if (minusOneLength.compareTo(actualQuantityOFDigits) >= 0
          && minusOneIteration.compareTo(TWO) < 0) {
        return getFromBasicIteration(firstString, secondString, minusOneIteration,
            actualQuantityOFDigits);
      } else if (minusOneLength.compareTo(actualQuantityOFDigits) >= 0) {
        actualIteration = minusOneIteration;
        continue;
      }

      actualQuantityOFDigits = actualQuantityOFDigits.subtract(minusOneLength);

      final BigInteger minusTwoLength = doFib(minusTwoIteration, firstWordLength, secondWordLength);

      if (minusTwoLength.compareTo(actualQuantityOFDigits) > 0
          && minusTwoIteration.compareTo(TWO) < 0) {
        return getFromBasicIteration(firstString, secondString, minusTwoIteration,
            actualQuantityOFDigits);
      }

      actualIteration = minusTwoIteration;
    }
  }

  private static char getFromBasicIteration(String firstString, String secondString,
      BigInteger actualIteration, BigInteger requiredQuantityDigits) {
    final int positionInsideString = requiredQuantityDigits
        .subtract(ONE)
        .intValueExact();

    if (actualIteration.compareTo(ZERO) == 0) {
      return firstString.charAt(positionInsideString);
    }

    return secondString.charAt(positionInsideString);
  }

  private static BigInteger doFib(BigInteger quantityIterations, BigInteger firstWordLength,
      BigInteger secondWordLength) {

    if (quantityIterations.compareTo(ZERO) == 0) {
      return firstWordLength;
    } else if (quantityIterations.compareTo(ONE) == 0) {
      return secondWordLength;
    }

    BigInteger actualIteration = ONE;
    BigInteger response = secondWordLength;

    while (actualIteration.compareTo(quantityIterations) < 0) {
      final BigInteger temporaryVariable = response;
      response = response.add(firstWordLength);
      firstWordLength = temporaryVariable;

      actualIteration = actualIteration.add(ONE);
    }

    return response;
  }
}