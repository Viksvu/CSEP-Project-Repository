package commons;

public enum RecipeLanguage {
    ENGLISH{
        @Override
        public String toString() {
            return "English";
        }
    },
    DUTCH{
        @Override
        public String toString() {
            return "Nederlands";
        }
    },
    GERMAN{
        @Override
        public String toString() {
            return "Deutsch";
        }
    },
    SPANISH{
        @Override
        public String toString() {
            return "Español";
        }
    }
}
