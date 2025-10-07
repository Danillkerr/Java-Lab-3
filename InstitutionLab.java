import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Laboratory Work №3
 * Topic: Classes in Java
 * Goal: Learn classes and use/define custom classes in Java.
 *
 * This class demonstrates:
 *  - definition of an EducationalInstitution class with at least 5 fields,
 *  - creation of an array of such objects in main (all variables declared and assigned in main),
 *  - sorting the array using standard Java facilities: first by studentCount (ascending),
 *    then by rating (descending),
 *  - searching the array for an object identical to a given one.
 *
 */
public class InstitutionLab {

    /**
     * Represents an educational institution with several fields.
     */
    public static class EducationalInstitution {
        private final String name;            
        private final String type;
        private final int yearEstablished;
        private final int studentCount;
        private final double rating;

        /**
         * Construct an EducationalInstitution instance.
         *
         * @param name            institution name (non-null)
         * @param type            institution type (non-null)
         * @param yearEstablished year when institution was founded (positive)
         * @param studentCount    number of students (>= 0)
         * @param rating          institution rating (0.0 .. 100.0 for example)
         * @throws IllegalArgumentException if arguments are invalid
         */
        public EducationalInstitution(String name, String type, int yearEstablished, int studentCount, double rating) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name must be non-empty.");
            }
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("Type must be non-empty.");
            }
            if (yearEstablished <= 0) {
                throw new IllegalArgumentException("yearEstablished must be positive.");
            }
            if (studentCount < 0) {
                throw new IllegalArgumentException("studentCount must be non-negative.");
            }
            if (Double.isNaN(rating) || Double.isInfinite(rating)) {
                throw new IllegalArgumentException("rating must be a finite number.");
            }

            this.name = name;
            this.type = type;
            this.yearEstablished = yearEstablished;
            this.studentCount = studentCount;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getYearEstablished() {
            return yearEstablished;
        }

        public int getStudentCount() {
            return studentCount;
        }

        public double getRating() {
            return rating;
        }

        /**
         * Two EducationalInstitution objects are considered equal when all fields are equal.
         *
         * @param o other object
         * @return true if identical by fields, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EducationalInstitution)) return false;
            EducationalInstitution that = (EducationalInstitution) o;
            return yearEstablished == that.yearEstablished
                    && studentCount == that.studentCount
                    && Double.compare(that.rating, rating) == 0
                    && name.equals(that.name)
                    && type.equals(that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type, yearEstablished, studentCount, rating);
        }

        @Override
        public String toString() {
            return String.format("%s (%s) - founded: %d, students: %d, rating: %.2f",
                    name, type, yearEstablished, studentCount, rating);
        }
    }

    /**
     * Utility: print an array of institutions with their indices.
     *
     * @param arr array to print (may be null)
     */
    private static void printInstitutions(EducationalInstitution[] arr) {
        if (arr == null) {
            System.out.println("[null array]");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("[%d] %s%n", i, arr[i]);
        }
    }

    /**
     * Finds index of the first institution in the array that is equal to the target.
     *
     * @param arr    array of institutions (non-null)
     * @param target institution to find (may be null)
     * @return index of found element, or -1 if not found
     * @throws IllegalArgumentException if arr is null
     */
    private static int findInstitutionIndex(EducationalInstitution[] arr, EducationalInstitution target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array must not be null.");
        }
        for (int i = 0; i < arr.length; i++) {
            if (Objects.equals(arr[i], target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Program entry point.
     * All variables used by the assignment are declared and assigned in this method.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        EducationalInstitution[] institutions = new EducationalInstitution[] {
                new EducationalInstitution("Greenfield University", "University", 1965, 12000, 8.7),
                new EducationalInstitution("Riverside College", "College", 1978, 8500, 7.9),
                new EducationalInstitution("Tech Valley Institute", "Institute", 1992, 8500, 9.1),
                new EducationalInstitution("Metro Vocational School", "Vocational", 2005, 1500, 6.8),
                new EducationalInstitution("Oldtown Academy", "Academy", 1890, 2300, 8.0),
                new EducationalInstitution("Northbridge University", "University", 1988, 12000, 8.9)
        };

        System.out.println("=== Original array ===");
        printInstitutions(institutions);

        Comparator<EducationalInstitution> comparator =
                Comparator.comparingInt(EducationalInstitution::getStudentCount)
                        .thenComparing(Comparator.comparingDouble(EducationalInstitution::getRating).reversed());

        Arrays.sort(institutions, comparator);

        System.out.println("\n=== Sorted array (studentCount ASC, rating DESC) ===");
        printInstitutions(institutions);

        EducationalInstitution target = new EducationalInstitution("Tech Valley Institute", "Institute", 1992, 8500, 9.1);

        int indexFound = findInstitutionIndex(institutions, target);

        System.out.println("\n=== Search result ===");
        if (indexFound >= 0) {
            System.out.printf("Found identical institution at index %d: %s%n", indexFound, institutions[indexFound]);
        } else {
            System.out.println("No identical institution found in the array.");
        }

        EducationalInstitution notPresent = new EducationalInstitution("Non Existent College", "College", 2020, 500, 5.5);
        int notFoundIndex = findInstitutionIndex(institutions, notPresent);
        System.out.println("\nSearching for a non-existing institution returns index: " + notFoundIndex);
    }
}
