package view;

import controller.Controller;
import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.MyDictionary;
import model.types.InterfaceType;


public class RunExample extends Command {
    private Controller controller;
    public RunExample(String key, String description,Controller c) {
        super(key, description);
        this.controller=c;
    }

    @Override
    public void execute() {

          try{
              InterfaceDictionary<String,InterfaceType> assignedTypes=new MyDictionary<>();
              controller.getOriginalProgram().typecheck(assignedTypes);
              controller.allStep();
          }catch(MyException exception)
          {
              System.out.println(exception.getMessage());
          }


    }
}
