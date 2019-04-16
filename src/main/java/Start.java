public class Start {
    int id;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object t) {
        return this.id == ((Start)t).id;
    }

    public static void main(String[] args) {
        Start t1 = new Start();
        t1.id = 1;
        Start t2 = new Start();
        t2.id = 1;

        if (t1.equals(t2)) System.out.println("True");
        else System.out.println("False");
    }
}
