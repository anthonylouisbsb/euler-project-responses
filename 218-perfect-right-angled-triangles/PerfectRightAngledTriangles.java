import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class PerfectRightAngledTriangles {

  private static final Pattern EOF_PATTERN = Pattern.compile("(\r\n|[\n\r\u2028\u2029\u0085])?");

  private static final Set<Long> perfectSquareNumbers = getListOfPerfectSquare();

  public static void main(final String[] args) {
    final Scanner scanner = new Scanner(System.in);

    final int quantityLines = scanner.nextInt();
    scanner.skip(EOF_PATTERN);

    for (int i = 0; i < quantityLines; i++) {
      final long maximumSide = scanner.nextLong();
      scanner.skip(EOF_PATTERN);

      final long quantityOfTriangles = getPerfectAndNotSuperPerfectTriangle(maximumSide);

      System.out.println(quantityOfTriangles);
    }
  }

  private static long getPerfectAndNotSuperPerfectTriangle(long maximumSide) {
    long quantityOfTriangle = 0;
    for (int minorSide = 1; minorSide < maximumSide - 1; minorSide++) {
      quantityOfTriangle = iterateThroughAllMediumFile(maximumSide, quantityOfTriangle, minorSide);
    }

    return quantityOfTriangle;
  }

  private static long iterateThroughAllMediumFile(long maximumSide, long quantityOfTriangle,
      int minorSide) {
    for (int mediumSide = minorSide + 1; mediumSide < maximumSide; mediumSide++) {
      quantityOfTriangle = iterateThroughAllBigSize(maximumSide, quantityOfTriangle, minorSide,
          mediumSide);
    }
    return quantityOfTriangle;
  }

  private static long iterateThroughAllBigSize(long maximumSide, long quantityOfTriangle,
      int minorSide, int mediumSide) {
    for (int bigSize = mediumSide + 1; bigSize < maximumSide + 1; bigSize++) {
      final boolean isPerfectAndNotSuperPerfectTriangle = matchesCondition(minorSide,
          mediumSide, bigSize);

      if (isPerfectAndNotSuperPerfectTriangle) {
        quantityOfTriangle++;
      }
    }
    return quantityOfTriangle;
  }

  private static boolean matchesCondition(int minorSide, int mediumSide, int maximumSide) {
    final boolean isPerfectRightAngled = isPerfectRightAngled(minorSide, mediumSide,
        maximumSide);

    if (!isPerfectRightAngled) {
      return false;
    }

    return !isAreaMultipleOfPerfectNumbers(minorSide, mediumSide, maximumSide);
  }

  private static boolean isAreaMultipleOfPerfectNumbers(int minorSide, int mediumSide,
      int maximumSide) {
    final long triangleArea = getTriangleArea(minorSide, mediumSide);

    return (triangleArea % 6 == 0) && (triangleArea % 28 == 0);
  }

  private static long getTriangleArea(int minorSide, int mediumSide) {
    return (minorSide * mediumSide) / 2;
  }

  private static boolean isPerfectRightAngled(int minorSide, int mediumSide, int maximumSide) {
    final boolean isRightAngledTriangle = isRightAngledTriangle(minorSide, mediumSide,
        maximumSide);

    final boolean isHypotenusePerfectSquare = perfectSquareNumbers.contains(maximumSide);

    return isRightAngledTriangle && isHypotenusePerfectSquare;
  }

  private static boolean isRightAngledTriangle(int minorSide, int mediumSide, int bigSize) {
    final boolean isTriangle = (minorSide + mediumSide) < bigSize;

    if (!isTriangle) {
      return false;
    }

    return numberPowTwo(minorSide) + numberPowTwo(mediumSide) == numberPowTwo(bigSize);
  }

  private static long numberPowTwo(final int number) {
    return number * number;
  }

  private static Set<Long> getListOfPerfectSquare() {
    final long maxNumber = 1 << 18;

    final Set<Long> perfectSquares = new HashSet<>();

    int index = 1;

    while (index <= maxNumber) {
      final long numberPowTwo = numberPowTwo(index);

      if (numberPowTwo > maxNumber) {
        break;
      }

      perfectSquares.add(numberPowTwo);
      index++;
    }

    return perfectSquares;
  }
}
