package pethome;

class Animal {
    private String name;
    private String species;
    private int age;
    private String[] commands;

    public Animal(String name, String species, int age) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.commands = new String[0];
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public String[] getCommands() {
        return commands;
    }

    public void addCommand(String command) {
        // Logic to add a new command
    }
}