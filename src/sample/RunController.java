package sample;

import exceptions.MyException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.ProgramState;
import model.collections.InterfaceBarrierTable;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.collections.MyList;
import model.values.InterfaceValue;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class RunController implements Initializable {
    private controller.Controller myController;

    @FXML
    public TextField numberOfProgramStatesField=new TextField();
    @FXML
    public ListView outputListView=new ListView();
    @FXML
    public ListView threadListView=new ListView();
    @FXML
    public TableView symbolTableView=new TableView();
    @FXML
    public TableView barrierTableView=new TableView();
    @FXML
    public Button oneStepButton=new Button();
    @FXML
    public TableView fileTableView=new TableView();
    @FXML
    public TableView heapTableView=new TableView();
    @FXML
    public ListView exeStackListView=new ListView();
    @FXML
    public TableColumn<Map.Entry<String, InterfaceValue>, String> symbolTableColumnVariable=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<String, InterfaceValue>, Integer> symbolTableColumnValue=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<Integer, Pair<InterfaceValue,MyList<Integer>>>, String> barrierTableColumnVariable=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<Integer, Pair<InterfaceValue,MyList<Integer>>>,Integer> barrierTableColumnValue=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<InterfaceValue, BufferedReader>, InterfaceValue> fileTableColumnId=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<InterfaceValue, BufferedReader>, InterfaceValue> fileTableColumnFilename=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<Integer, InterfaceValue>, Integer> heapTableColumnAddress=new TableColumn<>();
    @FXML
    public TableColumn<Map.Entry<Integer, InterfaceValue>, InterfaceValue> heapTableColumnValue=new TableColumn<>();

    public void setRunController(controller.Controller controller){this.myController=controller;}

    private List<Integer> getProgramStatesIds(List<ProgramState> programStates)
    {
        return programStates.stream().map(ProgramState::getId).collect(Collectors.toList());
    }

    private void populateThreadList()
    {
        List<ProgramState> programStates = myController.getRepository().getProgramList();
        ObservableList<Integer> statesIds = FXCollections.observableArrayList(getProgramStatesIds(programStates));
        threadListView.setItems(statesIds);

        //if (statesIds.size() > 0)
          //  threadListView.getSelectionModel().select(0);
        //System.out.println(myController.getRepository().getProgramList().size());
       }



    private void populateAllLists(ProgramState programState)
    {
        if (programState == null)
            return;

        System.out.println(programState);

        updateSymbolTable(programState);
        updateOutput(programState);
        updateHeapTable(programState);
        updateFileTable(programState);
        updateExeStack(programState);
        updateBarrierTable(programState);
        numberOfProgramStatesField.setText(String.valueOf(myController.getRepository().getProgramList().size()));
    }


    private ProgramState getCurrentProgramState()
    {
        if (threadListView.getFocusModel().getFocusedIndex() == -1)
            return null;
        int index = (int) threadListView.getFocusModel().getFocusedItem();

        return myController.getRepository().getProgramStateById(index);
    }

    private void updateExeStack(ProgramState programState)
    {
        ObservableList list = FXCollections.observableArrayList(programState.getExecutionStack().getAll());
        exeStackListView.setItems(list);
        exeStackListView.refresh();

    }


    private void updateFileTable(ProgramState programState)
    {
        InterfaceDictionary<InterfaceValue, BufferedReader> fileTable = programState.getFileTable();
        Map<InterfaceValue, String> fileTableMap = new HashMap<>();

        for (InterfaceValue key:fileTable.keySet())
        {

                String filename = fileTable.get(key).toString();
                fileTableMap.put(key, filename);
        }

        List<Map.Entry<Integer, String>> fileTableList = new ArrayList(fileTableMap.entrySet());
        ObservableList list = FXCollections.observableArrayList(fileTableList);
        fileTableView.setItems(list);
        fileTableView.refresh();

    }


    private void updateHeapTable(ProgramState programState)
    {
        InterfaceHeap<InterfaceValue> heapTable = programState.getHeap();
        List<Map.Entry<Integer, Integer>> heapTableList = new ArrayList(heapTable.getEntrySet());
        ObservableList list = FXCollections.observableArrayList(heapTableList);
        heapTableView.setItems(list);
        heapTableView.refresh();
    }


    private void updateOutput(ProgramState programState)
    {
        ObservableList list = FXCollections.observableArrayList(programState.getOut().getAll());
        outputListView.setItems(list);
        outputListView.refresh();
    }

    @FXML
    public void executeOneStep()
    {


            if (myController == null)
            {
                Alert errorAlert = new Alert(ERROR);
                errorAlert.setHeaderText("");
                errorAlert.setContentText("Please select a program.");
                errorAlert.showAndWait();
                return;
            }

            boolean programIsFinished = getCurrentProgramState().getExecutionStack().isEmpty();
            if (programIsFinished)
            {
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("");
                errorAlert.setContentText("Program has finished.");
                errorAlert.showAndWait();
                return;
            }
            myController.OneStep();
            populateThreadList();
            populateAllLists(getCurrentProgramState());


    }

    private void updateSymbolTable(ProgramState programState)
    {
        InterfaceDictionary<String, InterfaceValue> symbolTable = programState.getSymbolTable();
        List<Map.Entry<String, Integer>> symbolTableList = new ArrayList(symbolTable.getEntrySet());
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableList));
        symbolTableView.refresh();
    }

    private void updateBarrierTable(ProgramState programState)
    {
        InterfaceBarrierTable<InterfaceValue> barrierTable = programState.getBarrierTable();
        List<Map.Entry<Integer, Pair<InterfaceValue, MyList<Integer>>>> barrierTableList = new ArrayList(barrierTable.getEntrySet());
        barrierTableView.setItems(FXCollections.observableArrayList(barrierTableList));
        barrierTableView.refresh();
    }


    @FXML
    public void changeCurrentProgramState()
    {
        int index =  threadListView.getSelectionModel().getSelectedIndex();
        ProgramState programState = myController.getRepository().getProgramStateById(index);
        populateAllLists(programState);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        symbolTableColumnVariable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symbolTableColumnValue.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue()));

        fileTableColumnId.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        fileTableColumnFilename.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue()));

        heapTableColumnAddress.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getKey()));
        heapTableColumnValue.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue()));
        populateThreadList();
        populateAllLists(getCurrentProgramState());
        threadListView.setOnMouseClicked(mouseEvent -> { changeCurrentProgramState(); });

        oneStepButton.setOnAction(actionEvent -> { executeOneStep(); });
        numberOfProgramStatesField.setText(String.valueOf(myController.getRepository().getProgramList().size()));

        barrierTableColumnVariable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().toString()));
        barrierTableColumnValue.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue()));


    }
}
