import java.io.IOException;
import java.io.Reader;

public interface Parser
{
    public Program parseProgram(String exeName, Reader reader) throws IOException;
}
