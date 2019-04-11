public class StupidInterpreter implements Interpreter
{
    public StupidInterpreter() {
    }

    public void run(Program prog, Grid grid) {
        grid.avancer(grid.lire());
        grid.tourner(grid.lire());
        grid.avancer(7);
        grid.tourner(-3);
        grid.avancer(grid.lire());
        grid.tourner(-3);
        grid.avancer(grid.lire());
        grid.tourner(2-1);
        grid.avancer(grid.lire());
        grid.ecrire(grid.lire()+10);
    }
}
