package example.dao;

import example.model.Employee;
import example.model.Gender;
import example.util.EmUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAO {
    private static final EntityManager em = EmUtil.getEntityManager();

    public static boolean isTableExists() {
        EntityManager em = EmUtil.getEntityManager();
        try {
            String sql = "SELECT to_regclass('public.employee')";
            Query query = em.createNativeQuery(sql);
            Object result = query.getSingleResult();
            return result != null;
        } finally {
            em.close();
        }
    }

    public void createTable() {
        EntityManager em = EmUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            String sql = """
                CREATE TABLE IF NOT EXISTS employee (
                    id SERIAL PRIMARY KEY,
                    full_name VARCHAR(100) NOT NULL,
                    birth_date DATE NOT NULL,
                    gender VARCHAR(10) NOT NULL
                )
            """;

            Query query = em.createNativeQuery(sql);
            query.executeUpdate();

            em.getTransaction().commit();
            System.out.println("Table 'employee' created successfully.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void createIndexes() {
        if(!isTableExists()){
            throw new RuntimeException("Table 'employees' doesn't exist.");
        }
        EntityManager em = EmUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            String indexLastName = "CREATE INDEX IF NOT EXISTS idx_last_name ON employee (last_name)";
            em.createNativeQuery(indexLastName).executeUpdate();

            String indexFirstName = "CREATE INDEX IF NOT EXISTS idx_first_name ON employee (first_name)";
            em.createNativeQuery(indexFirstName).executeUpdate();

            String indexGender = "CREATE INDEX IF NOT EXISTS idx_gender ON employee (gender)";
            em.createNativeQuery(indexGender).executeUpdate();

            String indexGenderLastName = "CREATE INDEX IF NOT EXISTS idx_gender_last_name ON employee (gender, last_name)";
            em.createNativeQuery(indexGenderLastName).executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void save(Employee employee) {
        if(employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (!isTableExists()){
            throw new RuntimeException("Table 'employees' doesn't exist.");
        }
        EntityManager em = EmUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public static List<Employee> findAll() {
        if (!isTableExists()){
            throw new RuntimeException("Table 'employees' doesn't exist.");
        }
        List<Employee> employees = em.createQuery("FROM Employee ORDER BY lastName, firstName, middleName", Employee.class).getResultList();
        em.close();
        return employees;
    }

    public List<Employee> findByGenderAndNamePrefix(Gender gender, String prefix) {
        if (!isTableExists()){
            throw new RuntimeException("Table 'employees' doesn't exist.");
        }
        EntityManager em = EmUtil.getEntityManager();
        TypedQuery<Employee> query = em.createQuery(
                "FROM Employee WHERE gender = :gender AND lastName LIKE :prefix", Employee.class);
        query.setParameter("gender", gender);
        query.setParameter("prefix", prefix + "%");

        List<Employee> employees = query.getResultList();
        em.close();
        return employees;
    }

    public Employee findById(int id) {
        if (!isTableExists()){
            throw new RuntimeException("Table 'employees' doesn't exist.");
        }
        EntityManager em = EmUtil.getEntityManager();
        Employee employee = em.find(Employee.class, id);
        em.close();
        return employee;
    }

    public void batchInsert(List<Employee> employees) {
        if (!isTableExists()){
            throw new RuntimeException("Table 'employees' doesn't exist.");
        }
        EntityManager em = EmUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (int i = 0; i < employees.size(); i++) {
                em.persist(employees.get(i));

                if (i % 1000 == 0) {

                    em.flush();
                    em.clear();
                }
                System.out.println(i + " / " + employees.size());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
