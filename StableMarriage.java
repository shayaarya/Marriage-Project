// Shaya Arya
// BIT 143 Winter Quarter
// 2.0
// 01/24/2022

// This program reads an input file of preferences and find a stable marriage
// scenario.  The algorithm gives preference to either men or women depending
// upon whether this call is made from main:
//      makeMatches(men, women);
// or whether this call is made:
//      makeMatches(women, men);

import java.io.*;
import java.util.*;

public class StableMarriage {
    public static final String LIST_END = "END";

    public static void main(String[] args) throws FileNotFoundException {


        Scanner console = new Scanner(System.in);
        System.out.print("What is the input file? ");
        String fileName = console.nextLine();
        Scanner input = new Scanner(new File(fileName));
        System.out.println();

        List<Person> men = readHalf(input);
        List<Person> women = readHalf(input);
        makeMatches(men, women);
        writeList(men, women, "Matches for men");
        writeList(women, men, "Matches for women");
    }

    public static Person readPerson(String line) {
        int index = line.indexOf(":");
        Person result = new Person(line.substring(0, index));
        Scanner data = new Scanner(line.substring(index + 1));
        while (data.hasNextInt()) {
            result.addChoice(data.nextInt());
        }
        return result;
    }

    public static List<Person> readHalf(Scanner input) {
        List<Person> result = new ArrayList<Person>();
        String line = input.nextLine();
        while (!line.equals(LIST_END)) {
            result.add(readPerson(line));
            line = input.nextLine();
        }
        return result;
    }

    public static void makeMatches(List<Person> list1, List<Person> list2) {
        
            for(Person p : list1){
                p.erasePartner();
              }
            for(Person p : list2){
                p.erasePartner();
                }

            int maleIndexNumber=0;
            for(maleIndexNumber=0; maleIndexNumber<list1.size(); maleIndexNumber++){
                if (!list1.get(maleIndexNumber).hasPartner() && list1.get(maleIndexNumber).hasChoices()){
                    Person male = list1.get(maleIndexNumber);
                    Person female = list2.get(male.getFirstChoice());
                    if (female.hasPartner()){
                        list1.get(female.getPartner()).erasePartner();
                    }

                for (int i = female.getChoices().indexOf(maleIndexNumber) +1; i<female.getChoices().size();) {
                    int maleNum = female.getChoices().get(i);
                    female.getChoices().remove(i);

                list1.get(maleNum).getChoices().remove(list1.get(maleNum).getChoices().indexOf(male.getFirstChoice()));

                }

                 maleIndexNumber = 0;                

                }
            }
    }

    public static void writeList(List<Person> list1, List<Person> list2, String title) {
        System.out.println(title);
        System.out.println("Name           Choice  Partner");
        System.out.println("--------------------------------------");
        int sum = 0;
        int count = 0;

        for (Person p : list1){
            System.out.printf("%-15s", p.getName());
            if (!p.hasPartner()){
                System.out.println("No Partner");
            } else {
            int ranking = p.getPartnerRank();
            sum += ranking;
            count++;
            System.out.printf("%4d  %s\n", ranking, list2.get(p.getPartner()).getName());

            }

            
        }



        System.out.println("Mean choice = " + (double) sum / count);
        System.out.println();
    }
}
