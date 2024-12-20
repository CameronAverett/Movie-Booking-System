import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovieBookingSystem {
    private static List<Movie> currentMovies = new ArrayList<>();

    private static boolean isAdmin = false;

    public static void main(String[] args) {
        populateMovies();
        SwingUtilities.invokeLater(() -> showLoginPage());
    }

    private static void showLoginPage() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            User user = users.stream()
                    .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                if (user.isAdmin()) {
                    isAdmin = true;
                    frame.dispose();
                    showAdminPage();
                } else {
                    isAdmin = false;
                    frame.dispose();
                    showHomePage();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            showRegistrationPage();
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel()); // Spacer
        panel.add(loginButton);
        panel.add(new JLabel()); // Spacer
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    private static void showRegistrationPage() {
        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"User", "Admin"});

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        registerButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            boolean isAdmin = roleBox.getSelectedItem().equals("Admin");

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
                JOptionPane.showMessageDialog(frame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                users.add(new User(username, password, isAdmin));
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                frame.dispose();
                showLoginPage();
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(new JLabel()); // Spacer
        panel.add(registerButton);
        panel.add(new JLabel()); // Spacer
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static class User {
        private String username;
        private String password;
        private boolean isAdmin;

        public User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public boolean isAdmin() {
            return isAdmin;
        }
    }
    private static List<User> users = new ArrayList<>();

    static {
        // Add default admin account
        users.add(new User("admin", "admin123", true));
    }

    private static void populateMovies() {
        // Add sample current movies
        currentMovies.add(new Movie(
                "The Batman",
                "Action",
                "A detective story with Batman facing the Riddler.",
                120,
                true,
                "Robert Pattinson, Zoë Kravitz, Colin Farrell",
                "An intense, grounded superhero movie.",
                4.5,
                new String[]{"12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"},
                new String[]{"Theater 1", "Theater 2"}
        ));
        currentMovies.add(new Movie(
                "Spider-Man: No Way Home",
                "Adventure",
                "Spider-Man teams up with alternate versions of himself to fight villains.",
                150,
                true,
                "Tom Holland, Zendaya, Benedict Cumberbatch",
                "An ambitious, nostalgic adventure.",
                4.8,
                new String[]{"1:00 PM", "4:00 PM", "7:00 PM", "10:00 PM"},
                new String[]{"Theater 3", "Theater 4"}
        ));
    }

    private static void showHomePage() {
        JFrame frame = new JFrame("Movie Booking System - Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new GridLayout(currentMovies.size(), 1));
        JLabel welcomeLabel = new JLabel("Welcome to the Movie Booking System!", SwingConstants.CENTER);

        panel.add(welcomeLabel);

        for (Movie movie : currentMovies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(frame, movie));
            panel.add(movieButton);
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showMovieDetails(JFrame parent, Movie movie) {
        parent.dispose();
        JFrame frame = new JFrame("Movie Details - " + movie.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Movie Details
        panel.add(new JLabel("Title: " + movie.getTitle()));
        panel.add(new JLabel("Genre: " + movie.getGenre()));
        panel.add(new JLabel("Runtime: " + movie.getRuntime() + " minutes"));
        panel.add(new JLabel("Synopsis: " + movie.getDescription()));
        panel.add(new JLabel("Cast: " + movie.getCast()));
        panel.add(new JLabel("Review: " + (movie.getReview() != null ? movie.getReview() : "No reviews yet")));
        panel.add(new JLabel("Rating: " + (movie.getRating() != null ? movie.getRating() + " / 5" : "N/A")));

        // Reviews Section
        panel.add(new JLabel("User Reviews:"));
        JTextArea reviewsArea = new JTextArea(10, 40);
        reviewsArea.setEditable(false);
        reviewsArea.setLineWrap(true);
        reviewsArea.setWrapStyleWord(true);
        StringBuilder reviewsText = new StringBuilder();
        for (String review : movie.getReviews()) {
            reviewsText.append("- ").append(review).append("\n");
        }
        reviewsArea.setText(reviewsText.toString());
        panel.add(new JScrollPane(reviewsArea));

        // Add Review Section
        panel.add(new JLabel("Write a Review:"));
        JTextArea reviewInput = new JTextArea(5, 40);
        reviewInput.setLineWrap(true);
        reviewInput.setWrapStyleWord(true);
        JButton submitReviewButton = new JButton("Submit Review");
        submitReviewButton.addActionListener(e -> {
            String newReview = reviewInput.getText().trim();
            if (!newReview.isEmpty()) {
                movie.addReview(newReview);
                reviewsText.append("- ").append(newReview).append("\n");
                reviewsArea.setText(reviewsText.toString());
                reviewInput.setText("");
                JOptionPane.showMessageDialog(frame, "Thank you for your review!");
            } else {
                JOptionPane.showMessageDialog(frame, "Review cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(new JScrollPane(reviewInput));
        panel.add(submitReviewButton);

        // Purchase Ticket Section
        panel.add(new JLabel("Purchase Tickets:"));
        JLabel theaterLabel = new JLabel("Select Theater:");
        JComboBox<String> theaterBox = new JComboBox<>(movie.getTheaters());

        JLabel timeLabel = new JLabel("Select Showtime:");
        JComboBox<String> timeBox = new JComboBox<>(movie.getShowtimes());

        JLabel seatsLabel = new JLabel("Number of Seats:");
        SpinnerNumberModel seatModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner seatsSpinner = new JSpinner(seatModel);

        JButton purchaseButton = new JButton("Proceed to Payment");
        purchaseButton.addActionListener(e -> showPaymentPage(frame, movie, theaterBox, timeBox, seatsSpinner));

        panel.add(theaterLabel);
        panel.add(theaterBox);
        panel.add(timeLabel);
        panel.add(timeBox);
        panel.add(seatsLabel);
        panel.add(seatsSpinner);
        panel.add(purchaseButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            showHomePage();
        });
        panel.add(backButton);

        frame.add(new JScrollPane(panel));
        frame.setVisible(true);
    }


    private static void showBookingPage(JFrame parent, Movie movie) {
        parent.dispose();
        JFrame frame = new JFrame("Book Tickets - " + movie.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        JLabel theaterLabel = new JLabel("Select Theater:");
        JComboBox<String> theaterBox = new JComboBox<>(movie.getTheaters());

        JLabel timeLabel = new JLabel("Select Showtime:");
        JComboBox<String> timeBox = new JComboBox<>(movie.getShowtimes());

        JLabel seatsLabel = new JLabel("Number of Seats:");
        SpinnerNumberModel seatModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner seatsSpinner = new JSpinner(seatModel);

        JButton nextButton = new JButton("Proceed to Payment");
        nextButton.addActionListener(e -> showPaymentPage(frame, movie, theaterBox, timeBox, seatsSpinner));

        panel.add(theaterLabel);
        panel.add(theaterBox);
        panel.add(timeLabel);
        panel.add(timeBox);
        panel.add(seatsLabel);
        panel.add(seatsSpinner);
        panel.add(new JLabel()); // Spacer
        panel.add(nextButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    private static void showPaymentPage(JFrame parent, Movie movie, JComboBox<String> theaterBox, JComboBox<String> timeBox, JSpinner seatsSpinner) {
        parent.dispose();
        JFrame frame = new JFrame("Payment - " + movie.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        JLabel paymentMethodLabel = new JLabel("Select Payment Method:");
        JComboBox<String> paymentMethodBox = new JComboBox<>(new String[]{"Debit Card", "Credit Card"});

        JLabel cardNumberLabel = new JLabel("Card Number:");
        JTextField cardNumberField = new JTextField();

        JLabel nameOnCardLabel = new JLabel("Name on Card:");
        JTextField nameOnCardField = new JTextField();

        JLabel expiryDateLabel = new JLabel("Expiry Date (MM/YY):");
        JTextField expiryDateField = new JTextField();

        JLabel cvvLabel = new JLabel("CVV:");
        JTextField cvvField = new JTextField();

        JButton confirmButton = new JButton("Confirm Purchase");
        confirmButton.addActionListener(e -> {
            String paymentMethod = (String) paymentMethodBox.getSelectedItem();
            String cardNumber = cardNumberField.getText();
            String nameOnCard = nameOnCardField.getText();
            String expiryDate = expiryDateField.getText();
            String cvv = cvvField.getText();

            if (validatePaymentFields(cardNumber, nameOnCard, expiryDate, cvv)) {
                int seats = (int) seatsSpinner.getValue();
                String theater = (String) theaterBox.getSelectedItem();
                String time = (String) timeBox.getSelectedItem();

                JOptionPane.showMessageDialog(frame, "Purchase confirmed!\n" +
                        "Movie: " + movie.getTitle() + "\n" +
                        "Theater: " + theater + "\n" +
                        "Showtime: " + time + "\n" +
                        "Seats: " + seats + "\n" +
                        "Payment Method: " + paymentMethod);
                frame.dispose();
                showHomePage();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid payment details. Please check and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(paymentMethodLabel);
        panel.add(paymentMethodBox);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(nameOnCardLabel);
        panel.add(nameOnCardField);
        panel.add(expiryDateLabel);
        panel.add(expiryDateField);
        panel.add(cvvLabel);
        panel.add(cvvField);
        panel.add(new JLabel()); // Spacer
        panel.add(confirmButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    private static boolean validatePaymentFields(String cardNumber, String nameOnCard, String expiryDate, String cvv) {
        return !cardNumber.isEmpty() && cardNumber.matches("\\d{16}") &&
                !nameOnCard.isEmpty() &&
                !expiryDate.isEmpty() && expiryDate.matches("\\d{2}/\\d{2}") &&
                !cvv.isEmpty() && cvv.matches("\\d{3}");
    }
    private static void processBooking(JFrame parent, Movie movie, JComboBox<String> theaterBox, JComboBox<String> timeBox, JSpinner seatsSpinner) {
        String theater = (String) theaterBox.getSelectedItem();
        String time = (String) timeBox.getSelectedItem();
        int seats = (int) seatsSpinner.getValue();

        // Simulate secure payment (placeholder)
        boolean paymentSuccess = processPayment();

        if (paymentSuccess) {
            movie.sellTickets(seats); // Update tickets sold
            JOptionPane.showMessageDialog(parent, "Booking confirmed!\n" +
                    "Movie: " + movie.getTitle() + "\n" +
                    "Theater: " + theater + "\n" +
                    "Showtime: " + time + "\n" +
                    "Seats: " + seats);
            parent.dispose();
            showHomePage();
        } else {
            JOptionPane.showMessageDialog(parent, "Payment failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showAdminPage() {
        JFrame frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel header = new JLabel("System Status Report", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(header);

        // Display movie and ticket information
        for (Movie movie : currentMovies) {
            panel.add(new JLabel("Movie: " + movie.getTitle()));
            panel.add(new JLabel("Tickets Sold: " + movie.getTicketsSold()));
        }

        JButton backButton = new JButton("Log Out");
        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });

        panel.add(backButton);

        frame.add(new JScrollPane(panel));
        frame.setVisible(true);
    }

    private static boolean processPayment() {
        // Placeholder for secure payment simulation
        // In a real system, integrate secure payment APIs and never store sensitive information.
        return JOptionPane.showConfirmDialog(null, "Confirm payment of $10 per ticket?", "Payment",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}

class Movie {
    private String title;
    private String genre;
    private String description;
    private int runtime;
    private boolean canPurchase;
    private String cast;
    private String review;
    private Double rating;
    private String[] showtimes;
    private String[] theaters;
    private List<String> reviews;
    private int ticketsSold; // New field to track tickets sold

    public Movie(String title, String genre, String description, int runtime, boolean canPurchase,
                 String cast, String review, Double rating, String[] showtimes, String[] theaters) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.runtime = runtime;
        this.canPurchase = canPurchase;
        this.cast = cast;
        this.review = review;
        this.rating = rating;
        this.showtimes = showtimes;
        this.theaters = theaters;
        this.reviews = new ArrayList<>();
        this.ticketsSold = 0; // Initialize tickets sold to 0
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public int getRuntime() {
        return runtime;
    }

    public boolean isCanPurchase() {
        return canPurchase;
    }

    public String getCast() {
        return cast;
    }

    public String getReview() {
        return review;
    }

    public Double getRating() {
        return rating;
    }

    public String[] getShowtimes() {
        return showtimes;
    }

    public String[] getTheaters() {
        return theaters;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void sellTickets(int count) {
        this.ticketsSold += count;
    }
}

