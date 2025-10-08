class Movie {
    String title;
    double imdbRating;
    int releaseYear;
    int watchTimePopularity; // Number of views or watch minutes

    Movie(String title, double rating, int year, int popularity) {
        this.title = title;
        this.imdbRating = rating;
        this.releaseYear = year;
        this.watchTimePopularity = popularity;
    }
}

public class QuickSortMovies {

    // QuickSort Function
    public static void quickSort(Movie[] movies, int low, int high, String parameter) {
        if (low < high) {
            int pivotIndex = partition(movies, low, high, parameter);
            quickSort(movies, low, pivotIndex - 1, parameter);
            quickSort(movies, pivotIndex + 1, high, parameter);
        }
    }

    // Partition Function
    private static int partition(Movie[] movies, int low, int high, String parameter) {
        Movie pivot = movies[(low + high) / 2]; // using middle element as pivot
        while (low <= high) {
            while (compare(movies[low], pivot, parameter) > 0) low++;   // Descending order
            while (compare(movies[high], pivot, parameter) < 0) high--;
            if (low <= high) {
                swap(movies, low, high);
                low++;
                high--;
            }
        }
        return low;
    }

    // Comparison based on selected parameter
    private static int compare(Movie m1, Movie m2, String parameter) {
        switch (parameter) {
            case "rating":
                return Double.compare(m1.imdbRating, m2.imdbRating);
            case "year":
                return Integer.compare(m1.releaseYear, m2.releaseYear);
            case "popularity":
                return Integer.compare(m1.watchTimePopularity, m2.watchTimePopularity);
            default:
                return 0;
        }
    }

    // Swap function
    private static void swap(Movie[] movies, int i, int j) {
        Movie temp = movies[i];
        movies[i] = movies[j];
        movies[j] = temp;
    }

    // Driver Code
    public static void main(String[] args) {
        Movie[] movies = {
                new Movie("Inception", 8.8, 2010, 5000000),
                new Movie("Avengers: Endgame", 8.4, 2019, 9000000),
                new Movie("The Dark Knight", 9.0, 2008, 8000000),
                new Movie("Interstellar", 8.6, 2014, 7000000),
                new Movie("Oppenheimer", 8.7, 2023, 6000000)
        };

        String parameter = "rating"; // can be "rating", "year", or "popularity"
        quickSort(movies, 0, movies.length - 1, parameter);

        System.out.println("Sorted by " + parameter.toUpperCase() + ":");
        for (Movie m : movies) {
            System.out.println(m.title + " | " + m.imdbRating + " | " + m.releaseYear + " | " + m.watchTimePopularity);
        }
    }
}
