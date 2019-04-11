import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);
        //Parser parser = new StupidParser();
        Parser parser = new SmartParser(new FileReader(new File(args[0])));
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        Interpreter interp = new StupidInterpreter();
        interp.run(prog, grid);
        System.out.println(prog);
        ioEnv.outGrid.println(grid);
        Program.addGrille(grid);
        prog.eval(new HashMap<String,Integer>());
        ioEnv.outGrid.println(grid);
    }
}
