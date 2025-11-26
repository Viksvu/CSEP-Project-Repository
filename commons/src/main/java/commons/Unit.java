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
