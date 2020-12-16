package sample;

import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ProgramState;
import model.collections.MyDictionary;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    private static InterfaceRepository r1,r2,r3,r4,r5,r6,r7,r8,r9,r10;
    private static controller.Controller c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;

    @FXML
    ListView<controller.Controller> myProgramList=new ListView<>();
    @FXML
    Button chooseButton;

    public void setup()
    {
        //int v; v=5;Print(v)
        InterfaceStatement firstExample = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(5))),
                        new PrintStatement(new VariableExpression("v"))));

        ArrayList<ProgramState> firstProgram = new ArrayList<>();
        firstProgram.add(new ProgramState(firstExample));
        r1 = new Repository(firstProgram, "firstLog.txt");
        c1 = new controller.Controller(r1);

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
        r2 = new Repository(secondProgram, "secondLog.txt");
        c2 = new controller.Controller(r2);

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        InterfaceStatement thirdExample = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        ArrayList<ProgramState> thirdProgram = new ArrayList<>();
        thirdProgram.add(new ProgramState(thirdExample));
        r3 = new Repository(thirdProgram, "thirdLog.txt");
        c3 = new controller.Controller(r3);

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
        r4=new Repository(fourthProgram,"fourthLog.txt");
        c4=new controller.Controller(r4);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        InterfaceStatement fifthExample = new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new NewStatement(new StringValue("a"), new VariableExpression("v"))),  new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new VariableExpression("a")))));
        ArrayList<ProgramState> fifthProgram=new ArrayList<>();
        fifthProgram.add(new ProgramState(fifthExample));
        r5=new Repository(fifthProgram,"fifthLog.txt");
        c5=new controller.Controller(r5);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        InterfaceStatement sixthExample = new CompoundStatement( new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement(
                ("v"), new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())))), new NewStatement(new StringValue("a"),
                new VariableExpression("v"))), new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))), new PrintStatement(
                new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                        new ValueExpression(new IntValue(5)),"+")));
        ArrayList<ProgramState> sixthProgram=new ArrayList<>();
        sixthProgram.add(new ProgramState(sixthExample));
        r6=new Repository(sixthProgram,"sixthLog.txt");
        c6=new controller.Controller(r6);

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)
        InterfaceStatement seventhExample =new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"),
                new ValueExpression(new IntValue(20)))), new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))),
                new WriteHeapStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)),"+")));
        ArrayList<ProgramState> seventhProgram=new ArrayList<>();
        seventhProgram.add(new ProgramState(seventhExample));
        r7=new Repository(seventhProgram,"seventhLog.txt");
        c7=new controller.Controller(r7);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        InterfaceStatement eighthExample=new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement(new StringValue("v"),new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewStatement(new StringValue("a"),new VariableExpression("v")),
                                        new CompoundStatement(new NewStatement(new StringValue("v"),new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));


        ArrayList<ProgramState> eighthProgram=new ArrayList<>();
        eighthProgram.add(new ProgramState(eighthExample));
        r8=new Repository(eighthProgram,"eighthLog.txt");
        c8=new controller.Controller(r8);

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        InterfaceStatement ninthExample=new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(
                new AssignmentStatement("v",new ValueExpression(new IntValue(4))),new CompoundStatement(new WhileStatement(
                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new AssignmentStatement( "v",new ArithmeticExpression(new VariableExpression("v"),
                                new ValueExpression(new IntValue(1)),"-")))),new PrintStatement(new VariableExpression("v")))));
        ArrayList<ProgramState> ninthProgram=new ArrayList<>();
        ninthProgram.add(new ProgramState(ninthExample));
        r9=new Repository(ninthProgram,"ninthLog.txt");
        c9=new controller.Controller(r9);

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
        r10=new Repository(tenthProgram,"tenthLog.txt");
        c10=new controller.Controller(r10);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    setup();
    ObservableList<controller.Controller> myData = FXCollections.observableArrayList();
    myData.add(c1);
    myData.add(c2);
    myData.add(c3);
    myData.add(c4);
    myData.add(c5);
    myData.add(c6);
    myData.add(c7);
    myData.add(c8);
    myData.add(c9);
    myData.add(c10);
    myProgramList.setItems(myData);
    //myProgramList.getSelectionModel().selectFirst();
    myProgramList.setFocusTraversable(true);
    chooseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == RunController.class) {
                        try{

                        myProgramList.getFocusModel().getFocusedItem().getOriginalProgram().typecheck(new MyDictionary<>());
                        RunController runController=new RunController();
                        runController.setRunController(myProgramList.getFocusModel().getFocusedItem());
                        return runController;}
                        catch(MyException ex){
                            System.out.println(ex.getMessage());
                        throw new RuntimeException(ex.getMessage());}
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("run.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
