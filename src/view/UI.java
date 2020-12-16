package view;

import controller.Controller;
import exceptions.MyException;
import model.ProgramState;
import model.collections.*;
import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.InterfaceValue;
import repository.InterfaceRepository;
import repository.Repository;


import java.util.ArrayList;
import java.util.Scanner;
/*
public class UI {
    public static void main(String[] args)
    {
        InterfaceRepository repository=new Repository();
        Controller controller=new Controller(repository);

        //int v; v=5;Print(v)
        InterfaceStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(5))),
                        new PrintStatement(new VariableExpression("v"))));
        //int a;int b; a=2+3*5;b=a+1;Print(b)
        InterfaceStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),"*"),"+")),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)),"+")),
                                        new PrintStatement(new VariableExpression("b"))))) ) ;



        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        InterfaceStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        //bool a; a=true;(If a Then int v=2; Print(v) Else int v=3;Print(v)
        /*InterfaceStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                        new IfStatement(new VariableExpression("a"),new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                                        new AssignmentStatement("b",new ValueExpression(new IntValue(2)))),new PrintStatement(new VariableExpression("b"))),
                                        new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                                                new AssignmentStatement("b", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("b"))))));*/
        /*ProgramState program1=new ProgramState(ex1);
        ProgramState program2=new ProgramState(ex2);
        ProgramState program3=new ProgramState(ex3);
        /*ProgramState program4=new ProgramState(ex4);*/


        /*int option;
        Scanner scanner=new Scanner(System.in);
        while(true)
        {
            printMenu();
            option=scanner.nextInt();

            switch (option){

                case 1:
                    controller.addNewProgram(program1);
                    break;
                case 2:
                    controller.addNewProgram(program2);
                    break;
                case 3:
                    controller.addNewProgram(program3);
                    break;


                case 4:
                    try{
                        controller.allSteps();
                    }
                    catch(MyException e){
                        System.out.println(e);}
                    break;
                case 5:
                    return;

            }

        }*/







/*
    }
    public static void printMenu()
    {
        System.out.println("Input a program:\n    1.Ex1:int v; v=5;Print(v)\n    2.Ex2:int a;int b; a=2+3*5;b=a+1;Print(b)\n    3.Ex3:bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)\n4.Complete execution of a program\n5.Exit\n");
    }

}
*/