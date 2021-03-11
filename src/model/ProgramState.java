package model;

import exceptions.MyException;
import model.collections.*;
import model.statements.InterfaceStatement;
import model.values.InterfaceValue;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private InterfaceStack<InterfaceStatement> executionStack;
    private InterfaceDictionary<String, InterfaceValue> symbolTable;
    private InterfaceList<InterfaceValue> out;
    private InterfaceStatement originalProgram;
    private InterfaceDictionary<InterfaceValue, BufferedReader> fileTable;
    private InterfaceHeap<InterfaceValue> heap;
    private int id;
    private int lastId=0;
    private static final AtomicInteger assignedId=new AtomicInteger(0);
    private InterfaceBarrierTable<InterfaceValue> barrierTable;


    public ProgramState(InterfaceStack<InterfaceStatement> stack,InterfaceDictionary<String, InterfaceValue> table,InterfaceList<InterfaceValue> output,InterfaceStatement program,InterfaceDictionary<InterfaceValue,BufferedReader>fileTable,InterfaceHeap<InterfaceValue> heap,InterfaceBarrierTable<InterfaceValue> barrierTable)
    {
        executionStack=stack;
        symbolTable=table;
        out=output;
        this.fileTable=fileTable;
        this.heap=heap;
        this.barrierTable=barrierTable;
        //id=getNextId();
        id=assignedId.incrementAndGet();
        //id=assignedId.getAndIncrement();
        originalProgram=program.deepCopy();
        executionStack.push(originalProgram);
    }
    public ProgramState(InterfaceStatement initialProgram)
    {
        executionStack=new MyStack<InterfaceStatement>();
        symbolTable=new MyDictionary<String,InterfaceValue>();
        out=new MyList<InterfaceValue>();
        fileTable=new MyDictionary<InterfaceValue,BufferedReader>();
        heap=new MyHeap<InterfaceValue>();
        barrierTable=new BarrierTable<>();
        //id=getNextId();
        id=assignedId.incrementAndGet();
        //id=assignedId.getAndIncrement();
        originalProgram=initialProgram.deepCopy();
        executionStack.push(originalProgram);

    }
    public int getNextId()
    {
        this.lastId+=1;
        return lastId;

    }
    public int  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InterfaceStack<InterfaceStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(InterfaceStack<InterfaceStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public InterfaceDictionary<String, InterfaceValue> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(InterfaceDictionary<String, InterfaceValue> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public InterfaceList<InterfaceValue> getOut() {
        return out;
    }

    public void setOut(InterfaceList<InterfaceValue> out) {
        this.out = out;
    }

    public InterfaceStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(InterfaceStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString() {
        return  "Id:\n"+id+"\n"+
                "Execution stack:\n"+executionStack.toString()+"\n"+
                "Symbol table:\n"+symbolTable.toString()+"\n"+
                "Output:\n"+out.toString()+"\n"+
                "File table:\n"+fileTable.toString()+"\n"+
                "Heap table:\n"+heap.toString()+"\n"+
                "Barriertable:\n"+barrierTable.toString()+"\n";
    }
    public String toString1() {
        return  "Id:\n"+id+"\n"+
                "Execution stack:\n"+executionStack.toString1()+"\n"+
                "Symbol table:\n"+symbolTable.toString()+"\n"+
                "Output:\n"+out.toString()+"\n"+
                "File table:\n"+fileTable.toString()+"\n"+
                "Heap table:\n"+heap.toString()+"\n"+
                "Barriertable:\n"+barrierTable.toString()+"\n";
    }
    public InterfaceDictionary<InterfaceValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(InterfaceDictionary<InterfaceValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public InterfaceHeap<InterfaceValue> getHeap() {
        return heap;
    }

    public void setHeap(InterfaceHeap<InterfaceValue> heap) {
        this.heap = heap;
    }

    public Boolean isNotCompleted()
    {
        return !executionStack.isEmpty();
    }
    public ProgramState oneStep()throws MyException
    {
     if(executionStack.isEmpty())
         throw new MyException("Execution stack is empty.");
     InterfaceStatement currentStatement=executionStack.pop();
     return currentStatement.execute(this);
    }


    public InterfaceBarrierTable<InterfaceValue> getBarrierTable() {
        return barrierTable;
    }

    public void setBarrierTable(InterfaceBarrierTable<InterfaceValue> barrierTable) {
        this.barrierTable = barrierTable;
    }


}
