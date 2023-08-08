package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите два числа (арабских или римских): ");
                String expression = scanner.nextLine();
                System.out.println(parse(expression));
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println("Хотите выполнить еще одну операцию? (да, yes/нет, no)");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("да")) if (!answer.equalsIgnoreCase("yes"))
                break;
        }
        scanner.close();
    }

    public static String parse(String expression) throws Exception {
        int num1;
        int num2;
        String oper;
        String result;
        boolean isRoman;
        String[] operands = expression.split("[+\\-*/]");
        if (operands.length != 2) throw new Exception("Данная строка не является математической операцией");
        oper = detectOperation(expression);
        if (oper == null) throw new Exception("Неподдерживаемая математическая операция");
        // если в выражение оба числа римские
        if (Roman.isRoman(operands[0]) && Roman.isRoman(operands[1])) {
            num1 = Roman.convertToArabian(operands[0]);
            num2 = Roman.convertToArabian(operands[1]);
            isRoman = true;
        }
        // если в выражение оба числа арабские
        else if (!Roman.isRoman(operands[0]) && !Roman.isRoman(operands[1])) {
            num1 = Integer.parseInt(operands[0]);
            num2 = Integer.parseInt(operands[1]);
            isRoman = false;
        }
        // если одни число римское, а другое - арабское
        else {
            throw new Exception("Числа должны быть в одном формате");
        }
        if (num1 > 10 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10");
        }
        int arabian = calc(num1, num2, oper);
        if (isRoman) {
            // если римское число получилось меньше либо равно нулю, генерируем ошибку
            if (arabian <= 0) {
                throw new Exception("Римское число должно быть больше нуля");
            }
            // конвертируем результат операции из арабского в римское
            result = Roman.convertToRoman(arabian);
        } else {
            // конвертируем арабское число в тип String
            result = String.valueOf(arabian);
        }
        //возвращаем результат
        return result;
    }



    static String detectOperation(String expression) {
        if (expression.contains("+")) return "+";
        else if (expression.contains("-")) return "-";
        else if (expression.contains("*")) return "*";
        else if (expression.contains("/")) return "/";
        else return null;
    }

    static int calc(int a, int b, String oper) {

        return switch (oper) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> a / b;
        };
    }

}

class Roman {
    static String[] romanArray = new String[]{"0", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI",
            "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV",
            "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI",
            "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII",
            "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII",
            "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV",
            "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV",
            "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII",
            "XCVIII", "XCIX", "C", "CI", "CII", "CIII", "CIV", "CV", "CVI", "CVII", "CVIII", "CIX", "CX", "CXI",
            "CXII", "CXIII", "CXIV", "CXV", "CXVI", "CXVII", "CXVIII", "CXIX", "CXX", "CXXI", "CXXII", "CXXIII", "CXXIV",
            "CXXV", "CXXVI", "CXXVII", "CXXVIII", "CXXIX", "CXXX", "CXXXI", "CXXXII", "CXXXIII", "CXXXIV", "CXXXV",
            "CXXXVI", "CXXXVII", "CXXXVIII", "CXXXIX", "CXL", "CXLI", "CXLII", "CXLIII", "CXLIV", "CXLV", "CXLVI", "CXLVII",
            "CXLVIII", "CXLIX", "CL", "CLI", "CLII", "CLIII", "CLIV", "CLV", "CLVI", "CLVII", "CLVIII", "CLIX", "CLX", "CLXI",
            "CLXII", "CLXIII", "CLXIV", "CLXV", "CLXVI", "CLXVII", "CLXVIII", "CLXIX", "CLXX", "CLXXI", "CLXXII", "CLXXIII", "CLXXIV",
            "CLXXV", "CLXXVI", "CLXXVII", "CLXXVIII", "CLXXIX", "CLXXX", "CLXXXI", "CLXXXII", "CLXXXIII", "CLXXXIV", "CLXXXV",
            "CLXXXVI", "CLXXXVII", "CLXXXVIII", "CLXXXIX", "CXC", "CXCI", "CXCII", "CXCIII", "CXCIV", "CXCV", "CXCVI", "CXCVII",
            "CXCVIII", "CXCIX", "CC", "M"};


    public static boolean isRoman(String val) {
        for (String s : romanArray) {
            if (val.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static int convertToArabian(String roman) {
        for (int i = 0; i < romanArray.length; i++) {
            if (roman.equals(romanArray[i])) {
                return i;
            }
        }
        return -1;
    }

    public static String convertToRoman(int arabian) {
        return romanArray[arabian];
    }

}

