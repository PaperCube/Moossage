package studio.papercube.moossage.utilities;

public class SuffixUtil {
    private static class DoubleWithSuffix {
        public static DoubleWithSuffix parse(String s) {
            String sTrimmed = s.trim();
            final int size = sTrimmed.length();
            int digitEnd = size;
            for (int i = 0; i < size; i++) {
                char ch = sTrimmed.charAt(i);
                if (!Character.isDigit(ch) && ch != '.') {
                    digitEnd = i;
                    break;
                }
            }
            return new DoubleWithSuffix(
                    Double.parseDouble(sTrimmed.substring(0, digitEnd)),
                    sTrimmed.substring(digitEnd, size)
            );
        }

        private double number;
        private String suffix;

        public double getNumber() {
            return number;
        }

        public void setNumber(double number) {
            this.number = number;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        private DoubleWithSuffix(double number, String suffix) {
            this.number = number;
            this.suffix = suffix;
        }
    }

	public static int toMillis(String formula){
        try {
            DoubleWithSuffix doubleWithSuffix = DoubleWithSuffix.parse(formula);
            String suffix = doubleWithSuffix.getSuffix().toLowerCase();
            double number = doubleWithSuffix.number;
			switch(suffix){
                case "s":
                    return (int) (number * 1000);
                case "m":
                    return (int) (number * 1000 * 60);
                case "h":
                    return (int) (number * 1000 * 60 * 60);
                case "ms":
                    return (int) number;
                default:
                    return (int) number;
			}
		}catch(NumberFormatException e){
			throw new InvalidSuffixException(e);
		}

    }

	public static int toTimes(String formula){
        try {
            DoubleWithSuffix doubleWithSuffix = DoubleWithSuffix.parse(formula);
            String suffix = doubleWithSuffix.getSuffix().toLowerCase();
            double number = doubleWithSuffix.number;
            switch (suffix) {
                case "k":
                    return (int) (number * 1000);
                case "m":
                    return (int) (number * 1000 * 1000);
                default:
                    return (int) number;
			}
		}catch(NumberFormatException e){
			throw new InvalidSuffixException(e);
		}
	}

    public static class InvalidSuffixException extends RuntimeException {

		private static final long serialVersionUID = 5881490402039607656L;

		public InvalidSuffixException() {
			super();
		}

		public InvalidSuffixException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
			super(arg0, arg1, arg2, arg3);
		}

		public InvalidSuffixException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public InvalidSuffixException(String arg0) {
			super(arg0);
		}

		public InvalidSuffixException(Throwable arg0) {
			super(arg0);
		}

    }


}
