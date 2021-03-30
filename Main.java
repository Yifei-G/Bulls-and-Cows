package bullscows;
import java.util.Random;
import  java.util.Scanner;
public class Main {
    public static void main(String[] args) {
          System.out.println("Input the length of the secret code:");
          Scanner scanner = new Scanner(System.in);
          String codeLen = scanner.nextLine();
          try {
              int secretCodeLen = Integer.parseInt(codeLen);
              System.out.println("Input the number of possible symbols in the code:");
              int codeRange = scanner.nextInt();

              if (secretCodeLen < codeRange && secretCodeLen != 0) {
                  String secretSymbol = "*";
                  String allSymbols = "0123456789abcdefghijklmnopqrstuvwxyz";

                  if (codeRange > 10) {
                      System.out.printf(" (0-9, a-%s).\n",allSymbols.charAt(codeRange - 1));
                  } else {
                      System.out.printf(" (0-%s).\n", allSymbols.charAt(codeRange - 1));
                  }
                  System.out.print("The secret is prepared: ");
                  for (int i = 0; i  < secretCodeLen; i ++) {
                      System.out.print(secretSymbol);
                  }
                  System.out.println("Okay, let's start a game!");
                  String secretCode = generateSecret(codeRange, allSymbols, secretCodeLen);
                  processResult(secretCode);
              } else {
                  System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", secretCodeLen, codeRange);
              }

          } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
          } catch (Exception e) {
              System.out.printf("Error: \"%s\" isn't a valid number.\n", codeLen);
          }
    }

    
    public static String generateSecret(int codeRange, String allSymbols, int secretCodeLen) {
        StringBuilder secret = new StringBuilder();
        Random random = new Random();
        while (true) {
            int randomIndex = random.nextInt(codeRange);
            char randomSymbol = allSymbols.charAt(randomIndex);
            if (!secret.toString().contains(Character.toString(randomSymbol))) {
                secret.append(randomSymbol);
            }
            if (secret.length() == secretCodeLen) {
                break;
            }
        }
        return secret.toString();
    }


    public static void processResult(String secretCode) {
        int turn = 1;
        while(true) {
            System.out.printf("Turn %d:\n" ,turn);
            Scanner scanner1 = new Scanner(System.in);
            String userGuess = scanner1.nextLine();
            int bullCount = 0;
            int cowCount = 0;
            for (int index = 0; index < userGuess.length(); index++) {
                char userDigit = userGuess.charAt(index);
                char secretDigit = secretCode.charAt(index);
                if (userDigit == secretDigit) {
                    bullCount += 1;
                } else if (secretCode.contains(Character.toString(userDigit))) {
                    cowCount += 1;
                }
            }

            if (bullCount == 0 && cowCount == 0) {
                System.out.println("Grade: None. ");
            } else if (bullCount == 0 && cowCount > 0) {
                System.out.printf("Grade: %d cow(s).\n",cowCount);
            } else if (cowCount == 0 && bullCount > 0) {
                if (bullCount == secretCode.length()) {
                    System.out.println("Grade: " + bullCount + " bulls");
                    System.out.println("Congratulations! You guessed the secret code.");
                    break;
                }
                System.out.printf("Grade: %d bull(s).\n",bullCount);
            } else {
                System.out.printf("Grade: %d bull(s) and %d cow(s).\n",bullCount,cowCount);
            }
            turn++;
        }
    }
}
