package view;

import controller.Controller;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.InterfaceRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {


        //int v; v=5;Print(v)
        InterfaceStatement firstExample = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(5))),
                        new PrintStatement(new VariableExpression("v"))));

        ArrayList<ProgramState> firstProgram = new ArrayList<>();
        firstProgram.add(new ProgramState(firstExample));
        InterfaceRepository firstRepository = new Repository(firstProgram, "firstLog.txt");
        Controller firstController = new Controller(firstRepository);

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        InterfaceStatement secondExample = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), "*"), "+")),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), "+")),
                                        new PrintStatement(new VariableExpression("b"))))));

        ArrayList<ProgramState> secondProgram = new ArrayList<>();
        secondProgram.add(new ProgramState(secondExample));
        InterfaceRepository secondRepository = new Repository(secondProgram, "secondLog.txt");
        Controller secondController = new Controller(secondRepository);

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        InterfaceStatement thirdExample = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        ArrayList<ProgramState> thirdProgram = new ArrayList<>();
        thirdProgram.add(new ProgramState(thirdExample));
        InterfaceRepository thirdRepository = new Repository(thirdProgram, "thirdLog.txt");
        Controller thirdController = new Controller(thirdRepository);

        //string varf; varf="test.in"; openFile(varf); int varc; readFile(varf,varc); print(varc);
        //readFile(varf,varc); print(varc); closeFile(varf);
        InterfaceStatement fourthExample= new CompoundStatement(new VariableDeclarationStatement("varf",new StringType()),
                new CompoundStatement(new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc",new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),new StringValue("varc")),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),new StringValue("varc")),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseFileStatement(new VariableExpression("varf"))))))))));

        ArrayList<ProgramState> fourthProgram=new ArrayList<>();
        fourthProgram.add(new ProgramState(fourthExample));
        InterfaceRepository fourthRepository=new Repository(fourthProgram,"fourthLog.txt");
        Controller fourthController=new Controller(fourthRepository);

        //bool a; a=true;(If a Then int v=2; Print(v) Else int v=3;Print(v)
        /*InterfaceStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                        new IfStatement(new VariableExpression("a"),new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                                        new AssignmentStatement("b",new ValueExpression(new IntValue(2)))),new PrintStatement(new VariableExpression("b"))),
                                        new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                                                new AssignmentStatement("b", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("b"))))));*/

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        InterfaceStatement fifthExample = new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new NewStatement(new StringValue("a"), new VariableExpression("v"))),  new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new VariableExpression("a")))));
        ArrayList<ProgramState> fifthProgram=new ArrayList<>();
        fifthProgram.add(new ProgramState(fifthExample));
        InterfaceRepository fifthRepository=new Repository(fifthProgram,"fifthLog.txt");
        Controller fifthController=new Controller(fifthRepository);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        InterfaceStatement sixthExample = new CompoundStatement( new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement(
                ("v"), new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())))), new NewStatement(new StringValue("a"),
                new VariableExpression("v"))), new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))), new PrintStatement(
                new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                        new ValueExpression(new IntValue(5)),"+")));
        ArrayList<ProgramState> sixthProgram=new ArrayList<>();
        sixthProgram.add(new ProgramState(sixthExample));
        InterfaceRepository sixthRepository=new Repository(sixthProgram,"sixthLog.txt");
        Controller sixthController=new Controller(sixthRepository);

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)
        InterfaceStatement seventhExample =new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"),
                new ValueExpression(new IntValue(20)))), new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))),
                new WriteHeapStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)),"+")));
        ArrayList<ProgramState> seventhProgram=new ArrayList<>();
        seventhProgram.add(new ProgramState(seventhExample));
        InterfaceRepository seventhRepository=new Repository(seventhProgram,"seventhLog.txt");
        Controller seventhController=new Controller(seventhRepository);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        InterfaceStatement eighthExample=new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement(new StringValue("v"),new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewStatement(new StringValue("a"),new VariableExpression("v")),
                                        new CompoundStatement(new NewStatement(new StringValue("v"),new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));


        ArrayList<ProgramState> eighthProgram=new ArrayList<>();
        eighthProgram.add(new ProgramState(eighthExample));
        InterfaceRepository eighthRepository=new Repository(eighthProgram,"eighthLog.txt");
        Controller eighthController=new Controller(eighthRepository);

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        InterfaceStatement ninthExample=new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(
                new AssignmentStatement("v",new ValueExpression(new IntValue(4))),new CompoundStatement(new WhileStatement(
                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new AssignmentStatement( "v",new ArithmeticExpression(new VariableExpression("v"),
                                new ValueExpression(new IntValue(1)),"-")))),new PrintStatement(new VariableExpression("v")))));
        ArrayList<ProgramState> ninthProgram=new ArrayList<>();
        ninthProgram.add(new ProgramState(ninthExample));
        InterfaceRepository ninthRepository=new Repository(ninthProgram,"ninthLog.txt");
        Controller ninthController=new Controller(ninthRepository);

        //int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        InterfaceStatement tenthExample=new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement(new StringValue("a"),new ValueExpression(new IntValue(22))),
                                new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement(new StringValue("a"),new ValueExpression(new IntValue(30))),
                                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

        ArrayList<ProgramState> tenthProgram=new ArrayList<>();
        tenthProgram.add(new ProgramState(tenthExample));
        InterfaceRepository tenthRepository=new Repository(tenthProgram,"tenthLog.txt");
        Controller tenthController=new Controller(tenthRepository);

        InterfaceStatement heapExample=new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement(new StringValue("a"),new ValueExpression(new IntValue(101))),
                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement(new StringValue("a"),new ValueExpression(new IntValue(103))),new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))),
                                new CompoundStatement(new WriteHeapStatement(new StringValue("a"),new ValueExpression(new IntValue(107))),
                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))));

        ArrayList<ProgramState> heapProgram=new ArrayList<>();
        heapProgram.add(new ProgramState(heapExample));
        InterfaceRepository heapRepository=new Repository(heapProgram,"heapExample.txt");
        Controller heapController=new Controller(heapRepository);


        InterfaceStatement statement=new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement(new StringValue("a"),new ValueExpression(new IntValue(101))),
                        new CompoundStatement(new ForkStatement(new WriteHeapStatement(new StringValue("a"),new ValueExpression(new IntValue(103)))),
                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))));

        ArrayList<ProgramState> program=new ArrayList<>();
        program.add(new ProgramState(statement));
        InterfaceRepository repository=new Repository(program,"file.txt");
        Controller controller=new Controller(repository);

        InterfaceStatement g=new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement(new StringValue("v"),new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewStatement(new StringValue("a"),new VariableExpression("v")),
                                        new CompoundStatement(new NewStatement(new StringValue("v"),new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new WriteHeapStatement(new StringValue("a"),new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))))))));

        ArrayList<ProgramState> gg=new ArrayList<>();
        gg.add(new ProgramState(g));
        InterfaceRepository ggg=new Repository(gg,"g.txt");
        Controller gggg=new Controller(ggg);



        InterfaceStatement barrierExample=new CompoundStatement(new VariableDeclarationStatement("v1",new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2",new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3",new ReferenceType(new IntType())),
                                new CompoundStatement(new NewStatement(new StringValue("v1"),new ValueExpression(new IntValue(2))),
                                        new CompoundStatement(new NewStatement(new StringValue("v2"),new ValueExpression(new IntValue(3))),
                                                new CompoundStatement(new NewStatement(new StringValue("v3"),new ValueExpression(new IntValue(4))),
                                                        new CompoundStatement(new NewBarrier("cnt",new ReadHeapExpression(new VariableExpression("v2"))),
                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitBarrierStatement("cnt"),
                                                                        new CompoundStatement(new WriteHeapStatement(new StringValue("v1"),new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)),"*")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))))),
                                                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitBarrierStatement("cnt"),
                                                                                new CompoundStatement(new WriteHeapStatement(new StringValue("v2"),new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(10)),"*")),
                                                                                        new CompoundStatement(new WriteHeapStatement(new StringValue("v2"),new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(10)),"*")),
                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))))))),
                                                                                new CompoundStatement(new AwaitBarrierStatement("cnt"),new PrintStatement(new ReadHeapExpression(new VariableExpression("v3")))))))))))));

        ArrayList<ProgramState> barrierProgram=new ArrayList<>();
        barrierProgram.add(new ProgramState(barrierExample));
        InterfaceRepository barrierRepository=new Repository(barrierProgram,"barrierLog.txt");
        Controller barrierController=new Controller(barrierRepository);



        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));
        textMenu.addCommand(new RunExample("1", firstExample.toString(), firstController));
        textMenu.addCommand(new RunExample("2", secondExample.toString(), secondController));
        textMenu.addCommand(new RunExample("3", thirdExample.toString(), thirdController));
        textMenu.addCommand(new RunExample("4",fourthExample.toString(),fourthController));
        textMenu.addCommand(new RunExample("5",fifthExample.toString(),fifthController));
        textMenu.addCommand(new RunExample("6",sixthExample.toString(),sixthController));
        textMenu.addCommand(new RunExample("7",seventhExample.toString(),seventhController));
        textMenu.addCommand(new RunExample("8",eighthExample.toString(),eighthController));
        textMenu.addCommand(new RunExample("9",ninthExample.toString(),ninthController));
        textMenu.addCommand(new RunExample("10",tenthExample.toString(),tenthController));
        textMenu.addCommand(new RunExample("101",heapExample.toString(),heapController));
        textMenu.addCommand(new RunExample("2991",statement.toString(),controller));
        textMenu.addCommand(new RunExample("1151191",g.toString(),gggg));
        textMenu.addCommand(new RunExample("1189",barrierExample.toString(),barrierController));
        textMenu.show();

    }
}