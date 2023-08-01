package com.example.socialmedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Socialmediamanage implements SocialMediaManager {

    public static void main(String[] args) {
        Socialmediamanage socialMediaManager = new Socialmediamanage();
        socialMediaManager.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Social Media Management System");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1. Add Post");
            System.out.println("2. Edit Post");
            System.out.println("3. Schedule Post");
            System.out.println("4. Delete Post");
            System.out.println("5. View All Posts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addPost();    // Abstraction - Call to addPost() method
                    break;
                case 2:
                    editPost();   // Abstraction - Call to editPost() method
                    break;
                case 3:
                    schedulePost();   // Abstraction - Call to schedulePost() method
                    break;
                case 4:
                    deletePost();    // Abstraction - Call to deletePost() method
                    break;
                case 5:
                    viewAllPosts();   // Abstraction - Call to viewAllPosts() method
                    break;
                case 6:
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override
    public void addPost() {
        Scanner scanner = new Scanner(System.in);
        Post newPost = new Post();

        System.out.print("Enter platform (e.g., Twitter): ");
        newPost.setPlatform(scanner.nextLine());

        System.out.print("Enter post content: ");
        newPost.setContent(scanner.nextLine());

        // Check if the user wants to schedule the post
        System.out.print("Do you want to schedule this post? (yes/no): ");
        String scheduleOption = scanner.nextLine();

        if (scheduleOption.equalsIgnoreCase("yes")) {
            System.out.print("Enter scheduled time (YYYY-MM-DD HH:mm:ss): ");
            newPost.setScheduledTime(scanner.nextLine());
        } else {
            newPost.setScheduledTime(null); // Set scheduled time as null if the user doesn't want to schedule
        }

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String sql;
                PreparedStatement preparedStatement;

                if (newPost.getScheduledTime() != null) {
                    // Scheduled post - include the scheduled_time column in the query
                    sql = "INSERT INTO Post (platform, content, scheduletime) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, newPost.getPlatform());
                    preparedStatement.setString(2, newPost.getContent());
                    preparedStatement.setString(3, newPost.getScheduledTime());
                } else {
                    // Regular post - set a default value (e.g., current timestamp) for the scheduled_time column
                    sql = "INSERT INTO Post (platform, content, scheduletime) VALUES (?, ?, CURRENT_TIMESTAMP)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, newPost.getPlatform());
                    preparedStatement.setString(2, newPost.getContent());
                }

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Post added to the database!");
                } else {
                    System.out.println("Failed to add the post.");
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void editPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the post to edit: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Post WHERE id = ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setInt(1, postId);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    Post post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setPlatform(resultSet.getString("platform"));
                    post.setContent(resultSet.getString("content"));
                    post.setScheduledTime(resultSet.getString("scheduletime"));

                    System.out.println("Existing Post:");
                    System.out.println(post);

                    System.out.println("Select what you want to edit:");
                    System.out.println("1. Content");
                    System.out.println("2. Scheduled Time");
                    System.out.print("Enter your choice: ");
                    int editChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    switch (editChoice) {
                        case 1:
                            System.out.print("Enter new post content: ");
                            String newContent = scanner.nextLine();
                            post.setContent(newContent);
                            break;
                        case 2:
                            System.out.print("Enter new scheduled time (YYYY-MM-DD HH:mm:ss): ");
                            String newScheduledTime = scanner.nextLine();
                            post.setScheduledTime(newScheduledTime);
                            break;
                        default:
                            System.out.println("Invalid choice. Nothing will be edited.");
                    }

                    String updateQuery = "UPDATE Post SET content = ?, scheduletime = ? WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, post.getContent());
                    updateStatement.setString(2, post.getScheduledTime());
                    updateStatement.setInt(3, post.getId());

                    int rowsAffected = updateStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Post updated successfully!");
                    } else {
                        System.out.println("Failed to update the post.");
                    }

                } else {
                    System.out.println("Post not found with ID: " + postId);
                }

                resultSet.close();
                selectStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void schedulePost() {
        Scanner scanner = new Scanner(System.in);
        Post newPost = new Post();

        System.out.print("Enter platform (e.g., Twitter, Facebook): ");
        newPost.setPlatform(scanner.nextLine());

        System.out.print("Enter post content: ");
        newPost.setContent(scanner.nextLine());

        System.out.print("Enter scheduled time (YYYY-MM-DD HH:mm:ss): ");
        newPost.setScheduledTime(scanner.nextLine());

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String sql = "INSERT INTO Post (platform, content, scheduletime) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newPost.getPlatform());
                preparedStatement.setString(2, newPost.getContent());
                preparedStatement.setString(3, newPost.getScheduledTime());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Scheduled Post added to the database!");
                } else {
                    System.out.println("Failed to add the Scheduled post.");
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deletePost() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the post to delete: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Post WHERE id = ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setInt(1, postId);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    Post post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setPlatform(resultSet.getString("platform"));
                    post.setContent(resultSet.getString("content"));
                    post.setScheduledTime(resultSet.getString("scheduletime"));

                    System.out.println("Post to Delete:");
                    System.out.println(post);

                    System.out.print("Are you sure you want to delete this post? (yes/no): ");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        String deleteQuery = "DELETE FROM Post WHERE id = ?";
                        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                        deleteStatement.setInt(1, postId);
                        int rowsAffected = deleteStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Post deleted successfully!");
                        } else {
                            System.out.println("Failed to delete the post.");
                        }
                    } else {
                        System.out.println("Post deletion cancelled.");
                    }

                } else {
                    System.out.println("Post not found with ID: " + postId);
                }

                resultSet.close();
                selectStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void viewAllPosts() {
        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Post";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    int postId = resultSet.getInt("id");
                    String platform = resultSet.getString("platform");
                    String content = resultSet.getString("content");
                    String scheduledTime = resultSet.getString("scheduletime");

                    System.out.println("Post:");
                    System.out.println("ID: " + postId);
                    System.out.println("Platform: " + platform);
                    System.out.println("Content: " + content);
                    System.out.println("Scheduled Time: " + scheduledTime);
                    System.out.println("----------------------------------------");
                }

                resultSet.close();
                selectStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}