package repository;

import exceptions.MyException;
import model.ProgramState;

import java.util.ArrayList;

public interface InterfaceRepository {
    //ProgramState getCurrentProgram();
    void addProgram(ProgramState program);
    ProgramState getProgramIndex(int index);
    void logPrgStateExec(ProgramState program) throws MyException;
    ArrayList<ProgramState> getAll();
    int length();
    void clearLog() throws MyException;
    ArrayList<ProgramState> getProgramList();
    ProgramState getProgramStateById(int Id);
    void setProgramList(ArrayList<ProgramState> newRepository);
}
