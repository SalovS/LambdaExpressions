import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Date;

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) throws ParseException {
        ArrayList<Employee> staff = loadStaffFromFile();

        System.out.println(staff);
        staff = (ArrayList<Employee>) staff.stream()
                .sorted((o1, o2) -> o1.getSalary() - o2.getSalary())
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
        System.out.println("----------------------------------------");
        System.out.println(staff);
        System.out.println("----------------------------------------");

        Date start = (new SimpleDateFormat(dateFormat)).parse("31.12.2016");
        Date end = (new SimpleDateFormat(dateFormat)).parse("01.01.2018");

        System.out.println(
                staff.stream()
                .filter(s -> s.getWorkStart().after(start))
                .filter(s -> s.getWorkStart().before(end))
                        .sorted((o1, o2) -> o1.getSalary() - o2.getSalary())
                .map((Function<Employee, String>) worker -> worker.getName())
                        .limit(1)
                        .collect(Collectors.joining(
                                ", ", "Сотрудника с максимальной зарплатой среди тех," +
                                        " кто пришёл в 2017 году зовут : ","")));
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}