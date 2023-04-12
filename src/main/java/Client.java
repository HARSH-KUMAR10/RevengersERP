import view.AccountView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client
{

    private static final BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args)
    {
        try
        {
            System.out.println("Welcome to Revenger's staff-book");

            boolean loopFlag = true;

            while (loopFlag)
            {
                System.out.println("1. Login\n0. Exit\n Enter choice: ");

                switch (userInput.readLine()){

                    case "1" -> {
                        AccountView account = new AccountView();
                        account.login(userInput);
                    }

                    case "0" -> loopFlag=false;
                    default -> System.out.println("Wrong input");

                }
            }
        }catch (Exception exception){
            System.out.println("Client error: "+exception.getMessage());
        }
    }
}
