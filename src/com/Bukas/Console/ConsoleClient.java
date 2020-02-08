package com.Bukas.Console;

import com.Bukas.Login;
import com.Bukas.Registsration;
import com.Bukas.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient implements Runnable{
    User user;
    public ConsoleClient(User user){
        this.user = user;
    }
    @Override
    public void run() {
        try(Scanner scanner = new Scanner(user.getSocket().getInputStream())){
            PrintWriter printWriter = new PrintWriter(user.getSocket().getOutputStream(),true);

            System.out.println("New connection : " + user.getSocket());

            printWriter.println("Select 'login' or 'register'");

            String option = scanner.nextLine();
            while (!option.toLowerCase().equals("login")&&!option.toLowerCase().equals("register")){
                printWriter.println("Wrong command. Try again");
                option = scanner.nextLine();
            }
            getUserLogin(option,scanner,printWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private User getUserLogin(String option, Scanner scanner, PrintWriter printWriter){
        String username;
        String password;
        if(option.equals("register")){
            printWriter.println("Enter username: ");
            username = scanner.nextLine();
            printWriter.println("Enter password: ");
            password = Registsration.hashPassword(scanner.nextLine());
            int res = Registsration.registerUser(username,password);
            if (res == 0){
                printWriter.println("Registered successfully");
            }else if (res == 1){
                printWriter.println("Selected username already exists. Try another one");
                getUserLogin(option,scanner,printWriter);
            }else {
                printWriter.println("Error " + res);
            }
        }
        //Logging in
        printWriter.println("Enter username: ");
        username = scanner.nextLine();
        printWriter.println("Enter password: ");
        password = Registsration.hashPassword(scanner.nextLine());
        if (Login.attemptLogin(username,password)){
            user.setUsername(username);
            printWriter.println("You're logged in!");
        }else {
            printWriter.println("Failed to login. Try again");
            getUserLogin("login",scanner,printWriter);
        }
        return null;
    }
}
