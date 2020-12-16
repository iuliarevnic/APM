package model.collections;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements InterfaceStack<T> {
    private Stack<T> stack;
    public MyStack(){
        stack=new Stack<T>();
    }
    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public String toString() {
        String result=new String();
        result+="{";
        int size = this.stack.size()-1;

        for(int i=size;i>=0;i--)
        {
            if(i!=0)
            result+=(this.stack.get(i).toString())+"|";
            else result+=(this.stack.get(i).toString())+"}";
        }
        return result;
    }
    @Override
    public String toString1()
    {
        String result=new String();

        int size = this.stack.size()-1;

        for(int i=size;i>=0;i--)
        {

                result+=(this.stack.get(i).toString())+"\n";

        }
        return result;
    }

    @Override
    public List<T> getAll() {
        return stack;
    }
}
