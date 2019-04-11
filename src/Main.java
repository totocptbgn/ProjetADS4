import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        runMain(args);
        runTests(args);
    }

    private static void runMain(String[] args) throws IOException {
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

        Parser parser = new SmartParser(new FileReader(new File(args[0])));
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        ioEnv.outGrid.println(grid);
        Interpreter interp = new SmartInterpreter();

        interp.run(prog, grid);
        System.out.println(prog);
        prog.debug();
        ioEnv.outGrid.println(grid);
    }

    private static void runTests(String[] args) throws IOException {
        String exeName = "Tests";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

        // Faire les tests
    }
}
