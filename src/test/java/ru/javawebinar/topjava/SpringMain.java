package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.DB_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/mock.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            List<User> users = adminUserController.getAll();
            System.out.println(users);

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);


            String tmpdir = System.getProperty("java.io.tmpdir");
            System.out.println("The default value of the java.io.tmpdir system property is: \""
                    + tmpdir  + "\"\n");

            //Specify some temporary files.
            String prefix = "file";
            String suffix = ".txt";

            File tempFile = null;
            File tempFile2 = null;

            try {
                //Create two temporary files.
                tempFile = File.createTempFile(prefix, suffix);
                tempFile2 = File.createTempFile(prefix, null);
            }
            catch (IOException ex) {
                System.err.println("An IOException was caught: " + ex.getMessage());
                ex.printStackTrace();
            }

            //Printing the name of every file.
            System.out.println("A new file called \"" + tempFile.getName()
                    + "\" was created in the directory: \"" + tmpdir + "\"");

            System.out.println("A new file called \"" + tempFile2.getName()
                    + "\" was created in the directory: \"" + tmpdir + "\"\n");


            //Printing the parent directories of every file.
            System.out.println("The parent directory of the file \"" + tempFile.getName()
                    + "\" is: \"" + tempFile.getParent() + "\"");
            System.out.println("The parent directory of the file \"" + tempFile2.getName()
                    + "\" is: \"" + tempFile2.getParent() + "\"");

            //Delete the temporary files.
            if(tempFile.delete())
                System.out.println("Successfully deleted the file with name: \""
                        + tempFile.getName() + "\"");
            else
                System.out.println("Couldn't delete the file with name: \"" + tmpdir + "\"");

            if(tempFile2.delete())
                System.out.println("Successfully deleted the file with name: \""
                        + tempFile2.getName() + "\"");
            else
                System.out.println("Couldn't delete the file with name: \"" + tmpdir + "\"");



        }
    }
}
