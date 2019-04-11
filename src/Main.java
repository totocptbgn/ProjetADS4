import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);
       
        Parser parser = new SmartParser(new FileReader(new File(args[0])));
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        ioEnv.outGrid.println(grid);
        Interpreter interp = new SmartInterpreter();

        interp.run(prog, grid);
        System.out.println(prog);
        ioEnv.outGrid.println(grid);
    }
}
