package commons;

public enum Unit {
    GRAM{
        @Override
        public String toString() {
            return "gram/s";
        }
    },
    KILOGRAM{
        @Override
        public String toString() {
            return "kilogram/s";
        }
    },
    LITER{
        @Override
        public String toString() {
            return "liter/s";
        }
    },
    TABLE_SPOON{
        @Override
        public String toString() {
            return "table spoon/s";
        }
    },
    PINCH{
        @Override
        public String toString() {
            return "pinch/s";
        }
    },
    CUP{
        @Override
        public String toString() {
            return "cup/s";
        }
    },
    MILLILITER{
        @Override
        public String toString() {
            return "milliliter/s";
        }
    },
    TEASPOON{
        @Override
        public String toString() {
            return "teaspoon/s";
        }
    },
    PIECE{
        @Override
        public String toString() {
            return "piece/s";
        }
    },
    NULL{
        @Override
        public String toString() {
            return "";
        }
    }
}
