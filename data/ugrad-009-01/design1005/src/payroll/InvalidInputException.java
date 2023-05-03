package payroll;
class InvalidInputException extends Exception {
    InvalidInputException() {
        super();
    }
    void printErrorMessage(int lineCount, String message) {
        System.out.println("Error (line " + lineCount + "): " + message);
    }
    String dollarSign(String input, int lineCount) throws InvalidInputException {
        if (!input.contains("$")) {
            printErrorMessage(lineCount,"Dollar sign missing");
            throw new InvalidInputException();
        }
        return input.replaceAll("\\$","");
    }
    private float getFloat(String number) throws Exception {
        if (number.contains(".")) {
            int[] digits = new int[2];
            String[] splitNum = number.split("\\.");
            if ((splitNum.length>2)) {
                throw new Exception();
            }
            for (int i = 0; i < splitNum.length; i++) {
                if (!splitNum[i].matches("[0-9]+")) {
                    throw new Exception();
                }
                digits[i] = Integer.parseInt(splitNum[i]);
            }
            if (digits[1]>=10) {
                return (float) digits[0] + ((float) digits[1] / 100);
            } else {
                return (float) digits[0] + ((float) digits[1] /10);
            }
        } else {
            return (float)Integer.parseInt(number);
        }
    }
    float testFloat(String value, int lineCount, String message) throws InvalidInputException {
        float num;
        try {
            num = getFloat(value);
        } catch (Exception ex) {
            printErrorMessage(lineCount,message);
            throw new InvalidInputException();
        }
        return num;
    }
}
