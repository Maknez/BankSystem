package bank.system;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Database implements Serializable {

    private String name;
    private Map<BigDecimal, Account> database;

    public Database(String name) {
        this.name = name;
        database = new HashMap<BigDecimal, Account>();
    }

    public Map<BigDecimal, Account> getDatabase() {
        return database;
    }


    public String getName() {
        return name;
    }

    public Account get(BigDecimal index) {
        return database.get(index);
    }

    public void put(BigDecimal index, Account acc) {
        database.put(index, acc);
    }

    public void saveToFile(String name) {
        boolean success = new File("databases").mkdir();
        try (FileOutputStream file = new FileOutputStream("databases/" + name + ".baz");
             ObjectOutputStream output = new ObjectOutputStream(file)) {
            output.writeObject(this);
        } catch (IOException e) {
            System.out.println("DATABASE_LOAD_ERROR. Typed database DOESN'T EXIST or cannot be loaded properly.");
        }
    }

    public Database load(String name) throws IOException {
        Database database = null;
        FileInputStream file = new FileInputStream("databases/" + name + ".baz");
        ObjectInputStream output = new ObjectInputStream(file);
        try {
            database = (Database) output.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Internal error. Please contact administrator.");
        }
        return database;
    }

    public void printAllClients() {
        if (database.isEmpty()) {
            System.out.println("Database is EMPTY");
        }
        for (BigDecimal ID : database.keySet()) {
            System.out.println(database.get(ID).toString());
        }
    }

    public Database searchByName(String name) {
        Database output = new Database("temp");
        for (BigDecimal ID : database.keySet()) {
            if (database.get(ID).getName().contains(name)) {
                output.put(database.get(ID).getID(), database.get(ID));
            }
        }
        return output;
    }

    public Database searchBySurname(String surname) {
        Database output = new Database("temp");
        for (BigDecimal ID : database.keySet()) {
            if (database.get(ID).getSurname().contains(surname)) {
                output.put(database.get(ID).getID(), database.get(ID));
            }
        }
        return output;
    }

    public Database searchByAddress(String address) {
        Database output = new Database("temp");
        for (BigDecimal ID : database.keySet()) {
            if (database.get(ID).getAddress().contains(address)) {
                output.put(database.get(ID).getID(), database.get(ID));
            }
        }
        return output;
    }

    public Database searchByID(BigDecimal id) {
        Database output = new Database("temp");
        for (BigDecimal ID : database.keySet()) {
            if (database.get(ID).getID().equals(id)) {
                output.put(database.get(ID).getID(), database.get(ID));
            }
        }
        return output;
    }

    public Database searchByPESEL(BigDecimal PESEL) {
        Database output = new Database("temp");
        for (BigDecimal ID : database.keySet()) {
            if (database.get(ID).getPESEL().equals(PESEL)) {
                output.put(database.get(ID).getID(), database.get(ID));
            }
        }
        return output;
    }

    public void isIDDuplicated(BigDecimal id) throws IDDuplicatedException {
        for (BigDecimal ID : database.keySet()) {
            if (database.get(ID).getID().equals(id)) {
                throw new IDDuplicatedException();
            }
        }
    }

    public static void printDatabases() {
        File databases = new File("databases/");
        String[] databases2 = databases.list();
        try {
            System.out.println("Clients databases: \n");
            for(int i = 0; i < databases2.length; i++) {
                System.out.println(databases2[i]);
            }
            System.out.println();
        }
        catch(NullPointerException e) {
            System.out.println("\nThere is no existing databases. Create NEW one.\n");
        }
    }

}