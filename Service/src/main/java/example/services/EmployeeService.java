package example.services;

import example.dao.EmployeeDAO;
import example.model.Employee;
import example.model.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeService {

    public EmployeeService(){}

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void createTable(){
        try {
            employeeDAO.createDatabase();
            employeeDAO.createTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveEmployee(Employee employee){
        try {
            employeeDAO.save(employee);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getAllEmployees(){
        try {
            List<Employee> result = EmployeeDAO.findAll();
            if(result == null){
                throw new RuntimeException("Unknown error, cannot get employees");
            }
            return result;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Employee> findByGenderAndNamePrefix(){
        try {

            List<Employee> result = employeeDAO.findByGenderAndNamePrefix(Gender.Male, "F");
            if(result == null){
                throw new RuntimeException("Unknown error, cannot get employees");
            }
            return result;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void generateEmployees() {
        if (!employeeDAO.isTableExists()) {
            throw new RuntimeException("Employee table does not exist");
        }

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        String[] maleFirstNames = {
                "Ivan", "Petr", "Alexey", "Dmitry", "Sergey", "Andrey", "Nikolay", "Vladimir", "Mikhail", "Artem",
                "Alexander", "Boris", "Gennady", "Evgeny", "Leonid", "Maxim", "Oleg", "Ruslan", "Stanislav", "Victor",
                "Vasiliy", "Stepan", "Roman", "Timofey", "Eduard", "Arkadiy", "Konstantin", "Valentin", "Anatoliy", "Igor",
                "Felix", "Yaroslav", "Vadim", "Rodion", "Veniamin", "Herman", "Ilya", "Kirill", "Semyon", "Platon",
                "Gavriil", "Zakhar", "Egor", "Bogdan", "Artur", "Denis", "Vyacheslav", "Danila", "Matvey", "Saveliy",
                "Ignat", "Albert", "Timur", "Miron", "Vladislav", "Gleb", "Elisei", "Tikhon", "Makar", "Mark",
                "Lubomir", "Zahar", "Grigoriy", "Arseniy", "Artemiy", "Anatol", "Svyatoslav", "Nazar", "German", "Ostap",
                "Lavrentiy", "Rostislav", "Taras", "Fedor", "Arkhip", "Stepan", "Emil", "Rodislav", "Lukyan", "Marat"
        };

        String[] maleMiddleNames = {
                "Ivanovich", "Petrovich", "Alexeevich", "Dmitrievich", "Sergeevich", "Andreevich", "Nikolaevich", "Vladimirovich",
                "Mikhailovich", "Artemovich", "Alexandrovich", "Gennadievich", "Evgenievich", "Leonidovich", "Maximovich", "Olegovich",
                "Ruslanovich", "Stanislavovich", "Victorovich", "Vasilievich", "Stepanovich", "Romanovich", "Timofeevich", "Eduardovich",
                "Arkadievich", "Konstantinovich", "Valentinovich", "Anatolievich", "Igorevich", "Feliksovich", "Yaroslavovich", "Vadimovich",
                "Rodionovich", "Veniaminovich", "Germanoich", "Ilyich", "Kirillovich", "Semyonovich", "Platonovich", "Gavriilovich",
                "Zakharovich", "Egorovich", "Bogdanovich", "Arturovich", "Denisovich", "Vyacheslavovich", "Danilovich", "Matveevich",
                "Savelievich", "Ignatovich", "Albertovich", "Timurovich", "Mironovich", "Vladislavovich", "Glebovich", "Eliseevich",
                "Tikhonovich", "Makarovich", "Markovich", "Lubomirovich", "Zaharovich", "Grigorievich", "Arsenievich", "Artemievich",
                "Anatolievich", "Svyatoslavovich", "Nazarovich", "Germanovich", "Ostapovich", "Lavrentievich", "Rostislavovich",
                "Tarasovich", "Fedorovich", "Arkhipovich", "Stepanovich", "Emilovich", "Rodislavovich", "Lukyanovich", "Maratovich"
        };

        String[] maleLastNames = {
                "Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov", "Popov", "Volkov", "Fedorov", "Mikhailov", "Nikolaev",
                "Orlov", "Pavlov", "Romanov", "Semenov", "Stepanov", "Yakovlev", "Gavrilov", "Antonov", "Vasiliev", "Gusev",
                "Frolov", "Sorokin", "Matveev", "Voronov", "Vlasov", "Zaitsev", "Borodin", "Kozlov", "Tarasov", "Efimov",
                "Leonov", "Gerasimov", "Timofeev", "Filatov", "Markov", "Vinogradov", "Sergeev", "Belyaev", "Trofimov", "Konovalov",
                "Savelyev", "Zhdanov", "Zubov", "Maksimov", "Korneev", "Ryabov", "Gromov", "Lapin", "Pankratov", "Chernov",
                "Fokin", "Fedoseev", "Filimonov", "Firsov", "Fyodorov", "Fomin", "Fedulov", "Fominykh", "Fetisov", "Filin",
                "Fet", "Firs", "Frolyov", "Fadeyev", "Fandeev", "Fominov", "Fedosov", "Filinov", "Fedoskin", "Filimonenko",
                "Fominykh", "Fedulov", "Fetisov", "Fokinov", "Firsov", "Fominovich", "Filippov", "Felixov", "Fomchenkov", "Fandorin"
        };

        String[] maleLastNamesF = {
                "Fedorov", "Filatov", "Fedotov", "Fomichev", "Fokin", "Frolov", "Fedoseev", "Filimonov", "Firsov", "Fyodorov",
                "Fomin", "Fedulov", "Fominykh", "Fetisov", "Filin", "Fet", "Firs", "Frolyov", "Fadeyev", "Fandeev",
                "Fominov", "Fedosov", "Filinov", "Fedoskin", "Filimonenko", "Fominykh", "Fedulov", "Fetisov", "Fokinov", "Firsov"
        };

        String[] femaleFirstNames = {
                "Anna", "Elena", "Olga", "Maria", "Tatiana", "Natalia", "Svetlana", "Ekaterina", "Irina", "Vera",
                "Alina", "Valeria", "Yulia", "Angelina", "Daria", "Karina", "Galina", "Lilia", "Nadezhda", "Polina",
                "Lyudmila", "Sofia", "Margarita", "Taisia", "Veronika", "Zoya", "Oksana", "Tamara", "Victoria", "Elizaveta",
                "Arina", "Zinaida", "Albina", "Milana", "Nina", "Alevtina", "Alena", "Kseniya", "Anfisa", "Eugenia",
                "Lana", "Lada", "Marina", "Yaroslavna", "Aglaya", "Emilia", "Antonina", "Evangelina", "Pelageya", "Stefania",
                "Stanislava", "Renata", "Kristina", "Izabella", "Bogdana", "Ludmila", "Dina", "Ruslana", "Eseniya", "Vasilisa",
                "Zlata", "Yana", "Arianna", "Vlada", "Taisiya", "Raisa", "Augustina", "Uliana", "Inessa", "Mirra",
                "Mila", "Snezana", "Yunna", "Felicia", "Gertruda", "Nelli", "Eleonora", "Solomiya", "Tereza", "Alyona"
        };

        String[] femaleMiddleNames = {
                "Ivanovna", "Petrovna", "Alexeevna", "Dmitrievna", "Sergeevna", "Andreevna", "Nikolaevna", "Vladimirovna",
                "Mikhailovna", "Artemovna", "Alexandrovna", "Gennadievna", "Evgenievna", "Leonidovna", "Maximovna", "Olegovna",
                "Ruslanovna", "Stanislavovna", "Victorovna", "Vasilievna", "Stepanovna", "Romanovna", "Timofeevna", "Eduardovna",
                "Arkadievna", "Konstantinovna", "Valentinovna", "Anatolievna", "Igorevna", "Feliksovna", "Yaroslavovna", "Vadimovna",
                "Rodionovna", "Veniaminovna", "Germanoichna", "Ilyichna", "Kirillovna", "Semyonovna", "Platonovna", "Gavriilovna",
                "Zakharovna", "Egorovna", "Bogdanovna", "Arturovna", "Denisovna", "Vyacheslavovna", "Danilovna", "Matveevna",
                "Savelievna", "Ignatovna", "Albertovna", "Timurovna", "Mironovna", "Vladislavovna", "Glebovna", "Eliseevna",
                "Tikhonovna", "Makarovna", "Markovna", "Lubomirovna", "Zaharovna", "Grigorievna", "Arsenievna", "Artemievna",
                "Anatolievna", "Svyatoslavovna", "Nazarovna", "Germanovna", "Ostapovna", "Lavrentievna", "Rostislavovna",
                "Tarasovna", "Fedorovna", "Arkhipovna", "Stepanovna", "Emilovna", "Rodislavovna", "Lukyanovna", "Maratovna"
        };

        String[] femaleLastNames = {
                "Ivanova", "Petrova", "Sidorova", "Smirnova", "Kuznetsova", "Popova", "Volkova", "Fedorova", "Mikhailova", "Nikolaeva",
                "Orlova", "Pavlova", "Romanova", "Semenova", "Stepanova", "Yakovleva", "Gavrilova", "Antonova", "Vasilieva", "Guseva",
                "Frolova", "Sorokina", "Matveeva", "Voronova", "Vlasova", "Zaitseva", "Borodina", "Kozlova", "Tarasova", "Efimova",
                "Leonova", "Gerasimova", "Timofeeva", "Filatova", "Markova", "Vinogradova", "Sergeeva", "Belyaeva", "Trofimova", "Konovalova",
                "Savelyeva", "Zhdanova", "Zubova", "Maksimova", "Korneeva", "Ryabova", "Gromova", "Lapina", "Pankratova", "Chernova",
                "Fokina", "Fedoseeva", "Filimonova", "Firsova", "Fyodorova", "Fomina", "Fedulova", "Fominykh", "Fetisova", "Filina",
                "Fet", "Firs", "Frolyova", "Fadeyeva", "Fandeeva", "Fominova", "Fedosova", "Filinova", "Fedoskina", "Filimonenko",
                "Fominykh", "Fedulova", "Fetisova", "Fokinova", "Firsova", "Fominovich", "Filippova", "Felixova", "Fomchenkova", "Fandorina"
        };





        for (int i = 0; 1000000 >= i; ++i){
            if(i%2==0){
                Gender gender = Gender.Male;
                for (String firstName : maleFirstNames) {
                    for (String middleName : maleMiddleNames) {
                        for (String lastName : maleLastNames) {
                            LocalDate birthDate = LocalDate.of(1950 + random.nextInt(50), random.nextInt(12) + 1, random.nextInt(28) + 1);
                            employees.add(new Employee(firstName, lastName, middleName, birthDate, gender));
                            if (employees.size() >= 1000000) break;
                        }
                        if (employees.size() >= 1000000) break;
                    }
                    if (employees.size() >= 1000000) break;
                }
            }
            else{
                Gender gender = Gender.Female;
                for (String firstName : femaleFirstNames) {
                    for (String middleName : femaleMiddleNames) {
                        for (String lastName : femaleLastNames) {
                            LocalDate birthDate = LocalDate.of(1950 + random.nextInt(50), random.nextInt(12) + 1, random.nextInt(28) + 1);
                            employees.add(new Employee(firstName, lastName, middleName, birthDate, gender));
                            if (employees.size() >= 1000000) break;
                        }
                        if (employees.size() >= 1000000) break;
                    }
                    if (employees.size() >= 1000000) break;
                }
            }
        }

        for (int i = 0; 100 >= i; ++i){
            for (String firstName : maleFirstNames) {
                for (String middleName : maleMiddleNames) {
                    for (String lastName : maleLastNamesF) {
                        LocalDate birthDate = LocalDate.of(1950 + random.nextInt(50), random.nextInt(12) + 1, random.nextInt(28) + 1);
                        employees.add(new Employee(firstName, lastName, middleName, birthDate, Gender.Male));
                        if (employees.size() >= 1000000) break;
                    }
                    if (employees.size() >= 1000000) break;
                }
                if (employees.size() >= 1000000) break;
            }
        }

        try {
            employeeDAO.batchInsert(employees);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void Indexing(){
        try {
            employeeDAO.createIndexes();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
